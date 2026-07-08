package DAO;

import CONEX.ConexionBD;
import LOGICA.CompraClass;
import LOGICA.DetalleCompraClass;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

public class CompraDAO {

    // LISTAR COMPRAS
    public List<CompraClass> listar() {
        List<CompraClass> compras = new ArrayList<>();
        String sql = "SELECT c.id_compra, c.numero_compra, c.fecha_pedido, c.fecha_recepcion, c.id_proveedor, p.razon_social AS proveedor, c.id_usuario, c.documento_proveedor, c.subtotal, c.descuento, c.igv, c.total, c.estado, c.observaciones, c.motivo_anulacion, c.creado_en, c.actualizado_en "
                + "FROM compras c INNER JOIN proveedores p ON p.id_proveedor = c.id_proveedor ORDER BY c.fecha_pedido DESC";

        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                compras.add(mapearCompra(rs));
            }

        } catch (SQLException e) {
            mostrarError("Error al listar compras", e);
        }

        return compras;
    }

    // BUSCAR COMPRAS
    public List<CompraClass> buscar(String criterio) {
        List<CompraClass> compras = new ArrayList<>();
        String sql = "SELECT c.id_compra, c.numero_compra, c.fecha_pedido, c.fecha_recepcion, c.id_proveedor, p.razon_social AS proveedor, c.id_usuario, c.documento_proveedor, c.subtotal, c.descuento, c.igv, c.total, c.estado, c.observaciones, c.motivo_anulacion, c.creado_en, c.actualizado_en "
                + "FROM compras c INNER JOIN proveedores p ON p.id_proveedor = c.id_proveedor "
                + "WHERE c.numero_compra LIKE ? OR c.documento_proveedor LIKE ? OR p.razon_social LIKE ? OR c.estado LIKE ? ORDER BY c.fecha_pedido DESC";

        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            String filtro = "%" + criterio + "%";
            stmt.setString(1, filtro);
            stmt.setString(2, filtro);
            stmt.setString(3, filtro);
            stmt.setString(4, filtro);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    compras.add(mapearCompra(rs));
                }
            }

        } catch (SQLException e) {
            mostrarError("Error al buscar compras", e);
        }

        return compras;
    }

    // REGISTRAR COMPRA CON DETALLES
    public boolean registrarCompra(CompraClass compra) {
        String sqlCompra = "INSERT INTO compras (numero_compra, fecha_pedido, fecha_recepcion, id_proveedor, id_usuario, documento_proveedor, subtotal, descuento, igv, total, estado, observaciones) VALUES (?, NOW(), ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        String sqlDetalle = "INSERT INTO detalle_compras (id_compra, id_medicamento, id_lote, numero_lote, fecha_vencimiento, cantidad, costo_unitario, descuento, subtotal) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        String sqlStock = "UPDATE medicamentos SET stock = stock + ?, precio_compra = ? WHERE id_medicamento = ?";
        Connection conn = null;

        try {
            conn = ConexionBD.getConnection();
            conn.setAutoCommit(false);

            try (PreparedStatement stmtCompra = conn.prepareStatement(sqlCompra, Statement.RETURN_GENERATED_KEYS)) {
                stmtCompra.setString(1, compra.getNumeroCompra());
                stmtCompra.setTimestamp(2, compra.getFechaRecepcion());
                stmtCompra.setInt(3, compra.getIdProveedor());
                stmtCompra.setInt(4, compra.getIdUsuario());
                stmtCompra.setString(5, compra.getDocumentoProveedor());
                stmtCompra.setBigDecimal(6, compra.getSubtotal());
                stmtCompra.setBigDecimal(7, compra.getDescuento());
                stmtCompra.setBigDecimal(8, compra.getIgv());
                stmtCompra.setBigDecimal(9, compra.getTotal());
                stmtCompra.setString(10, compra.getEstado() == null ? "Pendiente" : compra.getEstado());
                stmtCompra.setString(11, compra.getObservaciones());
                stmtCompra.executeUpdate();

                try (ResultSet rs = stmtCompra.getGeneratedKeys()) {
                    if (rs.next()) {
                        compra.setIdCompra(rs.getLong(1));
                    }
                }
            }

            for (DetalleCompraClass detalle : compra.getDetalles()) {
                try (PreparedStatement stmtDetalle = conn.prepareStatement(sqlDetalle)) {
                    stmtDetalle.setLong(1, compra.getIdCompra());
                    stmtDetalle.setInt(2, detalle.getIdMedicamento());
                    setNullableLong(stmtDetalle, 3, detalle.getIdLote());
                    stmtDetalle.setString(4, detalle.getNumeroLote());
                    stmtDetalle.setDate(5, detalle.getFechaVencimiento());
                    stmtDetalle.setInt(6, detalle.getCantidad());
                    stmtDetalle.setBigDecimal(7, detalle.getCostoUnitario());
                    stmtDetalle.setBigDecimal(8, detalle.getDescuento());
                    stmtDetalle.setBigDecimal(9, detalle.getSubtotal());
                    stmtDetalle.executeUpdate();
                }

                try (PreparedStatement stmtStock = conn.prepareStatement(sqlStock)) {
                    stmtStock.setInt(1, detalle.getCantidad());
                    stmtStock.setBigDecimal(2, detalle.getCostoUnitario());
                    stmtStock.setInt(3, detalle.getIdMedicamento());
                    stmtStock.executeUpdate();
                }
            }

            conn.commit();
            return true;

        } catch (SQLException e) {
            rollback(conn);
            mostrarError("Error al registrar la compra", e);
            return false;
        } finally {
            cerrarConexion(conn);
        }
    }

    // ANULAR COMPRA
    public boolean anularCompra(long idCompra, String motivo) {
        String sql = "UPDATE compras SET estado = 'Anulada', motivo_anulacion = ? WHERE id_compra = ? AND estado <> 'Anulada'";

        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, motivo);
            stmt.setLong(2, idCompra);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            mostrarError("Error al anular la compra", e);
            return false;
        }
    }

    private CompraClass mapearCompra(ResultSet rs) throws SQLException {
        CompraClass compra = new CompraClass();
        compra.setIdCompra(rs.getLong("id_compra"));
        compra.setNumeroCompra(rs.getString("numero_compra"));
        compra.setFechaPedido(rs.getTimestamp("fecha_pedido"));
        compra.setFechaRecepcion(rs.getTimestamp("fecha_recepcion"));
        compra.setIdProveedor(rs.getInt("id_proveedor"));
        compra.setProveedorNombre(rs.getString("proveedor"));
        compra.setIdUsuario(rs.getInt("id_usuario"));
        compra.setDocumentoProveedor(rs.getString("documento_proveedor"));
        compra.setSubtotal(rs.getBigDecimal("subtotal"));
        compra.setDescuento(rs.getBigDecimal("descuento"));
        compra.setIgv(rs.getBigDecimal("igv"));
        compra.setTotal(rs.getBigDecimal("total"));
        compra.setEstado(rs.getString("estado"));
        compra.setObservaciones(rs.getString("observaciones"));
        compra.setMotivoAnulacion(rs.getString("motivo_anulacion"));
        compra.setCreadoEn(rs.getTimestamp("creado_en"));
        compra.setActualizadoEn(rs.getTimestamp("actualizado_en"));
        return compra;
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
