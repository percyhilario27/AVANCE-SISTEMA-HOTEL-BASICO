package controller;

import config.basededatos;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.*;


@WebServlet("/updateReservaServlet")
public class updateReservaServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String idReserva = request.getParameter("id");
        String nuevoEstado = request.getParameter("estado");

        String sql = "UPDATE reservas SET estado = ? WHERE id_reserva = ?";

        Connection c = new basededatos().getCon();

        try {
            PreparedStatement pst = c.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();

            pst.setString(1, nuevoEstado);
            pst.setString(2, idReserva);
            pst.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        response.sendRedirect("adminDashboard.jsp");
    }
}
