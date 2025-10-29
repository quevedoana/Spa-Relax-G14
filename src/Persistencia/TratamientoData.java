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
    String query = "INSERT INTO tratamiento(codTratam, nombre, detalle, tipo, duracion, costo, activo, productos) VALUES (?,?,?,?,?,?,?,?)";
    try {
        PreparedStatement ps = conexion.prepareStatement(query);
        ps.setInt(1, t.getCodTratam());
        ps.setString(2, t.getNombre());
        ps.setString(3, t.getDetalle());
        ps.setString(4, t.getTipo());
        ps.setInt(5, t.getDuracion());
        ps.setDouble(6, t.getCosto());
        ps.setBoolean(7, t.isActivo());
        ps.setString(8, t.getProductos()); 

        int filasAfectadas = ps.executeUpdate();
        
        if (filasAfectadas > 0) {
            System.out.println("Tratamiento agregado correctamente a la BD");
        } else {
            System.out.println("No se pudo agregar el tratamiento");
        }
        
        ps.close();
        
    } catch (SQLException e) {
        JOptionPane.showMessageDialog(null, "Error al guardar el Tratamiento: " + e.getMessage());
        
    }
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
            System.out.println("Error al eliminar tratamiento: " + ex.getMessage());
        }
        return false;

    }
    //PONER INACTIVO UN TRATAMIENTO
    public boolean BajaLogicaTratamiento(int codTratam) {
        String sql = "UPDATE tratamiento SET activo = false WHERE codTratam = ?";

        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setInt(1, codTratam);

            int filasAfectadas = ps.executeUpdate();
            return filasAfectadas > 0;

        } catch (SQLException ex) {
            System.out.println("Error al dar de baja tratamiento: " + ex.getMessage());
        }
        return false;
    }

    //PONER ACTIVO UN TRTAMIENTO
    public boolean AltaLogicaTratamiento(int codTratam) {
        String sql = "UPDATE tratamiento SET activo = true WHERE codTratam = ?";

        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setInt(1, codTratam);

            int filasAfectadas = ps.executeUpdate();
            return filasAfectadas > 0;

        } catch (SQLException ex) {
            System.out.println("Error al dar de baja tratamiento: " + ex.getMessage());
        }
        return false;
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

            ps.executeUpdate();
            ps.close();
            return true;

        } catch (SQLException ex) {
            System.out.println("Error al actualizar tratamiento: " + ex.getMessage());
        }
        return false;
    }

    //LISTAR TRATAMIENTOS POR TIPO
    public List<Tratamiento> listarTratamientosPorTipo(String tipo) {
        List<Tratamiento> tratamientos = new ArrayList<>();
        String sql = "SELECT * FROM tratamiento WHERE tipo = ? AND activo = true ORDER BY nombre";

        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setString(1, tipo);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Tratamiento tratamiento = crearTratamientoDesdeResultSet(rs);
                tratamientos.add(tratamiento);
            }

        } catch (SQLException ex) {
            System.out.println("Error al listar tratamientos por tipo: " + ex.getMessage());
        }

        return tratamientos;
    }
    
    public List<Tratamiento> listarTodosTratamientos() {
        List<Tratamiento> tratamientos = new ArrayList<>();
        String sql = "SELECT * FROM tratamiento WHERE activo = true ORDER BY nombre";

        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Tratamiento tratamiento = crearTratamientoDesdeResultSet(rs);
                tratamientos.add(tratamiento);
            }

        } catch (SQLException ex) {
            System.out.println("Error al listar todos los tratamientos: " + ex.getMessage());
        }

        return tratamientos;
    }
    

    //METODO AUX
    private Tratamiento crearTratamientoDesdeResultSet(ResultSet rs) throws SQLException {
        Tratamiento tratamiento = new Tratamiento();
        tratamiento.setCodTratam(rs.getInt("codTratam"));
        tratamiento.setNombre(rs.getString("nombre"));
        tratamiento.setDetalle(rs.getString("detalle"));
        tratamiento.setTipo(rs.getString("tipo"));
        tratamiento.setDuracion(rs.getInt("duracion"));
        tratamiento.setCosto(rs.getDouble("costo"));
        tratamiento.setActivo(rs.getBoolean("activo"));
        tratamiento.setProductos(rs.getString("productos"));

        return tratamiento;
    }
}
