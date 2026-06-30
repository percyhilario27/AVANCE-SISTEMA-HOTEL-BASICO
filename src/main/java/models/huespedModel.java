/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

/**
 *
 * @author olivera
 */
public class huespedModel {
    private int idHuesped;
    private int idReserva; // FK a Reserva
    private String tipoDoc;
    private String nroDoc;
    private String nombreCompleto;
    private boolean esMenor;

    public huespedModel() {}

    public huespedModel(int idHuesped, int idReserva, String tipoDoc, String nroDoc, String nombreCompleto, boolean esMenor) {
        this.idHuesped = idHuesped;
        this.idReserva = idReserva;
        this.tipoDoc = tipoDoc;
        this.nroDoc = nroDoc;
        this.nombreCompleto = nombreCompleto;
        this.esMenor = esMenor;
    }

    public int getIdHuesped() {
        return idHuesped;
    }

    public void setIdHuesped(int idHuesped) {
        this.idHuesped = idHuesped;
    }

    public int getIdReserva() {
        return idReserva;
    }

    public void setIdReserva(int idReserva) {
        this.idReserva = idReserva;
    }

    public String getTipoDoc() {
        return tipoDoc;
    }

    public void setTipoDoc(String tipoDoc) {
        this.tipoDoc = tipoDoc;
    }

    public String getNroDoc() {
        return nroDoc;
    }

    public void setNroDoc(String nroDoc) {
        this.nroDoc = nroDoc;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public boolean isEsMenor() {
        return esMenor;
    }

    public void setEsMenor(boolean esMenor) {
        this.esMenor = esMenor;
    }
    
    
}
