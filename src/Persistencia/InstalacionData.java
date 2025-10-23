/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Persistencia;


import Modelo.Conexion;
import Modelo.Instalacion;
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
public class InstalacionData {

    private Connection conexion = null;

    public InstalacionData() {
        conexion = Conexion.getConexion();
    }

    public void guardarInstalacion(Instalacion in) {
        String query = "INSERT INTO instalacion (nombre, detalleDeUso, precio30m, estado) VALUES (?,?,?,?)";
        try {
            PreparedStatement ps = conexion.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, in.getNombre());
            ps.setString(2, in.getDetalleDeUso());
            ps.setDouble(3, in.getPrecio30m());
            ps.setBoolean(4, in.isEstado());
            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                in.setCodInstal(rs.getInt(1));
            } else {
                JOptionPane.showMessageDialog(null, "No se pudo obtener el id de la instalacion");
                ps.close();
            }
            ps.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al guardar la instalacion" + e.getMessage());
        }

    }

    public Instalacion buscarInstalacion(int CodInstal) {
        String sql = "SELECT * FROM instalacion WHERE codInstal=?";
        Instalacion ins = null;
        try {
            PreparedStatement ps = conexion.prepareStatement(sql);
            ps.setInt(1, CodInstal);
            ResultSet resultado = ps.executeQuery();
            if (resultado.next()) {

                ins = new Instalacion(resultado.getString("nombre"), resultado.getString("detalleDeUso"), resultado.getDouble("precio30m"), resultado.getBoolean("estado"));
                ins.setCodInstal(resultado.getInt("codInstal"));

            } else {
                System.out.println("No se encontro la instalacion");
            }
            ps.close();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al buscar la instalacion" + e.getMessage());

        }
        return ins;
    }
    
    public List<Instalacion> ListarInstalacion() { 
        String sql = "SELECT * FROM instalacion";
        List<Instalacion> instalacion = new ArrayList ();
        try {
            PreparedStatement ps = conexion.prepareStatement(sql);
            ResultSet resultado = ps.executeQuery();
            while (resultado.next()) {

                Instalacion ins = new Instalacion(resultado.getString("nombre"), resultado.getString("detalleDeUso"), resultado.getDouble("precio30m"), resultado.getBoolean("estado"));
                ins.setCodInstal(resultado.getInt("codInstal"));
                instalacion.add(ins);
            }
            ps.close();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al buscar la instalacion" + e.getMessage());

        }
        return instalacion;
    }
    public void actualizarInstalacion(Instalacion in) {
        String query = "UPDATE instalacion SET nombre = ?, detalleDeUso = ?, precio30m = ?, estado = ? WHERE codInstal = ?";

        try {
            PreparedStatement ps = conexion.prepareStatement(query);
                ps.setString(1, in.getNombre());
                ps.setString(2, in.getDetalleDeUso());
                ps.setDouble(3, in.getPrecio30m());
                ps.setBoolean(4, in.isEstado());
                ps.setInt(5, in.getCodInstal());
                ps.executeUpdate();
                
                ps.close();
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al actualizar la instalacion" + e.getMessage());
        }
    }
    public void BorrarInstalacion(int codInstal) {
        String query = "DELETE FROM instalacion WHERE codInstal = ?";

        try {
            PreparedStatement ps = conexion.prepareStatement(query);
            ps.setInt(1, codInstal);
            ps.executeUpdate();
            
            ps.close();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al eliminar la instalacion" + e.getMessage());
        }
    }
    
    public void HabilitarInstalacion(Instalacion in){
        String query = "UPDATE instalacion SET estado = 1 WHERE codInstal = ? AND estado = 0";
        
        try {
            PreparedStatement ps = conexion.prepareStatement(query);
                ps.setInt(1, in.getCodInstal());
                ps.executeUpdate();
                
                ps.close();
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al habilitar la instalacion" + e.getMessage());
        }
    }
    
    public void DeshabilitarInstalacion(Instalacion in){
        String query = "UPDATE instalacion SET estado = 0 WHERE codInstal = ? AND estado = 1";
        
        try {
            PreparedStatement ps = conexion.prepareStatement(query);
                ps.setInt(1, in.getCodInstal());
                ps.executeUpdate();
                
                ps.close();
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al deshabilitar la instalacion" + e.getMessage());
        }
    }
    

}
