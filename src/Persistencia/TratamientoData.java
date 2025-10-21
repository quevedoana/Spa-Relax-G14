/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Persistencia;

import Modelo.Conexion;
import Modelo.Producto;
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

    public void AltaTratamiento(Tratamiento t) { //insert
        String query = "INSERT INTO tratamiento(codTratam, nombre, detalle, duracion, costo, activo, tipo) VALUES (?,?,?,?,?,?,?,?)";
        try {
            PreparedStatement ps = conexion.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, t.getCodTratam());
            ps.setString(2, t.getNombre());
            ps.setString(3, t.getDetalle());
            ps.setInt(4, t.getDuracion());
            ps.setDouble(5, t.getCosto());
            ps.setBoolean(6, t.isActivo());
             ps.setString(7, t.getTipo());
            
            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            guardarProductosTratamiento(t);

            ps.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al guardar el Tratamiento" + e.getMessage());
        }

    }
     private void guardarProductosTratamiento(Tratamiento t) {
        String sql = "INSERT INTO producto (codTratam, idProducto, nombre, descripcion) VALUES (?, ?, ?, ?)";
        
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            for (Producto producto : t.getProductos()) {
                ps.setInt(1, t.getCodTratam());
                ps.setInt(2, producto.getIdProducto());
                ps.setString(3, producto.getNombre());
                ps.setString(4, producto.getDescripcion());
                ps.executeUpdate();
            }
        } catch (SQLException ex) {
            System.out.println("Error al guardar productos del tratamiento: " + ex.getMessage());
        }
    }
     
    private boolean eliminarProductosTratamiento(int codTratam) {
        String sql = "DELETE FROM tratamiento_producto WHERE codTratam = ?";
        
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setInt(1, codTratam);
            ps.executeUpdate();
            return true;
        } catch (SQLException ex) {
            System.out.println("Error al eliminar productos del tratamiento: " + ex.getMessage());
        }
        return false;
    }
     
    public boolean BajaTratamiento(int codTratam) {
        // Primero eliminar las relaciones con productos
        if (eliminarProductosTratamiento(codTratam)) {
            String sql = "DELETE FROM tratamiento WHERE codTratam = ?";
            
            try (PreparedStatement ps = conexion.prepareStatement(sql)) {
                ps.setInt(1, codTratam);
                
                int filasAfectadas = ps.executeUpdate();
                return filasAfectadas > 0;
                
            } catch (SQLException ex) {
                System.out.println("Error al eliminar tratamiento: " + ex.getMessage());
            }
        }
        return false;
    }

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
    
    public boolean ModificarTratamiento(Tratamiento tratamiento) {
        String sql = "UPDATE tratamiento SET nombre = ?, detalle = ?, tipo = ?, duracion = ?, costo = ?, activo = ? WHERE codTratam = ?";
        
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setString(1, tratamiento.getNombre());
            ps.setString(2, tratamiento.getDetalle());
            ps.setString(3, tratamiento.getTipo());
            ps.setInt(4, tratamiento.getDuracion());
            ps.setDouble(5, tratamiento.getCosto());
            ps.setBoolean(6, tratamiento.isActivo());
            ps.setInt(7, tratamiento.getCodTratam());
            
            int filasAfectadas = ps.executeUpdate();
            
            if (filasAfectadas > 0) {
                // Actualizar productos (eliminar y volver a insertar)
                eliminarProductosTratamiento(tratamiento.getCodTratam());
                guardarProductosTratamiento(tratamiento);
                return true;
            }
            
        } catch (SQLException ex) {
            System.out.println("Error al actualizar tratamiento: " + ex.getMessage());
        }
        return false;
    }
    
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
     private Tratamiento crearTratamientoDesdeResultSet(ResultSet rs) throws SQLException {
        Tratamiento tratamiento = new Tratamiento();
        tratamiento.setCodTratam(rs.getInt("codTratam"));
        tratamiento.setNombre(rs.getString("nombre"));
        tratamiento.setDetalle(rs.getString("detalle"));
        tratamiento.setTipo(rs.getString("tipo"));
        tratamiento.setDuracion(rs.getInt("duracion"));
        tratamiento.setCosto(rs.getDouble("costo"));
        tratamiento.setActivo(rs.getBoolean("activo"));
        
        return tratamiento;
    }
}
