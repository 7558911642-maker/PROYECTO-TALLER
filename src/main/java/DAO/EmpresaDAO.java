package DAO;

import CONEX.ConexionBD;
import LOGICA.EmpresaClass;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

public class EmpresaDAO {

    // LISTAR EMPRESAS
    public List<EmpresaClass> listar() {
        List<EmpresaClass> empresas = new ArrayList<>();
        String sql = "SELECT id_empresa, razon_social, nombre_comercial, ruc, direccion, telefono, correo, logo, porcentaje_igv, dias_alerta_vencimiento, moneda, estado, creado_en, actualizado_en FROM empresa ORDER BY id_empresa";

        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                empresas.add(mapearEmpresa(rs));
            }

        } catch (SQLException e) {
            mostrarError("Error al listar empresas", e);
        }

        return empresas;
    }

    // BUSCAR EMPRESA ACTIVA
    public EmpresaClass obtenerEmpresaActiva() {
        String sql = "SELECT id_empresa, razon_social, nombre_comercial, ruc, direccion, telefono, correo, logo, porcentaje_igv, dias_alerta_vencimiento, moneda, estado, creado_en, actualizado_en FROM empresa WHERE estado = 'Activo' ORDER BY id_empresa LIMIT 1";

        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            if (rs.next()) {
                return mapearEmpresa(rs);
            }

        } catch (SQLException e) {
            mostrarError("Error al obtener empresa activa", e);
        }

        return null;
    }

    // ACTUALIZAR DATOS DE LA EMPRESA
    public boolean actualizar(EmpresaClass empresa) {
        String sql = "UPDATE empresa SET razon_social = ?, nombre_comercial = ?, ruc = ?, direccion = ?, telefono = ?, correo = ?, logo = ?, porcentaje_igv = ?, dias_alerta_vencimiento = ?, moneda = ?, estado = ? WHERE id_empresa = ?";

        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, empresa.getRazonSocial());
            stmt.setString(2, empresa.getNombreComercial());
            stmt.setString(3, empresa.getRuc());
            stmt.setString(4, empresa.getDireccion());
            stmt.setString(5, empresa.getTelefono());
            stmt.setString(6, empresa.getCorreo());
            stmt.setString(7, empresa.getLogo());
            stmt.setBigDecimal(8, empresa.getPorcentajeIgv());
            stmt.setInt(9, empresa.getDiasAlertaVencimiento());
            stmt.setString(10, empresa.getMoneda());
            stmt.setString(11, empresa.getEstado() == null ? "Activo" : empresa.getEstado());
            stmt.setInt(12, empresa.getIdEmpresa());
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            mostrarError("Error al actualizar empresa", e);
            return false;
        }
    }

    private EmpresaClass mapearEmpresa(ResultSet rs) throws SQLException {
        EmpresaClass empresa = new EmpresaClass();
        empresa.setIdEmpresa(rs.getInt("id_empresa"));
        empresa.setRazonSocial(rs.getString("razon_social"));
        empresa.setNombreComercial(rs.getString("nombre_comercial"));
        empresa.setRuc(rs.getString("ruc"));
        empresa.setDireccion(rs.getString("direccion"));
        empresa.setTelefono(rs.getString("telefono"));
        empresa.setCorreo(rs.getString("correo"));
        empresa.setLogo(rs.getString("logo"));
        empresa.setPorcentajeIgv(rs.getBigDecimal("porcentaje_igv"));
        empresa.setDiasAlertaVencimiento(rs.getInt("dias_alerta_vencimiento"));
        empresa.setMoneda(rs.getString("moneda"));
        empresa.setEstado(rs.getString("estado"));
        empresa.setCreadoEn(rs.getTimestamp("creado_en"));
        empresa.setActualizadoEn(rs.getTimestamp("actualizado_en"));
        return empresa;
    }

    private void mostrarError(String mensaje, SQLException e) {
        JOptionPane.showMessageDialog(null, mensaje + ": " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        e.printStackTrace();
    }
}
