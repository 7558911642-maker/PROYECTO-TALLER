package DAO;

import CONEX.ConexionBD;
import LOGICA.EmpleadoClass;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EmpleadoDAO {

    public List<EmpleadoClass> listar() {
        List<EmpleadoClass> lista = new ArrayList<>();
        String sql = "SELECT id_usuario, usuario, nombres, rol FROM usuarios";

        try (Connection cn = ConexionBD.getConnection();
             PreparedStatement pst = cn.prepareStatement(sql);
             ResultSet rs = pst.executeQuery()) {

            while (rs.next()) {
                EmpleadoClass emp = new EmpleadoClass();
                emp.setIdEmpleado(rs.getInt("id_usuario"));
                emp.setNombre(rs.getString("nombres"));
                emp.setApellidos(rs.getString("usuario"));
                emp.setCargo(rs.getString("rol"));
                lista.add(emp);
            }
        } catch (SQLException e) {
            System.out.println("Error al listar empleados: " + e.getMessage());
        }
        return lista;
    }

}
