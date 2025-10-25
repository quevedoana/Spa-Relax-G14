/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Persistencia;

import Modelo.Conexion;
import Modelo.Especialista;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;


public class EspecialistaData {
    private Connection conexion = null;

    public EspecialistaData() {
        this.conexion = Conexion.getConexion();
    }
    //Guardar Especialista
    public void guardarEspecialista(Especialista e) {
        try {
            //Masajista: matricula, nombre y ape, teléfono, especialidad (facial, corporal, relajación, o estético), estado
            String sql = "INSERT INTO especialista (matricula,NombreyApellido,telefono,especialidad,estado) VALUES (?,?,?,?,?);";
            PreparedStatement ps = conexion.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, e.getMatricula());
            ps.setString(2, e.getNombreYApellido());
            ps.setLong(3, e.getTelefono());
            ps.setString(4, e.getEspecialidad());
            ps.setBoolean(5, e.isEstado());
            ps.executeUpdate();

            
               
             JOptionPane.showMessageDialog(null, "Especialista Registrado");

            

        } catch (SQLException es) {
            JOptionPane.showMessageDialog(null, "Error al guardar el especialista" + es.getMessage());
        }
    }
    //Buscar Especialista
    public Especialista buscarEspecialista(String matricula) { //select 1 alumno
        String sql = "SELECT * FROM especialista WHERE matricula=?";
        Especialista esp = null;
        try {
            PreparedStatement ps = conexion.prepareStatement(sql);
            ps.setString(1, matricula);
            ResultSet resultado = ps.executeQuery();
            if (resultado.next()) {
                   //Masajista: matricula, nombre y ape, teléfono, especialidad (facial, corporal, relajación, o estético), estado
                esp = new Especialista(resultado.getString("Matricula"), resultado.getString("NombreyApellido"), resultado.getInt("Telefono"), resultado.getString("Especialidad"), resultado.getBoolean("Estado"));
                esp.setMatricula(resultado.getString("Matricula"));

            } else {
                System.out.println("No se encontro el alumno");
            }
            ps.close();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al buscar el Especialista" + e.getMessage());

        }
        return esp;
    }
    //Listar Especialista
    public List<Especialista> listarEspecialistas() { //select *
        String sql = "SELECT * FROM especialista";
        List<Especialista> especia = new ArrayList();

        try {
            PreparedStatement ps = conexion.prepareStatement(sql);
            ResultSet resultado = ps.executeQuery();
            while (resultado.next()) {
                Especialista esp = new Especialista(resultado.getString("Matricula"), resultado.getString("NombreyApellido"), resultado.getInt("Telefono"), resultado.getString("Especialidad"), resultado.getBoolean("Estado"));
                especia.add(esp);

            }
            ps.close();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al listar especialista" + e.getMessage());
        }

        return especia;
    }
    //ACTUALIZAR Especialista
    public void actualizarEspecialista(Especialista a) {
        String query = "UPDATE especialista SET matricula = ?, NombreYApellido = ?, telefono = ?, especialidad = ?, estado = ? WHERE matricula = ?";

        try {
            PreparedStatement ps = conexion.prepareStatement(query);
                ps.setString(1, a.getMatricula());
                ps.setString(2, a.getNombreYApellido());
                ps.setLong(3, a.getTelefono());
                ps.setString(4, a.getEspecialidad());
                ps.setBoolean(5, a.isEstado());
                ps.setString(6, a.getMatricula());
                
                ps.executeUpdate();
                
                ps.close();
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al actualizar el Especialista" + e.getMessage());
        }
        
    }
    //BORRAR Especialista
    public void BorrarEspecialista(String matricula) {
        String query = "DELETE FROM especialista WHERE matricula = ?";

        try {
            PreparedStatement ps = conexion.prepareStatement(query);
            ps.setString(1, matricula);
            ps.executeUpdate();
            
            ps.close();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al eliminar el Especialista" + e.getMessage());
        }
    }
    //ALTA LOGICA = darle estado activo a los inactivos
    public void HabilitarEspecialista(Especialista a){
        String query = "UPDATE especialista SET estado = 1 WHERE matricula = ? AND Estado = 0";
        
        try {
            PreparedStatement ps = conexion.prepareStatement(query);
                ps.setString(1, a.getMatricula());
                ps.executeUpdate();
                
                ps.close();
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al habilitar el Especialista" + e.getMessage());
        }
    }
    //BAJA LOGICA = darle estado inactivo a los activos
     public void DeshabilitarEspecialista(Especialista a){
        String query = "UPDATE especialista SET estado = 0 WHERE matricula = ? AND Estado = 1";
        
        try {
            PreparedStatement ps = conexion.prepareStatement(query);
                ps.setString(1, a.getMatricula());
                ps.executeUpdate();
                
                ps.close();
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al deshabilitar el Especialista" + e.getMessage());
        }
    }
    
}
