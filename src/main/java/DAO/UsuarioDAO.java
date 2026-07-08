package DAO;

import CONEX.ConexionBD;
import LOGICA.UsuarioClass;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

public class UsuarioDAO {

    // VALIDAR LOGIN DE USUARIO
    public boolean login(String user, String pass) {
        String sql = "SELECT id_usuario FROM usuarios WHERE usuario = ? AND contrasena = ? AND estado = 'Activo'";

        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, user);
            stmt.setString(2, pass);

            try (ResultSet rs = stmt.executeQuery()) {
                boolean existe = rs.next();
                if (existe) {
                    actualizarUltimoAcceso(conn, user);
                }
                return existe;
            }

        } catch (SQLException e) {
            mostrarError("Error al iniciar sesión", e);
            return false;
        }
    }

    // LISTAR TODOS LOS USUARIOS
    public List<UsuarioClass> listar() {
        List<UsuarioClass> usuarios = new ArrayList<>();
        String sql = "SELECT id_usuario, usuario, contrasena, nombres, rol, estado, ultimo_acceso, intentos_fallidos, requiere_cambio_clave, creado_en, actualizado_en FROM usuarios ORDER BY id_usuario";

        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                usuarios.add(mapearUsuario(rs));
            }

        } catch (SQLException e) {
            mostrarError("Error al listar usuarios", e);
        }

        return usuarios;
    }

    // BUSCAR USUARIOS POR USUARIO, NOMBRE, ROL O ESTADO
    public List<UsuarioClass> buscar(String criterio) {
        List<UsuarioClass> usuarios = new ArrayList<>();
        String sql = "SELECT id_usuario, usuario, contrasena, nombres, rol, estado, ultimo_acceso, intentos_fallidos, requiere_cambio_clave, creado_en, actualizado_en "
                + "FROM usuarios WHERE usuario LIKE ? OR nombres LIKE ? OR rol LIKE ? OR estado LIKE ? ORDER BY id_usuario";

        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            String filtro = "%" + criterio + "%";
            stmt.setString(1, filtro);
            stmt.setString(2, filtro);
            stmt.setString(3, filtro);
            stmt.setString(4, filtro);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    usuarios.add(mapearUsuario(rs));
                }
            }

        } catch (SQLException e) {
            mostrarError("Error al buscar usuarios", e);
        }

        return usuarios;
    }

    // BUSCAR USUARIO POR ID
    public UsuarioClass buscarPorId(int idUsuario) {
        String sql = "SELECT id_usuario, usuario, contrasena, nombres, rol, estado, ultimo_acceso, intentos_fallidos, requiere_cambio_clave, creado_en, actualizado_en FROM usuarios WHERE id_usuario = ?";

        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idUsuario);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapearUsuario(rs);
                }
            }

        } catch (SQLException e) {
            mostrarError("Error al buscar el usuario", e);
        }

        return null;
    }

    // REGISTRAR USUARIO
    public boolean registrar(UsuarioClass usuario) {
        String sql = "INSERT INTO usuarios (usuario, contrasena, nombres, rol, estado, requiere_cambio_clave) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, usuario.getUsuario());
            stmt.setString(2, usuario.getContrasena());
            stmt.setString(3, usuario.getNombres() != null ? usuario.getNombres() : usuario.getNombre());
            stmt.setString(4, usuario.getRol() == null ? "Vendedor" : usuario.getRol());
            stmt.setString(5, usuario.getEstado() == null ? "Activo" : usuario.getEstado());
            stmt.setBoolean(6, usuario.getRequiereCambioClave());

            boolean registrado = stmt.executeUpdate() > 0;

            if (registrado) {
                try (ResultSet rs = stmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        usuario.setIdUsuario(rs.getInt(1));
                    }
                }
            }

            return registrado;

        } catch (SQLException e) {
            mostrarError("Error al registrar el usuario", e);
            return false;
        }
    }

    // ACTUALIZAR USUARIO
    public boolean actualizar(UsuarioClass usuario) {
        String sql = "UPDATE usuarios SET usuario = ?, contrasena = ?, nombres = ?, rol = ?, estado = ?, requiere_cambio_clave = ? WHERE id_usuario = ?";

        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, usuario.getUsuario());
            stmt.setString(2, usuario.getContrasena());
            stmt.setString(3, usuario.getNombres() != null ? usuario.getNombres() : usuario.getNombre());
            stmt.setString(4, usuario.getRol() == null ? "Vendedor" : usuario.getRol());
            stmt.setString(5, usuario.getEstado() == null ? "Activo" : usuario.getEstado());
            stmt.setBoolean(6, usuario.getRequiereCambioClave());
            stmt.setInt(7, usuario.getIdUsuario());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            mostrarError("Error al actualizar el usuario", e);
            return false;
        }
    }

    // ELIMINAR USUARIO
    public boolean eliminar(int idUsuario) {
        String sql = "UPDATE usuarios SET estado = 'Inactivo' WHERE id_usuario = ?";

        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idUsuario);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            mostrarError("Error al eliminar el usuario", e);
            return false;
        }
    }

    private void actualizarUltimoAcceso(Connection conn, String user) throws SQLException {
        String sql = "UPDATE usuarios SET ultimo_acceso = NOW(), intentos_fallidos = 0 WHERE usuario = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, user);
            stmt.executeUpdate();
        }
    }

    private UsuarioClass mapearUsuario(ResultSet rs) throws SQLException {
        UsuarioClass usuario = new UsuarioClass();
        usuario.setIdUsuario(rs.getInt("id_usuario"));
        usuario.setUsuario(rs.getString("usuario"));
        usuario.setContrasena(rs.getString("contrasena"));
        usuario.setNombres(rs.getString("nombres"));
        usuario.setNombre(rs.getString("nombres"));
        usuario.setRol(rs.getString("rol"));
        usuario.setEstado(rs.getString("estado"));
        usuario.setUltimoAcceso(rs.getTimestamp("ultimo_acceso"));
        usuario.setIntentosFallidos(rs.getInt("intentos_fallidos"));
        usuario.setRequiereCambioClave(rs.getBoolean("requiere_cambio_clave"));
        usuario.setCreadoEn(rs.getTimestamp("creado_en"));
        usuario.setActualizadoEn(rs.getTimestamp("actualizado_en"));
        return usuario;
    }

    private void mostrarError(String mensaje, SQLException e) {
        JOptionPane.showMessageDialog(null, mensaje + ": " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        e.printStackTrace();
    }
}
