package DAO;

import CONEX.ConexionBD;
import LOGICA.ClienteClass;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ClienteDAO {

    public boolean registrarCliente(ClienteClass cli) {
        String sql = "INSERT INTO clientes (tipo_documento, numero_documento, nombres, apellidos, telefono, correo, direccion, estado) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, 'Activo')";

        try (Connection cn = ConexionBD.getConnection();
             PreparedStatement pst = cn.prepareStatement(sql)) {

            pst.setString(1, "DNI");
            pst.setString(2, cli.getDocumento() != null && !cli.getDocumento().isEmpty() ? cli.getDocumento() : "PEND-" + System.currentTimeMillis());
            pst.setString(3, cli.getNombreCliente());
            pst.setString(4, "");
            pst.setString(5, cli.getTelefono() != null ? cli.getTelefono() : "");
            pst.setString(6, "");
            pst.setString(7, cli.getDireccion() != null ? cli.getDireccion() : "");

            return pst.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error al registrar cliente: " + e.getMessage());
            return false;
        }
    }

    public List<ClienteClass> listarClientes() {
        List<ClienteClass> lista = new ArrayList<>();
        String sql = "SELECT id_cliente, tipo_documento, numero_documento, nombres, apellidos, telefono, correo, direccion, estado FROM clientes";

        try (Connection cn = ConexionBD.getConnection();
             PreparedStatement pst = cn.prepareStatement(sql);
             ResultSet rs = pst.executeQuery()) {

            while (rs.next()) {
                ClienteClass cli = new ClienteClass();
                cli.setIdCliente(rs.getInt("id_cliente"));
                cli.setDocumento(rs.getString("numero_documento"));
                cli.setNombreCliente(rs.getString("nombres") + " " + rs.getString("apellidos"));
                cli.setTelefono(rs.getString("telefono"));
                cli.setDireccion(rs.getString("direccion"));
                lista.add(cli);
            }
        } catch (SQLException e) {
            System.out.println("Error al listar clientes: " + e.getMessage());
        }
        return lista;
    }

    public ClienteClass buscarPorDocumento(String dni) {
        String sql = "SELECT id_cliente, tipo_documento, numero_documento, nombres, apellidos, telefono, correo, direccion, estado "
                + "FROM clientes WHERE numero_documento = ?";
        try (Connection cn = ConexionBD.getConnection();
             PreparedStatement pst = cn.prepareStatement(sql)) {
            pst.setString(1, dni);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    ClienteClass cli = new ClienteClass();
                    cli.setIdCliente(rs.getInt("id_cliente"));
                    cli.setDocumento(rs.getString("numero_documento"));
                    cli.setNombreCliente(rs.getString("nombres"));
                    cli.setApellidos(rs.getString("apellidos"));
                    cli.setTelefono(rs.getString("telefono"));
                    cli.setCorreo(rs.getString("correo"));
                    cli.setDireccion(rs.getString("direccion"));
                    cli.setEstado(rs.getString("estado"));
                    return cli;
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al buscar cliente por documento: " + e.getMessage());
        }
        return null;
    }

    public boolean actualizar(ClienteClass cli) {
        String sql = "UPDATE clientes SET numero_documento=?, nombres=?, apellidos=?, telefono=?, correo=?, direccion=?, estado=? WHERE id_cliente=?";
        try (Connection cn = ConexionBD.getConnection();
             PreparedStatement pst = cn.prepareStatement(sql)) {
            pst.setString(1, cli.getDocumento());
            pst.setString(2, cli.getNombreCliente());
            pst.setString(3, cli.getApellidos() != null ? cli.getApellidos() : "");
            pst.setString(4, cli.getTelefono() != null ? cli.getTelefono() : "");
            pst.setString(5, cli.getCorreo() != null ? cli.getCorreo() : "");
            pst.setString(6, cli.getDireccion() != null ? cli.getDireccion() : "");
            pst.setString(7, cli.getEstado() != null ? cli.getEstado() : "Activo");
            pst.setInt(8, cli.getIdCliente());
            return pst.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error al actualizar cliente: " + e.getMessage());
            return false;
        }
    }

    public List<ClienteClass> buscarClientes(String criterio) {
        List<ClienteClass> lista = new ArrayList<>();
        String sql = "SELECT id_cliente, tipo_documento, numero_documento, nombres, apellidos, telefono, correo, direccion, estado "
                + "FROM clientes WHERE numero_documento LIKE ? OR nombres LIKE ? OR apellidos LIKE ?";

        try (Connection cn = ConexionBD.getConnection();
             PreparedStatement pst = cn.prepareStatement(sql)) {

            String busqueda = "%" + criterio + "%";
            pst.setString(1, busqueda);
            pst.setString(2, busqueda);
            pst.setString(3, busqueda);

            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    ClienteClass cli = new ClienteClass();
                    cli.setIdCliente(rs.getInt("id_cliente"));
                    cli.setDocumento(rs.getString("numero_documento"));
                    cli.setNombreCliente(rs.getString("nombres") + " " + rs.getString("apellidos"));
                    cli.setTelefono(rs.getString("telefono"));
                    cli.setDireccion(rs.getString("direccion"));
                    lista.add(cli);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al buscar clientes: " + e.getMessage());
        }
        return lista;
    }

}
