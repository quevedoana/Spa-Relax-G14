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

    public void guardarEspecialista(Especialista e) {
        try {
            //Masajista: matricula, nombre y ape, teléfono, especialidad (facial, corporal, relajación, o estético), estado
            String sql = "INSERT INTO especialista (matricula,NombreYApellido,telefono,especialidad,estado) VALUES (?,?,?,?,?);";
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
                esp = new Especialista(resultado.getString("matricula"), resultado.getString("NombreYApellido"), resultado.getInt("telefono"), resultado.getString("especialidad"), resultado.getBoolean("estado"));
                esp.setMatricula(resultado.getString("matricula"));

            } else {
                System.out.println("No se encontro el Especialista");
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
                Especialista esp = new Especialista(resultado.getString("matricula"), resultado.getString("NombreYApellido"), resultado.getInt("telefono"), resultado.getString("especialidad"), resultado.getBoolean("estado"));
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
    public void HabilitarEspecialista(Especialista a) {
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
    public void DeshabilitarEspecialista(Especialista a) {
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

    // Listar masajistas libres en una franja horaria
    public List<Especialista> listarMasajistasLibresEnFranja(java.sql.Timestamp inicio, java.sql.Timestamp fin) {
        String sql = "SELECT e.* FROM especialista e "
                + "WHERE e.estado = true "
                + "AND e.matricula NOT IN ("
                + "    SELECT s.matriculaMasajista FROM sesion s "
                + "    WHERE s.estado = true "
                + "    AND ((s.fechaYHoraInicio BETWEEN ? AND ?) "
                + "    OR (s.fechaYHoraFin BETWEEN ? AND ?) "
                + "    OR (? BETWEEN s.fechaYHoraInicio AND s.fechaYHoraFin))"
                + ")";
        List<Especialista> especialistas = new ArrayList<>();

        try {
            PreparedStatement ps = conexion.prepareStatement(sql);
            ps.setTimestamp(1, inicio);
            ps.setTimestamp(2, fin);
            ps.setTimestamp(3, inicio);
            ps.setTimestamp(4, fin);
            ps.setTimestamp(5, inicio);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Especialista e = new Especialista(
                        rs.getString("matricula"),
                        rs.getString("NombreYApellido"),
                        rs.getLong("telefono"),
                        rs.getString("especialidad"),
                        rs.getBoolean("estado")
                );
                especialistas.add(e);
            }
            ps.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al listar masajistas libres: " + e.getMessage());
        }
        return especialistas;
    }

    // Listar especialistas por especialidad
    public List<Especialista> listarMasajistasPorEspecialidad(String especialidad) {
        String sql = "SELECT * FROM especialista WHERE especialidad = ? AND estado = true";
        List<Especialista> especialistas = new ArrayList<>();

        try {
            PreparedStatement ps = conexion.prepareStatement(sql);
            ps.setString(1, especialidad);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Especialista e = new Especialista(
                        rs.getString("matricula"),
                        rs.getString("NombreYApellido"),
                        rs.getLong("telefono"),
                        rs.getString("especialidad"),
                        rs.getBoolean("estado")
                );
                especialistas.add(e);
            }
            ps.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al listar masajistas por especialidad: " + e.getMessage());
        }
        return especialistas;
    }

}
