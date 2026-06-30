<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="models.habitacionModel"%>
<%@page import="java.time.LocalDate"%>
<%@page import="java.time.temporal.ChronoUnit"%>
<!DOCTYPE html>
<html lang="es">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Resumen de reserva</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
        <style>
            body {
                background: #f4f5f9;
                font-family: 'Segoe UI', sans-serif;
                padding-bottom: 2rem;
            }
            .img-banner {
                width: 100%;
                height: 180px;
                object-fit: cover;
                background: linear-gradient(135deg,#1a1a2e,#0f3460);
            }
            .img-banner-placeholder {
                width: 100%;
                height: 180px;
                background: linear-gradient(135deg,#1a1a2e,#0f3460);
                display: flex;
                align-items: center;
                justify-content: center;
            }
            .back-row {
                display: flex;
                align-items: center;
                gap: 10px;
                padding: 16px 16px 0;
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
                flex-shrink: 0;
            }
            .seccion {
                background: #fff;
                border-radius: 12px;
                border: 1px solid #eee;
                margin: 14px 16px 0;
                padding: 16px;
            }
            .sec-titulo {
                font-size: 11px;
                font-weight: 600;
                letter-spacing: .07em;
                color: #999;
                margin-bottom: 12px;
                text-transform: uppercase;
            }
            .hab-nombre {
                font-size: 16px;
                font-weight: 600;
                color: #1a1a1a;
                margin-bottom: 4px;
            }
            .hab-tipo {
                font-size: 13px;
                color: #666;
            }
            .fila {
                display: flex;
                justify-content: space-between;
                align-items: center;
                padding: 6px 0;
                font-size: 14px;
            }
            .fila-label {
                color: #666;
            }
            .fila-valor {
                color: #1a1a1a;
                font-weight: 500;
            }
            .divider {
                border: none;
                border-top: 1px solid #f0f0f0;
                margin: 10px 0;
            }
            .fila-total .fila-label {
                font-size: 15px;
                font-weight: 600;
                color: #1a1a1a;
            }
            .fila-total .fila-valor {
                font-size: 18px;
                font-weight: 700;
                color: #007bff;
            }
            .nota {
                font-size: 12px;
                color: #aaa;
                margin-top: 8px;
                line-height: 1.5;
            }
            .btn-confirmar {
                display: block;
                width: calc(100% - 32px);
                margin: 18px 16px 0;
                padding: 14px;
                background: #333;
                color: #fff;
                border: none;
                border-radius: 10px;
                font-size: 15px;
                font-weight: 600;
                cursor: pointer;
            }
            .btn-volver {
                display: block;
                width: calc(100% - 32px);
                margin: 10px 16px 0;
                padding: 12px;
                background: transparent;
                color: #888;
                border: 1px solid #ddd;
                border-radius: 10px;
                font-size: 14px;
                cursor: pointer;
            }

            /* Estilos para el Modal de Pago */
            .modal-overlay {
                position: fixed;
                top: 0;
                left: 0;
                width: 100%;
                height: 100%;
                background: rgba(0, 0, 0, 0.75);
                z-index: 2000;
                display: flex;
                align-items: center;
                justify-content: center;
            }
            .modal-content {
                background: #0b132b;
                width: 90%;
                max-width: 380px;
                border-radius: 12px;
                padding: 24px;
                box-shadow: 0 10px 30px rgba(0,0,0,0.5);
                color: white;
                transition: background 0.3s ease;
            }
            .modal-titulo {
                font-size: 1.1rem;
                font-weight: bold;
                text-align: center;
                margin-bottom: 24px;
            }
            .btn-metodo {
                display: block;
                width: 100%;
                background: #1c2541;
                color: white;
                border: 1px solid #3a506b;
                padding: 16px;
                border-radius: 8px;
                margin-bottom: 12px;
                font-size: 0.95rem;
                cursor: pointer;
                transition: 0.2s;
            }
            .btn-metodo:hover {
                background: #3a506b;
            }
            .btn-cerrar {
                display: block;
                width: 100%;
                background: transparent;
                color: #888;
                border: none;
                padding: 10px;
                cursor: pointer;
                text-align: center;
                margin-top: 10px;
            }
            .btn-confirmar-modal {
                display: block;
                width: 100%;
                background: #00e5ff;
                color: #000;
                border: none;
                padding: 14px;
                border-radius: 8px;
                font-weight: bold;
                margin-top: 15px;
                cursor: pointer;
                transition: 0.2s;
            }
            .pago-efectivo-box {
                background: white;
                color: #333;
                padding: 15px;
                border-radius: 8px;
                text-align: center;
            }
            .qr-placeholder {
                width: 140px;
                height: 140px;
                background: #f4f5f9;
                margin: 15px auto;
                display: flex;
                align-items: center;
                justify-content: center;
                font-size: 4rem;
                color: #ccc;
            }
            .form-tarjeta input {
                width: 100%;
                padding: 12px;
                border: none;
                border-radius: 6px;
                margin-bottom: 10px;
                background: #fff;
            }
            .lista-tarjetas {
                background: rgba(255,255,255,0.05);
                border-radius: 8px;
                padding: 15px;
            }
            .tarjeta-item {
                display: flex;
                align-items: center;
                gap: 10px;
                margin-bottom: 10px;
                cursor: pointer;
                font-size: 0.95rem;
            }
            .spinner {
                width: 50px;
                height: 50px;
                border: 5px solid rgba(255,255,255,0.1);
                border-top-color: #00e5ff;
                border-radius: 50%;
                animation: spin 1s linear infinite;
                margin: 0 auto;
            }
            @keyframes spin {
                100% {
                    transform: rotate(360deg);
                }
            }
        </style>
    </head>
    <body>
        <%
            habitacionModel hab  = (habitacionModel) request.getAttribute("habitacion");
            String fechaEntrada  = (String) request.getAttribute("fechaEntrada");
            String fechaSalida   = (String) request.getAttribute("fechaSalida");
            String huespedesStr  = (String) request.getAttribute("huespedes");

            if (hab == null || fechaEntrada == null || fechaSalida == null) {
                response.sendRedirect("reservaServlet");
                return;
            }

            // Calcular noches
            LocalDate entrada = LocalDate.parse(fechaEntrada);
            LocalDate salida  = LocalDate.parse(fechaSalida);
            long noches       = ChronoUnit.DAYS.between(entrada, salida);
            double subtotal   = noches * hab.getPrecioNoche();
            int huespedes     = huespedesStr != null ? Integer.parseInt(huespedesStr) : 1;

            // Formatear fechas para mostrar
            String[] meses = {"Ene","Feb","Mar","Abr","May","Jun","Jul","Ago","Sep","Oct","Nov","Dic"};
            String entradaFormateada = entrada.getDayOfMonth() + " " + meses[entrada.getMonthValue()-1] + " " + entrada.getYear();
            String salidaFormateada  = salida.getDayOfMonth()  + " " + meses[salida.getMonthValue()-1]  + " " + salida.getYear();
        %>

        <div style="max-width:480px; margin:0 auto; background:#f4f5f9; min-height:100vh;">

            <%-- Banner imagen --%>
            <% if (hab.getUrlImagen() != null && !hab.getUrlImagen().isEmpty()) { %>
            <img class="img-banner" src="<%= hab.getUrlImagen() %>" alt="<%= hab.getTipo() %>">
            <% } else { %>
            <div class="img-banner-placeholder">
                <i class="fa-solid fa-building" style="font-size:3rem; color:rgba(255,255,255,0.2)"></i>
            </div>
            <% } %>

            <%-- Header --%>
            <div class="back-row">
                <button class="back-btn" onclick="history.back()">&#8592;</button>
                <h2 style="font-size:15px; font-weight:600; margin:0;">Resumen de reserva</h2>
            </div>

            <%-- Mensaje de error si viene del servlet --%>
            <% if (request.getAttribute("error") != null) { %>
            <div class="alert alert-warning" style="margin:10px 16px;">
                <%= request.getAttribute("error") %>
            </div>
            <% } %>

            <%-- Habitación --%>
            <div class="seccion">
                <p class="sec-titulo">Habitación</p>
                <p class="hab-nombre">Habitación <%= hab.getTipo() %></p>
                <p class="hab-tipo">
                    <i class="fa-solid fa-door-open me-1"></i><%= hab.getTipo() %>
                    &nbsp;&middot;&nbsp;
                    <i class="fa-solid fa-user-group me-1"></i>hasta <%= hab.getCapacidadMax() %> personas
                </p>
            </div>

            <%-- Fechas y huéspedes --%>
            <div class="seccion">
                <p class="sec-titulo">Fechas y huéspedes</p>
                <div class="fila">
                    <span class="fila-label"><i class="fa-regular fa-calendar me-2"></i>Entrada</span>
                    <span class="fila-valor"><%= entradaFormateada %></span>
                </div>
                <div class="fila">
                    <span class="fila-label"><i class="fa-regular fa-calendar-xmark me-2"></i>Salida</span>
                    <span class="fila-valor"><%= salidaFormateada %></span>
                </div>
                <div class="fila">
                    <span class="fila-label"><i class="fa-solid fa-user-group me-2"></i>Huéspedes</span>
                    <span class="fila-valor"><%= huespedes %> persona<%= huespedes > 1 ? "s" : "" %></span>
                </div>
                <div class="fila">
                    <span class="fila-label"><i class="fa-regular fa-moon me-2"></i>Noches</span>
                    <span class="fila-valor"><%= noches %> noche<%= noches > 1 ? "s" : "" %></span>
                </div>
            </div>

            <%-- Detalle de pago --%>
            <div class="seccion">
                <p class="sec-titulo">Detalle de pago</p>
                <div class="fila">
                    <span class="fila-label"><i class="fa-solid fa-tag me-2"></i>Precio por noche</span>
                    <span class="fila-valor">S/. <%= String.format("%.2f", hab.getPrecioNoche()) %></span>
                </div>
                <div class="fila">
                    <span class="fila-label"><i class="fa-solid fa-calculator me-2"></i>Subtotal (<%= noches %> noches)</span>
                    <span class="fila-valor">S/. <%= String.format("%.2f", subtotal) %></span>
                </div>
                <hr class="divider">
                <div class="fila fila-total">
                    <span class="fila-label">Total a pagar</span>
                    <span class="fila-valor">S/. <%= String.format("%.2f", subtotal) %></span>
                </div>
                <p class="nota">El pago se realizará al momento del check-in en recepción.</p>
            </div>

            <%-- Formulario de confirmación --%>
            <%-- Formulario de confirmación - agrega metodoPago como hidden --%>
            <form id="formReserva" action="confirmarReservaServlet" method="POST">
                <input type="hidden" name="nroHab"       value="<%= hab.getNroHab() %>">
                <input type="hidden" name="fechaEntrada" value="<%= fechaEntrada %>">
                <input type="hidden" name="fechaSalida"  value="<%= fechaSalida %>">
                <input type="hidden" name="huespedes"    value="<%= huespedes %>">
                <input type="hidden" name="total"        value="<%= subtotal %>">
                <input type="hidden" name="metodoPago"   id="metodoPago" value=""> <!-- ← se llena por JS -->
                <button type="submit" class="btn-confirmar">Confirmar reserva</button>
            </form>
            <button class="btn-volver" onclick="history.back()">Volver a habitaciones</button>

        </div>

        <div id="modalPago" class="modal-overlay" style="display: none;">
            <div class="modal-content" id="cajaModal">

                <div id="pantallaMetodo">
                    <h3 class="modal-titulo">Método de pago</h3>
                    <button class="btn-metodo" onclick="elegirMetodo('Tarjeta_Directa', 'pantallaTarjeta')">
                        💳 Pago con Tarjeta
                    </button>
                    <button class="btn-metodo" onclick="elegirMetodo('PagoEfectivo', 'pantallaEfectivo')">
                        📱 PagoEfectivo
                    </button>
                    <button class="btn-cerrar" onclick="cerrarModal()">Cancelar</button>
                </div>

                <div id="pantallaEfectivo" style="display: none;">
                    <h3 class="modal-titulo">Realiza el pago</h3>
                    <div class="pago-efectivo-box">
                        <p style="font-size: 0.85rem; color:#666; margin-bottom: 5px;">Código de pago (CIP):</p>
                        <h2 style="margin: 0; font-weight: bold; letter-spacing: 2px;">9123456</h2>
                        <div class="qr-placeholder"><i class="fa-solid fa-qrcode"></i></div>
                        <p style="font-size: 1.1rem; font-weight:bold;">S/. <%= String.format("%.2f", subtotal) %></p>
                    </div>
                    <button class="btn-confirmar-modal" onclick="simularPago()">Simular pago realizado</button>
                    <button class="btn-cerrar" onclick="mostrarPantalla('pantallaMetodo')">Volver</button>
                </div>

                <div id="pantallaTarjeta" style="display: none;">
                    <h3 class="modal-titulo">Pago con Tarjeta</h3>

                    <p style="text-align:center; font-size: 0.85rem; color:#aaa; margin-bottom:15px;">No tienes tarjetas guardadas.</p>
                    <div class="form-tarjeta">
                        <input type="text" id="numTarjeta" placeholder="Número de tarjeta" maxlength="16">
                        <div style="display: flex; gap: 10px;">
                            <input type="text" placeholder="MM/AA" maxlength="5">
                            <input type="password" placeholder="CVV" maxlength="3">
                        </div>
                    </div>

                    <button class="btn-confirmar-modal" onclick="simularPago()">Pagar S/. <%= String.format("%.2f", subtotal) %></button>
                    <button class="btn-cerrar" onclick="mostrarPantalla('pantallaMetodo')">Volver</button>
                </div>

                <div id="pantallaCarga" style="display: none; text-align: center; padding: 20px 0;">
                    <div class="spinner"></div>
                    <p style="margin-top: 20px; font-size: 0.9rem; color: #ccc;">Procesando tu pago en entorno local...</p>
                </div>

                <div id="pantallaExito" style="display: none; text-align: center;">
                    <i class="fa-solid fa-circle-check" style="color: #2e7d32; font-size: 4.5rem; margin-bottom: 20px;"></i>
                    <h3 style="color: #1a1a2e; font-weight: bold; margin-bottom: 10px;">¡Reserva confirmada!</h3>
                    <p style="color: #666; font-size: 0.9rem; margin-bottom: 20px;">
                        Tu reserva se ha completado satisfactoriamente.
                    </p>
                    <button class="btn-confirmar-modal" style="background: #ff9800; color: white;" onclick="irAlHistorial()">
                        Ver mis reservas
                    </button>
                </div>

                <div id="pantallaError" style="display: none; text-align: center;">
                    <i class="fa-solid fa-circle-xmark" style="color: #c62828; font-size: 4.5rem; margin-bottom: 20px;"></i>
                    <h3 style="color: white; font-weight: bold; margin-bottom: 10px;">Error al procesar</h3>
                    <p style="color: #aaa; font-size: 0.9rem; margin-bottom: 20px;">
                        No se pudo completar tu reserva. Intenta de nuevo.
                    </p>
                    <button class="btn-confirmar-modal" onclick="mostrarPantalla('pantallaMetodo')">Intentar de nuevo</button>
                    <button class="btn-cerrar" onclick="cerrarModal()">Cancelar</button>
                </div>

            </div>
        </div>

        <script>
            // Interceptar el formulario — abre el modal en lugar de enviar
            document.getElementById('formReserva').addEventListener('submit', function (e) {
                e.preventDefault();
                document.getElementById('modalPago').style.display = 'flex';
                mostrarPantalla('pantallaMetodo');
            });

            // Guarda el método elegido y muestra la pantalla correspondiente
            function elegirMetodo(metodo, pantalla) {
                document.getElementById('metodoPago').value = metodo;
                mostrarPantalla(pantalla);
            }

            function cerrarModal() {
                document.getElementById('modalPago').style.display = 'none';
                document.getElementById('cajaModal').style.background = '#0b132b';
            }

            function mostrarPantalla(idPantalla) {
                const pantallas = ['pantallaMetodo', 'pantallaEfectivo', 'pantallaTarjeta', 'pantallaCarga', 'pantallaExito', 'pantallaError'];
                pantallas.forEach(id => {
                    const el = document.getElementById(id);
                    if (el)
                        el.style.display = 'none';
                });
                document.getElementById(idPantalla).style.display = 'block';
            }

            function simularPago() {
                // Validación si es tarjeta
                const pantallaTarjeta = document.getElementById('pantallaTarjeta');
                if (pantallaTarjeta.style.display === 'block') {
                    const num = document.getElementById('numTarjeta').value;
                    if (num.length < 16) {
                        alert("Ingresa al menos 16 dígitos.");
                        return;
                    }
                }

                // 1. Mostrar spinner
                mostrarPantalla('pantallaCarga');

                // 2. Recoger datos del formulario
                const form = document.getElementById('formReserva');
                const params = new URLSearchParams(new FormData(form)).toString();

                // 3. Enviar al servlet en background via fetch
                fetch('confirmarReservaServlet', {
                    method: 'POST',
                    headers: {'Content-Type': 'application/x-www-form-urlencoded'},
                    body: params
                })
                        .then(res => {
                            // El servlet redirige con sendRedirect → fetch recibe la página destino
                            // Si llegó a misReservasServlet = éxito, cualquier otra cosa = error
                            if (res.ok && res.url.includes('misReservasServlet')) {
                                document.getElementById('cajaModal').style.background = '#ffffff';
                                mostrarPantalla('pantallaExito');
                            } else if (res.ok) {
                                // Puede que el servlet hizo forward con error
                                document.getElementById('cajaModal').style.background = '#ffffff';
                                mostrarPantalla('pantallaExito'); // igual mostramos éxito si HTTP 200
                            } else {
                                mostrarPantalla('pantallaError');
                            }
                        })
                        .catch(() => mostrarPantalla('pantallaError'));
            }

            // Botón de la pantalla de éxito → redirige manualmente
            function irAlHistorial() {
                window.location.href = 'historialReserva.jsp';
            }
        </script>               
    </body>
</html>