package DAO;

import CONEX.ConexionBD;
import LOGICA.UsuarioClass;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO {

    public boolean login(String user, String pass) {
        String sql = "SELECT * FROM usuarios WHERE usuario = ? AND contrasena = ? AND estado = 'Activo'";
        try (Connection cn = ConexionBD.getConnection();
             PreparedStatement pst = cn.prepareStatement(sql)) {
            pst.setString(1, user);
            pst.setString(2, pass);
            try (ResultSet rs = pst.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            System.out.println("Error al validar el usuario: " + e.getMessage());
            return false;
        }
    }

    public List<UsuarioClass> listar() {
        List<UsuarioClass> lista = new ArrayList<>();
        String sql = "SELECT id_usuario, usuario, nombres, rol, estado FROM usuarios";
        try (Connection cn = ConexionBD.getConnection();
             PreparedStatement pst = cn.prepareStatement(sql);
             ResultSet rs = pst.executeQuery()) {
            while (rs.next()) {
                UsuarioClass u = new UsuarioClass();
                u.setIdUsuario(rs.getInt("id_usuario"));
                u.setUsuario(rs.getString("usuario"));
                u.setNombre(rs.getString("nombres"));
                lista.add(u);
            }
        } catch (SQLException e) {
            System.out.println("Error al listar usuarios: " + e.getMessage());
        }
        return lista;
    }

    public boolean registrar(UsuarioClass u) {
        String sql = "INSERT INTO usuarios (usuario, contrasena, nombres, rol, estado) VALUES (?, ?, ?, ?, 'Activo')";
        try (Connection cn = ConexionBD.getConnection();
             PreparedStatement pst = cn.prepareStatement(sql)) {
            pst.setString(1, u.getUsuario());
            pst.setString(2, u.getContrasenia());
            pst.setString(3, u.getNombre() != null ? u.getNombre() : "");
            pst.setString(4, "Vendedor");
            return pst.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error al registrar usuario: " + e.getMessage());
            return false;
        }
    }

    public List<UsuarioClass> buscar(String criterio) {
        List<UsuarioClass> lista = new ArrayList<>();
        String sql = "SELECT id_usuario, usuario, nombres, rol, estado FROM usuarios WHERE usuario LIKE ? OR nombres LIKE ?";
        try (Connection cn = ConexionBD.getConnection();
             PreparedStatement pst = cn.prepareStatement(sql)) {
            String busqueda = "%" + criterio + "%";
            pst.setString(1, busqueda);
            pst.setString(2, busqueda);
            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    UsuarioClass u = new UsuarioClass();
                    u.setIdUsuario(rs.getInt("id_usuario"));
                    u.setUsuario(rs.getString("usuario"));
                    u.setNombre(rs.getString("nombres"));
                    lista.add(u);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al buscar usuarios: " + e.getMessage());
        }
        return lista;
    }
}
