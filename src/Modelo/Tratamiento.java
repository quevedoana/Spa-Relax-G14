/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import java.util.List;

/**
 *
 * @author Candela Naranjo
 */
public class Tratamiento {

    private int codTratam;
    private String nombre;
    private String detalle;
    private String tipo;
    private int duracion;
    private double costo;
    private boolean activo;
    private String producto;

    public Tratamiento() {
    }

    public Tratamiento(String nombre, String detalle, String tipo, int duracion, double costo, boolean activo, String producto) {
        
        this.nombre = nombre;
        this.detalle = detalle;
        this.duracion = duracion;
        this.costo = costo;
        this.activo = activo;
        this.producto = producto;
        this.tipo= tipo;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public int getCodTratam() {
        return codTratam;
    }

    public void setCodTratam(int codTratam) {
        this.codTratam = codTratam;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDetalle() {
        return detalle;
    }

    public void setDetalle(String detalle) {
        this.detalle = detalle;
    }

    public int getDuracion() {
        return duracion;
    }

    public void setDuracion(int duracion) {
        this.duracion = duracion;
    }

    public double getCosto() {
        return costo;
    }

    public void setCosto(double costo) {
        this.costo = costo;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public String getProductos() {
        return producto;
    }

    public void setProductos(String producto) {
        this.producto = producto;
    }

    @Override
    public String toString() {
        return "Tratamiento{" + "codTratam=" + codTratam + ", nombre=" + nombre + ", detalle=" + detalle + ", tipo=" + tipo + ", duracion=" + duracion + ", costo=" + costo + ", activo=" + activo + ", producto utilizado=" + producto + '}';
    }

   
    

}
