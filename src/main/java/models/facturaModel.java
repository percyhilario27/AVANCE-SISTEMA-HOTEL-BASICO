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
public class facturaModel {

    private int idFactura;
    private Integer idRecep; // Puede ser null si el pago es automático en línea [cite: 119]
    private String nroComprobante;
    private String tipoComprobante;
    private Date fechaEmision;
    private String metodoPago;
    private double montoTotal;
    private String estadoPago;

    public facturaModel() {}

    public facturaModel(int idFactura, Integer idRecep, String nroComprobante, String tipoComprobante, Date fechaEmision, String metodoPago, double montoTotal, String estadoPago) {
        this.idFactura = idFactura;
        this.idRecep = idRecep;
        this.nroComprobante = nroComprobante;
        this.tipoComprobante = tipoComprobante;
        this.fechaEmision = fechaEmision;
        this.metodoPago = metodoPago;
        this.montoTotal = montoTotal;
        this.estadoPago = estadoPago;
    }

    public int getIdFactura() {
        return idFactura;
    }

    public void setIdFactura(int idFactura) {
        this.idFactura = idFactura;
    }

    public Integer getIdRecep() {
        return idRecep;
    }

    public void setIdRecep(Integer idRecep) {
        this.idRecep = idRecep;
    }

    public String getNroComprobante() {
        return nroComprobante;
    }

    public void setNroComprobante(String nroComprobante) {
        this.nroComprobante = nroComprobante;
    }

    public String getTipoComprobante() {
        return tipoComprobante;
    }

    public void setTipoComprobante(String tipoComprobante) {
        this.tipoComprobante = tipoComprobante;
    }

    public Date getFechaEmision() {
        return fechaEmision;
    }

    public void setFechaEmision(Date fechaEmision) {
        this.fechaEmision = fechaEmision;
    }

    public String getMetodoPago() {
        return metodoPago;
    }

    public void setMetodoPago(String metodoPago) {
        this.metodoPago = metodoPago;
    }

    public double getMontoTotal() {
        return montoTotal;
    }

    public void setMontoTotal(double montoTotal) {
        this.montoTotal = montoTotal;
    }

    public String getEstadoPago() {
        return estadoPago;
    }

    public void setEstadoPago(String estadoPago) {
        this.estadoPago = estadoPago;
    }
    
    

}

