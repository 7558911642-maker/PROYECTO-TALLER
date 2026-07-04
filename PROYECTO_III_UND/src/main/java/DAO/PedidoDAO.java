package DAO;

import CONEX.ConexionBD;
import LOGICA.DetallePedidoClass;
import LOGICA.PedidoClass;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class PedidoDAO {

    public boolean registrarVenta(PedidoClass pedido, List<DetallePedidoClass> detalles) {
        String sqlVenta = "INSERT INTO ventas (numero_venta, fecha, id_cliente, id_usuario, total, efectivo, vuelto, estado) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        String sqlDetalle = "INSERT INTO detalle_ventas (id_venta, id_medicamento, cantidad, precio_unitario, subtotal) VALUES (?, ?, ?, ?, ?)";
        String sqlStock = "UPDATE medicamentos SET stock = stock - ? WHERE id_medicamento = ?";

        Connection cn = null;
        PreparedStatement pstVenta = null;
        PreparedStatement pstDetalle = null;
        PreparedStatement pstStock = null;
        ResultSet rs = null;

        try {
            cn = ConexionBD.getConnection();
            cn.setAutoCommit(false);

            pstVenta = cn.prepareStatement(sqlVenta, Statement.RETURN_GENERATED_KEYS);
            pstVenta.setString(1, "VTA-" + System.currentTimeMillis());
            pstVenta.setTimestamp(2, new java.sql.Timestamp(pedido.getFechaPedido().getTime()));
            pstVenta.setInt(3, pedido.getIdCliente());
            pstVenta.setInt(4, pedido.getIdEmpleado());
            pstVenta.setBigDecimal(5, pedido.getMontoPagado());
            pstVenta.setBigDecimal(6, pedido.getMontoPagado());
            pstVenta.setBigDecimal(7, java.math.BigDecimal.ZERO);
            pstVenta.setString(8, pedido.getEstado() != null ? pedido.getEstado() : "Pagada");

            int filasAfectadas = pstVenta.executeUpdate();
            if (filasAfectadas == 0) {
                throw new SQLException("Error: No se pudo registrar la cabecera de la venta.");
            }

            rs = pstVenta.getGeneratedKeys();
            int idVentaGenerado = 0;
            if (rs.next()) {
                idVentaGenerado = rs.getInt(1);
            }

            pstDetalle = cn.prepareStatement(sqlDetalle);
            pstStock = cn.prepareStatement(sqlStock);

            for (DetallePedidoClass det : detalles) {
                java.math.BigDecimal subtotal = det.getPrecioUnidad().multiply(new java.math.BigDecimal(det.getCantidad()));
                pstDetalle.setInt(1, idVentaGenerado);
                pstDetalle.setInt(2, det.getIdProducto());
                pstDetalle.setInt(3, det.getCantidad());
                pstDetalle.setBigDecimal(4, det.getPrecioUnidad());
                pstDetalle.setBigDecimal(5, subtotal);
                pstDetalle.executeUpdate();

                pstStock.setInt(1, det.getCantidad());
                pstStock.setInt(2, det.getIdProducto());
                pstStock.executeUpdate();
            }

            cn.commit();
            return true;

        } catch (SQLException e) {
            System.out.println("Error en la transacción de venta: " + e.getMessage());
            if (cn != null) {
                try {
                    cn.rollback();
                } catch (SQLException ex) {
                    System.out.println("Error al ejecutar rollback: " + ex.getMessage());
                }
            }
            return false;
        } finally {
            try {
                if (rs != null) rs.close();
                if (pstVenta != null) pstVenta.close();
                if (pstDetalle != null) pstDetalle.close();
                if (pstStock != null) pstStock.close();
                if (cn != null) cn.close();
            } catch (SQLException e) {
                System.out.println("Error al cerrar recursos: " + e.getMessage());
            }
        }
    }

    public boolean anularVenta(int idPedido) {
        String sqlCabecera = "UPDATE ventas SET estado = 'Anulada', fecha_anulacion = NOW() WHERE id_venta = ?";
        String sqlDetalle = "SELECT id_medicamento, cantidad FROM detalle_ventas WHERE id_venta = ?";
        String sqlStock = "UPDATE medicamentos SET stock = stock + ? WHERE id_medicamento = ?";

        Connection cn = null;
        PreparedStatement pstCabecera = null;
        PreparedStatement pstDetalle = null;
        PreparedStatement pstStock = null;
        ResultSet rs = null;

        try {
            cn = ConexionBD.getConnection();
            cn.setAutoCommit(false);

            pstCabecera = cn.prepareStatement(sqlCabecera);
            pstCabecera.setInt(1, idPedido);
            int filasAfectadas = pstCabecera.executeUpdate();
            if (filasAfectadas == 0) {
                throw new SQLException("La venta con ID " + idPedido + " no existe.");
            }

            pstDetalle = cn.prepareStatement(sqlDetalle);
            pstDetalle.setInt(1, idPedido);
            rs = pstDetalle.executeQuery();

            pstStock = cn.prepareStatement(sqlStock);
            while (rs.next()) {
                int idMedicamento = rs.getInt("id_medicamento");
                int cantidadDevuelta = rs.getInt("cantidad");
                pstStock.setInt(1, cantidadDevuelta);
                pstStock.setInt(2, idMedicamento);
                pstStock.executeUpdate();
            }

            cn.commit();
            return true;

        } catch (SQLException e) {
            System.out.println("Error al anular la venta: " + e.getMessage());
            if (cn != null) {
                try {
                    cn.rollback();
                } catch (SQLException ex) {
                    System.out.println("Error en rollback: " + ex.getMessage());
                }
            }
            return false;
        } finally {
            try {
                if (rs != null) rs.close();
                if (pstCabecera != null) pstCabecera.close();
                if (pstDetalle != null) pstDetalle.close();
                if (pstStock != null) pstStock.close();
                if (cn != null) cn.close();
            } catch (SQLException e) {
                System.out.println("Error al cerrar recursos: " + e.getMessage());
            }
        }
    }

    public List<Object[]> buscarPedidosParaAnular(String criterio) {
        List<Object[]> lista = new ArrayList<>();
        String sql = "SELECT v.id_venta, c.nombres AS cliente, v.fecha, v.estado "
                + "FROM ventas v "
                + "INNER JOIN clientes c ON v.id_cliente = c.id_cliente "
                + "WHERE (v.id_venta = ? OR c.nombres LIKE ?) AND v.estado = 'Pagada'";

        try (Connection cn = ConexionBD.getConnection();
             PreparedStatement pst = cn.prepareStatement(sql)) {

            int idBuscado = 0;
            try {
                idBuscado = Integer.parseInt(criterio);
            } catch (NumberFormatException e) {
            }

            pst.setInt(1, idBuscado);
            pst.setString(2, "%" + criterio + "%");

            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    Object[] fila = new Object[4];
                    fila[0] = rs.getInt("id_venta");
                    fila[1] = rs.getString("cliente");
                    fila[2] = rs.getDate("fecha");
                    fila[3] = rs.getString("estado");
                    lista.add(fila);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al buscar pedidos: " + e.getMessage());
        }
        return lista;
    }

    public List<Object[]> consultarVentasPorFechas(java.util.Date fechaInicio, java.util.Date fechaFin) {
        List<Object[]> lista = new ArrayList<>();
        String sql = "SELECT v.id_venta, c.nombres AS cliente, v.fecha, v.total, v.estado "
                + "FROM ventas v "
                + "INNER JOIN clientes c ON v.id_cliente = c.id_cliente "
                + "WHERE v.fecha BETWEEN ? AND ? "
                + "ORDER BY v.fecha DESC";

        try (Connection cn = ConexionBD.getConnection();
             PreparedStatement pst = cn.prepareStatement(sql)) {

            pst.setTimestamp(1, new java.sql.Timestamp(fechaInicio.getTime()));
            pst.setTimestamp(2, new java.sql.Timestamp(fechaFin.getTime()));

            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    Object[] fila = new Object[5];
                    fila[0] = rs.getInt("id_venta");
                    fila[1] = rs.getString("cliente");
                    fila[2] = rs.getTimestamp("fecha");
                    fila[3] = rs.getBigDecimal("total");
                    fila[4] = rs.getString("estado");
                    lista.add(fila);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al consultar ventas por fechas: " + e.getMessage());
        }
        return lista;
    }

    public List<Object[]> obtenerReporteDiario() {
        List<Object[]> lista = new ArrayList<>();
        String sql = "SELECT v.id_venta, c.nombres AS cliente, v.total, v.estado "
                + "FROM ventas v "
                + "INNER JOIN clientes c ON v.id_cliente = c.id_cliente "
                + "WHERE DATE(v.fecha) = CURDATE() "
                + "ORDER BY v.id_venta DESC";

        try (Connection cn = ConexionBD.getConnection();
             PreparedStatement pst = cn.prepareStatement(sql);
             ResultSet rs = pst.executeQuery()) {

            while (rs.next()) {
                Object[] fila = new Object[5];
                fila[0] = rs.getInt("id_venta");
                fila[1] = rs.getString("cliente");
                fila[2] = "Normal";
                String estado = rs.getString("estado");
                fila[3] = rs.getBigDecimal("total");
                fila[4] = "Pagada".equals(estado) ? "COMPLETADO" : "ANULADO";
                lista.add(fila);
            }
        } catch (SQLException e) {
            System.out.println("Error al generar el reporte diario: " + e.getMessage());
        }
        return lista;
    }

    public List<Object[]> obtenerTop5Medicamentos() {
        List<Object[]> lista = new ArrayList<>();
        String sql = "SELECT m.codigo, m.nombre_comercial, SUM(dv.cantidad) AS TotalVendido "
                + "FROM detalle_ventas dv "
                + "INNER JOIN medicamentos m ON dv.id_medicamento = m.id_medicamento "
                + "INNER JOIN ventas v ON dv.id_venta = v.id_venta "
                + "WHERE v.estado = 'Pagada' "
                + "GROUP BY m.id_medicamento, m.codigo, m.nombre_comercial "
                + "ORDER BY TotalVendido DESC "
                + "LIMIT 5";

        try (Connection cn = ConexionBD.getConnection();
             PreparedStatement pst = cn.prepareStatement(sql);
             ResultSet rs = pst.executeQuery()) {

            while (rs.next()) {
                Object[] fila = new Object[3];
                fila[0] = rs.getString("codigo");
                fila[1] = rs.getString("nombre_comercial");
                fila[2] = rs.getInt("TotalVendido");
                lista.add(fila);
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener el Top 5 de medicamentos: " + e.getMessage());
        }
        return lista;
    }

    public List<Object[]> obtenerEstadisticasClientes() {
        List<Object[]> lista = new ArrayList<>();
        String sql = "SELECT c.numero_documento, c.nombres, COUNT(v.id_venta) AS TotalPedidos, SUM(v.total) AS TotalInvertido "
                + "FROM ventas v "
                + "INNER JOIN clientes c ON v.id_cliente = c.id_cliente "
                + "WHERE v.estado = 'Pagada' "
                + "GROUP BY c.id_cliente, c.numero_documento, c.nombres "
                + "ORDER BY TotalInvertido DESC";

        try (Connection cn = ConexionBD.getConnection();
             PreparedStatement pst = cn.prepareStatement(sql);
             ResultSet rs = pst.executeQuery()) {

            while (rs.next()) {
                Object[] fila = new Object[4];
                fila[0] = rs.getString("numero_documento");
                fila[1] = rs.getString("nombres");
                fila[2] = rs.getInt("TotalPedidos");
                fila[3] = rs.getBigDecimal("TotalInvertido");
                lista.add(fila);
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener estadísticas de clientes: " + e.getMessage());
        }
        return lista;
    }

    public Object[] buscarVentaPorId(int idVenta) {
        String sql = "SELECT v.id_venta, v.id_cliente, v.id_usuario, v.total, v.estado, "
                + "c.numero_documento, c.nombres "
                + "FROM ventas v INNER JOIN clientes c ON v.id_cliente = c.id_cliente "
                + "WHERE v.id_venta = ?";
        try (Connection cn = ConexionBD.getConnection();
             PreparedStatement pst = cn.prepareStatement(sql)) {
            pst.setInt(1, idVenta);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    Object[] fila = new Object[7];
                    fila[0] = rs.getInt("id_venta");
                    fila[1] = rs.getInt("id_cliente");
                    fila[2] = rs.getInt("id_usuario");
                    fila[3] = rs.getBigDecimal("total");
                    fila[4] = rs.getString("numero_documento");
                    fila[5] = rs.getString("nombres");
                    fila[6] = rs.getString("estado");
                    return fila;
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al buscar venta por ID: " + e.getMessage());
        }
        return null;
    }

    public List<Object[]> buscarDetalleVenta(int idVenta) {
        List<Object[]> lista = new ArrayList<>();
        String sql = "SELECT dv.id_medicamento, m.codigo, m.nombre_comercial, "
                + "dv.cantidad, dv.precio_unitario, (dv.cantidad * dv.precio_unitario) AS subtotal "
                + "FROM detalle_ventas dv "
                + "INNER JOIN medicamentos m ON dv.id_medicamento = m.id_medicamento "
                + "WHERE dv.id_venta = ?";
        try (Connection cn = ConexionBD.getConnection();
             PreparedStatement pst = cn.prepareStatement(sql)) {
            pst.setInt(1, idVenta);
            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    Object[] fila = new Object[7];
                    fila[0] = rs.getInt("id_medicamento");
                    fila[1] = rs.getString("codigo");
                    fila[2] = rs.getString("nombre_comercial");
                    fila[3] = rs.getInt("cantidad");
                    fila[4] = rs.getBigDecimal("precio_unitario");
                    fila[5] = rs.getBigDecimal("subtotal");
                    fila[6] = "Activo";
                    lista.add(fila);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al buscar detalle de venta: " + e.getMessage());
        }
        return lista;
    }

    public boolean actualizarDetalleVenta(int idMedicamento, int cantidad, java.math.BigDecimal subtotal) {
        String sql = "UPDATE detalle_ventas SET cantidad = ?, precio_unitario = ? WHERE id_medicamento = ?";
        try (Connection cn = ConexionBD.getConnection();
             PreparedStatement pst = cn.prepareStatement(sql)) {
            pst.setInt(1, cantidad);
            pst.setBigDecimal(2, subtotal.divide(java.math.BigDecimal.valueOf(cantidad), 2, java.math.RoundingMode.HALF_UP));
            pst.setInt(3, idMedicamento);
            return pst.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error al actualizar detalle de venta: " + e.getMessage());
            return false;
        }
    }
}
