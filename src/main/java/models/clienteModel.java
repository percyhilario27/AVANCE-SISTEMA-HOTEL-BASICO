/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

/**
 *
 * @author olivera
 */
public class clienteModel extends usuarioModel{
    private int idCliente;
    private String tipoDoc;
    private String nroDoc;
    private String celular;
    private boolean estaVetado;
    private String fotoPerfil;

    public clienteModel() {}

    public clienteModel(int idUsuario, String email, String password, String rol, String authProvider, String nombres, String apellidos, int idCliente, String tipoDoc, String nroDoc, String celular, boolean estaVetado, String fotoPerfil) {
        super(idUsuario, email, password, rol, authProvider, nombres, apellidos);
        this.idCliente = idCliente;
        this.tipoDoc = tipoDoc;
        this.nroDoc = nroDoc;
        this.celular = celular;
        this.estaVetado = estaVetado;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }
    
 

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
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

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public boolean isEstaVetado() {
        return estaVetado;
    }

    public void setEstaVetado(boolean estaVetado) {
        this.estaVetado = estaVetado;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public String getAuthProvider() {
        return authProvider;
    }

    public void setAuthProvider(String authProvider) {
        this.authProvider = authProvider;
    }

    public String getFotoPerfil() {
        return fotoPerfil;
    }

    public void setFotoPerfil(String fotoPerfil) {
        this.fotoPerfil = fotoPerfil;
    }
    
    
}
