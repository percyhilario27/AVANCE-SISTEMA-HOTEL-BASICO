/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

/**
 *
 * @author olivera
 */
public class habitacionModel {

    private int nroHab;
    private String tipo;
    private int capacidadMax;
    private double precioNoche;
    private String estado;
    private String urlImagen; // <-- ¡Nuevo atributo agregado!

    public habitacionModel() {}

    public habitacionModel(int nroHab, String tipo, int capacidadMax, double precioNoche, String estado,String urlImagen) {
        this.nroHab = nroHab;
        this.tipo = tipo;
        this.capacidadMax = capacidadMax;
        this.precioNoche = precioNoche;
        this.estado = estado;
        this.urlImagen = urlImagen;
    }

    public int getNroHab() {
        return nroHab;
    }

    public void setNroHab(int nroHab) {
        this.nroHab = nroHab;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public int getCapacidadMax() {
        return capacidadMax;
    }

    public void setCapacidadMax(int capacidadMax) {
        this.capacidadMax = capacidadMax;
    }

    public double getPrecioNoche() {
        return precioNoche;
    }

    public void setPrecioNoche(double precioNoche) {
        this.precioNoche = precioNoche;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getUrlImagen() {
        return urlImagen;
    }

    public void setUrlImagen(String urlImagen) {
        this.urlImagen = urlImagen;
    }

}
