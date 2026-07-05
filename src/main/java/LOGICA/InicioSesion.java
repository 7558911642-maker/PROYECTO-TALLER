/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package LOGICA;

import CONEX.ConexionBD;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author HP
 */
public class InicioSesion {
    public static boolean validarCredenciales(String usuario,String contraseña){
        
        String sql = "SELECT * FROM usuarios WHERE usuario = ? AND contrasena = ?";
        
        try (Connection conn = ConexionBD.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)){
            
            pstmt.setString(1,usuario);
            pstmt.setString(2,contraseña);
            
            try (ResultSet rs = pstmt.executeQuery()){
                return rs.next();
                
            }
            
        }catch (SQLException e){
            e.printStackTrace();
            return false;
        }
        
    }
}
