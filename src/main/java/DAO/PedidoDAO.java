package DAO;

import CONEX.ConexionBD;
import LOGICA.DetallePedidoClass;
import LOGICA.DetalleVentaClass;
import LOGICA.PagoVentaClass;
import LOGICA.PedidoClass;
import LOGICA.VentaClass;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

public class PedidoDAO {

    // REGISTRAR VENTA DESDE LAS CLASES NUEVAS
    public boolean registrarVenta(VentaClass venta) {
        String sqlVenta = "INSERT INTO ventas (numero_venta, id_serie, numero_comprobante, fecha, id_cliente, id_usuario, cliente_tipo, cliente_documento, cliente_nombre, cliente_direccion, subtotal, descuento, porcentaje_igv, igv, total, efectivo, vuelto, estado, observaciones) "
                + "VALUES (?, ?, ?, NOW(), ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        String sqlDetalle = "INSERT INTO detalle_ventas (id_venta, id_medicamento, id_lote, cantidad, precio_unitario, costo_unitario, descuento, subtotal) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        String sqlStockMedicamento = "UPDATE medicamentos SET stock = stock - ? WHERE id_medicamento = ? AND stock >= ?";
        String sqlStockLote = "UPDATE lotes_medicamentos SET stock_lote = stock_lote - ? WHERE id_lote = ? AND stock_lote >= ?";
        String sqlPago = "INSERT INTO pagos_venta (id_venta, id_metodo_pago, monto, referencia, estado, fecha_pago) VALUES (?, ?, ?, ?, ?, NOW())";
        String sqlCorrelativo = "UPDATE series_comprobantes SET correlativo_actual = correlativo_actual + 1 WHERE id_serie = ?";

        Connection conn = null;

        try {
            conn = ConexionBD.getConnection();
            conn.setAutoCommit(false);

            try (PreparedStatement stmtVenta = conn.prepareStatement(sqlVenta, Statement.RETURN_GENERATED_KEYS)) {
                stmtVenta.setString(1, venta.getNumeroVenta());
                stmtVenta.setInt(2, venta.getIdSerie());
                stmtVenta.setInt(3, venta.getNumeroComprobante());
                stmtVenta.setInt(4, venta.getIdCliente());
                stmtVenta.setInt(5, venta.getIdUsuario());
                stmtVenta.setString(6, venta.getClienteTipo());
                stmtVenta.setString(7, venta.getClienteDocumento());
                stmtVenta.setString(8, venta.getClienteNombre());
                stmtVenta.setString(9, venta.getClienteDireccion());
                stmtVenta.setBigDecimal(10, venta.getSubtotal());
                stmtVenta.setBigDecimal(11, venta.getDescuento());
                stmtVenta.setBigDecimal(12, venta.getPorcentajeIgv());
                stmtVenta.setBigDecimal(13, venta.getIgv());
                stmtVenta.setBigDecimal(14, venta.getTotal());
                stmtVenta.setBigDecimal(15, venta.getEfectivo());
                stmtVenta.setBigDecimal(16, venta.getVuelto());
                stmtVenta.setString(17, venta.getEstado() == null ? "Pagada" : venta.getEstado());
                stmtVenta.setString(18, venta.getObservaciones());
                stmtVenta.executeUpdate();

                try (ResultSet rs = stmtVenta.getGeneratedKeys()) {
                    if (rs.next()) {
                        venta.setIdVenta(rs.getLong(1));
                    }
                }
            }

            for (DetalleVentaClass detalle : venta.getDetalles()) {
                try (PreparedStatement stmtDetalle = conn.prepareStatement(sqlDetalle)) {
                    stmtDetalle.setLong(1, venta.getIdVenta());
                    stmtDetalle.setInt(2, detalle.getIdMedicamento());
                    setNullableLong(stmtDetalle, 3, detalle.getIdLote());
                    stmtDetalle.setInt(4, detalle.getCantidad());
                    stmtDetalle.setBigDecimal(5, detalle.getPrecioUnitario());
                    stmtDetalle.setBigDecimal(6, detalle.getCostoUnitario());
                    stmtDetalle.setBigDecimal(7, detalle.getDescuento());
                    stmtDetalle.setBigDecimal(8, detalle.getSubtotal());
                    stmtDetalle.executeUpdate();
                }

                try (PreparedStatement stmtStock = conn.prepareStatement(sqlStockMedicamento)) {
                    stmtStock.setInt(1, detalle.getCantidad());
                    stmtStock.setInt(2, detalle.getIdMedicamento());
                    stmtStock.setInt(3, detalle.getCantidad());
                    if (stmtStock.executeUpdate() == 0) {
                        throw new SQLException("Stock insuficiente para el medicamento con ID " + detalle.getIdMedicamento());
                    }
                }

                if (detalle.getIdLote() != null) {
                    try (PreparedStatement stmtLote = conn.prepareStatement(sqlStockLote)) {
                        stmtLote.setInt(1, detalle.getCantidad());
                        stmtLote.setLong(2, detalle.getIdLote());
                        stmtLote.setInt(3, detalle.getCantidad());
                        if (stmtLote.executeUpdate() == 0) {
                            throw new SQLException("Stock insuficiente para el lote con ID " + detalle.getIdLote());
                        }
                    }
                }
            }

            for (PagoVentaClass pago : venta.getPagos()) {
                try (PreparedStatement stmtPago = conn.prepareStatement(sqlPago)) {
                    stmtPago.setLong(1, venta.getIdVenta());
                    stmtPago.setInt(2, pago.getIdMetodoPago());
                    stmtPago.setBigDecimal(3, pago.getMonto());
                    stmtPago.setString(4, pago.getReferencia());
                    stmtPago.setString(5, pago.getEstado() == null ? "Aprobado" : pago.getEstado());
                    stmtPago.executeUpdate();
                }
            }

            try (PreparedStatement stmtCorr = conn.prepareStatement(sqlCorrelativo)) {
                stmtCorr.setInt(1, venta.getIdSerie());
                stmtCorr.executeUpdate();
            }

            conn.commit();
            return true;

        } catch (SQLException e) {
            rollback(conn);
            mostrarError("Error al registrar la venta", e);
            return false;
        } finally {
            cerrarConexion(conn);
        }
    }

    // REGISTRAR VENTA DESDE LAS CLASES ANTIGUAS DEL PROYECTO
    public boolean registrarVenta(PedidoClass pedido, List<DetallePedidoClass> detalles) {
        VentaClass venta = new VentaClass();
        venta.setNumeroVenta("VTA-" + System.currentTimeMillis());
        venta.setIdSerie(1);
        venta.setNumeroComprobante(obtenerSiguienteCorrelativo(1));
        venta.setIdCliente(1);
        venta.setIdUsuario(pedido.getIdEmpleado() > 0 ? pedido.getIdEmpleado() : 1);
        venta.setClienteTipo("PERSONA_NATURAL");
        venta.setClienteDocumento("00000000");
        venta.setClienteNombre("Consumidor Final");
        venta.setClienteDireccion(null);
        venta.setDescuento(BigDecimal.ZERO);
        venta.setPorcentajeIgv(new BigDecimal("18.00"));
        venta.setEstado("Pagada");
        venta.setObservaciones("Venta registrada desde clase PedidoClass por compatibilidad.");

        BigDecimal total = BigDecimal.ZERO;
        for (DetallePedidoClass d : detalles) {
            DetalleVentaClass detalle = new DetalleVentaClass();
            detalle.setIdMedicamento(d.getIdProducto());
            detalle.setCantidad(d.getCantidad());
            detalle.setPrecioUnitario(d.getPrecioUnidad());
            detalle.setCostoUnitario(BigDecimal.ZERO);
            detalle.setDescuento(BigDecimal.ZERO);
            detalle.setSubtotal(d.getSubtotal());
            total = total.add(d.getSubtotal());
            venta.agregarDetalle(detalle);
        }

        venta.setTotal(total);
        venta.setSubtotal(total.divide(new BigDecimal("1.18"), 2, java.math.RoundingMode.HALF_UP));
        venta.setIgv(total.subtract(venta.getSubtotal()));
        venta.setEfectivo(pedido.getMontoPagado() == null ? total : pedido.getMontoPagado());
        venta.setVuelto(venta.getEfectivo().subtract(total));

        PagoVentaClass pago = new PagoVentaClass();
        pago.setIdMetodoPago(1);
        pago.setMonto(total);
        pago.setEstado("Aprobado");
        venta.agregarPago(pago);

        return registrarVenta(venta);
    }

    // ANULAR VENTA
    public boolean anularVenta(int idVenta) {
        String sql = "UPDATE ventas SET estado = 'Anulada', fecha_anulacion = NOW(), motivo_anulacion = 'Anulación desde el sistema' WHERE id_venta = ? AND estado <> 'Anulada'";

        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idVenta);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            mostrarError("Error al anular la venta", e);
            return false;
        }
    }

    // BUSCAR VENTAS PARA ANULAR
    public List<Object[]> buscarPedidosParaAnular(String criterio) {
        List<Object[]> ventas = new ArrayList<>();
        String sql = "SELECT id_venta, numero_venta, comprobante, fecha, cliente_nombre, total, estado FROM vw_ventas_resumen "
                + "WHERE numero_venta LIKE ? OR comprobante LIKE ? OR cliente_nombre LIKE ? OR cliente_documento LIKE ? ORDER BY fecha DESC";

        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            String filtro = "%" + criterio + "%";
            stmt.setString(1, filtro);
            stmt.setString(2, filtro);
            stmt.setString(3, filtro);
            stmt.setString(4, filtro);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    ventas.add(new Object[]{rs.getLong("id_venta"), rs.getString("numero_venta"), rs.getString("comprobante"), rs.getTimestamp("fecha"), rs.getString("cliente_nombre"), rs.getBigDecimal("total"), rs.getString("estado")});
                }
            }

        } catch (SQLException e) {
            mostrarError("Error al buscar ventas", e);
        }

        return ventas;
    }

    // CONSULTAR VENTAS POR FECHAS
    public List<Object[]> consultarVentasPorFechas(java.util.Date fechaInicio, java.util.Date fechaFin) {
        List<Object[]> ventas = new ArrayList<>();
        String sql = "SELECT id_venta, numero_venta, comprobante, fecha, cliente_nombre, nombre_usuario, subtotal, descuento, igv, total, estado FROM vw_ventas_resumen WHERE DATE(fecha) BETWEEN ? AND ? ORDER BY fecha DESC";

        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setDate(1, new java.sql.Date(fechaInicio.getTime()));
            stmt.setDate(2, new java.sql.Date(fechaFin.getTime()));

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    ventas.add(new Object[]{rs.getLong("id_venta"), rs.getString("numero_venta"), rs.getString("comprobante"), rs.getTimestamp("fecha"), rs.getString("cliente_nombre"), rs.getString("nombre_usuario"), rs.getBigDecimal("subtotal"), rs.getBigDecimal("descuento"), rs.getBigDecimal("igv"), rs.getBigDecimal("total"), rs.getString("estado")});
                }
            }

        } catch (SQLException e) {
            mostrarError("Error al consultar ventas por fechas", e);
        }

        return ventas;
    }

    // OBTENER REPORTE DIARIO DE VENTAS
    public List<Object[]> obtenerReporteDiario() {
        List<Object[]> reporte = new ArrayList<>();
        String sql = "SELECT fecha, cantidad_comprobantes, total_ventas, ventas_anuladas, monto_anulado, ganancia_bruta FROM vw_reporte_diario_ventas ORDER BY fecha DESC";

        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                reporte.add(new Object[]{rs.getDate("fecha"), rs.getInt("cantidad_comprobantes"), rs.getBigDecimal("total_ventas"), rs.getInt("ventas_anuladas"), rs.getBigDecimal("monto_anulado"), rs.getBigDecimal("ganancia_bruta")});
            }

        } catch (SQLException e) {
            mostrarError("Error al obtener reporte diario", e);
        }

        return reporte;
    }

    // OBTENER TOP 5 MEDICAMENTOS MÁS VENDIDOS
    public List<Object[]> obtenerTop5Medicamentos() {
        List<Object[]> top = new ArrayList<>();
        String sql = "SELECT id_medicamento, codigo, nombre_comercial, unidades_vendidas, ingreso_total FROM vw_medicamentos_mas_vendidos ORDER BY unidades_vendidas DESC LIMIT 5";

        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                top.add(new Object[]{rs.getInt("id_medicamento"), rs.getString("codigo"), rs.getString("nombre_comercial"), rs.getInt("unidades_vendidas"), rs.getBigDecimal("ingreso_total")});
            }

        } catch (SQLException e) {
            mostrarError("Error al obtener top de medicamentos", e);
        }

        return top;
    }

    // OBTENER ESTADÍSTICAS DE CLIENTES
    public List<Object[]> obtenerEstadisticasClientes() {
        List<Object[]> clientes = new ArrayList<>();
        String sql = "SELECT id_cliente, tipo_cliente, numero_documento, nombre_cliente, total_compras, total_consumo, ultima_compra FROM vw_clientes_frecuentes ORDER BY total_consumo DESC";

        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                clientes.add(new Object[]{rs.getInt("id_cliente"), rs.getString("tipo_cliente"), rs.getString("numero_documento"), rs.getString("nombre_cliente"), rs.getInt("total_compras"), rs.getBigDecimal("total_consumo"), rs.getTimestamp("ultima_compra")});
            }

        } catch (SQLException e) {
            mostrarError("Error al obtener estadísticas de clientes", e);
        }

        return clientes;
    }

    // BUSCAR VENTA POR ID
    public Object[] buscarVentaPorId(int idVenta) {
        String sql = "SELECT id_venta, numero_venta, comprobante, fecha, cliente_nombre, subtotal, descuento, igv, total, estado FROM vw_ventas_resumen WHERE id_venta = ?";

        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idVenta);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Object[]{rs.getLong("id_venta"), rs.getString("numero_venta"), rs.getString("comprobante"), rs.getTimestamp("fecha"), rs.getString("cliente_nombre"), rs.getBigDecimal("subtotal"), rs.getBigDecimal("descuento"), rs.getBigDecimal("igv"), rs.getBigDecimal("total"), rs.getString("estado")};
                }
            }

        } catch (SQLException e) {
            mostrarError("Error al buscar la venta", e);
        }

        return null;
    }

    // BUSCAR DETALLE DE VENTA
    public List<Object[]> buscarDetalleVenta(int idVenta) {
        List<Object[]> detalles = new ArrayList<>();
        String sql = "SELECT dv.id_detalle_venta, dv.id_medicamento, m.codigo, m.nombre_comercial, dv.cantidad, dv.precio_unitario, dv.costo_unitario, dv.descuento, dv.subtotal "
                + "FROM detalle_ventas dv INNER JOIN medicamentos m ON m.id_medicamento = dv.id_medicamento WHERE dv.id_venta = ? ORDER BY dv.id_detalle_venta";

        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idVenta);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    detalles.add(new Object[]{rs.getLong("id_detalle_venta"), rs.getInt("id_medicamento"), rs.getString("codigo"), rs.getString("nombre_comercial"), rs.getInt("cantidad"), rs.getBigDecimal("precio_unitario"), rs.getBigDecimal("costo_unitario"), rs.getBigDecimal("descuento"), rs.getBigDecimal("subtotal")});
                }
            }

        } catch (SQLException e) {
            mostrarError("Error al buscar detalle de venta", e);
        }

        return detalles;
    }

    // ACTUALIZAR DETALLE DE VENTA
    public boolean actualizarDetalleVenta(int idMedicamento, int cantidad, BigDecimal subtotal) {
        String sql = "UPDATE detalle_ventas SET cantidad = ?, subtotal = ? WHERE id_medicamento = ?";

        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, cantidad);
            stmt.setBigDecimal(2, subtotal);
            stmt.setInt(3, idMedicamento);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            mostrarError("Error al actualizar detalle de venta", e);
            return false;
        }
    }

    // OBTENER SIGUIENTE CORRELATIVO DE UNA SERIE
    public int obtenerSiguienteCorrelativo(int idSerie) {
        String sql = "SELECT correlativo_actual + 1 AS siguiente FROM series_comprobantes WHERE id_serie = ?";

        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idSerie);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("siguiente");
                }
            }

        } catch (SQLException e) {
            mostrarError("Error al obtener correlativo", e);
        }

        return 1;
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
