/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Persistencia;

import Modelo.Conexion;
import Modelo.Turno;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author esteb
 */
public class TurnoData {

    private Connection conexion = null;

    public TurnoData() {
        conexion = Conexion.getConexion();
    }
    
    private TratamientoData tratamientodata = new TratamientoData();
    private ConsultorioData consultoriodata = new ConsultorioData();
    private EspecialistaData especialistadata = new EspecialistaData();
    private InstalacionData instalaciondata = new InstalacionData();
    private DiaDeSpaData diadespadata = new DiaDeSpaData();

    public void guardarSesion(Turno s) {

        String query = "INSERT INTO sesion(fechaYHoraInicio, fechaYHoraFin, codTratamiento, nroConsultorio, matriculaMasajista, codInstalacion, codPack, estado) VALUES (?,?,?,?,?,?,?,?)";
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

    public Turno buscarSesion(int codSesion) {
        String sql = "SELECT * FROM sesion WHERE codSesion = ? ";
        Turno s = null;

        try {
            PreparedStatement ps = conexion.prepareStatement(sql);
            ps.setInt(1, codSesion);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Timestamp ts = rs.getTimestamp("fechaYHoraInicio");
                Timestamp ts1 = rs.getTimestamp("fechaYHoraFin");

                s = new Turno(ts.toLocalDateTime(), ts1.toLocalDateTime(), tratamientodata.buscarTratamiento(rs.getInt("codTratamiento")), consultoriodata.buscarConsultorio(rs.getInt("nroConsultorio")),
                        especialistadata.buscarEspecialista(rs.getString("matriculaMasajista")), instalaciondata.buscarInstalacion(rs.getInt("codInstalacion")), diadespadata.buscarDiaDeSpa(rs.getInt("codPack")),
                        rs.getBoolean("estado"));
                s.setCodSesion(rs.getInt("codSesion"));

            } else {
                System.out.println("No se encontro la sesión");
            }
            ps.close();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al buscar la sesión" + e.getMessage());

        }
        return s;
    }

    public List<Turno> listarSesiones() {
        String sql = "SELECT * FROM sesion WHERE 1";
        Turno s = null;
        List<Turno> sesiones = new ArrayList();

        try {
            PreparedStatement ps = conexion.prepareStatement(sql);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Timestamp ts = rs.getTimestamp("fechaYHoraInicio");
                Timestamp ts1 = rs.getTimestamp("fechaYHoraFin");

                s = new Turno(ts.toLocalDateTime(), ts1.toLocalDateTime(), tratamientodata.buscarTratamiento(rs.getInt("codTratamiento")), consultoriodata.buscarConsultorio(rs.getInt("nroConsultorio")),
                        especialistadata.buscarEspecialista(rs.getString("matriculaMasajista")), instalaciondata.buscarInstalacion(rs.getInt("codInstalacion")), diadespadata.buscarDiaDeSpa(rs.getInt("codPack")),
                        rs.getBoolean("estado"));
                s.setCodSesion(rs.getInt("codSesion"));
                sesiones.add(s);

            } else {
                System.out.println("No se encontro la sesión");
            }
            ps.close();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al buscar la sesión" + e.getMessage());

        }
        return sesiones;
    }
     public void actualizarSesion(Turno s){
        String sql = "UPDATE sesion SET fechaYHoraInicio = ?, fechaYHoraFin = ?, codTratamiento = ?, nroConsultorio = ?, matriculaMasajista = ?, codInstalacion = ?, codPack = ?, estado = ? WHERE codSesion = ? ";
        try{
            PreparedStatement ps = conexion.prepareStatement(sql);
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
            ps.setInt(9, s.getCodSesion());
            ps.executeUpdate();
            
            ps.close();
            
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, "Error al actualizar la sesión" + e.getMessage());
        }
}
      public void borrarSesion(int codSesion){
            String sql = "DELETE FROM sesion WHERE codSesion = ? ";
            try{
                PreparedStatement ps = conexion.prepareStatement(sql);
                ps.setInt(1, codSesion);
                ps.executeUpdate();
                ps.close();
            }catch(SQLException e){
            JOptionPane.showMessageDialog(null, "Error al borrar la sesión" + e.getMessage());
        }    
}
       public void habilitarSesion(Turno s){
         String sql = "UPDATE sesion SET estado = 1 WHERE codSesion = ? AND estado = 0 ";
         try{
             PreparedStatement ps = conexion.prepareStatement(sql);
             ps.setInt(1, s.getCodSesion());
             ps.setBoolean(2, s.isEstado());
             ps.executeUpdate();
             
             ps.close();
             
     }catch(SQLException e){
            JOptionPane.showMessageDialog(null, "Error al habilitar la sesión" + e.getMessage());
}
     }
       public void deshabilitarSesion(Turno s){
         String sql = "UPDATE sesion SET estado = 0 WHERE codSesion = ? AND estado = 1 ";
         try{
             PreparedStatement ps = conexion.prepareStatement(sql);
             ps.setInt(1, s.getCodSesion());
             ps.setBoolean(2, s.isEstado());
             ps.executeUpdate();
             
             ps.close();
             
     }  catch(SQLException e){
            JOptionPane.showMessageDialog(null, "Error al deshabilitar la sesión" + e.getMessage());
}
     }
      
      
}
