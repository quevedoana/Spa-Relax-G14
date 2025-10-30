/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Persistencia;

import Modelo.Conexion;
import Modelo.Sesion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import javax.swing.JOptionPane;

/**
 *
 * @author esteb
 */
public class SesionData {
     private Connection conexion = null;

    public SesionData() {
        conexion = Conexion.getConexion();
    }
     public void guardarSesion(Sesion s){
       
        String query = "INSERT INTO sesion(fechaYHoraInicio, fechaYHoraFin, codTratamiento, nroConsultorio, matriculaMasajista, codInstalacion,codPack estado) VALUES (?,?,?,?,?,?,?,?)";
        try {
            PreparedStatement ps = conexion.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            Timestamp tm = Timestamp.valueOf(s.getFechaYHoraDeInicio());
            Timestamp tm2 = Timestamp.valueOf(s.getFechaYHoraDeFin());
            ps.setTimestamp(1, tm);
            ps.setTimestamp(2, tm2);
            ps.setInt(3, s.getTratamiento().getCodTratam());
            ps.setInt(4, s.getConsultorio().getNroConsultorio());
            ps.setString(5, s.getEspecialista().getMatricula());
            ps.setInt(6, s.getInstalacion().getCodInstal());
            ps.setInt(7, s.getDiaDeSpa().getCodPack());
            ps.setBoolean(8, s.isEstado());
            
            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                s.setCodSesion(rs.getInt(1));
            } else {
                JOptionPane.showMessageDialog(null, "No se pudo obtener el codSesion de la sesión");
                ps.close();
            }
            ps.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al guardar la sesión" + e.getMessage());
        }
}
      public Sesion buscarSesion(int codSesion) { 
        String sql = "SELECT * FROM sesion WHERE codSesion = ? ";
        Sesion s = null;
        TratamientoData tratamientodata = new TratamientoData();
        ConsultorioData consultoriodata = new ConsultorioData();
        EspecialistaData especialistadata = new EspecialistaData();
        InstalacionData instalaciondata = new InstalacionData();
        DiaDeSpaData diadespadata = new DiaDeSpaData();
        
        try {
            PreparedStatement ps = conexion.prepareStatement(sql);
            ps.setInt(1, codSesion);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Timestamp ts = rs.getTimestamp("fechaYHoraInicio");
                Timestamp ts1 = rs.getTimestamp("fechaYHoraFin");

                s = new Sesion(ts.toLocalDateTime(),ts1.toLocalDateTime(),tratamientodata.buscarTratamiento(rs.getInt("codTratamiento")),consultoriodata.buscarConsultorio(rs.getInt("nroConsultorio")),
                        especialistadata.buscarEspecialista(rs.getString("matriculaMasajista")),instalaciondata.buscarInstalacion(rs.getInt("codInstalacion")),diadespadata.buscarDiaDeSpa(rs.getInt("codPack")),
                        rs.getBoolean("estado"));
               s.setCodSesion(rs.getInt("codSesion"));

            } else {
                System.out.println("No se encontro el día de spa");
            }
            ps.close();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al buscar el día de spa" + e.getMessage());

        }
        return s;
    }
}
