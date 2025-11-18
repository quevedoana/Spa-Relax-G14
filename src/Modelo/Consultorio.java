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
public class Consultorio {
    
    
    
    private int nroConsultorio;
    private String usos;
    private String equipamiento;
    private boolean apto;
    
    public Consultorio(){
        
    }

    public Consultorio( String usos, String equipamiento, boolean apto) {
        this.usos = usos;
        this.equipamiento = equipamiento;
        this.apto = apto;
    }

    public Consultorio(int nroConsultorio, String usos, String equipamiento, boolean apto) {
        this.nroConsultorio = nroConsultorio;
        this.usos = usos;
        this.equipamiento = equipamiento;
        this.apto = apto;
    }
    

    public int getNroConsultorio() {
        return nroConsultorio;
    }

    public void setNroConsultorio(int nroConsultorio) {
        this.nroConsultorio = nroConsultorio;
    }

    public String getUsos() {
        return usos;
    }

    public void setUsos(String usos) {
        this.usos = usos;
    }

    public String getEquipamiento() {
        return equipamiento;
    }

    public void setEquipamiento(String equipamiento) {
        this.equipamiento = equipamiento;
    }

    public boolean isApto() {
        return apto;
    }

    public void setApto(boolean apto) {
        this.apto = apto;
    }

    @Override
    public String toString() {
        return  nroConsultorio + " " + usos + " " + equipamiento + " " + apto;
    }
    
    
    
}
