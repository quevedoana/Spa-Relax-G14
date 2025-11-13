/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Persistencia;

import Modelo.Cliente;
import Modelo.Conexion;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author Toto
 */
public class ClienteData {
    
    private Connection conexion = null;

    public ClienteData() {
        conexion = Conexion.getConexion();
    }
    //Parte TOTO
    public void guardarCliente(Cliente c) { //insert
        String query = "INSERT INTO cliente(DNI, NombreCompleto, Telefono, Edad, Afecciones, Estado) VALUES (?,?,?,?,?,?)";
        try {
            PreparedStatement ps = conexion.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, c.getDni());
            ps.setString(2, c.getNombreCompleto());
            ps.setLong(3, c.getTelefono());
            ps.setInt(4, c.getEdad());
            ps.setString(5, c.getAfecciones());
            ps.setBoolean(6, c.isEstado());
            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                c.setCodCli(rs.getInt(1));
            } else {
                JOptionPane.showMessageDialog(null, "No se pudo obtener el id del Cliente");
                ps.close();
            }
            ps.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al guardar el Cliente" + e.getMessage());
        }

    }
    
    public Cliente buscarCliente(int CodCli) { //select 1 alumno
        String sql = "SELECT * FROM cliente WHERE codCli = ? ";
        Cliente cli = null;
        try {
            PreparedStatement ps = conexion.prepareStatement(sql);
            ps.setInt(1, CodCli);
            ResultSet resultado = ps.executeQuery();
            if (resultado.next()) {

                cli = new Cliente(
                        resultado.getInt("DNI"), 
                        resultado.getString("NombreCompleto"), 
                        resultado.getLong("Telefono"),
                        resultado.getInt("Edad"), 
                        resultado.getString("Afecciones"),
                        resultado.getBoolean("Estado")
                );
                
                cli.setCodCli(resultado.getInt("codCli"));

            } else {
                System.out.println("No se encontro el Cliente");
            }
            ps.close();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al buscar el Cliente" + e.getMessage());

        }
        return cli;
    }
   public Cliente buscarClientePorDni(int DNI) { //select 1 alumno
        String sql = "SELECT * FROM cliente WHERE DNI = ? " ;
        Cliente cli = null;
        try {
            PreparedStatement ps = conexion.prepareStatement(sql);
            ps.setInt(1, DNI);
            ResultSet resultado = ps.executeQuery();
            if (resultado.next()) {

                cli = new Cliente(resultado.getInt("DNI"), resultado.getString("NombreCompleto"), resultado.getInt("Telefono"), resultado.getInt("Edad"), resultado.getString("Afecciones"), resultado.getBoolean("Estado"));
                cli.setCodCli(resultado.getInt("CodCli"));

            } else {
                System.out.println("No se encontro el Cliente");
            }
            ps.close();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al buscar el Cliente" + e.getMessage());

        }
        return cli;
    }
    //para validar duplicados de dni
     public Cliente buscarClientePorDni(int DNI) { //select 1 alumno
        String sql = "SELECT * FROM cliente WHERE DNI = ? " ;
        Cliente cli = null;
        try {
            PreparedStatement ps = conexion.prepareStatement(sql);
            ps.setInt(1, DNI);
            ResultSet resultado = ps.executeQuery();
            if (resultado.next()) {

                cli = new Cliente(resultado.getInt("DNI"), resultado.getString("NombreCompleto"), resultado.getInt("Telefono"), resultado.getInt("Edad"), resultado.getString("Afecciones"), resultado.getBoolean("Estado"));
                cli.setCodCli(resultado.getInt("CodCli"));

            } else {
                System.out.println("No se encontro el Cliente");
            }
            ps.close();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al buscar el Cliente" + e.getMessage());

        }
        return cli;
    }
    
    public List<Cliente> ListarCliente() { //select 1 alumno
        String sql = "SELECT * FROM cliente WHERE 1";
        List<Cliente> cliente = new ArrayList ();
        try {
            PreparedStatement ps = conexion.prepareStatement(sql);
            ResultSet resultado = ps.executeQuery();
            while (resultado.next()) {

                Cliente cli = new Cliente(
                        resultado.getInt("DNI"),
                        resultado.getString("NombreCompleto"),
                        resultado.getLong("Telefono"),
                        resultado.getInt("Edad"),
                        resultado.getString("Afecciones"),
                        resultado.getBoolean("Estado")
                );
                cli.setCodCli(resultado.getInt("codCli"));
                cliente.add(cli);
            }
            ps.close();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al buscar el Cliente" + e.getMessage());

        }
        return cliente;
    }
    //Parte Turco
    //ACTUALIZAR CLIENTE 
    public void actualizarCliente(Cliente c) {
        String query = "UPDATE cliente SET DNI = ?, NombreCompleto = ?, Telefono = ?, Edad = ?, Afecciones = ?, Estado = ? WHERE codCli = ? ";

        try {
            PreparedStatement ps = conexion.prepareStatement(query);
                ps.setInt(1, c.getDni());
                ps.setString(2, c.getNombreCompleto());
                ps.setLong(3, c.getTelefono());
                ps.setInt(4, c.getEdad());
                ps.setString(5, c.getAfecciones());
                ps.setBoolean(6, c.isEstado());
                ps.setInt(7, c.getCodCli());
                ps.executeUpdate();
                
                ps.close();
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al actualizar el cliente" + e.getMessage());
        }
    }
    //BORRAR CLIENTE
    public void BorrarCliente(int codCli) {
        String query = "DELETE FROM cliente WHERE codCli = ?";

        try {
            PreparedStatement ps = conexion.prepareStatement(query);
            ps.setInt(1, codCli);
            ps.executeUpdate();
            
            ps.close();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al eliminar el cliente" + e.getMessage());
        }
    }
     //ALTA LOGICA = darle estado activo a los inactivos?
     public void HabilitarCliente(Cliente c){
        String query = "UPDATE cliente SET Estado = 1 WHERE codCli = ? AND Estado = 0";
        
        try {
            PreparedStatement ps = conexion.prepareStatement(query);
                ps.setInt(1, c.getCodCli());
                ps.setBoolean(2, c.isEstado());
                ps.executeUpdate();
                
                ps.close();
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al habilitar el cliente" + e.getMessage());
        }
    }
     //BAJA LOGICA = darle estado inactivo a los activos?
    public void DeshabilitarCliente(Cliente c){
        String query = "UPDATE cliente SET Estado = 0 WHERE codCli = ? AND Estado = 1";
        
        try {
            PreparedStatement ps = conexion.prepareStatement(query);
                ps.setInt(1, c.getCodCli());
                ps.setBoolean(2, c.isEstado());
                ps.executeUpdate();
                
                ps.close();
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al deshabilitar el cliente" + e.getMessage());
        }
    }
     
    
}
