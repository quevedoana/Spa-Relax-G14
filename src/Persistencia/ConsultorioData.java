/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Persistencia;

import Modelo.Consultorio;
import Modelo.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author Anitabonita
 */
public class ConsultorioData {
    private Connection conexion = null;
    
    public ConsultorioData() {
        conexion = Conexion.getConexion();
    }
    
    public void guardarConsultorio(Consultorio con) {
        String query = "INSERT INTO consultorio (nroConsultorio, usos, equipamiento, apto) VALUES (?, ?, ?, ?";
        try {
            PreparedStatement ps = conexion.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, con.getNroConsultorio());
            ps.setString(2, con.getUsos());
            ps.setString(3, con.getEquipamiento());
            ps.setBoolean(4, con.isApto());
            ps.executeUpdate();
            
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                con.setNroConsultorio(rs.getInt(1));
            } else {
                JOptionPane.showMessageDialog(null, "No se pudo obtener el numero del consultorio");
            }
            ps.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al guardar el consultorio: " + e.getMessage());
        }
    }
    
    public Consultorio buscarConsultorio(int nroConsultorio) {
        String sql = "SELECT * FROM consultorio WHERE nroConsultorio = ?";
        Consultorio cons = null;
        
        try {
            PreparedStatement ps = conexion.prepareStatement(sql);
            ps.setInt(1, nroConsultorio);
            ResultSet resultado = ps.executeQuery();
            
            if (resultado.next()) {
                cons = new Consultorio(
                resultado.getInt("nroConsultorio"),
                resultado.getString("usos"),
                resultado.getString("equipamiento"),
                resultado.getBoolean("apto")
                );
            } else {
                System.out.println("Nose encontro el consultorio con numero " + nroConsultorio);
        }
            ps.close();
        } catch (SQLException e) {
            JOptionPane.showConfirmDialog(null, "Error al buscar el consultorio: " + e.getMessage());
        }
        return cons;
    }
    
    
    public List<Consultorio> ListarConsultorios() {
        String sql = "SELECT * FROM consultorio";
        List<Consultorio> consultorios = new ArrayList<>();
        
        try {
            PreparedStatement ps = conexion.prepareStatement(sql);
            ResultSet resultado = ps.executeQuery();
            
            while (resultado.next()) {
                Consultorio con = new Consultorio(
                resultado.getInt("nroConsultorio"),
                resultado.getString("usos"),
                resultado.getString("equipamiento"),
                resultado.getBoolean("apto")
                );
                consultorios.add(con);
            }
            
            ps.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al listar los consultorios: " + e.getMessage());
        }
        return consultorios;
    }
    
    public void actualizarConsultorio(Consultorio con) {
        String query = "UPDATE consultorio SET usos = ?, equipamiento = ?, apto = ? WHERE nroConsultorio = ?";
        
        try {
            PreparedStatement ps = conexion.prepareStatement(query);
            ps.setString(1, con.getUsos());
            ps.setString(2, con.getEquipamiento());
            ps.setBoolean(3, con.isApto());
            ps.setInt(4, con.getNroConsultorio());
            ps.executeUpdate();
            
            ps.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al actualizar el consultorio: " + e.getMessage());
        }
    }
    
    public void borrarConsultorio(int nroConsultorio) {
        String query = "DELETE FROM consultorio WHERE nroConsultorio = ?";
        
        try {
            PreparedStatement ps = conexion.prepareStatement(query);
            ps.setInt(1, nroConsultorio);
            ps.executeUpdate();
            
            ps.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al eliminar el consultorio: " + e.getMessage());
        }
    }
    
    public void habilitarConsultorio(Consultorio c) {
        String query = "UPDATE consultorio SET apto = 1 WHERE nroConsultorio = ? AND apto = 0";

        try {
            PreparedStatement ps = conexion.prepareStatement(query);
            ps.setInt(1, c.getNroConsultorio());
            ps.executeUpdate();

            ps.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al habilitar el consultorio: " + e.getMessage());
        }
    }
    
    public void deshabilitarConsultorio(Consultorio c) {
        String query = "UPDATE consultorio SET apto = 0 WHERE nroConsultorio = ? AND apto = 1";

        try {
            PreparedStatement ps = conexion.prepareStatement(query);
            ps.setInt(1, c.getNroConsultorio());
            ps.executeUpdate();

            ps.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al deshabilitar el consultorio: " + e.getMessage());
        }
    }
}
