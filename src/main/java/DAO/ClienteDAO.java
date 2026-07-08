package DAO;

import CONEX.ConexionBD;
import LOGICA.ClienteClass;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

public class ClienteDAO {

    // LISTAR TODOS LOS CLIENTES
    public List<ClienteClass> listarClientes() {
        List<ClienteClass> clientes = new ArrayList<>();
        String sql = "SELECT id_cliente, tipo_cliente, tipo_documento, numero_documento, nombres, apellidos, fecha_nacimiento, "
                + "razon_social, nombre_comercial, representante_legal, tipo_documento_representante, numero_documento_representante, "
                + "telefono, correo, direccion, direccion_fiscal, distrito, provincia, departamento, estado, creado_en, actualizado_en "
                + "FROM clientes ORDER BY id_cliente";

        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                clientes.add(mapearCliente(rs));
            }

        } catch (SQLException e) {
            mostrarError("Error al listar clientes", e);
        }

        return clientes;
    }

    // BUSCAR CLIENTES POR DOCUMENTO, NOMBRE, RAZÓN SOCIAL, CORREO O TELÉFONO
    public List<ClienteClass> buscarClientes(String criterio) {
        List<ClienteClass> clientes = new ArrayList<>();
        String sql = "SELECT id_cliente, tipo_cliente, tipo_documento, numero_documento, nombres, apellidos, fecha_nacimiento, "
                + "razon_social, nombre_comercial, representante_legal, tipo_documento_representante, numero_documento_representante, "
                + "telefono, correo, direccion, direccion_fiscal, distrito, provincia, departamento, estado, creado_en, actualizado_en "
                + "FROM clientes WHERE numero_documento LIKE ? OR nombres LIKE ? OR apellidos LIKE ? OR razon_social LIKE ? "
                + "OR nombre_comercial LIKE ? OR telefono LIKE ? OR correo LIKE ? OR estado LIKE ? ORDER BY id_cliente";

        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            String filtro = "%" + criterio + "%";
            for (int i = 1; i <= 8; i++) {
                stmt.setString(i, filtro);
            }

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    clientes.add(mapearCliente(rs));
                }
            }

        } catch (SQLException e) {
            mostrarError("Error al buscar clientes", e);
        }

        return clientes;
    }

    // BUSCAR CLIENTE POR ID
    public ClienteClass buscarPorId(int idCliente) {
        String sql = "SELECT id_cliente, tipo_cliente, tipo_documento, numero_documento, nombres, apellidos, fecha_nacimiento, "
                + "razon_social, nombre_comercial, representante_legal, tipo_documento_representante, numero_documento_representante, "
                + "telefono, correo, direccion, direccion_fiscal, distrito, provincia, departamento, estado, creado_en, actualizado_en "
                + "FROM clientes WHERE id_cliente = ?";

        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idCliente);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapearCliente(rs);
                }
            }

        } catch (SQLException e) {
            mostrarError("Error al buscar el cliente", e);
        }

        return null;
    }

    // BUSCAR CLIENTE POR DOCUMENTO
    public ClienteClass buscarPorDocumento(String documento) {
        String sql = "SELECT id_cliente, tipo_cliente, tipo_documento, numero_documento, nombres, apellidos, fecha_nacimiento, "
                + "razon_social, nombre_comercial, representante_legal, tipo_documento_representante, numero_documento_representante, "
                + "telefono, correo, direccion, direccion_fiscal, distrito, provincia, departamento, estado, creado_en, actualizado_en "
                + "FROM clientes WHERE numero_documento = ?";

        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, documento);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapearCliente(rs);
                }
            }

        } catch (SQLException e) {
            mostrarError("Error al buscar el cliente por documento", e);
        }

        return null;
    }

    // REGISTRAR CLIENTE
    public boolean registrarCliente(ClienteClass cliente) {
        String sql = "INSERT INTO clientes (tipo_cliente, tipo_documento, numero_documento, nombres, apellidos, fecha_nacimiento, "
                + "razon_social, nombre_comercial, representante_legal, tipo_documento_representante, numero_documento_representante, "
                + "telefono, correo, direccion, direccion_fiscal, distrito, provincia, departamento, estado) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            prepararCliente(stmt, cliente, false);
            boolean registrado = stmt.executeUpdate() > 0;

            if (registrado) {
                try (ResultSet rs = stmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        cliente.setIdCliente(rs.getInt(1));
                    }
                }
            }

            return registrado;

        } catch (SQLException e) {
            mostrarError("Error al registrar el cliente", e);
            return false;
        }
    }

    // ACTUALIZAR CLIENTE
    public boolean actualizar(ClienteClass cliente) {
        String sql = "UPDATE clientes SET tipo_cliente = ?, tipo_documento = ?, numero_documento = ?, nombres = ?, apellidos = ?, fecha_nacimiento = ?, "
                + "razon_social = ?, nombre_comercial = ?, representante_legal = ?, tipo_documento_representante = ?, numero_documento_representante = ?, "
                + "telefono = ?, correo = ?, direccion = ?, direccion_fiscal = ?, distrito = ?, provincia = ?, departamento = ?, estado = ? "
                + "WHERE id_cliente = ?";

        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            prepararCliente(stmt, cliente, true);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            mostrarError("Error al actualizar el cliente", e);
            return false;
        }
    }

    // ELIMINAR CLIENTE
    public boolean eliminar(int idCliente) {
        String sql = "UPDATE clientes SET estado = 'Inactivo' WHERE id_cliente = ?";

        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idCliente);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            mostrarError("Error al eliminar el cliente", e);
            return false;
        }
    }

    // ELIMINAR CLIENTE, NOMBRE COMPATIBLE CON EL ESTILO DEL DOCENTE
    public boolean eliminarCliente(int idCliente) {
        return eliminar(idCliente);
    }

    private void prepararCliente(PreparedStatement stmt, ClienteClass c, boolean incluyeId) throws SQLException {
        stmt.setString(1, valor(c.getTipoCliente(), "PERSONA_NATURAL"));
        stmt.setString(2, valor(c.getTipoDocumento(), "DNI"));
        stmt.setString(3, valor(c.getNumeroDocumento(), c.getDocumento()));
        stmt.setString(4, c.getNombres() != null ? c.getNombres() : c.getNombreCliente());
        stmt.setString(5, c.getApellidos());
        stmt.setDate(6, c.getFechaNacimiento());
        stmt.setString(7, c.getRazonSocial());
        stmt.setString(8, c.getNombreComercial());
        stmt.setString(9, c.getRepresentanteLegal());
        stmt.setString(10, c.getTipoDocumentoRepresentante());
        stmt.setString(11, c.getNumeroDocumentoRepresentante());
        stmt.setString(12, c.getTelefono());
        stmt.setString(13, c.getCorreo());
        stmt.setString(14, c.getDireccion());
        stmt.setString(15, c.getDireccionFiscal());
        stmt.setString(16, c.getDistrito() != null ? c.getDistrito() : c.getCiudad());
        stmt.setString(17, c.getProvincia() != null ? c.getProvincia() : c.getRegion());
        stmt.setString(18, c.getDepartamento() != null ? c.getDepartamento() : c.getPais());
        stmt.setString(19, valor(c.getEstado(), "Activo"));

        if (incluyeId) {
            stmt.setInt(20, c.getIdCliente());
        }
    }

    private ClienteClass mapearCliente(ResultSet rs) throws SQLException {
        ClienteClass cliente = new ClienteClass();
        cliente.setIdCliente(rs.getInt("id_cliente"));
        cliente.setTipoCliente(rs.getString("tipo_cliente"));
        cliente.setTipoDocumento(rs.getString("tipo_documento"));
        cliente.setNumeroDocumento(rs.getString("numero_documento"));
        cliente.setDocumento(rs.getString("numero_documento"));
        cliente.setNombres(rs.getString("nombres"));
        cliente.setNombreCliente(rs.getString("nombres"));
        cliente.setApellidos(rs.getString("apellidos"));
        cliente.setFechaNacimiento(rs.getDate("fecha_nacimiento"));
        cliente.setRazonSocial(rs.getString("razon_social"));
        cliente.setNombreComercial(rs.getString("nombre_comercial"));
        cliente.setRepresentanteLegal(rs.getString("representante_legal"));
        cliente.setTipoDocumentoRepresentante(rs.getString("tipo_documento_representante"));
        cliente.setNumeroDocumentoRepresentante(rs.getString("numero_documento_representante"));
        cliente.setTelefono(rs.getString("telefono"));
        cliente.setCorreo(rs.getString("correo"));
        cliente.setDireccion(rs.getString("direccion"));
        cliente.setDireccionFiscal(rs.getString("direccion_fiscal"));
        cliente.setDistrito(rs.getString("distrito"));
        cliente.setProvincia(rs.getString("provincia"));
        cliente.setDepartamento(rs.getString("departamento"));
        cliente.setCiudad(rs.getString("distrito"));
        cliente.setRegion(rs.getString("provincia"));
        cliente.setPais(rs.getString("departamento"));
        cliente.setEstado(rs.getString("estado"));
        cliente.setCreadoEn(rs.getTimestamp("creado_en"));
        cliente.setActualizadoEn(rs.getTimestamp("actualizado_en"));
        return cliente;
    }

    private String valor(String actual, String defecto) {
        return actual == null || actual.trim().isEmpty() ? defecto : actual;
    }

    private void mostrarError(String mensaje, SQLException e) {
        JOptionPane.showMessageDialog(null, mensaje + ": " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        e.printStackTrace();
    }
}
