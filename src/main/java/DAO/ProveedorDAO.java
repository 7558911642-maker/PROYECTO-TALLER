package DAO;

import CONEX.ConexionBD;
import LOGICA.ProveedorClass;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

public class ProveedorDAO {

    // LISTAR TODOS LOS PROVEEDORES
    public List<ProveedorClass> listar() {
        List<ProveedorClass> proveedores = new ArrayList<>();
        String sql = "SELECT id_proveedor, ruc, razon_social, nombre_comercial, telefono, correo, direccion, contacto_nombre, contacto_cargo, contacto_telefono, estado, creado_en, actualizado_en FROM proveedores ORDER BY id_proveedor";

        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                proveedores.add(mapearProveedor(rs));
            }

        } catch (SQLException e) {
            mostrarError("Error al listar proveedores", e);
        }

        return proveedores;
    }

    // BUSCAR PROVEEDORES POR RUC, RAZÓN SOCIAL, NOMBRE COMERCIAL, CONTACTO O ESTADO
    public List<ProveedorClass> buscar(String criterio) {
        List<ProveedorClass> proveedores = new ArrayList<>();
        String sql = "SELECT id_proveedor, ruc, razon_social, nombre_comercial, telefono, correo, direccion, contacto_nombre, contacto_cargo, contacto_telefono, estado, creado_en, actualizado_en "
                + "FROM proveedores WHERE ruc LIKE ? OR razon_social LIKE ? OR nombre_comercial LIKE ? OR contacto_nombre LIKE ? OR telefono LIKE ? OR correo LIKE ? OR estado LIKE ? ORDER BY id_proveedor";

        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            String filtro = "%" + criterio + "%";
            for (int i = 1; i <= 7; i++) {
                stmt.setString(i, filtro);
            }

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    proveedores.add(mapearProveedor(rs));
                }
            }

        } catch (SQLException e) {
            mostrarError("Error al buscar proveedores", e);
        }

        return proveedores;
    }

    // BUSCAR PROVEEDOR POR ID
    public ProveedorClass buscarPorId(int idProveedor) {
        String sql = "SELECT id_proveedor, ruc, razon_social, nombre_comercial, telefono, correo, direccion, contacto_nombre, contacto_cargo, contacto_telefono, estado, creado_en, actualizado_en FROM proveedores WHERE id_proveedor = ?";

        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idProveedor);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapearProveedor(rs);
                }
            }

        } catch (SQLException e) {
            mostrarError("Error al buscar el proveedor", e);
        }

        return null;
    }

    // REGISTRAR PROVEEDOR
    public boolean registrar(ProveedorClass proveedor) {
        String sql = "INSERT INTO proveedores (ruc, razon_social, nombre_comercial, telefono, correo, direccion, contacto_nombre, contacto_cargo, contacto_telefono, estado) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            prepararProveedor(stmt, proveedor, false);
            boolean registrado = stmt.executeUpdate() > 0;

            if (registrado) {
                try (ResultSet rs = stmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        proveedor.setIdProveedor(rs.getInt(1));
                    }
                }
            }

            return registrado;

        } catch (SQLException e) {
            mostrarError("Error al registrar el proveedor", e);
            return false;
        }
    }

    // ACTUALIZAR PROVEEDOR
    public boolean actualizar(ProveedorClass proveedor) {
        String sql = "UPDATE proveedores SET ruc = ?, razon_social = ?, nombre_comercial = ?, telefono = ?, correo = ?, direccion = ?, contacto_nombre = ?, contacto_cargo = ?, contacto_telefono = ?, estado = ? WHERE id_proveedor = ?";

        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            prepararProveedor(stmt, proveedor, true);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            mostrarError("Error al actualizar el proveedor", e);
            return false;
        }
    }

    // ELIMINAR PROVEEDOR
    public boolean eliminar(int idProveedor) {
        String sql = "UPDATE proveedores SET estado = 'Inactivo' WHERE id_proveedor = ?";

        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idProveedor);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            mostrarError("Error al eliminar el proveedor", e);
            return false;
        }
    }

    // ELIMINAR PROVEEDOR, NOMBRE COMPATIBLE CON EL ESTILO DEL DOCENTE
    public boolean eliminarProveedor(int idProveedor) {
        return eliminar(idProveedor);
    }

    private void prepararProveedor(PreparedStatement stmt, ProveedorClass p, boolean incluyeId) throws SQLException {
        stmt.setString(1, p.getRuc());
        stmt.setString(2, p.getRazonSocial() != null ? p.getRazonSocial() : p.getNombreProveedor());
        stmt.setString(3, p.getNombreComercial());
        stmt.setString(4, p.getTelefono());
        stmt.setString(5, p.getCorreo());
        stmt.setString(6, p.getDireccion());
        stmt.setString(7, p.getContactoNombre());
        stmt.setString(8, p.getContactoCargo());
        stmt.setString(9, p.getContactoTelefono());
        stmt.setString(10, p.getEstado() == null ? "Activo" : p.getEstado());

        if (incluyeId) {
            stmt.setInt(11, p.getIdProveedor());
        }
    }

    private ProveedorClass mapearProveedor(ResultSet rs) throws SQLException {
        ProveedorClass proveedor = new ProveedorClass();
        proveedor.setIdProveedor(rs.getInt("id_proveedor"));
        proveedor.setRuc(rs.getString("ruc"));
        proveedor.setRazonSocial(rs.getString("razon_social"));
        proveedor.setNombreProveedor(rs.getString("razon_social"));
        proveedor.setNombreComercial(rs.getString("nombre_comercial"));
        proveedor.setTelefono(rs.getString("telefono"));
        proveedor.setCorreo(rs.getString("correo"));
        proveedor.setDireccion(rs.getString("direccion"));
        proveedor.setContactoNombre(rs.getString("contacto_nombre"));
        proveedor.setContactoCargo(rs.getString("contacto_cargo"));
        proveedor.setContactoTelefono(rs.getString("contacto_telefono"));
        proveedor.setEstado(rs.getString("estado"));
        proveedor.setCreadoEn(rs.getTimestamp("creado_en"));
        proveedor.setActualizadoEn(rs.getTimestamp("actualizado_en"));
        return proveedor;
    }

    private void mostrarError(String mensaje, SQLException e) {
        JOptionPane.showMessageDialog(null, mensaje + ": " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        e.printStackTrace();
    }
}
