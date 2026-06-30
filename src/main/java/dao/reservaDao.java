package dao;

import config.basededatos;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import models.reservaModel;
import models.habitacionModel;

public class reservaDao {

    public String insertarReservaConPago(int idCliente, int nroHab, String fechaInicio,
            String fechaFin, int nroOcupantes, double montoTotal,
            String pasarela, String idTransaccion) {

        String sqlReserva = "INSERT INTO reservas (id_cliente, nro_hab, fecha_inicio, fecha_fin, nro_ocupantes, estado) "
                + "VALUES (?, ?, ?, ?, ?, 'Confirmada')";

        // codigo_cip → solo se llena si es PagoEfectivo, sino NULL
        String sqlPago = "INSERT INTO pagos_online (id_reserva, id_transaccion_pasarela, codigo_cip, pasarela, monto, moneda, fecha_pago, estado_pago) "
                + "VALUES (?, ?, ?, ?, ?, 'PEN', NOW(), 'Exitoso')";

        Connection con = null;
        try {
            con = new basededatos().getCon();
            con.setAutoCommit(false);

            // 1. Insertar reserva
            int idReservaGenerado;
            try (PreparedStatement psReserva = con.prepareStatement(sqlReserva, Statement.RETURN_GENERATED_KEYS)) {
                psReserva.setInt(1, idCliente);
                psReserva.setInt(2, nroHab);
                psReserva.setDate(3, java.sql.Date.valueOf(fechaInicio));
                psReserva.setDate(4, java.sql.Date.valueOf(fechaFin));
                psReserva.setInt(5, nroOcupantes);

                if (psReserva.executeUpdate() == 0) {
                    con.rollback();
                    return "Error al insertar la reserva.";
                }
                try (ResultSet rs = psReserva.getGeneratedKeys()) {
                    if (!rs.next()) {
                        con.rollback();
                        return "No se obtuvo el ID de la reserva.";
                    }
                    idReservaGenerado = rs.getInt(1);
                    System.out.println("Reserva creada con ID: " + idReservaGenerado);
                }
            }

            // 2. Insertar pago
            try (PreparedStatement psPago = con.prepareStatement(sqlPago)) {
                psPago.setInt(1, idReservaGenerado);
                psPago.setString(2, idTransaccion);

                // codigo_cip: solo para PagoEfectivo, NULL para Tarjeta_Directa
                if (pasarela.equals("PagoEfectivo")) {
                    psPago.setString(3, generarCodigoCIP());
                } else {
                    psPago.setNull(3, java.sql.Types.VARCHAR); // ← NULL para tarjeta
                }

                psPago.setString(4, pasarela);
                psPago.setDouble(5, montoTotal);

                if (psPago.executeUpdate() == 0) {
                    con.rollback();
                    return "Error al insertar el pago.";
                }
            }


            con.commit();
            return "OK";

        } catch (SQLException ex) {
            System.out.println("Error BD en insertarReservaConPago: " + ex.getMessage());
            ex.printStackTrace();
            try {
                if (con != null) {
                    con.rollback();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return "Error BD: " + ex.getMessage();
        } finally {
            try {
                if (con != null) {
                    con.setAutoCommit(true);
                    con.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private String generarCodigoCIP() {
        return "CIP-" + System.currentTimeMillis();
    }

    public List<reservaModel> listarReservasCliente(int idCliente) {
        List<reservaModel> lista = new ArrayList<>();
        String sql = "SELECT r.id_reserva, r.id_cliente, r.nro_hab, r.fecha_inicio, r.fecha_fin, "
                + "       r.nro_ocupantes, r.estado AS estado_reserva, "
                + "       h.tipo, h.capacidad_max, h.precio_noche, "
                + "       h.estado AS estado_hab, h.url_imagen "
                + "FROM reservas r "
                + "LEFT JOIN habitaciones h ON r.nro_hab = h.nro_hab "
                + "WHERE r.id_cliente = ?";

        try (Connection con = new basededatos().getCon(); PreparedStatement pst = con.prepareStatement(sql)) {
            pst.setInt(1, idCliente);
            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    reservaModel r = new reservaModel();
                    habitacionModel h = new habitacionModel();

                    r.setIdReserva(rs.getInt("id_reserva"));
                    r.setIdCliente(rs.getInt("id_cliente"));
                    r.setNroHab(rs.getInt("nro_hab"));
                    r.setFechaInicio(rs.getDate("fecha_inicio"));
                    r.setFechaFin(rs.getDate("fecha_fin"));
                    r.setNroOcupantes(rs.getInt("nro_ocupantes"));
                    r.setEstado(rs.getString("estado_reserva"));

                    h.setNroHab(rs.getInt("nro_hab"));
                    h.setTipo(rs.getString("tipo") != null ? rs.getString("tipo") : "Desconocido");
                    h.setCapacidadMax(rs.getInt("capacidad_max"));
                    h.setPrecioNoche(rs.getDouble("precio_noche"));
                    h.setEstado(rs.getString("estado_hab"));
                    h.setUrlImagen(rs.getString("url_imagen"));

                    r.setHabitacion(h);
                    lista.add(r);
                }
            }
        } catch (SQLException ex) {
            System.out.println("Error en listarReservasCliente: " + ex.getMessage());
        }
        return lista;
    }

    public boolean eliminarReserva(int idReserva) {
        String sql = "UPDATE reservas SET estado = 'CANCELADA' WHERE id_reserva = ?";
        try (Connection con = new basededatos().getCon(); PreparedStatement pst = con.prepareStatement(sql)) {
            pst.setInt(1, idReserva);
            return pst.executeUpdate() > 0;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }
}
