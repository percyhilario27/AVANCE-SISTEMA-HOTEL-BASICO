package dao;

import config.basededatos;
import models.usuarioModel;
import java.sql.*;

public class usuarioDao {

    // Método para inicio de sesión
    public usuarioModel validar(String email, String password) {
        usuarioModel user = null;
        String sql = "SELECT * FROM usuarios WHERE email = ? AND password = ?";
        
        // Uso de Try-With-Resources para evitar fugas de memoria
        try (Connection c = new basededatos().getCon();
             PreparedStatement pst = c.prepareStatement(sql)) {
             
            pst.setString(1, email);
            pst.setString(2, password);
            
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    user = new usuarioModel();
                    user.setIdUsuario(rs.getInt("id_usuario"));
                    user.setEmail(rs.getString("email"));
                    user.setRol(rs.getString("rol"));
                }
            }
        } catch (SQLException e) {
            System.out.println("Error en DAO validar: " + e.getMessage());
        }
        return user;
    }

    // Registrar nuevo usuario base
    public boolean registrarUsuario(usuarioModel usuario) {
        // Se cambió a la clase de conexión correcta y a los nombres de columnas de tu DB
        String sql = "INSERT INTO usuarios (email, password, rol) VALUES (?, ?, ?)";

        try (Connection con = new basededatos().getCon();
             PreparedStatement ps = con.prepareStatement(sql)) {
             
            ps.setString(1, usuario.getEmail());
            ps.setString(2, usuario.getPassword());
            ps.setString(3, usuario.getRol()); // Asumiendo que ahora tu modelo tiene roles
            
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // Verificar si el correo (email) ya existe
    public boolean existeCorreo(String email) {
        String sql = "SELECT id_usuario FROM usuarios WHERE email = ?";

        try (Connection c = new basededatos().getCon();
             PreparedStatement ps = c.prepareStatement(sql)) {
             
            ps.setString(1, email);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // Buscar usuario por su email
    public usuarioModel buscarPorCorreo(String email) {
        usuarioModel usuario = null;
        String sql = "SELECT * FROM usuarios WHERE email = ?";

        try (Connection c = new basededatos().getCon();
             PreparedStatement ps = c.prepareStatement(sql)) {
             
            ps.setString(1, email);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    // Corregido: instancia correcta del modelo y columnas
                    usuario = new usuarioModel(); 
                    usuario.setIdUsuario(rs.getInt("id_usuario"));
                    usuario.setEmail(rs.getString("email"));
                    usuario.setRol(rs.getString("rol"));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return usuario;
    }
}