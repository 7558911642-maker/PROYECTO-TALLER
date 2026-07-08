package DAO;

import CONEX.ConexionBD;
import LOGICA.LoteMedicamentoClass;
import LOGICA.MovimientoInventarioClass;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

public class InventarioDAO {

    // LISTAR INVENTARIO DESDE LA VISTA
    public List<Object[]> listarInventario() {
        List<Object[]> inventario = new ArrayList<>();
        String sql = "SELECT id_medicamento, codigo, nombre_comercial, categoria, laboratorio, presentacion, precio_compra, precio_venta, stock, stock_minimo, stock_maximo, estado_stock, proximo_vencimiento, estado FROM vw_inventario ORDER BY nombre_comercial";

        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                inventario.add(new Object[]{rs.getInt("id_medicamento"), rs.getString("codigo"), rs.getString("nombre_comercial"), rs.getString("categoria"), rs.getString("laboratorio"), rs.getString("presentacion"), rs.getBigDecimal("precio_compra"), rs.getBigDecimal("precio_venta"), rs.getInt("stock"), rs.getInt("stock_minimo"), rs.getObject("stock_maximo"), rs.getString("estado_stock"), rs.getDate("proximo_vencimiento"), rs.getString("estado")});
            }

        } catch (SQLException e) {
            mostrarError("Error al listar inventario", e);
        }

        return inventario;
    }

    // LISTAR ALERTAS DE INVENTARIO
    public List<Object[]> listarAlertasInventario() {
        List<Object[]> alertas = new ArrayList<>();
        String sql = "SELECT id_medicamento, id_lote, codigo, medicamento, numero_lote, stock_total, stock_lote, fecha_vencimiento, tipo_alerta, prioridad FROM vw_alertas_inventario ORDER BY prioridad, fecha_vencimiento";

        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                alertas.add(new Object[]{rs.getInt("id_medicamento"), rs.getLong("id_lote"), rs.getString("codigo"), rs.getString("medicamento"), rs.getString("numero_lote"), rs.getInt("stock_total"), rs.getInt("stock_lote"), rs.getDate("fecha_vencimiento"), rs.getString("tipo_alerta"), rs.getString("prioridad")});
            }

        } catch (SQLException e) {
            mostrarError("Error al listar alertas de inventario", e);
        }

        return alertas;
    }

    // LISTAR LOTES DE MEDICAMENTO
    public List<LoteMedicamentoClass> listarLotesPorMedicamento(int idMedicamento) {
        List<LoteMedicamentoClass> lotes = new ArrayList<>();
        String sql = "SELECT id_lote, id_medicamento, numero_lote, fecha_fabricacion, fecha_vencimiento, stock_lote, costo_unitario, estado, creado_en, actualizado_en FROM lotes_medicamentos WHERE id_medicamento = ? ORDER BY fecha_vencimiento";

        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idMedicamento);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    lotes.add(mapearLote(rs));
                }
            }

        } catch (SQLException e) {
            mostrarError("Error al listar lotes", e);
        }

        return lotes;
    }

    // REGISTRAR MOVIMIENTO DE INVENTARIO
    public boolean registrarMovimiento(MovimientoInventarioClass movimiento) {
        String sql = "INSERT INTO movimientos_inventario (id_medicamento, id_lote, tipo_movimiento, cantidad, stock_anterior, stock_nuevo, tabla_referencia, id_referencia, id_usuario, observacion, fecha) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, NOW())";

        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, movimiento.getIdMedicamento());
            setNullableLong(stmt, 2, movimiento.getIdLote());
            stmt.setString(3, movimiento.getTipoMovimiento());
            stmt.setInt(4, movimiento.getCantidad());
            stmt.setInt(5, movimiento.getStockAnterior());
            stmt.setInt(6, movimiento.getStockNuevo());
            stmt.setString(7, movimiento.getTablaReferencia() == null ? "NINGUNA" : movimiento.getTablaReferencia());
            setNullableLong(stmt, 8, movimiento.getIdReferencia());
            stmt.setInt(9, movimiento.getIdUsuario());
            stmt.setString(10, movimiento.getObservacion());

            boolean registrado = stmt.executeUpdate() > 0;
            if (registrado) {
                try (ResultSet rs = stmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        movimiento.setIdMovimiento(rs.getLong(1));
                    }
                }
            }
            return registrado;

        } catch (SQLException e) {
            mostrarError("Error al registrar movimiento de inventario", e);
            return false;
        }
    }

    // AJUSTAR STOCK DE MEDICAMENTO
    public boolean ajustarStock(int idMedicamento, int nuevoStock, int idUsuario, String observacion) {
        String sqlBuscar = "SELECT stock FROM medicamentos WHERE id_medicamento = ?";
        String sqlActualizar = "UPDATE medicamentos SET stock = ? WHERE id_medicamento = ?";
        Connection conn = null;

        try {
            conn = ConexionBD.getConnection();
            conn.setAutoCommit(false);

            int stockAnterior;
            try (PreparedStatement stmtBuscar = conn.prepareStatement(sqlBuscar)) {
                stmtBuscar.setInt(1, idMedicamento);
                try (ResultSet rs = stmtBuscar.executeQuery()) {
                    if (!rs.next()) {
                        throw new SQLException("No existe el medicamento indicado.");
                    }
                    stockAnterior = rs.getInt("stock");
                }
            }

            try (PreparedStatement stmtActualizar = conn.prepareStatement(sqlActualizar)) {
                stmtActualizar.setInt(1, nuevoStock);
                stmtActualizar.setInt(2, idMedicamento);
                stmtActualizar.executeUpdate();
            }

            MovimientoInventarioClass movimiento = new MovimientoInventarioClass();
            movimiento.setIdMedicamento(idMedicamento);
            movimiento.setTipoMovimiento(nuevoStock >= stockAnterior ? "AJUSTE_ENTRADA" : "AJUSTE_SALIDA");
            movimiento.setCantidad(Math.abs(nuevoStock - stockAnterior));
            movimiento.setStockAnterior(stockAnterior);
            movimiento.setStockNuevo(nuevoStock);
            movimiento.setTablaReferencia("AJUSTE");
            movimiento.setIdUsuario(idUsuario);
            movimiento.setObservacion(observacion);
            registrarMovimientoEnConexion(conn, movimiento);

            conn.commit();
            return true;

        } catch (SQLException e) {
            rollback(conn);
            mostrarError("Error al ajustar stock", e);
            return false;
        } finally {
            cerrarConexion(conn);
        }
    }

    private void registrarMovimientoEnConexion(Connection conn, MovimientoInventarioClass m) throws SQLException {
        String sql = "INSERT INTO movimientos_inventario (id_medicamento, id_lote, tipo_movimiento, cantidad, stock_anterior, stock_nuevo, tabla_referencia, id_referencia, id_usuario, observacion, fecha) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, NOW())";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, m.getIdMedicamento());
            setNullableLong(stmt, 2, m.getIdLote());
            stmt.setString(3, m.getTipoMovimiento());
            stmt.setInt(4, m.getCantidad());
            stmt.setInt(5, m.getStockAnterior());
            stmt.setInt(6, m.getStockNuevo());
            stmt.setString(7, m.getTablaReferencia());
            setNullableLong(stmt, 8, m.getIdReferencia());
            stmt.setInt(9, m.getIdUsuario());
            stmt.setString(10, m.getObservacion());
            stmt.executeUpdate();
        }
    }

    private LoteMedicamentoClass mapearLote(ResultSet rs) throws SQLException {
        LoteMedicamentoClass lote = new LoteMedicamentoClass();
        lote.setIdLote(rs.getLong("id_lote"));
        lote.setIdMedicamento(rs.getInt("id_medicamento"));
        lote.setNumeroLote(rs.getString("numero_lote"));
        lote.setFechaFabricacion(rs.getDate("fecha_fabricacion"));
        lote.setFechaVencimiento(rs.getDate("fecha_vencimiento"));
        lote.setStockLote(rs.getInt("stock_lote"));
        lote.setCostoUnitario(rs.getBigDecimal("costo_unitario"));
        lote.setEstado(rs.getString("estado"));
        lote.setCreadoEn(rs.getTimestamp("creado_en"));
        lote.setActualizadoEn(rs.getTimestamp("actualizado_en"));
        return lote;
    }

    private void setNullableLong(PreparedStatement stmt, int index, Long value) throws SQLException {
        if (value == null || value <= 0) {
            stmt.setNull(index, java.sql.Types.BIGINT);
        } else {
            stmt.setLong(index, value);
        }
    }

    private void rollback(Connection conn) {
        if (conn != null) {
            try {
                conn.rollback();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private void cerrarConexion(Connection conn) {
        if (conn != null) {
            try {
                conn.setAutoCommit(true);
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private void mostrarError(String mensaje, SQLException e) {
        JOptionPane.showMessageDialog(null, mensaje + ": " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        e.printStackTrace();
    }
}
