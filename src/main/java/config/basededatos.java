/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package config;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class basededatos {

    public basededatos() {
    }

    public Connection getCon() {
        Connection c = null;
        String usr = "root";
        String pwd = "proyectoweb";
        String urlbd = "jdbc:mysql://localhost:3306/proyectoSoft2?useSSL=false&allowPublicKeyRetrieval=true";
        try {
            DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
            c = DriverManager.getConnection(urlbd, usr, pwd);
        } catch (SQLException ex) {
            Logger.getLogger(basededatos.class.getName()).log(Level.SEVERE, null, ex);
        }
        return c;
    }
}
