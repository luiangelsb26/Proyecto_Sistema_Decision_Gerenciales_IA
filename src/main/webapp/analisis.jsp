<%-- 
    Document   : analisis
    Created on : 12 oct. 2026, 10:46:37 p. m.
    Author     : Humberto
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.List"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="model.Usuario"%>
<%@page import="model.AnalisisIA"%>
<%@page import="model.ResultadoIndicador"%>

<%
    Usuario usuario =
            (Usuario) session.getAttribute("usuarioLogueado");

    if (usuario == null) {
        response.sendRedirect("login.jsp?error=2");
        return;
    }

    AnalisisIA analisis =
            (AnalisisIA) request.getAttribute("analisis");

    ResultadoIndicador resultadoGeneral =
            (ResultadoIndicador)
            request.getAttribute("resultadoGeneral");

    List<ResultadoIndicador> resultadosIndicadores =
            (List<ResultadoIndicador>)
            request.getAttribute("resultadosIndicadores");

    Integer idEvaluacion =
            (Integer) request.getAttribute("idEvaluacion");

    SimpleDateFormat formatoFecha =
            new SimpleDateFormat("dd/MM/yyyy HH:mm");
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

        <style>

            body {
                background-color: #f5f7fa;
            }

            .resultado-general {
                font-size: 2.8rem;
                font-weight: 700;
            }

            .seccion-analisis {
                white-space: pre-line;
            }

            .indicador-card {
                transition: 0.2s;
            }

            .indicador-card:hover {
                transform: translateY(-2px);
            }

        </style>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/app.css?v=20261127-1">
</head>
    <body class="sdg-app" data-section="analisis" data-page="analisis">
        <!-- ========================================= -->
        <!-- NAVBAR -->
        <!-- ========================================= -->

        <% request.setAttribute("uiSection", "analisis"); request.setAttribute("uiTitle", "Análisis empresarial"); %>
<%@ include file="WEB-INF/ui/shell.jspf" %>


        <!-- ========================================= -->
        <!-- CONTENIDO -->
        <!-- ========================================= -->

        <main id="contenido-principal" class="container py-4">

            <div class="d-flex justify-content-between
                        align-items-center mb-4">

                <div>

                    <h2 class="mb-1">
                        Análisis de la evaluación
                    </h2>

                    <p class="text-secondary mb-0">

                        Evaluación
                        #<%= idEvaluacion != null
                                ? idEvaluacion
                                : "" %>

                    </p>

                </div>

                <% if (idEvaluacion != null) { %>

                    <a href="ResultadoServlet?idEvaluacion=<%= idEvaluacion %>"
                       class="btn btn-outline-secondary">

                        Volver a resultados

                    </a>

                <% } %>

            </div>


            <% if (analisis != null
                    && resultadoGeneral != null) { %>


            <!-- ===================================== -->
            <!-- RESULTADO GENERAL -->
            <!-- ===================================== -->

            <div class="card border-0 shadow-sm mb-4">

                <div class="card-body text-center py-4">

                    <p class="text-secondary mb-1">
                        Índice general
                    </p>

                    <div class="resultado-general">

                        <%= String.format(
                                java.util.Locale.US,
                                "%.2f",
                                resultadoGeneral.getPuntaje()
                        ) %>

                        <span class="fs-5 text-secondary">
                            / 5.00
                        </span>

                    </div>

                    <span class="badge text-bg-primary fs-6">

                        <%= resultadoGeneral.getNivel() %>

                    </span>

                </div>

            </div>


            <!-- ===================================== -->
            <!-- RESULTADOS POR INDICADOR -->
            <!-- ===================================== -->

            <div class="card border-0 shadow-sm mb-4">

                <div class="card-body">

                    <h4 class="mb-3">
                        Resultados evaluados
                    </h4>

                    <div class="row g-3">

                        <% if (resultadosIndicadores != null) {

                            for (ResultadoIndicador resultado
                                    : resultadosIndicadores) {
                        %>

                        <div class="col-md-6 col-lg-4">

                            <div class="card h-100 indicador-card">

                                <div class="card-body">

                                    <h6>
                                        <%= resultado.getNombreIndicador() %>
                                    </h6>

                                    <div class="d-flex
                                                justify-content-between
                                                align-items-center">

                                        <strong class="fs-4">

                                            <%= String.format(
                                                    java.util.Locale.US,
                                                    "%.2f",
                                                    resultado.getPuntaje()
                                            ) %>

                                        </strong>

                                        <span class="badge text-bg-secondary">

                                            <%= resultado.getNivel() %>

                                        </span>

                                    </div>

                                </div>

                            </div>

                        </div>

                        <%
                            }
                        }
                        %>

                    </div>

                </div>

            </div>


            <!-- ===================================== -->
            <!-- RESUMEN -->
            <!-- ===================================== -->

            <div class="card border-0 shadow-sm mb-4">

                <div class="card-body">

                    <h4>
                        Resumen del análisis
                    </h4>

                    <p class="mb-0">

                        <%= analisis.getResumen() %>

                    </p>

                </div>

            </div>


            <div class="row g-4 mb-4">


                <!-- ================================= -->
                <!-- FORTALEZAS -->
                <!-- ================================= -->

                <div class="col-lg-6">

                    <div class="card border-0 shadow-sm h-100">

                        <div class="card-body">

                            <h4 class="text-success">
                                Fortalezas
                            </h4>

                            <div class="seccion-analisis">

                                <%= analisis.getFortalezas() %>

                            </div>

                        </div>

                    </div>

                </div>


                <!-- ================================= -->
                <!-- DEBILIDADES -->
                <!-- ================================= -->

                <div class="col-lg-6">

                    <div class="card border-0 shadow-sm h-100">

                        <div class="card-body">

                            <h4 class="text-danger">
                                Áreas de mejora
                            </h4>

                            <div class="seccion-analisis">

                                <%= analisis.getDebilidades() %>

                            </div>

                        </div>

                    </div>

                </div>

            </div>


            <!-- ===================================== -->
            <!-- RECOMENDACIONES -->
            <!-- ===================================== -->

            <div class="card border-0 shadow-sm mb-4">

                <div class="card-body">

                    <h4 class="text-primary">
                        Recomendaciones
                    </h4>

                    <div class="seccion-analisis">

                        <%= analisis.getRecomendaciones() %>

                    </div>

                </div>

            </div>


            <!-- ===================================== -->
            <!-- INFORMACIÓN -->
            <!-- ===================================== -->

            <div class="alert alert-info">

                <strong>Importante:</strong>

                Este análisis constituye información de apoyo
                para la toma de decisiones gerenciales.
                Las recomendaciones deben ser evaluadas por
                el responsable de la empresa antes de adoptar
                una decisión.

            </div>


            <% } else { %>

                <div class="alert alert-warning">

                    No se encontró información suficiente
                    para mostrar el análisis de esta evaluación.

                </div>

            <% } %>


        </main>


        <script
            src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js">
        </script>
    <%@ include file="WEB-INF/ui/footer.jspf" %>
</body>
</html>
