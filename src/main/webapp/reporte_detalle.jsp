<%-- 
    Document   : reporte_detalle
    Created on : 26 nov. 2026, 10:14:53 p. m.
    Author     : Humberto
--%>


<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="model.Empresa"%>
<%@page import="model.Evaluacion"%>
<%@page import="model.ResultadoIndicador"%>
<%@page import="model.AnalisisIA"%>
<%@page import="java.util.List"%>
<%@page import="java.text.SimpleDateFormat"%>

<%
    Empresa empresa = (Empresa) request.getAttribute("empresa");
    Evaluacion evaluacion = (Evaluacion) request.getAttribute("evaluacion");
    ResultadoIndicador resultadoGeneral =
            (ResultadoIndicador) request.getAttribute("resultadoGeneral");

    List<ResultadoIndicador> resultadosIndicadores =
            (List<ResultadoIndicador>) request.getAttribute("resultadosIndicadores");

    AnalisisIA analisis = (AnalisisIA) request.getAttribute("analisis");

    SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy HH:mm");

    String fechaEvaluacion = evaluacion.getFechaEvaluacion() != null
            ? formatoFecha.format(evaluacion.getFechaEvaluacion())
            : "No disponible";

    double puntajeGeneral = resultadoGeneral.getPuntaje();
    String nivelGeneral = resultadoGeneral.getNivel();

    String colorNivel = "bg-secondary";

    if (nivelGeneral != null) {
        if (nivelGeneral.equalsIgnoreCase("Alto")) {
            colorNivel = "bg-success";
        } else if (nivelGeneral.equalsIgnoreCase("Medio")) {
            colorNivel = "bg-warning text-dark";
        } else if (nivelGeneral.equalsIgnoreCase("Bajo")) {
            colorNivel = "bg-danger";
        }
    }
%>

<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <title>Reporte Gerencial</title>

    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
          rel="stylesheet">

    <style>
        body {
            background-color: #f4f6f9;
        }

        .reporte-container {
            max-width: 1100px;
            margin: 30px auto;
        }

        .card {
            border: none;
            border-radius: 12px;
        }

        .titulo-reporte {
            color: #0d6efd;
            font-weight: 700;
        }

        .resultado-card {
            border-left: 5px solid #0d6efd;
        }

        .indicador-barra {
            height: 18px;
        }

        .seccion-titulo {
            border-bottom: 2px solid #0d6efd;
            padding-bottom: 8px;
            margin-bottom: 20px;
        }

        .texto-analisis {
            white-space: pre-line;
        }

        @media print {

            body {
                background: white !important;
            }

            .no-print {
                display: none !important;
            }

            .reporte-container {
                margin: 0;
                max-width: 100%;
            }

            .card {
                box-shadow: none !important;
                border: 1px solid #ddd !important;
            }

            .page-break {
                page-break-before: always;
            }
        }
    </style>
<link rel="stylesheet" href="${pageContext.request.contextPath}/assets/app.css?v=20261127-1">
</head>

<body class="sdg-app" data-section="reportes" data-page="reporte_detalle">
<% request.setAttribute("uiSection", "reportes"); request.setAttribute("uiTitle", "Detalle del reporte"); %>
<%@ include file="WEB-INF/ui/shell.jspf" %>

<div id="contenido-principal" class="container reporte-container">

    <!-- BOTONES -->

    <div class="d-flex justify-content-between align-items-center mb-4 no-print">

        <a href="ReporteServlet" class="btn btn-outline-secondary">
            ← Volver a reportes
        </a>

        <button onclick="window.print()"
                class="btn btn-primary">
            Imprimir / Guardar PDF
        </button>

    </div>

    <!-- ENCABEZADO -->

    <div class="card shadow-sm mb-4">

        <div class="card-body text-center p-4">

            <h2 class="titulo-reporte">
                REPORTE DE EVALUACIÓN GERENCIAL
            </h2>

            <p class="text-muted mb-0">
                Sistema de Evaluación y Apoyo a la Toma de
                Decisiones Gerenciales mediante IA
            </p>

        </div>

    </div>

    <!-- DATOS DE EMPRESA -->

    <div class="card shadow-sm mb-4">

        <div class="card-body">

            <h4 class="seccion-titulo">
                Información de la empresa
            </h4>

            <div class="row">

                <div class="col-md-6 mb-3">
                    <strong>Razón social:</strong><br>
                    <%= empresa.getRazonSocial() %>
                </div>

                <div class="col-md-6 mb-3">
                    <strong>RUC:</strong><br>
                    <%= empresa.getRuc() %>
                </div>

                <div class="col-md-6 mb-3">
                    <strong>Sector:</strong><br>
                    <%= empresa.getSector() != null
                        ? empresa.getSector()
                        : "No especificado" %>
                </div>

                <div class="col-md-6 mb-3">
                    <strong>Correo:</strong><br>
                    <%= empresa.getCorreo() != null
                        ? empresa.getCorreo()
                        : "No especificado" %>
                </div>

            </div>

        </div>

    </div>

    <!-- DATOS EVALUACIÓN -->

    <div class="card shadow-sm mb-4">

        <div class="card-body">

            <h4 class="seccion-titulo">
                Información de la evaluación
            </h4>

            <div class="row">

                <div class="col-md-4 mb-3">
                    <strong>N.° de evaluación:</strong><br>
                    <%= evaluacion.getIdEvaluacion() %>
                </div>

                <div class="col-md-4 mb-3">
                    <strong>Fecha:</strong><br>
                    <%= fechaEvaluacion %>
                </div>

                <div class="col-md-4 mb-3">
                    <strong>Estado:</strong><br>
                    <%= evaluacion.getEstado() %>
                </div>

            </div>

            <div class="mt-2">

                <strong>Observaciones:</strong>

                <p class="mb-0">
                    <%= evaluacion.getObservaciones() != null
                        && !evaluacion.getObservaciones().trim().isEmpty()
                        ? evaluacion.getObservaciones()
                        : "Sin observaciones." %>
                </p>

            </div>

        </div>

    </div>

    <!-- RESULTADO GENERAL -->

    <div class="card shadow-sm mb-4 resultado-card">

        <div class="card-body text-center p-4">

            <h4 class="mb-3">
                Índice general de gestión
            </h4>

            <h1 class="display-4 fw-bold text-primary">
                <%= String.format(java.util.Locale.US, "%.2f", puntajeGeneral) %>
            </h1>

            <p class="text-muted">
                Puntaje general obtenido
            </p>

            <span class="badge <%= colorNivel %> fs-5 px-4 py-2">
                <%= nivelGeneral %>
            </span>

        </div>

    </div>

    <!-- RESULTADOS POR INDICADOR -->

    <div class="card shadow-sm mb-4">

        <div class="card-body">

            <h4 class="seccion-titulo">
                Resultados por dimensión
            </h4>

            <% if (resultadosIndicadores != null
                    && !resultadosIndicadores.isEmpty()) { %>

                <% for (ResultadoIndicador indicador : resultadosIndicadores) {

                    double valor = indicador.getPuntaje();

                    double porcentaje = Math.max(0, Math.min(100, valor * 20));

                    String colorBarra = "bg-primary";

                    if (indicador.getNivel() != null) {

                        if (indicador.getNivel().equalsIgnoreCase("Alto")) {
                            colorBarra = "bg-success";
                        } else if (indicador.getNivel().equalsIgnoreCase("Medio")) {
                            colorBarra = "bg-warning";
                        } else if (indicador.getNivel().equalsIgnoreCase("Bajo")) {
                            colorBarra = "bg-danger";
                        }
                    }
                %>

                    <div class="mb-4">

                        <div class="d-flex justify-content-between mb-2">

                            <strong>
                                <%= indicador.getNombreIndicador() %>
                            </strong>

                            <span>
                                <%= String.format(java.util.Locale.US, "%.2f", valor) %>
                                / 5
                            </span>

                        </div>

                        <div class="progress indicador-barra">

                            <div class="progress-bar <%= colorBarra %>"
                                 role="progressbar"
                                 style="width: <%= porcentaje %>%"
                                 aria-valuenow="<%= porcentaje %>"
                                 aria-valuemin="0"
                                 aria-valuemax="100">
                            </div>

                        </div>

                        <small class="text-muted">
                            Nivel: <%= indicador.getNivel() %>
                        </small>

                    </div>

                <% } %>

            <% } else { %>

                <p class="text-muted">
                    No se encontraron resultados por dimensión.
                </p>

            <% } %>

        </div>

    </div>

    <!-- ANÁLISIS IA -->

    <div class="card shadow-sm mb-4">

        <div class="card-body">

            <h4 class="seccion-titulo">
                Análisis gerencial mediante IA
            </h4>

            <% if (analisis != null) { %>

                <div class="mb-4">

                    <h5 class="text-primary">
                        Resumen del análisis
                    </h5>

                    <div class="texto-analisis">
                        <%= analisis.getResumen() != null
                            ? analisis.getResumen()
                            : "No disponible." %>
                    </div>

                </div>

                <hr>

                <div class="mb-4">

                    <h5 class="text-success">
                        Fortalezas identificadas
                    </h5>

                    <div class="texto-analisis">
                        <%= analisis.getFortalezas() != null
                            ? analisis.getFortalezas()
                            : "No disponible." %>
                    </div>

                </div>

                <hr>

                <div class="mb-4">

                    <h5 class="text-danger">
                        Debilidades identificadas
                    </h5>

                    <div class="texto-analisis">
                        <%= analisis.getDebilidades() != null
                            ? analisis.getDebilidades()
                            : "No disponible." %>
                    </div>

                </div>

                <hr>

                <div class="mb-4">

                    <h5 class="text-primary">
                        Recomendaciones gerenciales
                    </h5>

                    <div class="texto-analisis">
                        <%= analisis.getRecomendaciones() != null
                            ? analisis.getRecomendaciones()
                            : "No disponible." %>
                    </div>

                </div>

            <% } else { %>

                <div class="alert alert-warning">
                    Esta evaluación todavía no cuenta con un análisis IA registrado.
                    Puede consultar los resultados cuantitativos disponibles.
                </div>

            <% } %>

        </div>

    </div>

    <!-- CONCLUSIÓN -->

    <div class="card shadow-sm mb-4">

        <div class="card-body">

            <h4 class="seccion-titulo">
                Consideraciones finales
            </h4>

            <p>
                Los resultados presentados constituyen una herramienta
                de apoyo para identificar aspectos relevantes de la
                gestión empresarial. Las recomendaciones generadas
                mediante inteligencia artificial deben ser revisadas,
                contextualizadas y evaluadas por el responsable
                de la toma de decisiones de la empresa.
            </p>

            <p class="mb-0">
                El sistema no reemplaza el criterio, la experiencia
                ni la responsabilidad del gerente o propietario.
            </p>

        </div>

    </div>

    <!-- PIE -->

    <div class="text-center text-muted mb-4">

        <small>
            Reporte generado por el Sistema de Evaluación y Apoyo
            a la Toma de Decisiones Gerenciales mediante IA.
        </small>

    </div>

</div>

<%@ include file="WEB-INF/ui/footer.jspf" %>
</body>
</html>

