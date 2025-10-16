/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Persistencia;

import Modelo.Cliente;
import Modelo.Conexion;
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
 * @author Toto
 */
public class clienteData {
    
    private Connection conexion = null;

    public clienteData() {
        conexion = Conexion.getConexion();
    }
    
    public void guardarCliente(Cliente c) { //insert
        String query = "INSERT INTO cliente(DNI, NombreCompleto, Telefono, Edad, Afecciones, Estado) VALUES (?,?,?,?,?,?)";
        try {
            PreparedStatement ps = conexion.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, c.getDni());
            ps.setString(2, c.getNombreCompleto());
            ps.setInt(3, c.getTelefono());
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
        String sql = "SELECT * FROM cliente WHERE codCli=?";
        Cliente cli = null;
        try {
            PreparedStatement ps = conexion.prepareStatement(sql);
            ps.setInt(1, CodCli);
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
        String sql = "SELECT * FROM cliente WHERE";
        List<Cliente> cliente = new ArrayList ();
        try {
            PreparedStatement ps = conexion.prepareStatement(sql);
            ResultSet resultado = ps.executeQuery();
            while (resultado.next()) {

                Cliente cli = new Cliente(resultado.getInt("DNI"), resultado.getString("NombreCompleto"), resultado.getInt("Telefono"), resultado.getInt("Edad"), resultado.getString("Afecciones"), resultado.getBoolean("Estado"));
                cli.setCodCli(resultado.getInt("CodCli"));
                cliente.add(cli);
            }
            ps.close();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al buscar el Cliente" + e.getMessage());

        }
        return cliente;
    }
    
    
}
