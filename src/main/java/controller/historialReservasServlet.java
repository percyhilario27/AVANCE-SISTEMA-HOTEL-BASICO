package controller;

import dao.reservaDao;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import models.clienteModel;
import models.usuarioModel;
import java.io.IOException;
import java.util.List;
import models.reservaModel;

@WebServlet("/historialReservasServlet")
public class historialReservasServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession session = request.getSession();
        usuarioModel usuario = (usuarioModel) session.getAttribute("usuario");

        if (usuario == null || !(usuario instanceof clienteModel)) {
            response.sendRedirect("login.jsp");
            return;
        }

        clienteModel cliente = (clienteModel) usuario;
        reservaDao dao = new reservaDao();
        
        List<reservaModel> reservas = dao.listarReservasCliente(cliente.getIdCliente());
        System.out.println("Reservas cargadas para historial: " + reservas.size());

        request.setAttribute("reservas", reservas);
        request.getRequestDispatcher("historialReserva.jsp").forward(request, response);
    }
}