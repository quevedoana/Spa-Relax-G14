/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Persistencia;

import Modelo.Conexion;
import Modelo.Consultorio;
import Modelo.Especialista;
import Modelo.Instalacion;
import Modelo.Tratamiento;
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

    //AGREGAR TURNO
    public void AltaTurno(Turno sesion) {
        String query = "INSERT INTO sesion(fechaHoraInicio, fechaHoraFin, codTratamiento, nroConsultorio, matriculaMasajista, codInstalacion, estado) VALUES (?,?,?,?,?,?,?)";
        
        try {
            PreparedStatement ps = conexion.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            ps.setTimestamp(1, Timestamp.valueOf(sesion.getFechaYHoraDeInicio()));
            ps.setTimestamp(2, Timestamp.valueOf(sesion.getFechaYHoraDeFin()));
            ps.setInt(3, sesion.getTratamiento().getCodTratam());
            
            if (sesion.getConsultorio() != null) {
                ps.setInt(4, sesion.getConsultorio().getNroConsultorio());
            } else {
                ps.setNull(4, java.sql.Types.INTEGER);
            }
            
            ps.setString(5, sesion.getEspecialista().getMatricula());
            
            if (sesion.getInstalacion() != null) {
                ps.setInt(6, sesion.getInstalacion().getCodInstal());
            } else {
                ps.setNull(6, java.sql.Types.INTEGER);
            }
            
            ps.setBoolean(7, sesion.isEstado());

            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                sesion.setCodSesion(rs.getInt(1)); // Asignar el ID generado directamente al objeto
            }
            ps.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al guardar la sesión: " + e.getMessage());
        }
    }

    public void guardarSesionConPack(Turno sesion, int codPack) {
        String query = "INSERT INTO sesion(fechaHoraInicio, fechaHoraFin, codTratamiento, nroConsultorio, matriculaMasajista, codInstalacion, codPack, estado) VALUES (?,?,?,?,?,?,?,?)";
        
        try {
            PreparedStatement ps = conexion.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            ps.setTimestamp(1, Timestamp.valueOf(sesion.getFechaYHoraDeInicio()));
            ps.setTimestamp(2, Timestamp.valueOf(sesion.getFechaYHoraDeFin()));
            ps.setInt(3, sesion.getTratamiento().getCodTratam());
            
            if (sesion.getConsultorio() != null) {
                ps.setInt(4, sesion.getConsultorio().getNroConsultorio());
            } else {
                ps.setNull(4, java.sql.Types.INTEGER);
            }
            
            ps.setString(5, sesion.getEspecialista().getMatricula());
            
            if (sesion.getInstalacion() != null) {
                ps.setInt(6, sesion.getInstalacion().getCodInstal());
            } else {
                ps.setNull(6, java.sql.Types.INTEGER);
            }
            
            ps.setInt(7, codPack); 
            ps.setBoolean(8, sesion.isEstado());

            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                sesion.setCodSesion(rs.getInt(1));
            }
            ps.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al guardar la sesión: " + e.getMessage());
        }
    }
    public List<Turno> buscarSesionesPorDiaSpa(int codPack) {
        String sql = "SELECT * FROM sesion WHERE codPack = ?";
        List<Turno> sesiones = new ArrayList<>();
        
        try {
            PreparedStatement ps = conexion.prepareStatement(sql);
            ps.setInt(1, codPack);
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
                Turno sesion = new Turno();
                sesion.setCodSesion(rs.getInt("codSesion"));
                sesion.setFechaYHoraDeInicio(rs.getTimestamp("fechaHoraInicio").toLocalDateTime());
                sesion.setFechaYHoraDeFin(rs.getTimestamp("fechaHoraFin").toLocalDateTime());
                sesion.setTratamiento(tratamientodata.buscarTratamiento(rs.getInt("codTratamiento")));
                sesiones.add(sesion);
            }
            ps.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al buscar sesiones: " + e.getMessage());
        }
        return sesiones;
    }


    //BORRAR UN TURNO
    public void BajaTurno(int codSesion) {
        String sql = "DELETE FROM sesion WHERE codSesion = ? ";
        try {
            PreparedStatement ps = conexion.prepareStatement(sql);
            ps.setInt(1, codSesion);
            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al borrar la sesión" + e.getMessage());
        }
    }

    public Turno BuscarTurno(int codSesion) {
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

    public List<Turno> ListarTurnos() {
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

    public void ActualizarTurno(Turno s) {
        String sql = "UPDATE sesion SET fechaYHoraInicio = ?, fechaYHoraFin = ?, codTratamiento = ?, nroConsultorio = ?, matriculaMasajista = ?, codInstalacion = ?, codPack = ?, estado = ? WHERE codSesion = ? ";
        try {
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

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al actualizar la sesión" + e.getMessage());
        }
    }

    public void ActivarTurno(Turno s) {
        String sql = "UPDATE sesion SET estado = 1 WHERE codSesion = ? AND estado = 0 ";
        try {
            PreparedStatement ps = conexion.prepareStatement(sql);
            ps.setInt(1, s.getCodSesion());
            ps.setBoolean(2, s.isEstado());
            ps.executeUpdate();

            ps.close();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al habilitar la sesión" + e.getMessage());

        }
    }

    public void DesactivarTurno(Turno s) {
        String sql = "UPDATE sesion SET estado = 0 WHERE codSesion = ? AND estado = 1 ";
        try {
            PreparedStatement ps = conexion.prepareStatement(sql);
            ps.setInt(1, s.getCodSesion());
            ps.setBoolean(2, s.isEstado());
            ps.executeUpdate();

            ps.close();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al deshabilitar la sesión" + e.getMessage());
        }
    }
    
    public Tratamiento BuscarTratamiento(int codTratam){
        return tratamientodata.buscarTratamiento(codTratam);
    }
    
    public List<Especialista> ListarEspecialistas(String tipo){
        List<Especialista> especialistas = new ArrayList<>();
        String sql = "SELECT * FROM especialista WHERE especialidad = ? AND estado = true";

        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setString(1, tipo);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Especialista e = new Especialista();
                e.setMatricula(rs.getString("matricula"));
                e.setNombreYApellido(rs.getString("NombreYApellido"));
                e.setTelefono(rs.getLong("telefono"));
                e.setEspecialidad(rs.getString("especialidad"));
                e.setEstado(rs.getBoolean("estado"));
                especialistas.add(e);
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al listar los especilistas por tipo: " + ex.getMessage());
        }

        return especialistas;
    }
    public List<Consultorio> ListarConsultorios(String tipo){
        List<Consultorio> consultorios = new ArrayList<>();
        String sql = "SELECT * FROM consultorio WHERE usos = ? AND apto = true";

        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setString(1, tipo);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Consultorio e = new Consultorio();
                e.setNroConsultorio(rs.getInt("nroConsultorio"));
                e.setUsos(rs.getString("usos"));
                e.setEquipamiento(rs.getString("equipamiento"));
                e.setApto(rs.getBoolean("apto"));
                consultorios.add(e);
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al listar los especilistas por tipo: " + ex.getMessage());
        }

        return consultorios;
    }
    
    public List<Instalacion> ListaInstalaciones(){
        return instalaciondata.ListarInstalacion();
    }
    public void eliminarSesionesPorDiaSpa(int codPack) {
        String sql = "DELETE FROM sesion WHERE codPack = ?";
        try {
            PreparedStatement ps = conexion.prepareStatement(sql);
            ps.setInt(1, codPack);
            int filasEliminadas = ps.executeUpdate();
            System.out.println("Se eliminaron " + filasEliminadas + " sesiones del día de spa: " + codPack);
            ps.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al eliminar sesiones del día de spa: " + e.getMessage());
        }
    }

}
