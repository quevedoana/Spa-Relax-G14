/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spaRelax;

import Modelo.Cliente;
import Modelo.Conexion;
import Persistencia.ClienteData;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Anitabonita
 */
public class spaRelax {
    private static ClienteData clienteData;
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Connection con = Conexion.getConexion();
        List <Cliente>listaCliente=new ArrayList();
       Cliente cli1=new Cliente(45563392,"Naranjo Maria Candela",266412344, 22,"Muy eneferma de la cabezita", true);
       clienteData.guardarCliente(cli1);
       Cliente cli2=new Cliente(44075900,"Assat Antonio Tomas",26645687, 23,"Demasiado sano",false);
       clienteData.guardarCliente(cli2);
       Cliente cli3=new Cliente(39137807,"di Fiore Mariano Enzo",26645687, 15, "Mucha facha",true);
       clienteData.guardarCliente(cli3);
       Cliente cli4=new Cliente(45886496,"Barroso Esteban Jose",26645609, 21, "Mucho counter",true);
       clienteData.guardarCliente(cli4);
       Cliente cli5=new Cliente(43343200,"Quevedo Ana Banana",26647324,25, "Mucha lokura",true);
       clienteData.guardarCliente(cli5);
       
       listaCliente=clienteData.ListarCliente();// metodo listarAlumno
       
        for (Cliente cliente : listaCliente) {
            System.out.println(cliente);
        }
    }
    
}
