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
import java.sql.Timestamp;
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

            }
            ps.close();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al buscar la instalacion" + e.getMessage());

        }
        return ins;
    }

    public List<Instalacion> ListarInstalacion() {
        String sql = "SELECT * FROM instalacion";
        List<Instalacion> instalacion = new ArrayList();
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

    public void HabilitarInstalacion(Instalacion in) {
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

    public void DeshabilitarInstalacion(Instalacion in) {
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


    public List<Object[]> listarInstalacionesMasSolicitadas(java.sql.Date fechaInicio, java.sql.Date fechaFin) {
        String sql = "SELECT i.nombre, i.detalleDeUso, COUNT(s.codSesion) as cantidad_reservas "
                + "FROM instalacion i "
                + "JOIN sesion s ON i.codInstal = s.codInstalacion "
                + "WHERE DATE(s.fechaYHoraInicio) BETWEEN ? AND ? "
                + "AND s.codInstalacion IS NOT NULL "
                + "GROUP BY i.codInstal, i.nombre, i.detalleDeUso "
                + "ORDER BY cantidad_reservas DESC";
        List<Object[]> resultados = new ArrayList<>();

        try {
            PreparedStatement ps = conexion.prepareStatement(sql);
            ps.setDate(1, fechaInicio);
            ps.setDate(2, fechaFin);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Object[] fila = {
                    rs.getString("nombre"),
                    rs.getString("detalleDeUso"),
                    rs.getInt("cantidad_reservas")
                };
                resultados.add(fila);
            }
            ps.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al listar instalaciones mas solicitadas: " + e.getMessage());
        }
        return resultados;
    }

    // Listar instalaciones libres en franja con fecha
    public List<Instalacion> listarInstalacionesLibresEnFranjaPorFecha(Timestamp inicio, Timestamp fin) {
    String sql = "SELECT i.* FROM instalacion i " +
                 "WHERE i.estado = 1 " +
                 "AND i.codInstal NOT IN ( " +
                 "    SELECT s.CodInstalacion FROM sesion s " +
                 "    WHERE s.estado = 1 " +
                 "    AND s.CodInstalacion IS NOT NULL " +
                 "    AND ((s.fechaYHorainicio BETWEEN ? AND ?) " +
                 "    OR (s.fechaYHoraFin BETWEEN ? AND ?) " +
                 "    OR (? BETWEEN s.fechaYHorainicio AND s.fechaYHoraFin)) " +
                 ") " +
                 "ORDER BY i.codInstal"; // ✅ Ordenar para ver todas
    
    List<Instalacion> instalaciones = new ArrayList<>();
    
    try {
        PreparedStatement ps = conexion.prepareStatement(sql);
        ps.setTimestamp(1, inicio);
        ps.setTimestamp(2, fin);
        ps.setTimestamp(3, inicio);
        ps.setTimestamp(4, fin);
        ps.setTimestamp(5, inicio);
        
        ResultSet rs = ps.executeQuery();
        
        while (rs.next()) {
            Instalacion inst = new Instalacion();
            inst.setCodInstal(rs.getInt("codInstal"));
            inst.setNombre(rs.getString("nombre"));
            inst.setDetalleDeUso(rs.getString("detalleDeUso"));
            inst.setPrecio30m(rs.getDouble("precio30m"));
            inst.setEstado(rs.getBoolean("estado"));
            instalaciones.add(inst);
        }
        ps.close();
        
        // DEBUG
        System.out.println("Instalaciones libres encontradas: " + instalaciones.size());
        for (Instalacion inst : instalaciones) {
            System.out.println(" - " + inst.getCodInstal() + ": " + inst.getNombre());
        }
        
    } catch (SQLException e) {
        JOptionPane.showMessageDialog(null, "Error al buscar instalaciones libres: " + e.getMessage());
        e.printStackTrace();
    }
    return instalaciones;
}

}
