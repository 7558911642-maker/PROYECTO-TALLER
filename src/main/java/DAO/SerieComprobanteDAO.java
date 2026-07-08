package DAO;

import CONEX.ConexionBD;
import LOGICA.SerieComprobanteClass;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

public class SerieComprobanteDAO {

    // LISTAR SERIES DE COMPROBANTES
    public List<SerieComprobanteClass> listar() {
        List<SerieComprobanteClass> series = new ArrayList<>();
        String sql = "SELECT id_serie, tipo_comprobante, serie, correlativo_actual, estado, creado_en FROM series_comprobantes ORDER BY id_serie";

        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                series.add(mapearSerie(rs));
            }

        } catch (SQLException e) {
            mostrarError("Error al listar series", e);
        }

        return series;
    }

    // BUSCAR SERIE ACTIVA POR TIPO DE COMPROBANTE
    public SerieComprobanteClass buscarSerieActiva(String tipoComprobante) {
        String sql = "SELECT id_serie, tipo_comprobante, serie, correlativo_actual, estado, creado_en FROM series_comprobantes WHERE tipo_comprobante = ? AND estado = 'Activo' ORDER BY id_serie LIMIT 1";

        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, tipoComprobante);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapearSerie(rs);
                }
            }

        } catch (SQLException e) {
            mostrarError("Error al buscar la serie", e);
        }

        return null;
    }

    private SerieComprobanteClass mapearSerie(ResultSet rs) throws SQLException {
        SerieComprobanteClass serie = new SerieComprobanteClass();
        serie.setIdSerie(rs.getInt("id_serie"));
        serie.setTipoComprobante(rs.getString("tipo_comprobante"));
        serie.setSerie(rs.getString("serie"));
        serie.setCorrelativoActual(rs.getInt("correlativo_actual"));
        serie.setEstado(rs.getString("estado"));
        serie.setCreadoEn(rs.getTimestamp("creado_en"));
        return serie;
    }

    private void mostrarError(String mensaje, SQLException e) {
        JOptionPane.showMessageDialog(null, mensaje + ": " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        e.printStackTrace();
    }
}
