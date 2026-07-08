package LOGICA;

import CONEX.ConexionBD;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class InicioSesion {
    public static boolean validarCredenciales(String usuario, String contrasena) {
        String sql = "SELECT id_usuario FROM usuarios WHERE usuario = ? AND contrasena = ? AND estado = 'Activo'";
        try (Connection conn = ConexionBD.getConnection(); PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setString(1, usuario);
            pst.setString(2, contrasena);
            try (ResultSet rs = pst.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            System.out.println("Error al validar credenciales: " + e.getMessage());
            return false;
        }
    }

    public static UsuarioClass obtenerUsuarioActivo(String usuario, String contrasena) {
        String sql = "SELECT id_usuario, usuario, contrasena, nombres, rol, estado, ultimo_acceso, intentos_fallidos, requiere_cambio_clave, creado_en, actualizado_en FROM usuarios WHERE usuario = ? AND contrasena = ? AND estado = 'Activo'";
        try (Connection conn = ConexionBD.getConnection(); PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setString(1, usuario);
            pst.setString(2, contrasena);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    UsuarioClass u = new UsuarioClass();
                    u.setIdUsuario(rs.getInt("id_usuario"));
                    u.setUsuario(rs.getString("usuario"));
                    u.setContrasena(rs.getString("contrasena"));
                    u.setNombres(rs.getString("nombres"));
                    u.setRol(rs.getString("rol"));
                    u.setEstado(rs.getString("estado"));
                    u.setUltimoAcceso(rs.getTimestamp("ultimo_acceso"));
                    u.setIntentosFallidos(rs.getInt("intentos_fallidos"));
                    u.setRequiereCambioClave(rs.getBoolean("requiere_cambio_clave"));
                    u.setCreadoEn(rs.getTimestamp("creado_en"));
                    u.setActualizadoEn(rs.getTimestamp("actualizado_en"));
                    return u;
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener usuario activo: " + e.getMessage());
        }
        return null;
    }
}
