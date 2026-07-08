package DAO;

import CONEX.ConexionBD;
import LOGICA.CategoriaClass;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

public class CategoriaDAO {

    // LISTAR TODAS LAS CATEGORÍAS
    public List<CategoriaClass> listar() {
        List<CategoriaClass> categorias = new ArrayList<>();
        String sql = "SELECT id_categoria, codigo, nombre, descripcion, estado, creado_en, actualizado_en FROM categorias ORDER BY id_categoria";

        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                categorias.add(mapearCategoria(rs));
            }

        } catch (SQLException e) {
            mostrarError("Error al listar categorías", e);
        }

        return categorias;
    }

    // BUSCAR CATEGORÍAS POR CÓDIGO, NOMBRE O DESCRIPCIÓN
    public List<CategoriaClass> buscar(String criterio) {
        List<CategoriaClass> categorias = new ArrayList<>();
        String sql = "SELECT id_categoria, codigo, nombre, descripcion, estado, creado_en, actualizado_en FROM categorias "
                + "WHERE codigo LIKE ? OR nombre LIKE ? OR descripcion LIKE ? OR estado LIKE ? ORDER BY id_categoria";

        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            String filtro = "%" + criterio + "%";
            stmt.setString(1, filtro);
            stmt.setString(2, filtro);
            stmt.setString(3, filtro);
            stmt.setString(4, filtro);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    categorias.add(mapearCategoria(rs));
                }
            }

        } catch (SQLException e) {
            mostrarError("Error al buscar categorías", e);
        }

        return categorias;
    }

    // BUSCAR CATEGORÍAS POR NOMBRE
    public List<CategoriaClass> buscarPorNombre(String nombre) {
        return buscar(nombre);
    }

    // BUSCAR UNA CATEGORÍA POR ID
    public CategoriaClass buscarPorId(int idCategoria) {
        String sql = "SELECT id_categoria, codigo, nombre, descripcion, estado, creado_en, actualizado_en FROM categorias WHERE id_categoria = ?";

        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idCategoria);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapearCategoria(rs);
                }
            }

        } catch (SQLException e) {
            mostrarError("Error al buscar la categoría", e);
        }

        return null;
    }

    // REGISTRAR UNA CATEGORÍA
    public boolean registrar(CategoriaClass categoria) {
        String sql = "INSERT INTO categorias (codigo, nombre, descripcion, estado) VALUES (?, ?, ?, ?)";

        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, categoria.getCodigo());
            stmt.setString(2, categoria.getNombreCategoria());
            stmt.setString(3, categoria.getDescripcion());
            stmt.setString(4, categoria.getEstado() == null ? "Activo" : categoria.getEstado());

            boolean registrado = stmt.executeUpdate() > 0;

            if (registrado) {
                try (ResultSet rs = stmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        categoria.setIdCategoria(rs.getInt(1));
                    }
                }
            }

            return registrado;

        } catch (SQLException e) {
            mostrarError("Error al registrar la categoría", e);
            return false;
        }
    }

    // ACTUALIZAR UNA CATEGORÍA
    public boolean actualizar(CategoriaClass categoria) {
        String sql = "UPDATE categorias SET codigo = ?, nombre = ?, descripcion = ?, estado = ? WHERE id_categoria = ?";

        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, categoria.getCodigo());
            stmt.setString(2, categoria.getNombreCategoria());
            stmt.setString(3, categoria.getDescripcion());
            stmt.setString(4, categoria.getEstado() == null ? "Activo" : categoria.getEstado());
            stmt.setInt(5, categoria.getIdCategoria());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            mostrarError("Error al actualizar la categoría", e);
            return false;
        }
    }

    // ACTUALIZAR UNA CATEGORÍA, NOMBRE COMPATIBLE CON EL ESTILO DEL DOCENTE
    public boolean actualizarCategoria(CategoriaClass categoria) {
        return actualizar(categoria);
    }

    // ELIMINAR UNA CATEGORÍA
    public boolean eliminar(int idCategoria) {
        String sql = "UPDATE categorias SET estado = 'Inactivo' WHERE id_categoria = ?";

        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idCategoria);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            mostrarError("Error al eliminar la categoría", e);
            return false;
        }
    }

    // ELIMINAR UNA CATEGORÍA, NOMBRE COMPATIBLE CON EL ESTILO DEL DOCENTE
    public boolean eliminarCategoria(int idCategoria) {
        return eliminar(idCategoria);
    }

    private CategoriaClass mapearCategoria(ResultSet rs) throws SQLException {
        CategoriaClass categoria = new CategoriaClass();
        categoria.setIdCategoria(rs.getInt("id_categoria"));
        categoria.setCodigo(rs.getString("codigo"));
        categoria.setNombreCategoria(rs.getString("nombre"));
        categoria.setDescripcion(rs.getString("descripcion"));
        categoria.setEstado(rs.getString("estado"));
        categoria.setCreadoEn(rs.getTimestamp("creado_en"));
        categoria.setActualizadoEn(rs.getTimestamp("actualizado_en"));
        return categoria;
    }

    private void mostrarError(String mensaje, SQLException e) {
        JOptionPane.showMessageDialog(null, mensaje + ": " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        e.printStackTrace();
    }
}
