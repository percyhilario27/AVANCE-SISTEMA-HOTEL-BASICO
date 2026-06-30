<%@page import="models.usuarioModel"%>
<%@page import="models.usuarioModel"%>
<%@page import="models.clienteModel"%>
<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html lang="es">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Inicio - Sistema de Reservas</title>
        <style>
            * {
                margin: 0;
                padding: 0;
                box-sizing: border-box;
            }

            body {
                font-family: 'Segoe UI', Arial, sans-serif;
                background: #f5f5f5;
                min-height: 100vh;
                display: flex;
                flex-direction: column;
                align-items: center;
            }

            /* ── Banner superior ── */
            .banner {
                width: 100%;
                max-width: 430px;
                height: 180px;
                position: relative;
                overflow: hidden;
            }

            .banner img.banner-img {
                width: 100%;
                height: 100%;
                object-fit: cover;
                filter: brightness(0.65);
            }

            /* Fallback si no hay imagen */
            .banner-fallback {
                width: 100%;
                height: 100%;
                background: linear-gradient(135deg, #1a1a2e 0%, #16213e 50%, #0f3460 100%);
            }

            .banner-texto {
                position: absolute;
                bottom: 20px;
                left: 20px;
                color: #fff;
                font-size: 1.6rem;
                font-weight: 700;
                text-shadow: 0 2px 8px rgba(0,0,0,0.5);
            }

            /* ── Contenedor principal ── */
            .contenedor {
                width: 100%;
                max-width: 430px;
                background: #fff;
                min-height: calc(100vh - 180px);
                padding: 0 0 90px 0;
                position: relative;
            }

            /* ── Tarjeta de perfil ── */
            .perfil {
                display: flex;
                align-items: center;
                gap: 18px;
                padding: 24px 24px 16px 24px;
                border-bottom: 1px solid #f0f0f0;
            }

            .perfil-foto {
                width: 72px;
                height: 72px;
                border-radius: 50%;
                object-fit: cover;
                border: 3px solid #FF8C00;
                background: #e0e0e0;
                flex-shrink: 0;
            }

            .perfil-foto-placeholder {
                width: 72px;
                height: 72px;
                border-radius: 50%;
                background: linear-gradient(135deg, #FF8C00, #FFA500);
                display: flex;
                align-items: center;
                justify-content: center;
                font-size: 2rem;
                color: #fff;
                font-weight: 700;
                flex-shrink: 0;
                border: 3px solid #FF8C00;
            }

            .perfil-info h3 {
                font-size: 1.15rem;
                font-weight: 700;
                color: #1a1a1a;
                line-height: 1.3;
            }

            .perfil-info p {
                font-size: 0.82rem;
                color: #888;
                margin-top: 3px;
            }

            .perfil-info .rol-badge {
                display: inline-block;
                margin-top: 6px;
                background: #fff3e0;
                color: #FF8C00;
                font-size: 0.72rem;
                font-weight: 600;
                padding: 2px 10px;
                border-radius: 20px;
                border: 1px solid #FFD580;
            }

            /* ── Sección de acciones ── */
            .acciones {
                padding: 28px 24px 0 24px;
                display: flex;
                flex-direction: column;
                gap: 14px;
            }

            .btn-accion {
                display: block;
                width: 100%;
                padding: 16px;
                background: #FF8C00;
                color: #fff;
                text-align: center;
                font-size: 0.95rem;
                font-weight: 600;
                border: none;
                border-radius: 10px;
                cursor: pointer;
                text-decoration: none;
                transition: background 0.18s, transform 0.12s;
                letter-spacing: 0.3px;
            }

            .btn-accion:hover {
                background: #e07b00;
                transform: translateY(-1px);
            }

            .btn-accion:active {
                transform: translateY(0);
            }

            /* ── Botón cerrar sesión ── */
            .btn-salir {
                display: block;
                width: calc(100% - 48px);
                margin: 24px auto 0 auto;
                padding: 13px;
                background: transparent;
                color: #aaa;
                text-align: center;
                font-size: 0.88rem;
                font-weight: 500;
                border: 1.5px solid #e0e0e0;
                border-radius: 10px;
                cursor: pointer;
                transition: color 0.15s, border-color 0.15s;
            }

            .btn-salir:hover {
                color: #e53935;
                border-color: #e53935;
            }

            /* ── Barra de navegación inferior ── */
            .nav-bottom {
                position: fixed;
                bottom: 0;
                left: 50%;
                transform: translateX(-50%);
                width: 100%;
                max-width: 430px;
                background: #fff;
                border-top: 1px solid #eee;
                display: flex;
                justify-content: space-around;
                align-items: center;
                padding: 12px 0 16px 0;
                z-index: 100;
            }

            .nav-item {
                display: flex;
                flex-direction: column;
                align-items: center;
                gap: 3px;
                color: #bbb;
                cursor: pointer;
                transition: color 0.15s;
                text-decoration: none;
            }

            .nav-item.activo {
                color: #FF8C00;
            }

            .nav-item svg {
                width: 24px;
                height: 24px;
            }

            .nav-label {
                font-size: 0.65rem;
                font-weight: 500;
            }
        </style>
    </head>
    <body>
        <%
     usuarioModel usuario = (usuarioModel) session.getAttribute("usuario");
     if (usuario == null) {
         response.sendRedirect("login.jsp");
         return;
     }

     // Datos comunes del padre
     String nombreCompleto = usuario.getNombres() + " " + usuario.getApellidos();
     String primerNombre   = usuario.getNombres();
     String email          = usuario.getEmail();
     String rol            = usuario.getRol();
     String inicial        = primerNombre.substring(0, 1).toUpperCase();

     // Datos específicos del cliente
     String fotoPerfil = "";
     String celular    = "";

     if (usuario instanceof clienteModel) {
         clienteModel cliente = (clienteModel) usuario;
         fotoPerfil = cliente.getFotoPerfil() != null ? cliente.getFotoPerfil() : "";
         celular    = cliente.getCelular()    != null ? cliente.getCelular()    : "";
     }
        %>
        <!-- Banner superior -->
        <div class="banner">
            <%-- Si tienes una imagen de hotel, cámbiala aquí --%>
            <div class="banner-fallback"></div>
            <%-- O usa: <img class="banner-img" src="img/hotel-banner.jpg" alt="Hotel"> --%>
            <div class="banner-texto">¡Hola, <%= primerNombre %>!</div>
        </div>

        <div class="contenedor">

            <!-- Perfil -->
            <div class="perfil">
                <% if (!fotoPerfil.isEmpty()) { %>
                <img class="perfil-foto" src="<%= fotoPerfil %>" alt="Foto de perfil">
                <% } else { %>
                <div class="perfil-foto-placeholder"><%= inicial %></div>
                <% } %>

                <div class="perfil-info">
                    <h3><%= nombreCompleto %></h3>
                    <p><%= email %></p>
                    <span class="rol-badge"><%= rol %></span>
                </div>
            </div>

            <!-- Botones de acción -->
            <div class="acciones">
                <a href="seleccionarHabitacion.jsp" class="btn-accion">Realizar una Reserva</a>
                <a href="historialReservasServlet" class="btn-accion">Historial de Reservas</a>
                <a href="cancelarReserva.jsp" class="btn-accion">Cancelar Reservas</a>
            </div>

            <!-- Cerrar sesión -->
            <form action="LoginServlet" method="post">
                <input type="hidden" name="accion" value="salir">
                <button type="submit" class="btn-salir">Cerrar sesión</button>
            </form>

        </div>

        <!-- Navegación inferior -->
        <nav class="nav-bottom">
            <a href="inicio.jsp" class="nav-item activo">
                <svg fill="currentColor" viewBox="0 0 24 24">
                <path d="M10 20v-6h4v6h5v-8h3L12 3 2 12h3v8z"/>
                </svg>
                <span class="nav-label">Inicio</span>
            </a>
            <a href="misReservas.jsp" class="nav-item">
                <svg fill="none" stroke="currentColor" stroke-width="2" viewBox="0 0 24 24">
                <path stroke-linecap="round" stroke-linejoin="round"
                      d="M8 7V3m8 4V3m-9 8h10M5 21h14a2 2 0 002-2V7a2 2 0 00-2-2H5a2 2 0 00-2 2v12a2 2 0 002 2z"/>
                </svg>
                <span class="nav-label">Reservas</span>
            </a>
            <a href="perfil.jsp" class="nav-item">
                <svg fill="none" stroke="currentColor" stroke-width="2" viewBox="0 0 24 24">
                <path stroke-linecap="round" stroke-linejoin="round"
                      d="M16 7a4 4 0 11-8 0 4 4 0 018 0zM12 14a7 7 0 00-7 7h14a7 7 0 00-7-7z"/>
                </svg>
                <span class="nav-label">Perfil</span>
            </a>
        </nav>

    </body>
</html>