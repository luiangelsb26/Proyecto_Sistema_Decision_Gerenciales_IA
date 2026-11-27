<%-- 
    Document   : analisis_lista
    Created on : 13 oct. 2026, 12:03:11 p. m.
    Author     : Humberto
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.List"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="model.Usuario"%>
<%@page import="model.AnalisisEvaluacion"%>

<%
    Usuario usuario =
            (Usuario) session.getAttribute(
                    "usuarioLogueado"
            );

    if (usuario == null) {
        response.sendRedirect(
                "login.jsp?error=2"
        );
        return;
    }

    List<AnalisisEvaluacion> evaluaciones =
            (List<AnalisisEvaluacion>)
            request.getAttribute(
                    "evaluacionesDisponibles"
            );

    SimpleDateFormat formatoFecha =
            new SimpleDateFormat(
                    "dd/MM/yyyy HH:mm"
            );
%>
<!DOCTYPE html>
<html lang="es">
    <head>
        <meta charset="UTF-8">

        <meta name="viewport"
              content="width=device-width, initial-scale=1.0">

        <title>
            Análisis empresarial - Sistema de Decisión Gerencial
        </title>

        <link
            href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
            rel="stylesheet">

    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/app.css?v=20261127-1">
</head>
    <body class="sdg-app" data-section="analisis" data-page="analisis_lista">
        <% request.setAttribute("uiSection", "analisis"); request.setAttribute("uiTitle", "Análisis empresarial"); %>
<%@ include file="WEB-INF/ui/shell.jspf" %>


        <main id="contenido-principal" class="container py-4">

            <div class="mb-4">

                <h2 class="mb-1">
                    Análisis empresarial
                </h2>

                <p class="text-secondary mb-0">
                    Selecciona una evaluación para generar
                    o consultar su análisis gerencial.
                </p>

            </div>


            <div class="card border-0 shadow-sm">

                <div class="card-body">

                    <div class="table-responsive">

                        <table class="table table-hover align-middle">

                            <thead class="table-dark">

                                <tr>
                                    <th>ID</th>
                                    <th>Empresa</th>
                                    <th>Fecha</th>
                                    <th>Índice</th>
                                    <th>Nivel</th>
                                    <th>Estado análisis</th>
                                    <th>Acción</th>
                                </tr>

                            </thead>

                            <tbody>

                            <% if (evaluaciones != null
                                    && !evaluaciones.isEmpty()) {

                                for (AnalisisEvaluacion evaluacion
                                        : evaluaciones) {
                            %>

                                <tr>

                                    <td>
                                        <%= evaluacion.getIdEvaluacion() %>
                                    </td>

                                    <td>
                                        <%= evaluacion.getRazonSocial() %>
                                    </td>

                                    <td>

                                        <%= evaluacion.getFechaEvaluacion() != null
                                                ? formatoFecha.format(
                                                    evaluacion.getFechaEvaluacion()
                                                )
                                                : "Sin fecha"
                                        %>

                                    </td>

                                    <td>

                                        <strong>

                                            <%= String.format(
                                                    java.util.Locale.US,
                                                    "%.2f",
                                                    evaluacion.getIndiceGeneral()
                                            ) %>

                                            / 5.00

                                        </strong>

                                    </td>

                                    <td>

                                        <span class="badge text-bg-primary">

                                            <%= evaluacion.getNivel() %>

                                        </span>

                                    </td>

                                    <td>

                                        <% if (evaluacion.isTieneAnalisis()) { %>

                                            <span class="badge text-bg-success">
                                                Generado
                                            </span>

                                        <% } else { %>

                                            <span class="badge text-bg-warning">
                                                Pendiente
                                            </span>

                                        <% } %>

                                    </td>

                                    <td>

                                        <a
                                            href="AnalisisServlet?idEvaluacion=<%= evaluacion.getIdEvaluacion() %>"
                                            class="btn <%= evaluacion.isTieneAnalisis()
                                                    ? "btn-outline-primary"
                                                    : "btn-primary" %> btn-sm">

                                            <%= evaluacion.isTieneAnalisis()
                                                    ? "Ver análisis"
                                                    : "Generar análisis" %>

                                        </a>

                                    </td>

                                </tr>

                            <%
                                }

                            } else {
                            %>

                                <tr>

                                    <td colspan="7"
                                        class="text-center text-secondary py-4">

                                        No existen evaluaciones con
                                        resultados disponibles para analizar.

                                    </td>

                                </tr>

                            <% } %>

                            </tbody>

                        </table>

                    </div>

                </div>

            </div>

        </main>


        <script
            src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js">
        </script>
    <%@ include file="WEB-INF/ui/footer.jspf" %>
</body>
</html>
