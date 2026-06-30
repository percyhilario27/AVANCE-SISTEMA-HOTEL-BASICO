<%@page import="models.reservaModel"%>
<%@page import="java.util.List"%>
<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html lang="es">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Mis Reservas</title>
        <link rel="stylesheet" href="css/estilo.css">
        <style>
            .page {
                padding: 1rem 0;
            }
            .header {
                display: flex;
                align-items: center;
                gap: 10px;
                margin-bottom: 1.25rem;
                padding: 0 1rem;
            }
            .back-btn {
                width: 32px;
                height: 32px;
                border-radius: 50%;
                border: 1px solid #ddd;
                background: #fff;
                display: flex;
                align-items: center;
                justify-content: center;
                cursor: pointer;
            }
            .header h2 {
                flex: 1;
                text-align: center;
                font-size: 15px;
                font-weight: 600;
                letter-spacing: .05em;
                margin: 0;
            }
            .reservas-lista {
                display: flex;
                flex-direction: column;
                gap: 14px;
                padding: 0 1rem;
            }
            .card {
                background: #fff;
                border: 1px solid #eee;
                border-radius: 12px;
                overflow: hidden;
            }
            .card-img {
                width: 100%;
                height: 110px;
                object-fit: cover;
                background: #e0e0e0;
            }
            .card-img-placeholder {
                width: 100%;
                height: 110px;
                background: linear-gradient(135deg, #1a1a2e, #0f3460);
            }
            .card-body {
                padding: 14px;
            }
            .card-title {
                font-size: 14px;
                font-weight: 600;
                margin: 0 0 10px;
            }
            .card-meta {
                display: flex;
                flex-direction: column;
                gap: 5px;
                margin-bottom: 12px;
            }
            .card-meta-row {
                display: flex;
                align-items: center;
                gap: 7px;
                font-size: 13px;
                color: #666;
            }
            .codigo-box {
                background: #f7f7f7;
                border-radius: 8px;
                padding: 9px 12px;
                display: flex;
                align-items: center;
                justify-content: space-between;
                margin-bottom: 10px;
            }
            .codigo-label {
                font-size: 11px;
                font-weight: 600;
                letter-spacing: .08em;
                color: #999;
            }
            .codigo-val {
                font-size: 13px;
                font-weight: 600;
                color: #333;
            }
            .card-footer {
                display: flex;
                align-items: center;
                justify-content: space-between;
            }
            .badge {
                display: inline-block;
                font-size: 12px;
                font-weight: 600;
                padding: 4px 10px;
                border-radius: 20px;
            }
            .badge-CONFIRMADA {
                background: #e6f4ea;
                color: #2e7d32;
            }
            .badge-PENDIENTE  {
                background: #fff8e1;
                color: #f57f17;
            }
            .badge-CANCELADA  {
                background: #fdecea;
                color: #c62828;
            }
            .ver-detalles {
                font-size: 13px;
                color: #1a73e8;
                background: none;
                border: none;
                cursor: pointer;
                padding: 0;
            }
            .empty {
                text-align: center;
                padding: 3rem 1rem;
                color: #999;
            }
        </style>
    </head>
    <body>
        <%
            List<reservaModel> reservas = (List<reservaModel>) request.getAttribute("reservas");
        %>
        <div class="page">

            <div class="header">
                <button class="back-btn" onclick="history.back()">&#8592;</button>
                <h2>MIS RESERVAS</h2>
                <div style="width:32px"></div>
            </div>

            <div class="reservas-lista">
                <% if (reservas == null || reservas.isEmpty()) { %>
                <div class="empty">
                    <p>No tienes reservas registradas.</p>
                </div>
                <% } else {
                    for (reservaModel r : reservas) {
                        String urlImagen = r.getHabitacion().getUrlImagen();
                        String badgeClass = "badge-" + r.getEstado().toUpperCase();
                %>
                <div class="card">
                    <% if (urlImagen != null && !urlImagen.isEmpty()) { %>
                    <img class="card-img" src="<%= urlImagen %>" alt="Habitación">
                    <% } else { %>
                    <div class="card-img-placeholder"></div>
                    <% } %>

                    <div class="card-body">
                        <p class="card-title">
                            Habitación <%= r.getHabitacion().getTipo() %> &middot; <%= r.getNroOcupantes() %> huéspedes
                        </p>

                        <div class="card-meta">
                            <div class="card-meta-row">
                                &#128197; <%= r.getFechaInicio() %> – <%= r.getFechaFin() %>
                            </div>
                            <div class="card-meta-row">
                                &#36; S/. <%= r.getHabitacion().getPrecioNoche() %> / noche
                            </div>
                        </div>

                        <div class="codigo-box">
                            <span class="codigo-label">CÓDIGO DE RESERVA</span>
                            <span class="codigo-val">#RES-<%= r.getIdReserva() %></span>
                        </div>

                        <div class="card-footer">
                            <span class="badge <%= badgeClass %>"><%= r.getEstado() %></span>
                            <button class="ver-detalles"
                                    onclick="location.href = 'detalleReservaServlet?id=<%= r.getIdReserva() %>'">
                                Ver detalles &#8594;
                            </button>
                        </div>
                    </div>
                </div>
                <%  }
       } %>
            </div>
        </div>
    </body>
</html>