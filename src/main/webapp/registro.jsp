<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
    <head>
        <title>Registrarse - Sistema de Reservas</title>
        <link rel="stylesheet" href="css/login.css">
    </head>
    <body>
        <div class="contenedor">
            <h2>Registrarse</h2>

            <% if (request.getAttribute("error") != null) { %>
            <p class="error"><%= request.getAttribute("error") %></p>
            <% } %>

            <form action="${pageContext.request.contextPath}/registroServlet" method="post">
                <label>Nombre</label>
                <input type="text" name="nombre" placeholder="nombre..." required>

                <label>Apellido</label>
                <input type="text" name="apellido" placeholder="apellido..." required>

                <label>Tipo Documento</label>
                <label for="opciones">Elige una opción:</label>
                <select id="opciones" name="tipoDoc" required>
                    <option value="DNI">DNI</option>
                    <option value="CE">RUC</option>
                    <option value="Pasaporte">Pasaporte</option>
                </select>

                <label>Nro Documento</label>
                <input type="text" name="nroDoc" placeholder="Nro Documento..." required>

                <label>Nro Telefono</label>
                <input type="text" name="telefono" placeholder="987654321..." required>

                <label>Correo electrónico</label>
                <input type="email" name="correo" placeholder="correoelectronico@dominio.com" required>

                <label>Contraseña</label>
                <input type="password" name="password" placeholder="contraseña..." required>

                <label>Confirmar contraseña</label>
                <input type="password" name="confirmar" placeholder="confirmar contraseña" required>

                <button type="submit" class="btn-continuar">Continuar</button>
            </form>

            <div class="separador"><span>o</span></div>

            <a href="${pageContext.request.contextPath}/GoogleLoginServlet" class="btn-social btn-google" style="display:block; text-align:center; text-decoration:none; color:#000;">Registrarse con Google</a>

            <p class="texto-pequeno">
                ¿Ya tienes cuenta? <a href="login.jsp">Inicia sesión</a>
            </p>
            <p class="texto-pequeno">
                Al hacer clic en continuar, aceptas nuestros 
                <a href="#">Términos de servicio</a> y <a href="#">Política de privacidad</a>.
            </p>
        </div>
    </body>
</html>