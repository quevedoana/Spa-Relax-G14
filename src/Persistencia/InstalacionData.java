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

    /**
     * Listar instalaciones libres en una franja horaria
     */
    public List<Instalacion> listarInstalacionesLibresEnFranja(java.sql.Timestamp inicio, java.sql.Timestamp fin) {
        String sql = "SELECT i.* FROM instalacion i "
                + "WHERE i.estado = true "
                + "AND i.codInstal NOT IN ("
                + "    SELECT s.codInstalacion FROM sesion s "
                + "    WHERE s.estado = true AND s.codInstalacion IS NOT NULL "
                + "    AND ((s.fechaYHoraInicio BETWEEN ? AND ?) "
                + "    OR (s.fechaYHoraFin BETWEEN ? AND ?) "
                + "    OR (? BETWEEN s.fechaYHoraInicio AND s.fechaYHoraFin))"
                + ")";
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
                Instalacion i = new Instalacion(
                        rs.getString("nombre"),
                        rs.getString("detalleDeUso"),
                        rs.getDouble("precio30m"),
                        rs.getBoolean("estado")
                );
                i.setCodInstal(rs.getInt("codInstal"));
                instalaciones.add(i);
            }
            ps.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al listar instalaciones libres: " + e.getMessage());
        }
        return instalaciones;
    }

// Listar instalaciones mas solicitadas entre fechas
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
}
