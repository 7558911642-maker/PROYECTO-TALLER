package DAO;

import CONEX.ConexionBD;
import LOGICA.ProductoClass;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

public class ProductoDAO {

    // LISTAR MEDICAMENTOS PARA TABLAS Y REPORTES
    public List<Object[]> listarMedicamentos() {
        List<Object[]> medicamentos = new ArrayList<>();
        String sql = "SELECT m.id_medicamento, m.codigo, m.nombre_comercial, m.principio_activo, m.concentracion, m.presentacion, "
                + "COALESCE(c.nombre, '') AS categoria, COALESCE(p.razon_social, '') AS proveedor, COALESCE(l.nombre, '') AS laboratorio, "
                + "m.precio_compra, m.precio AS precio_venta, m.stock, m.stock_minimo, m.stock_maximo, m.estado "
                + "FROM medicamentos m INNER JOIN categorias c ON c.id_categoria = m.id_categoria "
                + "LEFT JOIN proveedores p ON p.id_proveedor = m.id_proveedor "
                + "LEFT JOIN laboratorios l ON l.id_laboratorio = m.id_laboratorio ORDER BY m.id_medicamento";

        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                medicamentos.add(new Object[]{
                    rs.getInt("id_medicamento"),
                    rs.getString("codigo"),
                    rs.getString("nombre_comercial"),
                    rs.getString("principio_activo"),
                    rs.getString("concentracion"),
                    rs.getString("presentacion"),
                    rs.getString("categoria"),
                    rs.getString("proveedor"),
                    rs.getString("laboratorio"),
                    rs.getBigDecimal("precio_compra"),
                    rs.getBigDecimal("precio_venta"),
                    rs.getInt("stock"),
                    rs.getInt("stock_minimo"),
                    rs.getObject("stock_maximo"),
                    rs.getString("estado")
                });
            }

        } catch (SQLException e) {
            mostrarError("Error al listar medicamentos", e);
        }

        return medicamentos;
    }

    // BUSCAR MEDICAMENTOS POR CÓDIGO, NOMBRE, PRINCIPIO ACTIVO, CATEGORÍA, PROVEEDOR O LABORATORIO
    public List<Object[]> buscarMedicamentos(String criterio) {
        List<Object[]> medicamentos = new ArrayList<>();
        String sql = "SELECT m.id_medicamento, m.codigo, m.nombre_comercial, m.principio_activo, m.concentracion, m.presentacion, "
                + "COALESCE(c.nombre, '') AS categoria, COALESCE(p.razon_social, '') AS proveedor, COALESCE(l.nombre, '') AS laboratorio, "
                + "m.precio_compra, m.precio AS precio_venta, m.stock, m.stock_minimo, m.stock_maximo, m.estado "
                + "FROM medicamentos m INNER JOIN categorias c ON c.id_categoria = m.id_categoria "
                + "LEFT JOIN proveedores p ON p.id_proveedor = m.id_proveedor "
                + "LEFT JOIN laboratorios l ON l.id_laboratorio = m.id_laboratorio "
                + "WHERE m.codigo LIKE ? OR m.nombre_comercial LIKE ? OR m.principio_activo LIKE ? OR m.presentacion LIKE ? OR c.nombre LIKE ? OR p.razon_social LIKE ? OR l.nombre LIKE ? OR m.estado LIKE ? "
                + "ORDER BY m.id_medicamento";

        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            String filtro = "%" + criterio + "%";
            for (int i = 1; i <= 8; i++) {
                stmt.setString(i, filtro);
            }

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    medicamentos.add(new Object[]{
                        rs.getInt("id_medicamento"), rs.getString("codigo"), rs.getString("nombre_comercial"), rs.getString("principio_activo"),
                        rs.getString("concentracion"), rs.getString("presentacion"), rs.getString("categoria"), rs.getString("proveedor"),
                        rs.getString("laboratorio"), rs.getBigDecimal("precio_compra"), rs.getBigDecimal("precio_venta"), rs.getInt("stock"),
                        rs.getInt("stock_minimo"), rs.getObject("stock_maximo"), rs.getString("estado")
                    });
                }
            }

        } catch (SQLException e) {
            mostrarError("Error al buscar medicamentos", e);
        }

        return medicamentos;
    }

    // BUSCAR MEDICAMENTO POR ID
    public ProductoClass buscarPorId(int idMedicamento) {
        String sql = "SELECT m.*, c.nombre AS categoria, p.razon_social AS proveedor, l.nombre AS laboratorio_nombre "
                + "FROM medicamentos m INNER JOIN categorias c ON c.id_categoria = m.id_categoria "
                + "LEFT JOIN proveedores p ON p.id_proveedor = m.id_proveedor "
                + "LEFT JOIN laboratorios l ON l.id_laboratorio = m.id_laboratorio WHERE m.id_medicamento = ?";

        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idMedicamento);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapearProducto(rs);
                }
            }

        } catch (SQLException e) {
            mostrarError("Error al buscar el medicamento", e);
        }

        return null;
    }

    // BUSCAR MEDICAMENTO POR CÓDIGO
    public ProductoClass buscarPorCodigo(String codigo) {
        String sql = "SELECT m.*, c.nombre AS categoria, p.razon_social AS proveedor, l.nombre AS laboratorio_nombre "
                + "FROM medicamentos m INNER JOIN categorias c ON c.id_categoria = m.id_categoria "
                + "LEFT JOIN proveedores p ON p.id_proveedor = m.id_proveedor "
                + "LEFT JOIN laboratorios l ON l.id_laboratorio = m.id_laboratorio WHERE m.codigo = ?";

        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, codigo);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapearProducto(rs);
                }
            }

        } catch (SQLException e) {
            mostrarError("Error al buscar el medicamento por código", e);
        }

        return null;
    }

    // REGISTRAR MEDICAMENTO
    public boolean registrarMedicamento(ProductoClass producto) {
        String sql = "INSERT INTO medicamentos (codigo, nombre_comercial, principio_activo, concentracion, presentacion, unidad_medida, registro_sanitario, "
                + "requiere_receta, id_categoria, id_proveedor, id_laboratorio, precio_compra, precio, stock, stock_minimo, stock_maximo, estado) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            prepararProducto(stmt, producto, false);
            boolean registrado = stmt.executeUpdate() > 0;

            if (registrado) {
                try (ResultSet rs = stmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        producto.setIdMedicamento(rs.getInt(1));
                    }
                }
            }

            return registrado;

        } catch (SQLException e) {
            mostrarError("Error al registrar el medicamento", e);
            return false;
        }
    }

    // ACTUALIZAR MEDICAMENTO
    public boolean actualizar(ProductoClass producto) {
        String sql = "UPDATE medicamentos SET codigo = ?, nombre_comercial = ?, principio_activo = ?, concentracion = ?, presentacion = ?, unidad_medida = ?, registro_sanitario = ?, "
                + "requiere_receta = ?, id_categoria = ?, id_proveedor = ?, id_laboratorio = ?, precio_compra = ?, precio = ?, stock = ?, stock_minimo = ?, stock_maximo = ?, estado = ? "
                + "WHERE id_medicamento = ?";

        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            prepararProducto(stmt, producto, true);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            mostrarError("Error al actualizar el medicamento", e);
            return false;
        }
    }

    // ELIMINAR MEDICAMENTO
    public boolean eliminar(int idMedicamento) {
        String sql = "UPDATE medicamentos SET estado = 'Inactivo' WHERE id_medicamento = ?";

        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idMedicamento);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            mostrarError("Error al eliminar el medicamento", e);
            return false;
        }
    }

    // ANULAR VENTA, MÉTODO CONSERVADO POR COMPATIBILIDAD CON TU PROYECTO ANTERIOR
    public boolean anularVenta(int idVenta) {
        return new PedidoDAO().anularVenta(idVenta);
    }

    // BUSCAR VENTAS PARA ANULAR, MÉTODO CONSERVADO POR COMPATIBILIDAD CON TU PROYECTO ANTERIOR
    public List<Object[]> buscarPedidosParaAnular(String criterio) {
        return new PedidoDAO().buscarPedidosParaAnular(criterio);
    }

    private void prepararProducto(PreparedStatement stmt, ProductoClass p, boolean incluyeId) throws SQLException {
        stmt.setString(1, p.getCodigo());
        stmt.setString(2, p.getNombreComercial());
        stmt.setString(3, p.getPrincipioActivo());
        stmt.setString(4, p.getConcentracion());
        stmt.setString(5, p.getPresentacion());
        stmt.setString(6, p.getUnidadMedida() == null ? "Unidad" : p.getUnidadMedida());
        stmt.setString(7, p.getRegistroSanitario());
        stmt.setBoolean(8, p.getRequiereReceta());
        stmt.setInt(9, p.getIdCategoria());
        setNullableInt(stmt, 10, p.getIdProveedor());
        setNullableInt(stmt, 11, p.getIdLaboratorio());
        stmt.setBigDecimal(12, p.getPrecioCompra());
        stmt.setBigDecimal(13, p.getPrecio());
        stmt.setInt(14, p.getStock());
        stmt.setInt(15, p.getStockMinimo());
        setNullableInt(stmt, 16, p.getStockMaximo());
        stmt.setString(17, p.getEstado() == null ? "Activo" : p.getEstado());

        if (incluyeId) {
            stmt.setInt(18, p.getIdMedicamento());
        }
    }

    private ProductoClass mapearProducto(ResultSet rs) throws SQLException {
        ProductoClass producto = new ProductoClass();
        producto.setIdMedicamento(rs.getInt("id_medicamento"));
        producto.setCodigo(rs.getString("codigo"));
        producto.setNombreComercial(rs.getString("nombre_comercial"));
        producto.setPrincipioActivo(rs.getString("principio_activo"));
        producto.setConcentracion(rs.getString("concentracion"));
        producto.setPresentacion(rs.getString("presentacion"));
        producto.setUnidadMedida(rs.getString("unidad_medida"));
        producto.setRegistroSanitario(rs.getString("registro_sanitario"));
        producto.setRequiereReceta(rs.getBoolean("requiere_receta"));
        producto.setIdCategoria(rs.getInt("id_categoria"));
        producto.setIdProveedor((Integer) rs.getObject("id_proveedor"));
        producto.setIdLaboratorio((Integer) rs.getObject("id_laboratorio"));
        producto.setPrecioCompra(rs.getBigDecimal("precio_compra"));
        producto.setPrecio(rs.getBigDecimal("precio"));
        producto.setStock(rs.getInt("stock"));
        producto.setStockMinimo(rs.getInt("stock_minimo"));
        producto.setStockMaximo((Integer) rs.getObject("stock_maximo"));
        producto.setEstado(rs.getString("estado"));
        producto.setCreadoEn(rs.getTimestamp("creado_en"));
        producto.setActualizadoEn(rs.getTimestamp("actualizado_en"));
        producto.setNombreCategoria(rs.getString("categoria"));
        producto.setNombreProveedor(rs.getString("proveedor"));
        producto.setLaboratorio(rs.getString("laboratorio_nombre"));
        return producto;
    }

    private void setNullableInt(PreparedStatement stmt, int index, Integer value) throws SQLException {
        if (value == null || value <= 0) {
            stmt.setNull(index, java.sql.Types.INTEGER);
        } else {
            stmt.setInt(index, value);
        }
    }

    private void mostrarError(String mensaje, SQLException e) {
        JOptionPane.showMessageDialog(null, mensaje + ": " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        e.printStackTrace();
    }
}
