/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

/**
 *
 * @author olivera
 */
public class usuarioModel {
    protected int idUsuario;
    protected String email;
    protected String password; // Recuerda que almacenará el hash 
    protected String rol;
    protected String authProvider;
    protected String nombres;
    protected String apellidos;

    public usuarioModel() {
    }

    public usuarioModel(int idUsuario, String email, String password, String rol, String authProvider, String nombre, String apellidos) {
        this.idUsuario = idUsuario;
        this.email = email;
        this.password = password;
        this.rol = rol;
        this.authProvider = authProvider;
    }

    public int getIdUsuario() {
        return idUsuario;
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
    
    
}
