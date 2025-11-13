/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Persistencia;

import Modelo.Conexion;
import Modelo.Tratamiento;
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
public class TratamientoData {

    private Connection conexion = null;

    public TratamientoData() {
        conexion = Conexion.getConexion();
    }

    //AGREGAR UN TRATAMIENTO
    public void AltaTratamiento(Tratamiento t) {
        String query = "INSERT INTO tratamiento(nombre, detalle, tipo, duracion, costo, activo, productos) VALUES (?,?,?,?,?,?,?)";
        try {
            PreparedStatement ps = conexion.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, t.getNombre());
            ps.setString(2, t.getDetalle());
            ps.setString(3, t.getTipo());
            ps.setInt(4, t.getDuracion());
            ps.setDouble(5, t.getCosto());
            ps.setBoolean(6, t.isActivo());
            ps.setString(7, t.getProductos());
            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                t.setCodTratam(rs.getInt(1));
            } else {
                JOptionPane.showMessageDialog(null, "No se pudo obtener el codigo del tratamiento");
            }
            ps.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al guardar el Tratamiento: " + e.getMessage());
        }
    }

    //BUSCAR TRATAMIENTO
    public Tratamiento buscarTratamiento(int codTratam) {
        String sql = "SELECT * FROM tratamiento WHERE codTratam = ? ";
        Tratamiento t = null;

        try {
            PreparedStatement ps = conexion.prepareStatement(sql);
            ps.setInt(1, codTratam);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {

                t = new Tratamiento(rs.getString("nombre"), rs.getString("detalle"), rs.getString("tipo"), rs.getInt("duracion"), rs.getDouble("costo"), rs.getBoolean("activo"), rs.getString("productos"));
                t.setCodTratam(rs.getInt("codTratam"));

            } else {
                System.out.println("No se encontro el tratamiento");
            }
            ps.close();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al buscar el tratamiento" + e.getMessage());

        }
        return t;
    }

    //ELIMAR UN TRATAMIENTO
    public boolean BajaTratamiento(int codTratam) {

        String sql = "DELETE FROM tratamiento WHERE codTratam = ?";

        try {
            PreparedStatement ps = conexion.prepareStatement(sql);
            ps.setInt(1, codTratam);
            ps.executeUpdate();

            ps.close();
            return true;

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al eliminar el tratamiento: " + ex.getMessage());
        }
        return false;

    }

    //PONER INACTIVO UN TRATAMIENTO
    public void BajaLogicaTratamiento(int codTratam) {
        String sql = "UPDATE tratamiento SET activo = false WHERE codTratam = ?";

        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setInt(1, codTratam);
            ps.executeUpdate();
            ps.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error en desactivar el tratamiento: " + ex.getMessage());
        }
    }

    //PONER ACTIVO UN TRTAMIENTO
    public void AltaLogicaTratamiento(int codTratam) {
        String sql = "UPDATE tratamiento SET activo = true WHERE codTratam = ?";

        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setInt(1, codTratam);
            ps.executeUpdate();
            ps.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error en activar el tratamiento: " + ex.getMessage());
        }
    }

    //MODIFICAR UN TRATAMIENTO
    public boolean ModificarTratamiento(Tratamiento t) {
        String sql = "UPDATE tratamiento SET nombre = ?, detalle = ?, tipo = ?, duracion = ?, costo = ?, activo = ?, productos = ? WHERE codTratam = ?";

        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setString(1, t.getNombre());
            ps.setString(2, t.getDetalle());
            ps.setString(3, t.getTipo());
            ps.setInt(4, t.getDuracion());
            ps.setDouble(5, t.getCosto());
            ps.setBoolean(6, t.isActivo());
            ps.setString(7, t.getProductos());
            ps.setInt(8, t.getCodTratam());

             int filasAfectadas = ps.executeUpdate();  // ejecuta la actualización

        if (filasAfectadas == 0) {
            // No se modificó ninguna fila: puede que codTratam no exista
            JOptionPane.showMessageDialog(null, "No se modificó ningún tratamiento. Verifique el código.");
            return false;
        }
            return true;

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al editar tratamiento: " + ex.getMessage());
        }
        return false;
    }

    //LISTAR TRATAMIENTOS POR TIPO
    public List<Tratamiento> listarTratamientosPorTipo(String tipo) {
        List<Tratamiento> tratamientos = new ArrayList<>();
        String sql = "SELECT * FROM tratamiento WHERE tipo = ? AND activo = true";

        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setString(1, tipo);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                 Tratamiento tratamiento = new Tratamiento();
                tratamiento.setCodTratam(rs.getInt("codTratam"));
                tratamiento.setNombre(rs.getString("nombre"));
                tratamiento.setDetalle(rs.getString("detalle"));
                tratamiento.setTipo(rs.getString("tipo"));
                tratamiento.setDuracion(rs.getInt("duracion"));
                tratamiento.setCosto(rs.getDouble("costo"));
                tratamiento.setActivo(rs.getBoolean("activo"));
                tratamiento.setProductos(rs.getString("productos"));
                tratamientos.add(tratamiento);
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al listar los tratamientos por tipo: " + ex.getMessage());
        }

        return tratamientos;
    }

    public List<Tratamiento> listarTodosTratamientos() {
        List<Tratamiento> tratamientos = new ArrayList<>();
        String sql = "SELECT * FROM tratamiento WHERE activo = true";

        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Tratamiento tratamiento = new Tratamiento();
                tratamiento.setCodTratam(rs.getInt("codTratam"));
                tratamiento.setNombre(rs.getString("nombre"));
                tratamiento.setDetalle(rs.getString("detalle"));
                tratamiento.setTipo(rs.getString("tipo"));
                tratamiento.setDuracion(rs.getInt("duracion"));
                tratamiento.setCosto(rs.getDouble("costo"));
                tratamiento.setActivo(rs.getBoolean("activo"));
                tratamiento.setProductos(rs.getString("productos"));
                tratamientos.add(tratamiento);
            }
            ps.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al listar los tratamientos: " + ex.getMessage());
        }

        return tratamientos;
    }

    //Buscar Tratamiento por nombre
    
}
