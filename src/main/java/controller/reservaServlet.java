package controller;

import dao.habitacionDao;
import dao.reservaDao; // Importante: Agregar el DAO de reservas
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import models.usuarioModel;
import models.habitacionModel;
import java.io.IOException;
import java.util.List;

@WebServlet("/reservaServlet")
public class reservaServlet extends HttpServlet {

    // ── Enrutadores principales HTTP ───────────────────────────────────────────
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        procesarPeticion(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        procesarPeticion(request, response);
    }

    // ── Controlador Central ────────────────────────────────────────────────────
    private void procesarPeticion(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Verificar sesión
        HttpSession session = request.getSession();
        usuarioModel usuario = (usuarioModel) session.getAttribute("usuario");
        if (usuario == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        String accion = request.getParameter("accion");
        if (accion == null) {
            accion = "";
        }

        switch (accion) {
            case "buscar":
                buscarHabitaciones(request, response);
                break;

            case "checkout":
                irACheckout(request, response);
                break;

            case "cancelarReserva":
                cancelarReserva(request, response);
                break;

            default:
                // Sin acción: muestra la página vacía sin lista
                request.getRequestDispatcher("seleccionarHabitacion.jsp").forward(request, response);
                break;
        }
    }

    // ── Buscar habitaciones disponibles ──────────────────────────────────────
    private void buscarHabitaciones(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String fechaEntrada = request.getParameter("fechaEntrada");
        String fechaSalida = request.getParameter("fechaSalida");
        String cantidadHuespedes = request.getParameter("cantidadHuespedes");
        String tipoHabitacion = request.getParameter("tipoHabitacion");

        // Validar que las fechas no estén vacías
        if (fechaEntrada == null || fechaEntrada.isEmpty()
                || fechaSalida == null || fechaSalida.isEmpty()) {
            request.setAttribute("error", "Debes seleccionar fechas de entrada y salida.");
            request.getRequestDispatcher("seleccionarHabitacion.jsp").forward(request, response);
            return;
        }

        // Validar que la fecha de salida sea posterior a la de entrada
        if (fechaSalida.compareTo(fechaEntrada) <= 0) {
            request.setAttribute("error", "La fecha de salida debe ser posterior a la de entrada.");
            request.getRequestDispatcher("seleccionarHabitacion.jsp").forward(request, response);
            return;
        }

        try {
            int huespedes = Integer.parseInt(cantidadHuespedes);
            habitacionDao dao = new habitacionDao();
            List<habitacionModel> lista = dao.buscarDisponibles(fechaEntrada, fechaSalida, huespedes, tipoHabitacion);

            request.setAttribute("listaHabitaciones", lista);
            request.getRequestDispatcher("seleccionarHabitacion.jsp").forward(request, response);

        } catch (Exception e) {
            System.out.println("Error en reservaServlet - buscar: " + e.getMessage());
            e.printStackTrace();
            request.setAttribute("error", "Error al buscar habitaciones. Intente de nuevo.");
            request.getRequestDispatcher("seleccionarHabitacion.jsp").forward(request, response);
        }
    }

    // ── Ir al checkout con los datos de la habitación seleccionada ────────────
    private void irACheckout(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String idHab = request.getParameter("id");
        String fechaEntrada = request.getParameter("fechaEntrada");
        String fechaSalida = request.getParameter("fechaSalida");
        String huespedes = request.getParameter("huespedes");
        String tipoHab = request.getParameter("tipoHab");

        if (idHab == null || idHab.isEmpty()) {
            response.sendRedirect("reservaServlet");
            return;
        }

        try {
            habitacionDao dao = new habitacionDao();
            habitacionModel hab = dao.buscarPorId(Integer.parseInt(idHab));

            if (hab == null) {
                request.setAttribute("error", "La habitación seleccionada no existe.");
                request.getRequestDispatcher("seleccionarHabitacion.jsp").forward(request, response);
                return;
            }

            // Pasamos todo al JSP de checkout
            request.setAttribute("habitacion", hab);
            request.setAttribute("fechaEntrada", fechaEntrada);
            request.setAttribute("fechaSalida", fechaSalida);
            request.setAttribute("huespedes", huespedes);
            request.setAttribute("tipoHab", tipoHab);
            request.getRequestDispatcher("checkout.jsp").forward(request, response);

        } catch (Exception e) {
            System.out.println("Error en reservaServlet - checkout: " + e.getMessage());
            e.printStackTrace();
            request.setAttribute("error", "Error al cargar la habitación.");
            request.getRequestDispatcher("seleccionarHabitacion.jsp").forward(request, response);
        }
    }

    // ── Cancelar reserva seleccionada ─────────────────────────────────────────
    private void cancelarReserva(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String idReservaParam = request.getParameter("idReserva");

        if (idReservaParam != null && !idReservaParam.isEmpty()) {
            try {
                int idReserva = Integer.parseInt(idReservaParam);
                reservaDao dao = new reservaDao();
                
                // Llamamos al método que construimos en el DAO para cambiar el estado a "CANCELADA"
                boolean exito = dao.eliminarReserva(idReserva);

                if (exito) {
                    request.setAttribute("mensaje", "La reserva #RES-" + idReserva + " ha sido cancelada exitosamente.");
                } else {
                    request.setAttribute("error", "No se pudo cancelar la reserva en la base de datos.");
                }
            } catch (Exception e) {
                System.out.println("Error en reservaServlet - cancelarReserva: " + e.getMessage());
                request.setAttribute("error", "Error interno al procesar la cancelación.");
            }
        }
        
        // Redirigimos de vuelta a la vista donde se listan las reservas del usuario.
        // NOTA: Ajusta "misReservas.jsp" al nombre real de tu archivo JSP.
        request.getRequestDispatcher("cancelarReserva.jsp").forward(request, response);
    }
}