package DAO;

import CONEX.ConexionBD;
import LOGICA.RolClass;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

public class RolDAO {

    // LISTAR ROLES
    public List<RolClass> listar() {
        List<RolClass> roles = new ArrayList<>();
        String sql = "SELECT id_rol, nombre, descripcion, estado, creado_en FROM roles ORDER BY id_rol";

        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                roles.add(mapearRol(rs));
            }

        } catch (SQLException e) {
            mostrarError("Error al listar roles", e);
        }

        return roles;
    }

    // BUSCAR ROLES
    public List<RolClass> buscar(String criterio) {
        List<RolClass> roles = new ArrayList<>();
        String sql = "SELECT id_rol, nombre, descripcion, estado, creado_en FROM roles WHERE nombre LIKE ? OR descripcion LIKE ? OR estado LIKE ? ORDER BY id_rol";

        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            String filtro = "%" + criterio + "%";
            stmt.setString(1, filtro);
            stmt.setString(2, filtro);
            stmt.setString(3, filtro);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    roles.add(mapearRol(rs));
                }
            }

        } catch (SQLException e) {
            mostrarError("Error al buscar roles", e);
        }

        return roles;
    }

    // REGISTRAR ROL
    public boolean registrar(RolClass rol) {
        String sql = "INSERT INTO roles (nombre, descripcion, estado) VALUES (?, ?, ?)";

        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, rol.getNombre());
            stmt.setString(2, rol.getDescripcion());
            stmt.setString(3, rol.getEstado() == null ? "Activo" : rol.getEstado());

            boolean registrado = stmt.executeUpdate() > 0;
            if (registrado) {
                try (ResultSet rs = stmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        rol.setIdRol(rs.getInt(1));
                    }
                }
            }
            return registrado;

        } catch (SQLException e) {
            mostrarError("Error al registrar el rol", e);
            return false;
        }
    }

    // ACTUALIZAR ROL
    public boolean actualizar(RolClass rol) {
        String sql = "UPDATE roles SET nombre = ?, descripcion = ?, estado = ? WHERE id_rol = ?";

        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, rol.getNombre());
            stmt.setString(2, rol.getDescripcion());
            stmt.setString(3, rol.getEstado() == null ? "Activo" : rol.getEstado());
            stmt.setInt(4, rol.getIdRol());
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            mostrarError("Error al actualizar el rol", e);
            return false;
        }
    }

    // ELIMINAR ROL
    public boolean eliminar(int idRol) {
        String sql = "UPDATE roles SET estado = 'Inactivo' WHERE id_rol = ?";

        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idRol);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            mostrarError("Error al eliminar el rol", e);
            return false;
        }
    }

    private RolClass mapearRol(ResultSet rs) throws SQLException {
        RolClass rol = new RolClass();
        rol.setIdRol(rs.getInt("id_rol"));
        rol.setNombre(rs.getString("nombre"));
        rol.setDescripcion(rs.getString("descripcion"));
        rol.setEstado(rs.getString("estado"));
        rol.setCreadoEn(rs.getTimestamp("creado_en"));
        return rol;
    }

    private void mostrarError(String mensaje, SQLException e) {
        JOptionPane.showMessageDialog(null, mensaje + ": " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        e.printStackTrace();
    }
}
