/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

import java.util.Date;

/**
 *
 * @author olivera
 */
public class reservaModel {

    private int idReserva;
    private int idCliente; // FK a Cliente
    private int nroHab;    // FK a Habitacion
    private Date fechaInicio;
    private Date fechaFin;
    private int nroOcupantes;
    private String estado;
    private habitacionModel habitacion; // ← agrega esto

    public reservaModel() {
    }

    public reservaModel(int idReserva, int idCliente, int nroHab, Date fechaInicio, Date fechaFin, int nroOcupantes, String estado) {
        this.idReserva = idReserva;
        this.idCliente = idCliente;
        this.nroHab = nroHab;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.nroOcupantes = nroOcupantes;
        this.estado = estado;
    }

    public habitacionModel getHabitacion() {
        return habitacion;
    }

    public void setHabitacion(habitacionModel habitacion) {
        this.habitacion = habitacion;
    }
    

    public int getIdReserva() {
        return idReserva;
    }

    public void setIdReserva(int idReserva) {
        this.idReserva = idReserva;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public int getNroHab() {
        return nroHab;
    }

    public void setNroHab(int nroHab) {
        this.nroHab = nroHab;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    public int getNroOcupantes() {
        return nroOcupantes;
    }

    public void setNroOcupantes(int nroOcupantes) {
        this.nroOcupantes = nroOcupantes;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

}
