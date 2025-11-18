/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

/**
 *
 * @author Anitabonita
 */
public class Especialista {
    private String matricula;
    private String NombreYApellido;
    private long telefono;
    private String especialidad;
    private boolean estado;

    public Especialista() {
    }

    
    public Especialista(String matricula, String NombreYApellido, long telefono, String especialidad, boolean estado) {
        this.matricula = matricula;
        this.NombreYApellido = NombreYApellido;
        this.telefono = telefono;
        this.especialidad = especialidad;
        this.estado = estado;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public String getNombreYApellido() {
        return NombreYApellido;
    }

    public void setNombreYApellido(String nombreYApellido) {
        this.NombreYApellido = nombreYApellido;
    }

    public long getTelefono() {
        return telefono;
    }

    public void setTelefono(long telefono) {
        this.telefono = telefono;
    }

    public String getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    @Override
    public String toString() {
        return "Especialista: " + "matricula=" + matricula + ", nombre y apellido=" + NombreYApellido + ", telefono=" + telefono + ", especialidad=" + especialidad + ", estado=" + estado;
    }


    
    
}
