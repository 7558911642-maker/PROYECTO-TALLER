package DAO;

import CONEX.ConexionBD;
import LOGICA.LaboratorioClass;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

public class LaboratorioDAO {

    // LISTAR LABORATORIOS
    public List<LaboratorioClass> listar() {
        List<LaboratorioClass> laboratorios = new ArrayList<>();
        String sql = "SELECT id_laboratorio, nombre, pais, telefono, correo, estado, creado_en FROM laboratorios ORDER BY nombre";

        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                laboratorios.add(mapearLaboratorio(rs));
            }

        } catch (SQLException e) {
            mostrarError("Error al listar laboratorios", e);
        }

        return laboratorios;
    }

    // BUSCAR LABORATORIOS POR NOMBRE O PAÍS
    public List<LaboratorioClass> buscar(String criterio) {
        List<LaboratorioClass> laboratorios = new ArrayList<>();
        String sql = "SELECT id_laboratorio, nombre, pais, telefono, correo, estado, creado_en FROM laboratorios WHERE nombre LIKE ? OR pais LIKE ? OR estado LIKE ? ORDER BY nombre";

        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            String filtro = "%" + criterio + "%";
            stmt.setString(1, filtro);
            stmt.setString(2, filtro);
            stmt.setString(3, filtro);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    laboratorios.add(mapearLaboratorio(rs));
                }
            }

        } catch (SQLException e) {
            mostrarError("Error al buscar laboratorios", e);
        }

        return laboratorios;
    }

    // REGISTRAR LABORATORIO
    public boolean registrar(LaboratorioClass laboratorio) {
        String sql = "INSERT INTO laboratorios (nombre, pais, telefono, correo, estado) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, laboratorio.getNombre());
            stmt.setString(2, laboratorio.getPais());
            stmt.setString(3, laboratorio.getTelefono());
            stmt.setString(4, laboratorio.getCorreo());
            stmt.setString(5, laboratorio.getEstado() == null ? "Activo" : laboratorio.getEstado());

            boolean registrado = stmt.executeUpdate() > 0;
            if (registrado) {
                try (ResultSet rs = stmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        laboratorio.setIdLaboratorio(rs.getInt(1));
                    }
                }
            }
            return registrado;

        } catch (SQLException e) {
            mostrarError("Error al registrar el laboratorio", e);
            return false;
        }
    }

    // ACTUALIZAR LABORATORIO
    public boolean actualizar(LaboratorioClass laboratorio) {
        String sql = "UPDATE laboratorios SET nombre = ?, pais = ?, telefono = ?, correo = ?, estado = ? WHERE id_laboratorio = ?";

        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, laboratorio.getNombre());
            stmt.setString(2, laboratorio.getPais());
            stmt.setString(3, laboratorio.getTelefono());
            stmt.setString(4, laboratorio.getCorreo());
            stmt.setString(5, laboratorio.getEstado() == null ? "Activo" : laboratorio.getEstado());
            stmt.setInt(6, laboratorio.getIdLaboratorio());
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            mostrarError("Error al actualizar el laboratorio", e);
            return false;
        }
    }

    // ELIMINAR LABORATORIO
    public boolean eliminar(int idLaboratorio) {
        String sql = "UPDATE laboratorios SET estado = 'Inactivo' WHERE id_laboratorio = ?";

        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idLaboratorio);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            mostrarError("Error al eliminar el laboratorio", e);
            return false;
        }
    }

    private LaboratorioClass mapearLaboratorio(ResultSet rs) throws SQLException {
        LaboratorioClass laboratorio = new LaboratorioClass();
        laboratorio.setIdLaboratorio(rs.getInt("id_laboratorio"));
        laboratorio.setNombre(rs.getString("nombre"));
        laboratorio.setPais(rs.getString("pais"));
        laboratorio.setTelefono(rs.getString("telefono"));
        laboratorio.setCorreo(rs.getString("correo"));
        laboratorio.setEstado(rs.getString("estado"));
        laboratorio.setCreadoEn(rs.getTimestamp("creado_en"));
        return laboratorio;
    }

    private void mostrarError(String mensaje, SQLException e) {
        JOptionPane.showMessageDialog(null, mensaje + ": " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        e.printStackTrace();
    }
}
