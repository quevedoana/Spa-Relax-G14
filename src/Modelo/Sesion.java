/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import java.time.LocalDateTime;

/**
 *
 * @author esteb
 */
public class Sesion {
    private int codSesion;
    private LocalDateTime fechaYHoraDeInicio;
    private LocalDateTime fechaYHoraDeFin;
    private Tratamiento tratamiento;
    private Consultorio Consultorio;
    private String matriculaMasajista;
    private Instalacion instalacion;
    private DiaDeSpa DiaDeSpa;
    private boolean estado;

    public Sesion() {
    }

    public Sesion(LocalDateTime fechaYHoraDeInicio, LocalDateTime fechaYHoraDeFin, Tratamiento tratamiento, Consultorio Consultorio, Instalacion instalacion, boolean estado) {
        this.fechaYHoraDeInicio = fechaYHoraDeInicio;
        this.fechaYHoraDeFin = fechaYHoraDeFin;
        this.tratamiento = tratamiento;
        this.Consultorio = Consultorio;
        this.instalacion = instalacion;
        this.estado = estado;
    }

    public int getCodSesion() {
        return codSesion;
    }

    public void setCodSesion(int codSesion) {
        this.codSesion = codSesion;
    }

    public LocalDateTime getFechaYHoraDeInicio() {
        return fechaYHoraDeInicio;
    }

    public void setFechaYHoraDeInicio(LocalDateTime fechaYHoraDeInicio) {
        this.fechaYHoraDeInicio = fechaYHoraDeInicio;
    }

    public LocalDateTime getFechaYHoraDeFin() {
        return fechaYHoraDeFin;
    }

    public void setFechaYHoraDeFin(LocalDateTime fechaYHoraDeFin) {
        this.fechaYHoraDeFin = fechaYHoraDeFin;
    }

    public Tratamiento getTratamiento() {
        return tratamiento;
    }

    public void setTratamiento(Tratamiento tratamiento) {
        this.tratamiento = tratamiento;
    }

    public Consultorio getConsultorio() {
        return Consultorio;
    }

    public void setConsultorio(Consultorio Consultorio) {
        this.Consultorio = Consultorio;
    }

    public String getMatriculaMasajista() {
        return matriculaMasajista;
    }

    public void setMatriculaMasajista(String matriculaMasajista) {
        this.matriculaMasajista = matriculaMasajista;
    }

    public Instalacion getInstalacion() {
        return instalacion;
    }

    public void setInstalacion(Instalacion instalacion) {
        this.instalacion = instalacion;
    }

    public DiaDeSpa getDiaDeSpa() {
        return DiaDeSpa;
    }

    public void setDiaDeSpa(DiaDeSpa DiaDeSpa) {
        this.DiaDeSpa = DiaDeSpa;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }
    
  
    
    
    
    

    
    
}
