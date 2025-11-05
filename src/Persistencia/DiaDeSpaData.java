/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Persistencia;

import Modelo.Conexion;
import Modelo.DiaDeSpa;
import Modelo.Turno;
import java.sql.Connection;
import java.sql.Timestamp;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;


/**
 *
 * @author esteb
 */
public class DiaDeSpaData {
    
     private Connection conexion = null;

    public DiaDeSpaData() {
        conexion = Conexion.getConexion();
    }
    //Métodos 
    public void guardarDiaDeSpa(DiaDeSpa d){
       
        String query = "INSERT INTO dia_de_spa(fechaYHora, preferencias, codCli, estado, codSesion, monto) VALUES (?,?,?,?,?,?)";
        try {
            PreparedStatement ps = conexion.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            Timestamp tm = Timestamp.valueOf(d.getFechaYHora());
            ps.setTimestamp(1, tm);
            ps.setString(2, d.getPreferencias());
            ps.setInt(3, d.getCliente().getCodCli());
            ps.setBoolean(4, d.isEstado());
            ps.setInt(5, d.getSesion().getCodSesion());
            ps.setDouble(6, d.getMonto());
            
            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                d.setCodPack(rs.getInt(1));
            } else {
                JOptionPane.showMessageDialog(null, "No se pudo obtener el codPack del Día de spa");
                ps.close();
            }
            ps.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al guardar el Día de spa" + e.getMessage());
        }

    }
    public DiaDeSpa buscarDiaDeSpa(int codPack) { 
        String sql = "SELECT * FROM dia_de_spa WHERE codPack=?";
        DiaDeSpa dia = null;
        ClienteData cd = new ClienteData();
        TurnoData td = new TurnoData();
        try {
            PreparedStatement ps = conexion.prepareStatement(sql);
            ps.setInt(1, codPack);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Timestamp ts = rs.getTimestamp("fechaYHora");

                dia = new DiaDeSpa(ts.toLocalDateTime(),rs.getString("preferencias"),rs.getDouble("monto"),rs.getBoolean("estado"),cd.buscarCliente(rs.getInt("codCli")),td.BuscarTurno(rs.getInt("codSesion")));
                dia.setCodPack(rs.getInt("codPack"));

            } else {
                System.out.println("No se encontro el día de spa");
            }
            ps.close();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al buscar el día de spa" + e.getMessage());

        }
        return dia;
    }
    
    public List<DiaDeSpa> listarDiasDeSpa() { 
        String sql = "SELECT * FROM dia_de_spa WHERE 1";
        DiaDeSpa dia = null;
        ClienteData cd = new ClienteData();
        TurnoData td = new TurnoData();
        List<DiaDeSpa> dias = new ArrayList();
        try {
            PreparedStatement ps = conexion.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                Timestamp ts = rs.getTimestamp("fechaYHora");

                dia = new DiaDeSpa(ts.toLocalDateTime(),rs.getString("preferencias"),rs.getDouble("monto"),rs.getBoolean("estado"),cd.buscarCliente(rs.getInt("codCli")),td.BuscarTurno(rs.getInt("codSesion")));
                dia.setCodPack(rs.getInt("codPack"));
                dias.add(dia);

            

        }
            ps.close();
        }catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al listar los días de spa" + e.getMessage());

        }
        return dias;
    }
    public void actualizarDiaDeSpa(DiaDeSpa d){
        String sql = "UPDATE dia_de_spa SET fechaYHora = ?, preferencias = ?, codCli = ?, estado = ?, codSesion = ?, monto = ? WHERE codPack = ? ";
        try{
            PreparedStatement ps = conexion.prepareStatement(sql);
            Timestamp tm = Timestamp.valueOf(d.getFechaYHora());
            ps.setTimestamp(1, tm);
            ps.setString(2, d.getPreferencias());
            ps.setInt(3, d.getCliente().getCodCli());
            ps.setBoolean(4, d.isEstado());
            ps.setInt(5,d.getSesion().getCodSesion());
            ps.setDouble(6, d.getMonto());
            ps.setInt(7, d.getCodPack());
            ps.executeUpdate();
            
            ps.close();
            
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, "Error al actualizar el día de spa" + e.getMessage());
        }
        
       
    }
     public void borrarDiaDeSpa(int codPack){
            String sql = "DELETE FROM dia_de_spa WHERE codPack = ? ";
            try{
                PreparedStatement ps = conexion.prepareStatement(sql);
                ps.setInt(1, codPack);
                ps.executeUpdate();
                ps.close();
            }catch(SQLException e){
            JOptionPane.showMessageDialog(null, "Error al borrar el día de spa" + e.getMessage());
        }
        
    }
     public void habilitarDiaDeSpa(DiaDeSpa d){
         String sql = "UPDATE dia_de_spa SET estado = 1 WHERE codPack = ? AND estado = 0 ";
         try{
             PreparedStatement ps = conexion.prepareStatement(sql);
             ps.setInt(1, d.getCodPack());
             ps.setBoolean(2, d.isEstado());
             ps.executeUpdate();
             
             ps.close();
             
     }catch(SQLException e){
            JOptionPane.showMessageDialog(null, "Error al habilitar el día de spa" + e.getMessage());
}
     }
     public void deshabilitarDiaDeSpa(DiaDeSpa d){
         String sql = "UPDATE dia_de_spa SET estado = 0 WHERE codPack = ? AND estado = 1 ";
         try{
             PreparedStatement ps = conexion.prepareStatement(sql);
             ps.setInt(1, d.getCodPack());
             ps.setBoolean(2, d.isEstado());
             ps.executeUpdate();
             
             ps.close();
             
     }catch(SQLException e){
            JOptionPane.showMessageDialog(null, "Error al deshabilitar el día de spa" + e.getMessage());
}
     }
}

    

