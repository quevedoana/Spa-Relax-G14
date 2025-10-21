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
import javax.swing.JOptionPane;


public class EspecialistaData {
    private Connection conexion = null;

    public EspecialistaData() {
        this.conexion = Conexion.getConexion();
    }
    public void guardarEspecialista(Especialista e) {
        try {
            //Masajista: matricula, nombre y ape, teléfono, especialidad (facial, corporal, relajación, o estético), estado
            String sql = "INSERT INTO Especialidad (Matricula,Nombre,Apellido,Telefono,Especialidad,Estado) VALUES (?,?,?,?,?,?);";
            PreparedStatement ps = conexion.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, e.getMatricula());
            ps.setString(2, e.getNombre());
            ps.setString(3, e.getApellido());
            ps.setInt(4, e.getTelefono());
            ps.setString(3, e.getEspecialidad());
            ps.setBoolean(6, e.isEstado());
            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
               // e.setIdInscripto(rs.getInt(1));
               e.setMatricula(rs.getString(1));
                JOptionPane.showMessageDialog(null, "Especialista Registrado");

            } else {
                JOptionPane.showMessageDialog(null, "No se pudo obtener la matricula del especialista");
                ps.close();
            }
            

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al guardar el especialista" + e.getMessage());
        }
    }
}
