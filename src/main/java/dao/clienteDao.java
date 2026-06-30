package dao;

import models.clienteModel;
import config.basededatos;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class clienteDao {

    // ── 1. Listar todos los clientes (JOIN con usuarios) ──────────────────────
    public List<clienteModel> listar() {
        List<clienteModel> lista = new ArrayList<>();
        String sql = "SELECT u.id_usuario, u.email, u.password, u.rol, u.nombres, u.apellidos, "
                + "       u.auth_provider, "
                + "       c.id_cliente, c.tipo_doc, c.nro_doc, c.celular, c.esta_vetado, c.foto_perfil "
                + "FROM usuarios u "
                + "INNER JOIN clientes c ON u.id_usuario = c.id_usuario";

        try (Connection con = new basededatos().getCon(); PreparedStatement pst = con.prepareStatement(sql); ResultSet rs = pst.executeQuery()) {

            while (rs.next()) {
                clienteModel c = mapearCliente(rs);
                lista.add(c);
            }
        } catch (SQLException ex) {
            System.out.println("Error en listar: " + ex.getMessage());
        }
        return lista;
    }

    // ── 2. Validar login (JOIN para traer datos de ambas tablas) ──────────────
    public clienteModel validar(String email, String password) {
        String sql = "SELECT u.id_usuario, u.email, u.password, u.rol, u.nombres, u.apellidos, "
                + "       u.auth_provider, "
                + "       c.id_cliente, c.tipo_doc, c.nro_doc, c.celular, c.esta_vetado, c.foto_perfil "
                + "FROM usuarios u "
                + "INNER JOIN clientes c ON u.id_usuario = c.id_usuario "
                + "WHERE u.email = ? AND u.password = ? AND u.rol = 'CLIENTE'";

        try (Connection con = new basededatos().getCon(); PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setString(1, email);
            pst.setString(2, password);

            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    return mapearCliente(rs);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error en validar: " + e.getMessage());
        }
        return null;
    }

    // ── 3. Registrar: inserta en usuarios y luego en clientes ─────────────────
    public boolean registrarCliente(clienteModel cliente) {
        String sqlUsuario = "INSERT INTO usuarios (email, password, rol, nombres, apellidos) "
                + "VALUES (?, ?, ?, ?, ?)";
        String sqlCliente = "INSERT INTO clientes (id_usuario, tipo_doc, nro_doc, celular) "
                + "VALUES (?, ?, ?, ?)";

        Connection con = null;
        try {
            con = new basededatos().getCon();
            con.setAutoCommit(false); // Transacción: si uno falla, ambos se revierten

            // 3a. Insertar en usuarios y recuperar el ID generado
            try (PreparedStatement psU = con.prepareStatement(sqlUsuario, Statement.RETURN_GENERATED_KEYS)) {
                psU.setString(1, cliente.getEmail());
                psU.setString(2, cliente.getPassword());
                psU.setString(3, "CLIENTE");
                psU.setString(4, cliente.getNombres());
                psU.setString(5, cliente.getApellidos());
                psU.executeUpdate();

                try (ResultSet keys = psU.getGeneratedKeys()) {
                    if (keys.next()) {
                        int idGenerado = keys.getInt(1);
                        cliente.setIdUsuario(idGenerado); // ← se usa abajo en el INSERT de clientes
                        System.out.println("ID usuario generado: " + idGenerado);
                    }
                }
            }

            // 3b. Insertar en clientes usando el ID recién generado
            try (PreparedStatement psC = con.prepareStatement(sqlCliente)) {
                psC.setInt(1, cliente.getIdUsuario());
                psC.setString(2, cliente.getTipoDoc());
                psC.setString(3, cliente.getNroDoc());
                psC.setString(4, cliente.getCelular());
                psC.executeUpdate();
            }

            con.commit(); // Todo salió bien
            return true;

        } catch (SQLException e) {
            e.printStackTrace(); // ← agrega esto
            System.out.println("Error en registrarCliente: " + e.getMessage());
            try {
                if (con != null) {
                    con.rollback();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            return false;
        } finally {
            try {
                if (con != null) {
                    con.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    // ── 4. Verificar si el correo ya existe ───────────────────────────────────
    public boolean existeCorreo(String email) {
        String sql = "SELECT id_usuario FROM usuarios WHERE email = ?";

        try (Connection con = new basededatos().getCon(); PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, email);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            System.out.println("Error en existeCorreo: " + e.getMessage());
            return false;
        }
    }

    // ── 5. Buscar cliente por correo ──────────────────────────────────────────
    public clienteModel buscarPorCorreo(String email) {
        String sql = "SELECT u.id_usuario, u.email, u.password, u.rol, u.nombres, u.apellidos, "
                + "       u.auth_provider, "
                + "       c.id_cliente, c.tipo_doc, c.nro_doc, c.celular, c.esta_vetado, c.foto_perfil "
                + "FROM usuarios u "
                + "INNER JOIN clientes c ON u.id_usuario = c.id_usuario "
                + "WHERE u.email = ?";

        try (Connection con = new basededatos().getCon(); PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, email);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapearCliente(rs);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error en buscarPorCorreo: " + e.getMessage());
        }
        return null;
    }

    // ── Método privado: mapea un ResultSet a clienteModel ────────────────────
    // Evita repetir el mismo código en cada método de arriba
    private clienteModel mapearCliente(ResultSet rs) throws SQLException {
        clienteModel c = new clienteModel();
        // Campos del padre (usuarioModel)
        c.setIdUsuario(rs.getInt("id_usuario"));
        c.setEmail(rs.getString("email"));
        c.setPassword(rs.getString("password"));
        c.setRol(rs.getString("rol"));
        c.setNombres(rs.getString("nombres"));
        c.setApellidos(rs.getString("apellidos"));
        c.setAuthProvider(rs.getString("auth_provider"));
        // Campos propios de clienteModel
        c.setIdCliente(rs.getInt("id_cliente"));
        c.setTipoDoc(rs.getString("tipo_doc"));
        c.setNroDoc(rs.getString("nro_doc"));
        c.setCelular(rs.getString("celular"));
        c.setEstaVetado(rs.getBoolean("esta_vetado"));
        c.setFotoPerfil(rs.getString("foto_perfil"));
        return c;
    }
}
