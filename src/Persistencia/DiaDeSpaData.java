/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Persistencia;

import Modelo.Cliente;
import Modelo.Conexion;
import Modelo.DiaDeSpa;
import Modelo.Turno;
import java.sql.Connection;
import java.sql.Date;
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
    private ClienteData clienteData;
    private TurnoData turnoData;

    public DiaDeSpaData() {
        conexion = Conexion.getConexion();
        this.clienteData = new ClienteData();
        this.turnoData = new TurnoData();
    }

    public void guardarDiaDeSpa(DiaDeSpa diaDeSpa) {
    // ✅ ELIMINAR la columna 'sesiones' del INSERT - es redundante
    String query = "INSERT INTO dia_de_spa(fechaYHora, preferencias, codCli, estado, monto) VALUES (?,?,?,?,?)";

    try {
        PreparedStatement ps = conexion.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
        ps.setTimestamp(1, Timestamp.valueOf(diaDeSpa.getFechaYHora()));
        ps.setString(2, diaDeSpa.getPreferencias());
        ps.setInt(3, diaDeSpa.getCliente().getCodCli());
        ps.setBoolean(4, diaDeSpa.isEstado());
        ps.setDouble(5, diaDeSpa.getMonto());

        ps.executeUpdate();

        ResultSet rs = ps.getGeneratedKeys();
        if (rs.next()) {
            int codPackGenerado = rs.getInt(1);
            diaDeSpa.setCodPack(codPackGenerado);
            
            System.out.println("✅ DEBUG - DiaDeSpa guardado con codPack: " + codPackGenerado);
            
            // ✅ AHORA guardar las sesiones CON el codPack
            if (diaDeSpa.getSesiones() != null && !diaDeSpa.getSesiones().isEmpty()) {
                for (Turno sesion : diaDeSpa.getSesiones()) {
                    System.out.println("✅ DEBUG - Guardando sesión para codPack: " + codPackGenerado);
                    turnoData.guardarSesionConPack(sesion, codPackGenerado);
                }
            }
        }
        ps.close();
        
        JOptionPane.showMessageDialog(null, "Día de Spa guardado exitosamente con código: " + diaDeSpa.getCodPack());
        
    } catch (SQLException e) {
        JOptionPane.showMessageDialog(null, "Error al guardar el Día de spa: " + e.getMessage());
        e.printStackTrace();
    }
}

    public DiaDeSpa buscarDiaDeSpa(int codPack) {
        String sql = "SELECT * FROM dia_de_spa WHERE codPack = ?";
        DiaDeSpa diaDeSpa = null;

        try {
            PreparedStatement ps = conexion.prepareStatement(sql);
            ps.setInt(1, codPack);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Timestamp fechaHora = rs.getTimestamp("fechaYHora");
                String preferencias = rs.getString("preferencias");
                double monto = rs.getDouble("monto");
                boolean estado = rs.getBoolean("estado");
                int codCli = rs.getInt("codCli");

                Cliente cliente = clienteData.buscarCliente(codCli);

                List<Turno> sesiones = turnoData.buscarSesionesPorDiaSpa(codPack);

                diaDeSpa = new DiaDeSpa(
                        fechaHora.toLocalDateTime(),
                        preferencias,
                        monto,
                        estado,
                        cliente,
                        sesiones
                );
                diaDeSpa.setCodPack(codPack);
            } else {
                JOptionPane.showMessageDialog(null, "No se encontró el día de spa con código: " + codPack);
            }
            ps.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al buscar el día de spa: " + e.getMessage());
        }
        return diaDeSpa;
    }

    public DiaDeSpa buscarDiaDeSpaPorFecha(Date fecha) {
        String sql = "SELECT * FROM dia_de_spa WHERE DATE(fechaYHora) = ? ";
        DiaDeSpa dia = null;
        ClienteData cd = new ClienteData();
        TurnoData td = new TurnoData();
        try {
            PreparedStatement ps = conexion.prepareStatement(sql);
            ps.setDate(1, fecha);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Timestamp ts = rs.getTimestamp("fechaYHora");

                dia = new DiaDeSpa(ts.toLocalDateTime(), rs.getString("preferencias"), rs.getDouble("monto"), rs.getBoolean("estado"), cd.buscarCliente(rs.getInt("codCli")), td.ListarTurnos());
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
            while (rs.next()) {
                Timestamp ts = rs.getTimestamp("fechaYHora");

                dia = new DiaDeSpa(ts.toLocalDateTime(), rs.getString("preferencias"), rs.getDouble("monto"), rs.getBoolean("estado"), cd.buscarCliente(rs.getInt("codCli")), td.ListarTurnos());
                dia.setCodPack(rs.getInt("codPack"));
                dias.add(dia);

            }
            ps.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al listar los días de spa" + e.getMessage());

        }
        return dias;
    }

    public void actualizarDiaDeSpa(DiaDeSpa diaDeSpa) {
        String sql = "UPDATE dia_de_spa SET fechaYHora = ?, preferencias = ?, codCli = ?, estado = ?, sesiones = ?, monto = ? WHERE codPack = ?";

        try {
            PreparedStatement ps = conexion.prepareStatement(sql);
            ps.setTimestamp(1, Timestamp.valueOf(diaDeSpa.getFechaYHora()));
            ps.setString(2, diaDeSpa.getPreferencias());
            ps.setInt(3, diaDeSpa.getCliente().getCodCli());
            ps.setBoolean(4, diaDeSpa.isEstado());

            if (diaDeSpa.getSesiones() != null && !diaDeSpa.getSesiones().isEmpty()) {
                ps.setInt(5, diaDeSpa.getSesiones().get(0).getCodSesion());
            } else {
                ps.setNull(5, java.sql.Types.INTEGER);
            }

            ps.setDouble(6, diaDeSpa.getMonto());
            ps.setInt(7, diaDeSpa.getCodPack());

            int filasAfectadas = ps.executeUpdate();
            if (filasAfectadas > 0) {

                actualizarSesionesDelDia(diaDeSpa);
                JOptionPane.showMessageDialog(null, "Día de spa actualizado exitosamente");
            } else {
                JOptionPane.showMessageDialog(null, "No se encontró el día de spa a actualizar");
            }
            ps.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al actualizar el día de spa: " + e.getMessage());
        }
    }

    private void actualizarSesionesDelDia(DiaDeSpa diaDeSpa) {
        try {

            List<Turno> sesionesActuales = turnoData.buscarSesionesPorDiaSpa(diaDeSpa.getCodPack());
            for (Turno sesionAntigua : sesionesActuales) {
                boolean existeEnNuevaLista = diaDeSpa.getSesiones().stream()
                        .anyMatch(s -> s.getCodSesion() == sesionAntigua.getCodSesion());

                if (!existeEnNuevaLista) {
                    turnoData.BajaTurno(sesionAntigua.getCodSesion());
                }
            }

            for (Turno sesion : diaDeSpa.getSesiones()) {
                if (sesion.getCodSesion() > 0) {
                    // Sesión existente - actualizar
                    turnoData.ActualizarTurno(sesion);
                } else {
                    // Nueva sesión - guardar
                    turnoData.guardarSesionConPack(sesion, diaDeSpa.getCodPack());
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al actualizar sesiones: " + e.getMessage());
        }
    }

    public void eliminarDiaDeSpa(int codPack) {
        String sql = "DELETE FROM dia_de_spa WHERE codPack = ?";
        try {

            turnoData.eliminarSesionesPorDiaSpa(codPack);

            PreparedStatement ps = conexion.prepareStatement(sql);
            ps.setInt(1, codPack);
            int filasAfectadas = ps.executeUpdate();

            if (filasAfectadas > 0) {
                JOptionPane.showMessageDialog(null, "Día de spa eliminado exitosamente");
            } else {
                JOptionPane.showMessageDialog(null, "No se encontró el día de spa a eliminar");
            }
            ps.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al eliminar el día de spa: " + e.getMessage());
        }
    }

    /*public void cambiarEstadoDiaDeSpa(int codPack, boolean estado) {
        String sql = "UPDATE dia_de_spa SET estado = ? WHERE codPack = ?";
        try {
            PreparedStatement ps = conexion.prepareStatement(sql);
            ps.setBoolean(1, estado);
            ps.setInt(2, codPack);

            int filasAfectadas = ps.executeUpdate();
            if (filasAfectadas > 0) {
                String estadoStr = estado ? "activado" : "desactivado";
                JOptionPane.showMessageDialog(null, "Día de spa " + estadoStr + " exitosamente");
            }
            ps.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al cambiar estado del día de spa: " + e.getMessage());
        }
    }*/
     //ALTA LOGICA = darle estado activo a los inactivos?
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
       //BAJA LOGICA = darle estado inactivo a los activos?
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

    public List<DiaDeSpa> buscarDiasDeSpaPorCliente(int codCli) {
        String sql = "SELECT * FROM dia_de_spa WHERE codCli = ?";
        List<DiaDeSpa> diasDeSpa = new ArrayList<>();

        try {
            PreparedStatement ps = conexion.prepareStatement(sql);
            ps.setInt(1, codCli);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int codPack = rs.getInt("codPack");
                Timestamp fechaHora = rs.getTimestamp("fechaYHora");
                String preferencias = rs.getString("preferencias");
                double monto = rs.getDouble("monto");
                boolean estado = rs.getBoolean("estado");

                Cliente cliente = clienteData.buscarCliente(codCli);
                List<Turno> sesiones = turnoData.buscarSesionesPorDiaSpa(codPack);

                DiaDeSpa diaDeSpa = new DiaDeSpa(
                        fechaHora.toLocalDateTime(),
                        preferencias,
                        monto,
                        estado,
                        cliente,
                        sesiones
                );
                diaDeSpa.setCodPack(codPack);
                diasDeSpa.add(diaDeSpa);
            }
            ps.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al buscar días de spa por cliente: " + e.getMessage());
        }
        return diasDeSpa;
    }

    public List<DiaDeSpa> buscarDiasDeSpaPorFecha(java.sql.Date fecha) {
        String sql = "SELECT * FROM dia_de_spa WHERE DATE(fechaYHora) = ?";
        List<DiaDeSpa> diasDeSpa = new ArrayList<>();

        try {
            PreparedStatement ps = conexion.prepareStatement(sql);
            ps.setDate(1, fecha);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int codPack = rs.getInt("codPack");
                Timestamp fechaHora = rs.getTimestamp("fechaYHora");
                String preferencias = rs.getString("preferencias");
                double monto = rs.getDouble("monto");
                boolean estado = rs.getBoolean("estado");
                int codCli = rs.getInt("codCli");

                Cliente cliente = clienteData.buscarCliente(codCli);
                List<Turno> sesiones = turnoData.buscarSesionesPorDiaSpa(codPack);

                DiaDeSpa diaDeSpa = new DiaDeSpa(
                        fechaHora.toLocalDateTime(),
                        preferencias,
                        monto,
                        estado,
                        cliente,
                        sesiones
                );
                diaDeSpa.setCodPack(codPack);
                diasDeSpa.add(diaDeSpa);
            }
            ps.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al buscar días de spa por fecha: " + e.getMessage());
        }
        return diasDeSpa;
    }

    public double calcularMontoTotal(int codPack) {
        String sql = "SELECT SUM(t.costo) as total FROM sesion s "
                + "JOIN tratamiento t ON s.codTratamiento = t.codTratam "
                + "WHERE s.codPack = ?";
        double total = 0.0;

        try {
            PreparedStatement ps = conexion.prepareStatement(sql);
            ps.setInt(1, codPack);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                total = rs.getDouble("total");
            }
            ps.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al calcular monto total: " + e.getMessage());
        }
        return total;
    }
    //Generar informe de días de spa en una fecha especifica

    public List<Object[]> generarInformeDiasDeSpaPorFecha(java.sql.Date fecha) {
        String sql = "SELECT d.codPack, c.NombreCompleto as cliente, d.fechaYHora, "
                + "d.preferencias, d.monto, COUNT(s.codSesion) as cantidad_sesiones "
                + "FROM dia_de_spa d "
                + "JOIN cliente c ON d.codCli = c.codCli "
                + "LEFT JOIN sesion s ON d.codPack = s.codPack "
                + "WHERE DATE(d.fechaYHora) = ? "
                + "GROUP BY d.codPack, c.NombreCompleto, d.fechaYHora, d.preferencias, d.monto "
                + "ORDER BY d.fechaYHora";
        List<Object[]> resultados = new ArrayList<>();

        try {
            PreparedStatement ps = conexion.prepareStatement(sql);
            ps.setDate(1, fecha);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Object[] fila = {
                    rs.getInt("codPack"),
                    rs.getString("cliente"),
                    rs.getTimestamp("fechaYHora"),
                    rs.getString("preferencias"),
                    rs.getDouble("monto"),
                    rs.getInt("cantidad_sesiones")
                };
                resultados.add(fila);
            }
            ps.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al generar informe de dias de spa: " + e.getMessage());
        }
        return resultados;
    }

    //Listar dias de spa con detalles completos por fecha
     
    public List<DiaDeSpa> listarDiasDeSpaCompletosPorFecha(java.sql.Date fecha) {
        String sql = "SELECT d.*, c.* FROM dia_de_spa d "
                + "JOIN cliente c ON d.codCli = c.codCli "
                + "WHERE DATE(d.fechaYHora) = ? "
                + "ORDER BY d.fechaYHora";
        List<DiaDeSpa> diasDeSpa = new ArrayList<>();

        try {
            PreparedStatement ps = conexion.prepareStatement(sql);
            ps.setDate(1, fecha);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                // Crear cliente
                Cliente cliente = new Cliente(
                        rs.getInt("DNI"),
                        rs.getString("NombreCompleto"),
                        rs.getLong("Telefono"),
                        rs.getInt("Edad"),
                        rs.getString("Afecciones"),
                        rs.getBoolean("Estado")
                );
                cliente.setCodCli(rs.getInt("codCli"));

                // Obtener sesiones
                List<Turno> sesiones = turnoData.buscarSesionesPorDiaSpa(rs.getInt("codPack"));

                // Crear dia de spa
                DiaDeSpa diaDeSpa = new DiaDeSpa(
                        rs.getTimestamp("fechaYHora").toLocalDateTime(),
                        rs.getString("preferencias"),
                        rs.getDouble("monto"),
                        rs.getBoolean("estado"),
                        cliente,
                        sesiones
                );
                diaDeSpa.setCodPack(rs.getInt("codPack"));
                diasDeSpa.add(diaDeSpa);
            }
            ps.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al listar dias de spa por fecha: " + e.getMessage());
        }
        return diasDeSpa;
    }
}
