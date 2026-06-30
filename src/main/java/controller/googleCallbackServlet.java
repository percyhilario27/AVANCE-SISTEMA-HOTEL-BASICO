package controller;

import dao.usuarioDao; // Importación corregida
import models.usuarioModel; // Importación corregida
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.HttpResponse;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.http.UrlEncodedContent;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.json.JsonObjectParser;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/googleCallbackServlet") // CamelCase aplicado
public class googleCallbackServlet extends HttpServlet {

    private static final String CLIENT_ID = "GOOGLE_CLIENT_ID";
    private static final String CLIENT_SECRET = "GOOGLE_CLIENT_SECRET";
    // Asegúrate de que esta URL coincida exactamente con la Consola de Google Cloud
    private static final String REDIRECT_URI = "http://localhost:8080/A/GoogleCallback";

    @SuppressWarnings("unchecked")
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String code = request.getParameter("code");

        if (code == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        try {
            // 1. Intercambiar el "code" por un access_token
            NetHttpTransport transport = new NetHttpTransport();

            Map<String, Object> params = new HashMap<>();
            params.put("code", code);
            params.put("client_id", CLIENT_ID);
            params.put("client_secret", CLIENT_SECRET);
            params.put("redirect_uri", REDIRECT_URI);
            params.put("grant_type", "authorization_code");

            HttpRequestFactory requestFactory = transport.createRequestFactory();
            HttpRequest tokenRequest = requestFactory.buildPostRequest(
                    new GenericUrl("https://oauth2.googleapis.com/token"),
                    new UrlEncodedContent(params)
            );
            tokenRequest.setParser(new JsonObjectParser(GsonFactory.getDefaultInstance()));

            HttpResponse tokenResponse = tokenRequest.execute();
            Map<String, Object> tokenData = tokenResponse.parseAs(Map.class);
            String accessToken = (String) tokenData.get("access_token");

            // 2. Usar el access_token para obtener datos del usuario
            HttpRequest userInfoRequest = requestFactory.buildGetRequest(
                    new GenericUrl("https://www.googleapis.com/oauth2/v2/userinfo?access_token=" + accessToken)
            );
            userInfoRequest.setParser(new JsonObjectParser(GsonFactory.getDefaultInstance()));

            HttpResponse userInfoResponse = userInfoRequest.execute();
            Map<String, Object> userInfo = userInfoResponse.parseAs(Map.class);

            // Google envía la llave "email", así que la usamos conceptualmente como tal
            String email = (String) userInfo.get("email");
            String nombre = (String) userInfo.get("name");

            // 3. Buscar si el usuario ya existe, si no, registrarlo
            usuarioDao dao = new usuarioDao();
            usuarioModel usuario = dao.buscarPorCorreo(email);

            if (usuario == null) {
                // Instanciamos y usamos Setters para alinearnos a las columnas reales de MySQL
                usuario = new usuarioModel();
                usuario.setEmail(email);
                usuario.setPassword("OAUTH_NO_PASSWORD"); // Una cadena genérica y segura
                usuario.setRol("CLIENTE"); 
                usuario.setAuthProvider("GOOGLE"); // Ahora sí usamos la columna correcta
                
                // Registramos las credenciales base
                dao.registrarUsuario(usuario);
                
                // Lo recuperamos para obtener el id_usuario auto-generado
                usuario = dao.buscarPorCorreo(email);
                
                // NOTA: Aquí falta la lógica para guardar la variable 'nombre' en la tabla 'clientes'
            }

            // 4. Crear sesión
            HttpSession session = request.getSession();
            session.setAttribute("usuario", usuario);
            response.sendRedirect("inicio.jsp");

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Error al iniciar sesión con Google");
            request.getRequestDispatcher("login.jsp").forward(request, response);
        }
    }
}