package DAO;

import CONEX.ConexionBD;
import LOGICA.ProductoClass;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductoDAO {

    public List<Object[]> listarMedicamentos() {
        List<Object[]> lista = new ArrayList<>();
        String sql = "SELECT m.id_medicamento, m.codigo, m.nombre_comercial, "
                + "COALESCE(c.nombre, '') AS categoria, "
                + "COALESCE(p.razon_social, '') AS proveedor, "
                + "m.precio, m.stock "
                + "FROM medicamentos m "
                + "LEFT JOIN categorias c ON m.id_categoria = c.id_categoria "
                + "LEFT JOIN proveedores p ON m.id_proveedor = p.id_proveedor";

        try (Connection cn = ConexionBD.getConnection();
             PreparedStatement pst = cn.prepareStatement(sql);
             ResultSet rs = pst.executeQuery()) {

            while (rs.next()) {
                Object[] fila = new Object[7];
                fila[0] = rs.getInt("id_medicamento");
                fila[1] = rs.getString("codigo");
                fila[2] = rs.getString("nombre_comercial");
                fila[3] = rs.getString("categoria");
                fila[4] = rs.getString("proveedor");
                fila[5] = rs.getBigDecimal("precio");
                fila[6] = rs.getInt("stock");
                lista.add(fila);
            }
        } catch (SQLException e) {
            System.out.println("Error al listar medicamentos: " + e.getMessage());
        }
        return lista;
    }

    public boolean registrarMedicamento(ProductoClass prod) {
        String sql = "INSERT INTO medicamentos (codigo, nombre_comercial, id_categoria, id_proveedor, precio, stock, estado) "
                + "VALUES (?, ?, 1, 1, ?, ?, 'Activo')";

        try (Connection cn = ConexionBD.getConnection();
             PreparedStatement pst = cn.prepareStatement(sql)) {

            pst.setString(1, prod.getCodigo());
            pst.setString(2, prod.getNombreComercial());
            pst.setBigDecimal(3, prod.getPrecioUnidad());
            pst.setInt(4, prod.getStock());

            return pst.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error al registrar medicamento: " + e.getMessage());
            return false;
        }
    }

    public List<Object[]> buscarMedicamentos(String criterio) {
        List<Object[]> lista = new ArrayList<>();
        String sql = "SELECT m.id_medicamento, m.codigo, m.nombre_comercial, "
                + "COALESCE(c.nombre, '') AS categoria, "
                + "COALESCE(p.razon_social, '') AS proveedor, "
                + "m.precio, m.stock "
                + "FROM medicamentos m "
                + "LEFT JOIN categorias c ON m.id_categoria = c.id_categoria "
                + "LEFT JOIN proveedores p ON m.id_proveedor = p.id_proveedor "
                + "WHERE m.codigo LIKE ? OR m.nombre_comercial LIKE ?";

        try (Connection cn = ConexionBD.getConnection();
             PreparedStatement pst = cn.prepareStatement(sql)) {

            String busqueda = "%" + criterio + "%";
            pst.setString(1, busqueda);
            pst.setString(2, busqueda);

            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    Object[] fila = new Object[7];
                    fila[0] = rs.getInt("id_medicamento");
                    fila[1] = rs.getString("codigo");
                    fila[2] = rs.getString("nombre_comercial");
                    fila[3] = rs.getString("categoria");
                    fila[4] = rs.getString("proveedor");
                    fila[5] = rs.getBigDecimal("precio");
                    fila[6] = rs.getInt("stock");
                    lista.add(fila);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al buscar medicamentos: " + e.getMessage());
        }
        return lista;
    }

    public boolean anularVenta(int idVenta) {
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
            pstCabecera.setInt(1, idVenta);
            int filas = pstCabecera.executeUpdate();
            if (filas == 0) {
                throw new SQLException("La venta con ID " + idVenta + " no existe.");
            }

            pstDetalle = cn.prepareStatement(sqlDetalle);
            pstDetalle.setInt(1, idVenta);
            rs = pstDetalle.executeQuery();

            pstStock = cn.prepareStatement(sqlStock);
            while (rs.next()) {
                int idMedicamento = rs.getInt("id_medicamento");
                int cantidad = rs.getInt("cantidad");
                pstStock.setInt(1, cantidad);
                pstStock.setInt(2, idMedicamento);
                pstStock.executeUpdate();
            }

            cn.commit();
            return true;

        } catch (SQLException e) {
            System.out.println("Error al anular venta: " + e.getMessage());
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
}
