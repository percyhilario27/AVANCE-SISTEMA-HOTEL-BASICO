<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.List"%>
<%@page import="models.habitacionModel"%>
<!DOCTYPE html>
<html lang="es">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Habitaciones Disponibles</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
        <style>
            body { background-color: #f4f5f9; font-family: 'Segoe UI', sans-serif; padding-bottom: 70px; }
            .header-bg { background-color: #a4d4cc; height: 250px; }
            .search-card {
                background: white; border-radius: 20px; border: 2px solid #3b00e3;
                padding: 20px; margin: -80px 20px 20px 20px;
                position: relative; box-shadow: 0 10px 20px rgba(0,0,0,0.1);
            }
            .search-card label { font-size: 0.85rem; font-weight: 600; color: #555; margin-bottom: 5px; }
            .btn-confirmar { background-color: #333; color: white; border-radius: 8px; width: 100%; font-weight: bold; padding: 10px; }
            .titulo-seccion { color: #007bff; font-weight: 700; text-align: center; margin: 20px 0; }
            .room-card { background: white; border-radius: 12px; overflow: hidden; box-shadow: 0 4px 8px rgba(0,0,0,0.05); margin-bottom: 15px; }
            .room-card img { width: 100%; height: 120px; object-fit: cover; }
            .room-info { padding: 12px; }
            .room-title { font-weight: bold; font-size: 0.95rem; margin-bottom: 5px; }
            .room-features { font-size: 0.75rem; color: #777; margin-bottom: 10px; }
            .room-price { color: #007bff; font-weight: bold; font-size: 1.3rem; margin: 0; }
            .room-price small { font-size: 0.7rem; font-weight: normal; color: #777; }
            .btn-detalles { color: #ff9800; border: 1px solid #ff9800; border-radius: 6px; background: transparent; font-size: 0.75rem; padding: 4px 10px; font-weight: 600; text-decoration: none; }
            .alerta-error { margin: 10px 20px; }
            .bottom-nav { position: fixed; bottom: 0; width: 100%; background: white; display: flex; justify-content: space-around; padding: 15px 0; box-shadow: 0 -2px 10px rgba(0,0,0,0.05); z-index: 1000; }
            .bottom-nav i { font-size: 1.5rem; color: #333; cursor: pointer; }
        </style>
    </head>
    <body>
        <div class="container" style="max-width: 480px; padding: 0; background: #f4f5f9; min-height: 100vh;">

            <div class="header-bg"></div>

            <!-- Formulario de búsqueda -->
            <div class="search-card">
                <%-- Preservar valores seleccionados tras la búsqueda --%>
                <%
                    String fechaEntrada      = request.getParameter("fechaEntrada")      != null ? request.getParameter("fechaEntrada")      : "";
                    String fechaSalida       = request.getParameter("fechaSalida")       != null ? request.getParameter("fechaSalida")       : "";
                    String cantidadHuespedes = request.getParameter("cantidadHuespedes") != null ? request.getParameter("cantidadHuespedes") : "2";
                    String tipoHabitacion    = request.getParameter("tipoHabitacion")    != null ? request.getParameter("tipoHabitacion")    : "Todas";
                %>
                <form action="reservaServlet" method="GET">
                    <input type="hidden" name="accion" value="buscar">
                    <div class="row g-3">
                        <div class="col-6">
                            <label>Entrada</label>
                            <input type="date" class="form-control form-control-sm" name="fechaEntrada" value="<%= fechaEntrada %>" required>
                        </div>
                        <div class="col-6">
                            <label>Huéspedes</label>
                            <select class="form-select form-select-sm" name="cantidadHuespedes">
                                <option value="1" <%= cantidadHuespedes.equals("1") ? "selected" : "" %>>1 Persona</option>
                                <option value="2" <%= cantidadHuespedes.equals("2") ? "selected" : "" %>>2 Personas</option>
                                <option value="3" <%= cantidadHuespedes.equals("3") ? "selected" : "" %>>3 Personas</option>
                                <option value="4" <%= cantidadHuespedes.equals("4") ? "selected" : "" %>>4 Personas</option>
                            </select>
                        </div>
                        <div class="col-6">
                            <label>Salida</label>
                            <input type="date" class="form-control form-control-sm" name="fechaSalida" value="<%= fechaSalida %>" required>
                        </div>
                        <div class="col-6">
                            <label>Tipo</label>
                            <select class="form-select form-select-sm" name="tipoHabitacion">
                                <option value="Todas"  <%= tipoHabitacion.equals("Todas")  ? "selected" : "" %>>Todas</option>
                                <option value="Simple" <%= tipoHabitacion.equals("Simple") ? "selected" : "" %>>Simple</option>
                                <option value="Doble"  <%= tipoHabitacion.equals("Doble")  ? "selected" : "" %>>Doble</option>
                                <option value="Duplex" <%= tipoHabitacion.equals("Duplex") ? "selected" : "" %>>Duplex</option>
                                <option value="Suite"  <%= tipoHabitacion.equals("Suite")  ? "selected" : "" %>>Suite</option>
                            </select>
                        </div>
                        <div class="col-12 mt-4">
                            <button type="submit" class="btn btn-confirmar">Confirmar</button>
                        </div>
                    </div>
                </form>
            </div>

            <!-- Mensaje de error si viene del servlet -->
            <% if (request.getAttribute("error") != null) { %>
                <div class="alert alert-warning alerta-error"><%= request.getAttribute("error") %></div>
            <% } %>

            <div class="text-center">
                <i class="fa-regular fa-heart" style="color: #007bff; font-size: 1.2rem;"></i>
                <h4 class="titulo-seccion">Habitaciones Disponibles</h4>
            </div>

            <!-- Lista de habitaciones -->
            <div class="container px-3">
                <div class="row">
                    <%
                        List<habitacionModel> lista = (List<habitacionModel>) request.getAttribute("listaHabitaciones");
                        if (lista != null && !lista.isEmpty()) {
                            for (habitacionModel hab : lista) {
                    %>
                        <div class="col-6 mb-3 px-2">
                            <div class="room-card">
                                <img src="<%= hab.getUrlImagen() %>" alt="<%= hab.getTipo() %>">
                                <div class="room-info">
                                    <div class="room-title"><%= hab.getTipo() %></div>
                                    <div class="room-features">
                                        <i class="fa-solid fa-user-group me-1"></i><%= hab.getCapacidadMax() %> pers.
                                    </div>
                                    <div class="d-flex justify-content-between align-items-end mt-2">
                                        <div class="room-price">
                                            S/<%= String.format("%.2f", hab.getPrecioNoche()) %><br>
                                            <small>por noche</small>
                                        </div>
                                        <%-- Pasamos también las fechas para usarlas en el checkout --%>
                                        <a href="reservaServlet?accion=checkout&id=<%= hab.getNroHab() %>&fechaEntrada=<%= fechaEntrada %>&fechaSalida=<%= fechaSalida %>&huespedes=<%= cantidadHuespedes %>"
                                           class="btn-detalles">ver detalles</a>
                                    </div>
                                </div>
                            </div>
                        </div>
                    <%
                            }
                        } else if (lista != null) {
                    %>
                        <div class="col-12 text-center text-muted mt-4">
                            <p>No hay habitaciones disponibles para estas fechas.</p>
                        </div>
                    <% } %>
                </div>
            </div>
            <br><br>
        </div>