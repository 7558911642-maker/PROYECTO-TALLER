package DAO;

import CONEX.ConexionBD;
import LOGICA.BitacoraClass;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

public class BitacoraDAO {

    // LISTAR BITÁCORA
    public List<BitacoraClass> listar() {
        List<BitacoraClass> bitacora = new ArrayList<>();
        String sql = "SELECT b.id_bitacora, b.id_usuario, u.nombres AS usuario_nombre, b.accion, b.modulo, b.tabla_afectada, b.id_registro, b.detalle, b.direccion_ip, b.fecha "
                + "FROM bitacora b LEFT JOIN usuarios u ON u.id_usuario = b.id_usuario ORDER BY b.fecha DESC";

        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                bitacora.add(mapearBitacora(rs));
            }

        } catch (SQLException e) {
            mostrarError("Error al listar bitácora", e);
        }

        return bitacora;
    }

    // BUSCAR BITÁCORA POR MÓDULO O ACCIÓN
    public List<BitacoraClass> buscar(String criterio) {
        List<BitacoraClass> bitacora = new ArrayList<>();
        String sql = "SELECT b.id_bitacora, b.id_usuario, u.nombres AS usuario_nombre, b.accion, b.modulo, b.tabla_afectada, b.id_registro, b.detalle, b.direccion_ip, b.fecha "
                + "FROM bitacora b LEFT JOIN usuarios u ON u.id_usuario = b.id_usuario "
                + "WHERE b.accion LIKE ? OR b.modulo LIKE ? OR b.tabla_afectada LIKE ? OR u.nombres LIKE ? ORDER BY b.fecha DESC";

        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            String filtro = "%" + criterio + "%";
            stmt.setString(1, filtro);
            stmt.setString(2, filtro);
            stmt.setString(3, filtro);
            stmt.setString(4, filtro);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    bitacora.add(mapearBitacora(rs));
                }
            }

        } catch (SQLException e) {
            mostrarError("Error al buscar bitácora", e);
        }

        return bitacora;
    }

    // REGISTRAR BITÁCORA
    public boolean registrar(BitacoraClass bitacora) {
        String sql = "INSERT INTO bitacora (id_usuario, accion, modulo, tabla_afectada, id_registro, detalle, direccion_ip, fecha) VALUES (?, ?, ?, ?, ?, ?, ?, NOW())";

        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            if (bitacora.getIdUsuario() == null || bitacora.getIdUsuario() <= 0) {
                stmt.setNull(1, java.sql.Types.INTEGER);
            } else {
                stmt.setInt(1, bitacora.getIdUsuario());
            }
            stmt.setString(2, bitacora.getAccion());
            stmt.setString(3, bitacora.getModulo());
            stmt.setString(4, bitacora.getTablaAfectada());
            stmt.setString(5, bitacora.getIdRegistro());
            stmt.setString(6, bitacora.getDetalle());
            stmt.setString(7, bitacora.getDireccionIp());

            boolean registrado = stmt.executeUpdate() > 0;
            if (registrado) {
                try (ResultSet rs = stmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        bitacora.setIdBitacora(rs.getLong(1));
                    }
                }
            }
            return registrado;

        } catch (SQLException e) {
            mostrarError("Error al registrar bitácora", e);
            return false;
        }
    }

    private BitacoraClass mapearBitacora(ResultSet rs) throws SQLException {
        BitacoraClass bitacora = new BitacoraClass();
        bitacora.setIdBitacora(rs.getLong("id_bitacora"));
        bitacora.setIdUsuario((Integer) rs.getObject("id_usuario"));
        bitacora.setUsuarioNombre(rs.getString("usuario_nombre"));
        bitacora.setAccion(rs.getString("accion"));
        bitacora.setModulo(rs.getString("modulo"));
        bitacora.setTablaAfectada(rs.getString("tabla_afectada"));
        bitacora.setIdRegistro(rs.getString("id_registro"));
        bitacora.setDetalle(rs.getString("detalle"));
        bitacora.setDireccionIp(rs.getString("direccion_ip"));
        bitacora.setFecha(rs.getTimestamp("fecha"));
        return bitacora;
    }

    private void mostrarError(String mensaje, SQLException e) {
        JOptionPane.showMessageDialog(null, mensaje + ": " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        e.printStackTrace();
    }
}
