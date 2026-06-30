package controller;

import dao.clienteDao;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import models.clienteModel;
import java.io.IOException;

@WebServlet("/registroServlet")
public class registroServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            // 1. Capturar parámetros del formulario
            String nombre   = request.getParameter("nombre");
            String apellido = request.getParameter("apellido");
            String email    = request.getParameter("correo");
            String tipoDoc  = request.getParameter("tipoDoc");
            String nroDoc   = request.getParameter("nroDoc");
            String celular  = request.getParameter("celular");
            String password  = request.getParameter("password");
            String confirmar = request.getParameter("confirmar");

            clienteDao dao = new clienteDao();

            // 2. Validar que las contraseñas coincidan
            if (!password.equals(confirmar)) {
                request.setAttribute("error", "Las contraseñas no coinciden.");
                request.getRequestDispatcher("registro.jsp").forward(request, response);
                return;
            }

            // 3. Validar que el correo no esté registrado
            if (dao.existeCorreo(email)) {
                request.setAttribute("error", "El correo ya está registrado en el sistema.");
                request.getRequestDispatcher("registro.jsp").forward(request, response);
                return;
            }

            // 4. Armar el modelo correctamente
            clienteModel nuevoCliente = new clienteModel();
            nuevoCliente.setEmail(email);
            nuevoCliente.setPassword(password);  // TODO: aplicar hash aquí
            nuevoCliente.setRol("CLIENTE");
            nuevoCliente.setNombres(nombre);      // ✅ setter correcto
            nuevoCliente.setApellidos(apellido);  // ✅ setter correcto
            nuevoCliente.setTipoDoc(tipoDoc);     // ✅ antes decía setNombres()
            nuevoCliente.setNroDoc(nroDoc);       // ✅ antes decía setApellidos()
            nuevoCliente.setCelular(celular);

            // 5. Registrar en la base de datos
            boolean exito = dao.registrarCliente(nuevoCliente);

            if (exito) {
                response.sendRedirect("login.jsp");
            } else {
                request.setAttribute("error", "Ocurrió un error al registrar. Intente de nuevo.");
                request.getRequestDispatcher("registro.jsp").forward(request, response);
            }

        } catch (Exception e) {
            e.printStackTrace(); // ← agrega esto
            System.out.println("Error crítico en registroServlet: " + e.getMessage());
            request.setAttribute("error", "Error interno del servidor. Intente más tarde.");
            request.getRequestDispatcher("registro.jsp").forward(request, response);
        }
    }
}