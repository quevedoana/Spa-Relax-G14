/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.List;


/**
 *
 * @author esteb
 */
public class DiaDeSpa {
    private int codPack;
    private LocalDateTime fechaYHora;
    private String preferencias;
    private double monto;
    private boolean estado;
    private Cliente c;
    private  List<Sesion> sesiones;

    public DiaDeSpa() {
    }

    public DiaDeSpa(LocalDateTime fechaYHora, String preferencias, double monto, boolean estado, Cliente c, List<Sesion> sesiones) {
        this.fechaYHora = fechaYHora;
        this.preferencias = preferencias;
        this.monto = monto;
        this.estado = estado;
        this.c = c;
        this.sesiones = sesiones;
    }

   

    public int getCodPack() {
        return codPack;
    }

    public void setCodPack(int codPack) {
        this.codPack = codPack;
    }

    public LocalDateTime getFechaYHora() {
        return fechaYHora;
    }

    public void setFechaYHora(LocalDateTime fechaYHora) {
        this.fechaYHora = fechaYHora;
    }

  
    public String getPreferencias() {
        return preferencias;
    }

    public void setPreferencias(String preferencias) {
        this.preferencias = preferencias;
    }

    public double getMonto() {
        return monto;
    }

    public void setMonto(double monto) {
        this.monto = monto;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public List<Sesion> getSesiones() {
        return sesiones;
    }

    public void setSesiones(List<Sesion> sesiones) {
        this.sesiones = sesiones;
    }



    public Cliente getCliente() {
        return c;
    }

    public void setCliente(Cliente c) {
        this.c = c;
    }
    

   

   
    
    
    
    
    
    
}
