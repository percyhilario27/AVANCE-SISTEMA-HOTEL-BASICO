package controller;

import dao.reservaDao;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import models.usuarioModel;
import models.clienteModel;
import java.io.IOException;

@WebServlet("/confirmarReservaServlet")
public class confirmarReservaServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        usuarioModel usuario = (usuarioModel) session.getAttribute("usuario");

        if (usuario == null || !(usuario instanceof clienteModel)) {
            response.sendRedirect("login.jsp");
            return;
        }

        clienteModel cliente = (clienteModel) usuario;
        System.out.println("Procesando confirmación para Cliente ID: " + cliente.getIdCliente());

        try {
            String nroHabStr    = request.getParameter("nroHab");
            String fechaEntrada = request.getParameter("fechaEntrada");
            String fechaSalida  = request.getParameter("fechaSalida");
            String huespedesStr = request.getParameter("huespedes");
            String totalStr     = request.getParameter("total");
            String metodoPago   = request.getParameter("metodoPago");

            // Validar parámetros
            if (nroHabStr == null || fechaEntrada == null || fechaSalida == null
                    || huespedesStr == null || totalStr == null || metodoPago == null
                    || metodoPago.isEmpty()) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Datos incompletos.");
                return;
            }

            // Validar que el método sea uno de los dos valores del ENUM
            if (!metodoPago.equals("Tarjeta_Directa") && !metodoPago.equals("PagoEfectivo")) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Método de pago inválido.");
                return;
            }

            int    nroHab    = Integer.parseInt(nroHabStr);
            int    huespedes = Integer.parseInt(huespedesStr);
            double total     = Double.parseDouble(totalStr);
            String idTransaccion = "TXN-" + System.currentTimeMillis();

            reservaDao dao = new reservaDao();
            String resultado = dao.insertarReservaConPago(
                cliente.getIdCliente(),
                nroHab,
                fechaEntrada,
                fechaSalida,
                huespedes,
                total,
                metodoPago,      // ← 'Tarjeta_Directa' o 'PagoEfectivo'
                idTransaccion
            );

            if (resultado.equals("OK")) {
                response.sendRedirect("misReservasServlet");
            } else {
                System.out.println("Error del DAO: " + resultado);
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, resultado);
            }

        } catch (NumberFormatException e) {
            System.out.println("Error de formato: " + e.getMessage());
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Datos inválidos.");
        } catch (Exception e) {
            System.out.println("Error en confirmarReservaServlet: " + e.getMessage());
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }
}