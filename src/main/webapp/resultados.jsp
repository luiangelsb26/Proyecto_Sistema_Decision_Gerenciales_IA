<%-- 
    Document   : resultados
    Created on : 8 oct. 2026, 1:03:25 p. m.
    Author     : Humberto
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.List"%>
<%@page import="model.ResultadoIndicador"%>

<%
    if (session.getAttribute("usuarioLogueado") == null) {
        response.sendRedirect("login.jsp?error=2");
        return;
    }

    ResultadoIndicador resultadoGeneral =
            (ResultadoIndicador)
            request.getAttribute("resultadoGeneral");

    List<ResultadoIndicador> resultadosIndicadores =
            (List<ResultadoIndicador>)
            request.getAttribute("resultadosIndicadores");

    Integer idEvaluacion =
            (Integer) request.getAttribute("idEvaluacion");
%>

<!DOCTYPE html>
<html lang="es">
    <head>
        <meta charset="UTF-8">

        <title>Resultados | Sistema de Evaluación Gerencial</title>

        <meta name="viewport"
              content="width=device-width, initial-scale=1.0">

        <link
            href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
            rel="stylesheet">

        <style>

            body {
                background-color: #f5f7fb;
            }

            .navbar-brand {
                font-weight: 600;
            }

            .resultado-general {
                border-radius: 16px;
                padding: 30px;
                background: white;
                box-shadow: 0 4px 15px rgba(0,0,0,.08);
            }

            .indice {
                font-size: 48px;
                font-weight: 700;
            }

            .nivel {
                font-size: 20px;
                font-weight: 600;
            }

            .indicador-card {
                background: white;
                border-radius: 14px;
                padding: 20px;
                box-shadow: 0 3px 12px rgba(0,0,0,.06);
                height: 100%;
            }

            .barra {
                height: 10px;
                border-radius: 10px;
                background-color: #e9ecef;
                overflow: hidden;
            }

            .barra-progreso {
                height: 100%;
                background-color: #0d6efd;
            }

        </style>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/app.css?v=20261127-1">
</head>
    <body class="sdg-app" data-section="resultados" data-page="resultados">
        <!-- ==========================================
            NAVBAR
            ========================================== -->

       <% request.setAttribute("uiSection", "resultados"); request.setAttribute("uiTitle", "Resultados"); %>
<%@ include file="WEB-INF/ui/shell.jspf" %>


       <!-- ==========================================
            CONTENIDO
            ========================================== -->

       <div id="contenido-principal" class="container py-4">

           <div class="d-flex
                       justify-content-between
                       align-items-center
                       mb-4">

               <div>

                   <h2 class="mb-1">
                       Resultados de evaluación
                   </h2>

                   <p class="text-muted mb-0">
                       Evaluación #<%= idEvaluacion %>
                   </p>

               </div>

               <a href="EvaluacionServlet"
                  class="btn btn-outline-secondary">

                   ← Volver a evaluaciones

               </a>

           </div>


           <!-- ======================================
                RESULTADO GENERAL
                ====================================== -->

           <div class="resultado-general mb-4">

               <div class="row align-items-center">

                   <div class="col-md-7">

                       <h4>
                           Índice general de evaluación
                       </h4>

                       <p class="text-muted">
                           Resultado global obtenido a partir
                           de los indicadores evaluados.
                       </p>

                   </div>

                   <div class="col-md-5 text-center">

                       <div class="indice">

                           <%= String.format(
                                   "%.2f",
                                   resultadoGeneral.getPuntaje()
                           ) %>

                           <span class="fs-5">
                               / 5.00
                           </span>

                       </div>

                       <div class="nivel">

                           <%= resultadoGeneral.getNivel() %>

                       </div>

                   </div>

               </div>

           </div>


           <!-- ======================================
                INDICADORES
                ====================================== -->

           <h4 class="mb-3">
               Resultados por indicador
           </h4>

           <div class="row g-4">

               <%
                   if (resultadosIndicadores != null
                           && !resultadosIndicadores.isEmpty()) {

                       for (ResultadoIndicador resultado
                               : resultadosIndicadores) {

                           double porcentaje =
                                   (resultado.getPuntaje()
                                   / 5.0) * 100;
               %>

               <div class="col-md-6 col-lg-4">

                   <div class="indicador-card">

                       <h5>
                           <%= resultado.getNombreIndicador() %>
                       </h5>

                       <div class="d-flex
                                   justify-content-between
                                   mb-2">

                           <span class="text-muted">
                               Puntaje
                           </span>

                           <strong>
                               <%= String.format(
                                       "%.2f",
                                       resultado.getPuntaje()
                               ) %>
                               / 5.00
                           </strong>

                       </div>

                       <div class="barra mb-3">

                           <div
                               class="barra-progreso"
                               style="width: <%= porcentaje %>%;">
                           </div>

                       </div>

                       <span class="badge text-bg-primary">

                           <%= resultado.getNivel() %>

                       </span>

                   </div>

               </div>

               <%
                       }

                   } else {
               %>

               <div class="col-12">

                   <div class="alert alert-warning">

                       No existen resultados por indicador
                       para esta evaluación.

                   </div>

               </div>

               <%
                   }
               %>

           </div>

       </div>
               
       <% if (idEvaluacion != null) { %>

            <div class="text-center mt-3">

                <a href="AnalisisServlet?idEvaluacion=<%= idEvaluacion %>"
                   class="btn btn-primary">

                    Generar / Ver análisis

                </a>

            </div>

        <% } %>


       <script
           src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js">
       </script>
    <%@ include file="WEB-INF/ui/footer.jspf" %>
</body>
</html>
