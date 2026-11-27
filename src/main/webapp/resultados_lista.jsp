<%-- 
    Document   : resultados_lista
    Created on : 8 oct. 2026, 7:28:23 p. m.
    Author     : Humberto
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.List"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="model.ResultadoEvaluacion"%>
<%@page import="model.Usuario"%>

<%
    Usuario usuario = (Usuario) session.getAttribute("usuarioLogueado");

    if (usuario == null) {
        response.sendRedirect("login.jsp?error=2");
        return;
    }

    List<ResultadoEvaluacion> evaluaciones =
            (List<ResultadoEvaluacion>) request.getAttribute(
                    "evaluacionesConResultados"
            );

    SimpleDateFormat formatoFecha =
            new SimpleDateFormat("dd/MM/yyyy HH:mm");
%>

<!DOCTYPE html>
<html lang="es">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">

        <title>Resultados | Sistema de Decisiones Gerenciales</title>

        <link
            href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
            rel="stylesheet">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/app.css?v=20261127-1">
</head>
    <body class="sdg-app" data-section="resultados" data-page="resultados_lista">
        <% request.setAttribute("uiSection", "resultados"); request.setAttribute("uiTitle", "Resultados"); %>
<%@ include file="WEB-INF/ui/shell.jspf" %>

    <main id="contenido-principal" class="container py-4">

        <div class="d-flex justify-content-between align-items-center mb-4">
            <div>
                <h2 class="mb-1">Resultados de evaluaciones</h2>
                <p class="text-secondary mb-0">
                    Consulta los resultados generales y el nivel alcanzado
                    por cada empresa.
                </p>
            </div>

            <a href="EvaluacionServlet" class="btn btn-outline-secondary">
                Volver a evaluaciones
            </a>
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
                                <th>Índice general</th>
                                <th>Nivel</th>
                                <th>Acción</th>
                            </tr>
                        </thead>

                        <tbody>
                            <% if (evaluaciones != null && !evaluaciones.isEmpty()) { %>

                                <% for (ResultadoEvaluacion resultado : evaluaciones) { %>
                                    <tr>
                                        <td>
                                            <%= resultado.getIdEvaluacion() %>
                                        </td>

                                        <td>
                                            <%= resultado.getRazonSocial() %>
                                        </td>

                                        <td>
                                            <%= resultado.getFechaEvaluacion() != null
                                                    ? formatoFecha.format(
                                                        resultado.getFechaEvaluacion())
                                                    : "Sin fecha" %>
                                        </td>

                                        <td>
                                            <strong>
                                                <%= String.format(
                                                        java.util.Locale.US,
                                                        "%.2f",
                                                        resultado.getIndiceGeneral()) %>
                                                / 5.00
                                            </strong>
                                        </td>

                                        <td>
                                            <span class="badge text-bg-primary">
                                                <%= resultado.getNivel() %>
                                            </span>
                                        </td>

                                        <td>
                                            <a class="btn btn-success btn-sm"
                                               href="ResultadoServlet?idEvaluacion=<%= resultado.getIdEvaluacion() %>">
                                                Ver resultados
                                            </a>
                                        </td>
                                    </tr>
                                <% } %>

                            <% } else { %>

                                <tr>
                                    <td colspan="6"
                                        class="text-center text-secondary py-4">
                                        Todavía no hay evaluaciones con resultados
                                        disponibles.
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
