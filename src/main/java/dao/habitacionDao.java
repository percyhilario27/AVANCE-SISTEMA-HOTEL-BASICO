package dao;

import config.basededatos;
import models.habitacionModel;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class habitacionDao {

    // ── Método privado: mapea ResultSet a habitacionModel ────────────────────
    private habitacionModel mapear(ResultSet rs) throws SQLException {
        habitacionModel h = new habitacionModel();
        h.setNroHab(rs.getInt("nro_hab"));
        h.setTipo(rs.getString("tipo"));
        h.setCapacidadMax(rs.getInt("capacidad_max"));
        h.setPrecioNoche(rs.getDouble("precio_noche"));
        h.setEstado(rs.getString("estado"));
        h.setUrlImagen(rs.getString("url_imagen"));
        return h;
    }

    // ── 1. Buscar habitaciones disponibles con filtros ────────────────────────
    // Una habitación está disponible si no tiene reservas que se solapen
    // con el rango de fechas pedido Y su capacidad cubre los huéspedes
    public List<habitacionModel> buscarDisponibles(String fechaEntrada, String fechaSalida,
                                                    int huespedes, String tipo) {
        List<habitacionModel> lista = new ArrayList<>();

        // Habitación disponible = no aparece en ninguna reserva activa que se solape
        // La condición de solapamiento es: entrada < fechaSalida AND salida > fechaEntrada
        String sql = "SELECT nro_hab, tipo, capacidad_max, precio_noche, estado, url_imagen " +
                     "FROM habitaciones h " +
                     "WHERE h.capacidad_max >= ? " +
                     "AND h.estado = 'DISPONIBLE' " +
                     "AND (? = 'Todas' OR h.tipo = ?) " +
                     "AND h.nro_hab NOT IN ( " +
                     "    SELECT r.nro_hab FROM reservas r " +
                     "    WHERE r.estado != 'CANCELADA' " +
                     "    AND r.fecha_inicio < ? " +
                     "    AND r.fecha_fin > ? " +
                     ")";

        try (Connection con = new basededatos().getCon();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setInt(1, huespedes);
            pst.setString(2, tipo);
            pst.setString(3, tipo);
            pst.setString(4, fechaSalida);   // fecha_inicio < fechaSalida
            pst.setString(5, fechaEntrada);  // fecha_fin    > fechaEntrada

            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    lista.add(mapear(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();    
            System.out.println("Error en buscarDisponibles: " + e.getMessage());
        }
        return lista;
    }

    // ── 2. Buscar habitación por ID ───────────────────────────────────────────
    public habitacionModel buscarPorId(int nroHab) {
        String sql = "SELECT nro_hab, tipo, capacidad_max, precio_noche, estado, url_imagen " +
                     "FROM habitaciones WHERE nro_hab = ?";

        try (Connection con = new basededatos().getCon();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setInt(1, nroHab);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    return mapear(rs);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error en buscarPorId: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    // ── 3. Listar todas las habitaciones ─────────────────────────────────────
    public List<habitacionModel> listar() {
        List<habitacionModel> lista = new ArrayList<>();
        String sql = "SELECT nro_hab, tipo, capacidad_max, precio_noche, estado, url_imagen " +
                     "FROM habitaciones";

        try (Connection con = new basededatos().getCon();
             PreparedStatement pst = con.prepareStatement(sql);
             ResultSet rs = pst.executeQuery()) {

            while (rs.next()) {
                lista.add(mapear(rs));
            }
        } catch (SQLException e) {
            System.out.println("Error en listar: " + e.getMessage());
            e.printStackTrace();
        }
        return lista;
    }
}