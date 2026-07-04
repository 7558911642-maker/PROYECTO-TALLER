package DAO;

import CONEX.ConexionBD;
import LOGICA.ProveedorClass;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProveedorDAO {

    public List<ProveedorClass> listar() {
        List<ProveedorClass> lista = new ArrayList<>();
        String sql = "SELECT id_proveedor, ruc, razon_social, telefono, correo, direccion, estado FROM proveedores";

        try (Connection cn = ConexionBD.getConnection();
             PreparedStatement pst = cn.prepareStatement(sql);
             ResultSet rs = pst.executeQuery()) {

            while (rs.next()) {
                lista.add(mapper(rs));
            }
        } catch (SQLException e) {
            System.out.println("Error al listar proveedores: " + e.getMessage());
        }
        return lista;
    }

    public boolean registrar(ProveedorClass prov) {
        String sql = "INSERT INTO proveedores (ruc, razon_social, telefono, correo, direccion, estado) VALUES (?, ?, ?, ?, ?, 'Activo')";
        try (Connection cn = ConexionBD.getConnection();
             PreparedStatement pst = cn.prepareStatement(sql)) {
            pst.setString(1, prov.getRuc() != null ? prov.getRuc() : "");
            pst.setString(2, prov.getNombreProveedor());
            pst.setString(3, prov.getTelefono() != null ? prov.getTelefono() : "");
            pst.setString(4, prov.getCorreo() != null ? prov.getCorreo() : "");
            pst.setString(5, prov.getDireccion() != null ? prov.getDireccion() : "");
            return pst.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error al registrar proveedor: " + e.getMessage());
            return false;
        }
    }

    public List<ProveedorClass> buscar(String criterio) {
        List<ProveedorClass> lista = new ArrayList<>();
        String sql = "SELECT id_proveedor, ruc, razon_social, telefono, correo, direccion, estado "
                + "FROM proveedores WHERE ruc LIKE ? OR razon_social LIKE ?";
        try (Connection cn = ConexionBD.getConnection();
             PreparedStatement pst = cn.prepareStatement(sql)) {
            String busqueda = "%" + criterio + "%";
            pst.setString(1, busqueda);
            pst.setString(2, busqueda);
            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    lista.add(mapper(rs));
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al buscar proveedores: " + e.getMessage());
        }
        return lista;
    }

    public boolean actualizar(ProveedorClass prov) {
        String sql = "UPDATE proveedores SET ruc=?, razon_social=?, telefono=?, correo=?, direccion=? WHERE id_proveedor=?";
        try (Connection cn = ConexionBD.getConnection();
             PreparedStatement pst = cn.prepareStatement(sql)) {
            pst.setString(1, prov.getRuc() != null ? prov.getRuc() : "");
            pst.setString(2, prov.getNombreProveedor());
            pst.setString(3, prov.getTelefono() != null ? prov.getTelefono() : "");
            pst.setString(4, prov.getCorreo() != null ? prov.getCorreo() : "");
            pst.setString(5, prov.getDireccion() != null ? prov.getDireccion() : "");
            pst.setInt(6, prov.getIdProveedor());
            return pst.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error al actualizar proveedor: " + e.getMessage());
            return false;
        }
    }

    public boolean eliminar(int id) {
        String sql = "UPDATE proveedores SET estado='Inactivo' WHERE id_proveedor=?";
        try (Connection cn = ConexionBD.getConnection();
             PreparedStatement pst = cn.prepareStatement(sql)) {
            pst.setInt(1, id);
            return pst.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error al eliminar proveedor: " + e.getMessage());
            return false;
        }
    }

    private ProveedorClass mapper(ResultSet rs) throws SQLException {
        ProveedorClass prov = new ProveedorClass();
        prov.setIdProveedor(rs.getInt("id_proveedor"));
        prov.setRuc(rs.getString("ruc"));
        prov.setNombreProveedor(rs.getString("razon_social"));
        prov.setTelefono(rs.getString("telefono"));
        prov.setCorreo(rs.getString("correo"));
        prov.setDireccion(rs.getString("direccion"));
        prov.setEstado(rs.getString("estado"));
        return prov;
    }
}
