<%-- 
    Document   : reportes
    Created on : 26 nov. 2026, 11:19:27 a. m.
    Author     : Humberto
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@page import="java.util.List"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="model.Empresa"%>
<%@page import="model.ResultadoEvaluacion"%>

<%!
    public String escaparHTML(String texto) {
        if (texto == null) return "";

        return texto
                .replace("&", "&amp;")
                .replace("<", "&lt;")
                .replace(">", "&gt;")
                .replace("\"", "&quot;")
                .replace("'", "&#39;");
    }
%>

<%
    if (session == null
            || session.getAttribute(
                    "usuarioLogueado"
            ) == null) {

        response.sendRedirect("login.jsp?error=2");
        return;
    }

    List<Empresa> empresas =
            (List<Empresa>) request.getAttribute(
                    "empresas"
            );

    List<ResultadoEvaluacion> reportes =
            (List<ResultadoEvaluacion>)
            request.getAttribute("reportes");

    Integer idEmpresaSeleccionada =
            (Integer) request.getAttribute(
                    "idEmpresaSeleccionada"
            );

    String fechaInicio =
            (String) request.getAttribute(
                    "fechaInicio"
            );

    String fechaFin =
            (String) request.getAttribute(
                    "fechaFin"
            );

    String errorFecha =
            (String) request.getAttribute(
                    "errorFecha"
            );

    if (fechaInicio == null) fechaInicio = "";
    if (fechaFin == null) fechaFin = "";

    if (idEmpresaSeleccionada == null) {
        idEmpresaSeleccionada = 0;
    }

    SimpleDateFormat formatoFecha =
            new SimpleDateFormat("dd/MM/yyyy HH:mm");

    int totalReportes =
            reportes != null ? reportes.size() : 0;
%>

<!DOCTYPE html>
<html lang="es">

    <head>

        <meta charset="UTF-8">

        <meta name="viewport"
              content="width=device-width, initial-scale=1">

        <title>Reportes - Sistema de Decisión Gerencial</title>

        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
              rel="stylesheet">

        <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.css"
              rel="stylesheet">

        <style>

            body {
                background-color: #f4f6f9;
            }

            .navbar {
                background-color: #163654;
            }

            .card {
                border: none;
                border-radius: 12px;
            }

            .titulo {
                font-weight: 600;
            }

            .tabla-container {
                overflow-x: auto;
            }

        </style>

    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/app.css?v=20261127-1">
</head>

    <body class="sdg-app" data-section="reportes" data-page="reportes">

    <!-- ==========================================
         NAVBAR
    ========================================== -->

    <% request.setAttribute("uiSection", "reportes"); request.setAttribute("uiTitle", "Reportes gerenciales"); %>
<%@ include file="WEB-INF/ui/shell.jspf" %>

    <!-- ==========================================
         CONTENIDO
    ========================================== -->

    <div id="contenido-principal" class="container py-4">

        <!-- ENCABEZADO -->

        <div class="mb-4">

            <h2 class="titulo">

                <i class="bi bi-file-earmark-bar-graph me-2"></i>

                Reportes Gerenciales

            </h2>

            <p class="text-muted">

                Consulta y visualiza los reportes generados
                a partir de las evaluaciones empresariales.

            </p>

        </div>

        <!-- ==========================================
             TARJETA DE RESUMEN
        ========================================== -->

        <div class="row mb-4">

            <div class="col-md-4">

                <div class="card shadow-sm">

                    <div class="card-body">

                        <div class="d-flex justify-content-between
                                    align-items-center">

                            <div>

                                <h6 class="text-muted">

                                    Reportes encontrados

                                </h6>

                                <h3 class="fw-bold">

                                    <%= totalReportes %>

                                </h3>

                            </div>

                            <i class="bi bi-file-earmark-text
                                      fs-1 text-primary"></i>

                        </div>

                    </div>

                </div>

            </div>

        </div>

        <!-- ==========================================
             FILTROS
        ========================================== -->

        <div class="card shadow-sm mb-4">

            <div class="card-body">

                <h5 class="mb-3">

                    <i class="bi bi-funnel me-2"></i>

                    Filtros de búsqueda

                </h5>

                <% if (errorFecha != null) { %>

                    <div class="alert alert-warning">

                        <%= escaparHTML(errorFecha) %>

                    </div>

                <% } %>

                <form action="ReporteServlet"
                      method="get">

                    <div class="row g-3">

                        <!-- EMPRESA -->

                        <div class="col-md-4">

                            <label class="form-label">

                                Empresa

                            </label>

                            <select name="idEmpresa"
                                    class="form-select">

                                <option value="0">

                                    Todas las empresas

                                </option>

                                <% if (empresas != null) {
                                    for (Empresa empresa : empresas) {
                                %>

                                    <option
                                        value="<%= empresa.getIdEmpresa() %>"
                                        <%= idEmpresaSeleccionada ==
                                            empresa.getIdEmpresa()
                                            ? "selected" : "" %>>

                                        <%= escaparHTML(
                                            empresa.getRazonSocial()
                                        ) %>

                                    </option>

                                <% }
                                } %>

                            </select>

                        </div>

                        <!-- FECHA INICIO -->

                        <div class="col-md-3">

                            <label class="form-label">

                                Fecha de inicio

                            </label>

                            <input type="date"
                                   name="fechaInicio"
                                   class="form-control"
                                   value="<%= escaparHTML(fechaInicio) %>">

                        </div>

                        <!-- FECHA FIN -->

                        <div class="col-md-3">

                            <label class="form-label">

                                Fecha de fin

                            </label>

                            <input type="date"
                                   name="fechaFin"
                                   class="form-control"
                                   value="<%= escaparHTML(fechaFin) %>">

                        </div>

                        <!-- BOTONES -->

                        <div class="col-md-2 d-flex
                                    align-items-end">

                            <button type="submit"
                                    class="btn btn-primary w-100">

                                <i class="bi bi-search me-1"></i>

                                Buscar

                            </button>

                        </div>

                    </div>

                    <div class="mt-3">

                        <a href="ReporteServlet"
                           class="btn btn-outline-secondary btn-sm">

                            <i class="bi bi-arrow-counterclockwise
                                      me-1"></i>

                            Limpiar filtros

                        </a>

                    </div>

                </form>

            </div>

        </div>

        <!-- ==========================================
             LISTADO
        ========================================== -->

        <div class="card shadow-sm">

            <div class="card-body">

                <h5 class="mb-3">

                    <i class="bi bi-table me-2"></i>

                    Listado de reportes

                </h5>

                <div class="tabla-container">

                    <table class="table table-hover
                                  table-striped align-middle">

                        <thead class="table-dark">

                            <tr>

                                <th>ID</th>

                                <th>Empresa</th>

                                <th>Fecha de evaluación</th>

                                <th>Índice general</th>

                                <th>Nivel</th>

                                <th>Acciones</th>

                            </tr>

                        </thead>

                        <tbody>

                        <% if (reportes != null
                                && !reportes.isEmpty()) {

                            for (ResultadoEvaluacion reporte
                                    : reportes) {

                                String fecha =
                                        reporte.getFechaEvaluacion()
                                        != null
                                        ? formatoFecha.format(
                                            reporte.getFechaEvaluacion()
                                          )
                                        : "";
                        %>

                            <tr>

                                <td>

                                    <%= reporte.getIdEvaluacion() %>

                                </td>

                                <td>

                                    <%= escaparHTML(
                                        reporte.getRazonSocial()
                                    ) %>

                                </td>

                                <td>

                                    <%= fecha %>

                                </td>

                                <td>

                                    <strong>

                                        <%= reporte.getIndiceGeneral() %>

                                    </strong>

                                </td>

                                <td>

                                    <%= escaparHTML(
                                        reporte.getNivel()
                                    ) %>

                                </td>

                                <td>
                                 
                                    <a href="ReporteDetalleServlet?idEvaluacion=<%= reporte.getIdEvaluacion() %>"
                                       class="btn btn-sm btn-primary">
                                       <i class="bi bi-file-earmark-bar-graph"></i>
                                       Ver reporte
                                    </a>

                                </td>

                            </tr>

                        <% }
                        } else { %>

                            <tr>

                                <td colspan="6"
                                    class="text-center py-4">

                                    <i class="bi bi-inbox fs-3
                                              text-muted"></i>

                                    <p class="text-muted mb-0">

                                        No se encontraron reportes
                                        para los filtros seleccionados.

                                    </p>

                                </td>

                            </tr>

                        <% } %>

                        </tbody>

                    </table>

                </div>

            </div>

        </div>

    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js">
    </script>

    <%@ include file="WEB-INF/ui/footer.jspf" %>
</body>

</html>

