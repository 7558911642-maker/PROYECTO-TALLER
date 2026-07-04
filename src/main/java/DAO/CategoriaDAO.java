package DAO;

import CONEX.ConexionBD;
import LOGICA.CategoriaClass;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CategoriaDAO {

    public List<CategoriaClass> listar() {
        List<CategoriaClass> lista = new ArrayList<>();
        String sql = "SELECT id_categoria, codigo, nombre, descripcion FROM categorias";

        try (Connection cn = ConexionBD.getConnection();
             PreparedStatement pst = cn.prepareStatement(sql);
             ResultSet rs = pst.executeQuery()) {

            while (rs.next()) {
                CategoriaClass cat = new CategoriaClass();
                cat.setIdCategoria(rs.getInt("id_categoria"));
                cat.setCodigo(rs.getString("codigo"));
                cat.setNombreCategoria(rs.getString("nombre"));
                cat.setDescripcion(rs.getString("descripcion"));
                lista.add(cat);
            }
        } catch (SQLException e) {
            System.out.println("Error al listar categorías: " + e.getMessage());
        }
        return lista;
    }

    public boolean registrar(CategoriaClass cat) {
        String sql = "INSERT INTO categorias (codigo, nombre, descripcion) VALUES (?, ?, ?)";
        try (Connection cn = ConexionBD.getConnection();
             PreparedStatement pst = cn.prepareStatement(sql)) {
            pst.setString(1, cat.getCodigo() != null ? cat.getCodigo() : "CAT-" + System.currentTimeMillis());
            pst.setString(2, cat.getNombreCategoria());
            pst.setString(3, cat.getDescripcion() != null ? cat.getDescripcion() : "");
            return pst.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error al registrar categoría: " + e.getMessage());
            return false;
        }
    }

    public List<CategoriaClass> buscar(String criterio) {
        List<CategoriaClass> lista = new ArrayList<>();
        String sql = "SELECT id_categoria, codigo, nombre, descripcion FROM categorias WHERE codigo LIKE ? OR nombre LIKE ?";
        try (Connection cn = ConexionBD.getConnection();
             PreparedStatement pst = cn.prepareStatement(sql)) {
            String busqueda = "%" + criterio + "%";
            pst.setString(1, busqueda);
            pst.setString(2, busqueda);
            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    CategoriaClass cat = new CategoriaClass();
                    cat.setIdCategoria(rs.getInt("id_categoria"));
                    cat.setCodigo(rs.getString("codigo"));
                    cat.setNombreCategoria(rs.getString("nombre"));
                    cat.setDescripcion(rs.getString("descripcion"));
                    lista.add(cat);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al buscar categorías: " + e.getMessage());
        }
        return lista;
    }

    public boolean actualizar(CategoriaClass cat) {
        String sql = "UPDATE categorias SET codigo=?, nombre=?, descripcion=? WHERE id_categoria=?";
        try (Connection cn = ConexionBD.getConnection();
             PreparedStatement pst = cn.prepareStatement(sql)) {
            pst.setString(1, cat.getCodigo() != null ? cat.getCodigo() : "");
            pst.setString(2, cat.getNombreCategoria());
            pst.setString(3, cat.getDescripcion() != null ? cat.getDescripcion() : "");
            pst.setInt(4, cat.getIdCategoria());
            return pst.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error al actualizar categoría: " + e.getMessage());
            return false;
        }
    }

    public boolean eliminar(int id) {
        String sql = "DELETE FROM categorias WHERE id_categoria=?";
        try (Connection cn = ConexionBD.getConnection();
             PreparedStatement pst = cn.prepareStatement(sql)) {
            pst.setInt(1, id);
            return pst.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error al eliminar categoría: " + e.getMessage());
            return false;
        }
    }
}
