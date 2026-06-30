<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Iniciar Sesión - Sistema de Reservas Hotel</title>
    <link href="css/login.css" rel="stylesheet" type="text/css">
</head>
<body>
    <div class="contenedor">
        <h2>Bienvenido de nuevo</h2>

        <% if (request.getAttribute("error") != null) { %>
            <p class="error"><%= request.getAttribute("error") %></p>
        <% } %>

        <form action="loginServlet" method="post">
            <label>Correo electrónico</label>
            <input type="email" name="correo" placeholder="correo@dominio.com" required>

            <label>Contraseña</label>
            <input type="password" name="password" placeholder="Tu contraseña..." required>

            <button type="submit" class="btn-continuar">Continuar</button>
        </form>

        <div class="separador"><span>o</span></div>

        <a href="${pageContext.request.contextPath}/googleCallbackServlet" class="btn-social btn-google" style="display:block; text-align:center; text-decoration:none; color:#000;">
            Continuar con Google
        </a>
        
        <p class="texto-pequeno">
            ¿No tienes cuenta? <a href="registro.jsp">Regístrate</a>
        </p>
        <p class="texto-pequeno">
            Al hacer clic en continuar, aceptas nuestros 
            <a href="#">Términos de servicio</a> y <a href="#">Política de privacidad</a>.
        </p>
    </div>
</body>
</html>