/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

/**
 *
 * @author olivera
 */
public class servicioAdicionalModel {

    private int idServicio;
    private String nombreServicio;
    private double precioEstandar;

    public servicioAdicionalModel() {
    }

    public servicioAdicionalModel(int idServicio, String nombreServicio, double precioEstandar) {
        this.idServicio = idServicio;
        this.nombreServicio = nombreServicio;
        this.precioEstandar = precioEstandar;
    }

    public int getIdServicio() {
        return idServicio;
    }

    public void setIdServicio(int idServicio) {
        this.idServicio = idServicio;
    }

    public String getNombreServicio() {
        return nombreServicio;
    }

    public void setNombreServicio(String nombreServicio) {
        this.nombreServicio = nombreServicio;
    }

    public double getPrecioEstandar() {
        return precioEstandar;
    }

    public void setPrecioEstandar(double precioEstandar) {
        this.precioEstandar = precioEstandar;
    }

}
