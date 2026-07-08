package DAO;

import CONEX.ConexionBD;
import LOGICA.MetodoPagoClass;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

public class MetodoPagoDAO {

    // LISTAR MÉTODOS DE PAGO
    public List<MetodoPagoClass> listar() {
        List<MetodoPagoClass> metodos = new ArrayList<>();
        String sql = "SELECT id_metodo_pago, nombre, requiere_referencia, estado FROM metodos_pago ORDER BY id_metodo_pago";

        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                metodos.add(mapearMetodoPago(rs));
            }

        } catch (SQLException e) {
            mostrarError("Error al listar métodos de pago", e);
        }

        return metodos;
    }

    // BUSCAR MÉTODOS DE PAGO
    public List<MetodoPagoClass> buscar(String criterio) {
        List<MetodoPagoClass> metodos = new ArrayList<>();
        String sql = "SELECT id_metodo_pago, nombre, requiere_referencia, estado FROM metodos_pago WHERE nombre LIKE ? OR estado LIKE ? ORDER BY id_metodo_pago";

        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            String filtro = "%" + criterio + "%";
            stmt.setString(1, filtro);
            stmt.setString(2, filtro);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    metodos.add(mapearMetodoPago(rs));
                }
            }

        } catch (SQLException e) {
            mostrarError("Error al buscar métodos de pago", e);
        }

        return metodos;
    }

    private MetodoPagoClass mapearMetodoPago(ResultSet rs) throws SQLException {
        MetodoPagoClass metodo = new MetodoPagoClass();
        metodo.setIdMetodoPago(rs.getInt("id_metodo_pago"));
        metodo.setNombre(rs.getString("nombre"));
        metodo.setRequiereReferencia(rs.getBoolean("requiere_referencia"));
        metodo.setEstado(rs.getString("estado"));
        return metodo;
    }

    private void mostrarError(String mensaje, SQLException e) {
        JOptionPane.showMessageDialog(null, mensaje + ": " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        e.printStackTrace();
    }
}
