<%-- 
    Document   : cuestionario
    Created on : 15 set. 2026, 10:25:04 a. m.
    Author     : Humberto
--%>

<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%@ page import="java.util.List" %>
<%@ page import="model.Indicador" %>
<%@ page import="model.Pregunta" %>
<%@ page import="model.Usuario" %>

<%
    // ==========================================
    // VERIFICAR SESIÓN
    // ==========================================

    Usuario usuario =
            (Usuario) session.getAttribute("usuarioLogueado");

    if (usuario == null) {
        response.sendRedirect("login.jsp?error=2");
        return;
    }

    // ==========================================
    // OBTENER DATOS
    // ==========================================

    Integer idEvaluacion =
            (Integer) request.getAttribute("idEvaluacion");

    List<Indicador> indicadores =
            (List<Indicador>) request.getAttribute("indicadores");

    List<Pregunta> preguntas =
            (List<Pregunta>) request.getAttribute("preguntas");

    if (idEvaluacion == null) {
        response.sendRedirect("EvaluacionServlet?error=evaluacion");
        return;
    }
%>

<!DOCTYPE html>
<html lang="es">
    <head>
        <meta charset="UTF-8">

        <meta name="viewport"
              content="width=device-width, initial-scale=1.0">

        <title>Cuestionario de Evaluación</title>

        <link
            href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
            rel="stylesheet">

        <style>

            body {
                background-color: #f5f7fa;
            }

            .navbar-brand {
                font-weight: 600;
            }

            .contenido {
                max-width: 1100px;
                margin: 40px auto;
            }

            .encabezado {
                margin-bottom: 30px;
            }

            .encabezado h2 {
                font-weight: 600;
            }

            .indicador-card {
                margin-bottom: 30px;
                border: none;
                border-radius: 12px;
                box-shadow: 0 2px 8px rgba(0,0,0,0.08);
            }

            .indicador-header {
                padding: 20px;
                background-color: #0d6efd;
                color: white;
                border-radius: 12px 12px 0 0;
            }

            .indicador-header h4 {
                margin: 0;
            }

            .indicador-header p {
                margin: 8px 0 0;
                opacity: 0.9;
            }

            .pregunta {
                padding: 20px;
                border-bottom: 1px solid #e9ecef;
            }

            .pregunta:last-child {
                border-bottom: none;
            }

            .numero-pregunta {
                font-weight: 600;
                color: #495057;
                margin-bottom: 8px;
            }

            .texto-pregunta {
                font-size: 1.05rem;
                margin-bottom: 18px;
            }

            .escala {
                display: flex;
                gap: 10px;
                flex-wrap: wrap;
            }

            .escala input {
                display: none;
            }

            .escala label {
                cursor: pointer;
                border: 1px solid #ced4da;
                border-radius: 8px;
                padding: 10px 16px;
                min-width: 90px;
                text-align: center;
                background-color: white;
                transition: 0.2s;
            }

            .escala label:hover {
                background-color: #f1f3f5;
            }

            .escala input:checked + label {
                background-color: #0d6efd;
                color: white;
                border-color: #0d6efd;
            }

            .botones {
                margin-top: 30px;
                margin-bottom: 50px;
            }

            .leyenda {
                background-color: white;
                border-radius: 10px;
                padding: 18px;
                margin-bottom: 25px;
                border: 1px solid #dee2e6;
            }

        </style>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/app.css?v=20261127-1">
</head>
    <body class="sdg-app" data-section="evaluaciones" data-page="cuestionario">
        
        <!-- ==========================================
            NAVBAR
            ========================================== -->

       <% request.setAttribute("uiSection", "evaluaciones"); request.setAttribute("uiTitle", "Cuestionario de evaluación"); %>
<%@ include file="WEB-INF/ui/shell.jspf" %>
       <!-- ==========================================
            CONTENIDO
            ========================================== -->

       <div id="contenido-principal" class="container contenido">

           <div class="encabezado">

               <h2>
                   Evaluación gerencial
               </h2>

               <p class="text-muted">
                   Complete las preguntas de cada indicador
                   para evaluar la situación actual de la empresa.
               </p>

               <span class="badge bg-secondary">
                   Evaluación #<%= idEvaluacion %>
               </span>

           </div>


           <!-- ==========================================
                ESCALA
                ========================================== -->

           <div class="leyenda">

               <strong>Escala de valoración</strong>

               <div class="row mt-3">

                   <div class="col-md">
                       <strong>1</strong> - Muy bajo
                   </div>

                   <div class="col-md">
                       <strong>2</strong> - Bajo
                   </div>

                   <div class="col-md">
                       <strong>3</strong> - Medio
                   </div>

                   <div class="col-md">
                       <strong>4</strong> - Alto
                   </div>

                   <div class="col-md">
                       <strong>5</strong> - Muy alto
                   </div>

               </div>

           </div>
        <!-- ==========================================
         CUESTIONARIO
         ========================================== -->

    <form
        action="EvaluacionRespuestaServlet"
        method="POST">

        <input
            type="hidden"
            name="idEvaluacion"
            value="<%= idEvaluacion %>">


        <%
            if (indicadores != null
                    && preguntas != null
                    && !indicadores.isEmpty()) {

                for (Indicador indicador : indicadores) {
        %>


        <!-- ==========================================
             INDICADOR
             ========================================== -->

        <div class="card indicador-card">

            <div class="indicador-header">

                <h4>
                    <%= indicador.getNombre() %>
                </h4>

                <%
                    if (indicador.getDescripcion() != null
                            && !indicador.getDescripcion().isEmpty()) {
                %>

                    <p>
                        <%= indicador.getDescripcion() %>
                    </p>

                <%
                    }
                %>

            </div>


            <div class="card-body p-0">

                <%
                    int numeroPregunta = 1;

                    for (Pregunta pregunta : preguntas) {

                        if (pregunta.getIdIndicador()
                                != indicador.getIdIndicador()) {

                            continue;
                        }
                %>


                <!-- ==========================================
                     PREGUNTA
                     ========================================== -->

                <div class="pregunta">

                    <div class="numero-pregunta">

                        Pregunta <%= numeroPregunta %>

                    </div>

                    <div class="texto-pregunta">

                        <%= pregunta.getPregunta() %>

                    </div>


                    <div class="escala">

                        <input
                            type="radio"
                            id="p<%= pregunta.getIdPregunta() %>_1"
                            name="pregunta_<%= pregunta.getIdPregunta() %>"
                            value="1"
                            required>

                        <label
                            for="p<%= pregunta.getIdPregunta() %>_1">
                            1<br>
                            <small>Muy bajo</small>
                        </label>


                        <input
                            type="radio"
                            id="p<%= pregunta.getIdPregunta() %>_2"
                            name="pregunta_<%= pregunta.getIdPregunta() %>"
                            value="2">

                        <label
                            for="p<%= pregunta.getIdPregunta() %>_2">
                            2<br>
                            <small>Bajo</small>
                        </label>


                        <input
                            type="radio"
                            id="p<%= pregunta.getIdPregunta() %>_3"
                            name="pregunta_<%= pregunta.getIdPregunta() %>"
                            value="3">

                        <label
                            for="p<%= pregunta.getIdPregunta() %>_3">
                            3<br>
                            <small>Medio</small>
                        </label>


                        <input
                            type="radio"
                            id="p<%= pregunta.getIdPregunta() %>_4"
                            name="pregunta_<%= pregunta.getIdPregunta() %>"
                            value="4">

                        <label
                            for="p<%= pregunta.getIdPregunta() %>_4">
                            4<br>
                            <small>Alto</small>
                        </label>


                        <input
                            type="radio"
                            id="p<%= pregunta.getIdPregunta() %>_5"
                            name="pregunta_<%= pregunta.getIdPregunta() %>"
                            value="5">

                        <label
                            for="p<%= pregunta.getIdPregunta() %>_5">
                            5<br>
                            <small>Muy alto</small>
                        </label>

                    </div>

                </div>


                <%
                        numeroPregunta++;
                    }
                %>

            </div>

        </div>


        <%
                }
            } else {
        %>

        <div class="alert alert-warning">

            No se encontraron indicadores o preguntas
            disponibles para esta evaluación.

        </div>

        <%
            }
        %>


        <!-- ==========================================
             BOTONES
             ========================================== -->

        <div class="botones d-flex justify-content-between">

            <a
                href="EvaluacionServlet"
                class="btn btn-outline-secondary">

                ← Volver a evaluaciones

            </a>

            <button
                type="submit"
                class="btn btn-primary">

                Guardar evaluación

            </button>

        </div>

    </form>

</div>
        
        <script
            src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js">
        </script>
        
    <%@ include file="WEB-INF/ui/footer.jspf" %>
</body>
</html>
