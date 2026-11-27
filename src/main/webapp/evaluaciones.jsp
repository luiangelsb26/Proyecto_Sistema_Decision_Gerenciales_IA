<%-- 
    Document   : evaluaciones
    Created on : 11 set. 2026, 10:16:06 a. m.
    Author     : Humberto
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ page import="java.util.List" %>
<%@ page import="model.Evaluacion" %>
<%@ page import="model.Usuario" %>

<%
    Usuario usuario =
            (Usuario) session.getAttribute("usuarioLogueado");

    if (usuario == null) {
        response.sendRedirect(
                "login.jsp?error=2"
        );
        return;
    }

    List<Evaluacion> evaluaciones =
            (List<Evaluacion>) request.getAttribute(
                    "evaluaciones"
            );

    String mensaje =
            request.getParameter("mensaje");

    String error =
            request.getParameter("error");
%>


<!DOCTYPE html>
<html lang="es">
    <head>
        <meta charset="UTF-8">

        <meta name="viewport"
              content="width=device-width, initial-scale=1.0">

        <title>Evaluaciones - Sistema de Decisión Gerencial</title>

        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
              rel="stylesheet">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/app.css?v=20261127-1">
</head>
    <body class="sdg-app" data-section="evaluaciones" data-page="evaluaciones">


    <!-- ========================================== -->
    <!-- MENÚ SUPERIOR -->
    <!-- ========================================== -->

    <% request.setAttribute("uiSection", "evaluaciones"); request.setAttribute("uiTitle", "Evaluaciones"); %>
<%@ include file="WEB-INF/ui/shell.jspf" %>


    <!-- ========================================== -->
    <!-- CONTENIDO -->
    <!-- ========================================== -->

    <main id="contenido-principal" class="container py-5">

        <div class="d-flex justify-content-between
                    align-items-center mb-4">

            <div>

                <h1 class="fw-bold">
                    Evaluaciones
                </h1>

                <p class="text-muted mb-0">

                    Gestión de las evaluaciones empresariales.

                </p>

            </div>

            <button class="btn btn-primary"
                    data-bs-toggle="modal"
                    data-bs-target="#modalNuevaEvaluacion">

                Nueva evaluación

            </button>

        </div>


        <!-- ========================================== -->
        <!-- MENSAJES -->
        <!-- ========================================== -->

        <% if ("registrado".equals(mensaje)) { %>

            <div class="alert alert-success">

                La evaluación se registró correctamente.

            </div>

        <% } %>


        <% if ("empresa".equals(error)) { %>

            <div class="alert alert-danger">

                Debes seleccionar una empresa válida.

            </div>

        <% } %>


        <% if ("registro".equals(error)) { %>

            <div class="alert alert-danger">

                No fue posible registrar la evaluación.

            </div>

        <% } %>


        <!-- ========================================== -->
        <!-- TABLA -->
        <!-- ========================================== -->

        <div class="card shadow-sm">

            <div class="card-body">

                <h5 class="card-title mb-4">

                    Evaluaciones registradas

                </h5>


                <div class="table-responsive">

                    <table class="table table-hover
                                  align-middle">

                        <thead class="table-light">

                            <tr>

                                <th>ID</th>

                                <th>Empresa</th>

                                <th>Usuario</th>

                                <th>Fecha</th>

                                <th>Estado</th>

                                <th>Observaciones</th>
                                
                                <th>Acciones</th>

                            </tr>

                        </thead>

                        <tbody>

                        <% if (evaluaciones != null
                                && !evaluaciones.isEmpty()) { %>

                            <% for (Evaluacion evaluacion
                                    : evaluaciones) { %>

                                <tr>

                                    <td>
                                        <%= evaluacion.getIdEvaluacion() %>
                                    </td>

                                    <td>
                                        Empresa #<%= evaluacion.getIdEmpresa() %>
                                    </td>

                                    <td>
                                        Usuario #<%= evaluacion.getIdUsuario() %>
                                    </td>

                                    <td>
                                        <%= evaluacion.getFechaEvaluacion() %>
                                    </td>

                                    <td>

                                        <% if ("PENDIENTE".equals(
                                                evaluacion.getEstado())) { %>

                                            <span class="badge text-bg-warning">
                                                PENDIENTE
                                            </span>

                                        <% } else { %>

                                            <span class="badge text-bg-success">
                                                <%= evaluacion.getEstado() %>
                                            </span>

                                        <% } %>

                                    </td>

                                    <td>
                                        <%= evaluacion.getObservaciones()
                                                != null
                                                ? evaluacion.getObservaciones()
                                                : "-" %>
                                    </td>
                                    
                                    <td>
                                        <% if ("PENDIENTE".equals(evaluacion.getEstado())) { %>

                                            <a href="EvaluacionCuestionarioServlet?idEvaluacion=<%= evaluacion.getIdEvaluacion() %>"
                                               class="btn btn-primary btn-sm">
                                                Realizar evaluación
                                            </a>

                                        <% } else { %>

                                            <a href="ResultadoServlet?idEvaluacion=<%= evaluacion.getIdEvaluacion() %>"
                                               class="btn btn-success btn-sm">
                                                Ver resultados
                                            </a>

                                        <% } %>
                                    </td>

                                </tr>

                            <% } %>

                        <% } else { %>

                            <tr>

                                <td colspan="7"
                                    class="text-center text-muted py-4">

                                    No existen evaluaciones registradas.

                                </td>

                            </tr>

                        <% } %>

                        </tbody>

                    </table>

                </div>

            </div>

        </div>

    </main>


    <!-- ========================================== -->
    <!-- MODAL NUEVA EVALUACIÓN -->
    <!-- ========================================== -->

    <div class="modal fade"
         id="modalNuevaEvaluacion"
         tabindex="-1"
         aria-hidden="true">

        <div class="modal-dialog">

            <div class="modal-content">

                <div class="modal-header">

                    <h5 class="modal-title">

                        Nueva evaluación

                    </h5>

                    <button type="button"
                            class="btn-close"
                            data-bs-dismiss="modal">

                    </button>

                </div>


                <form action="EvaluacionServlet"
                      method="POST">

                    <input type="hidden"
                           name="accion"
                           value="registrar">


                    <div class="modal-body">

                        <div class="mb-3">

                            <label for="idEmpresa"
                                   class="form-label">

                                Empresa

                            </label>

                            <select class="form-select"
                                    id="idEmpresa"
                                    name="idEmpresa"
                                    required>

                                <option value="">
                                    Seleccione una empresa
                                </option>

                                <% 
                                    List<model.Empresa> empresas =
                                            (List<model.Empresa>) request.getAttribute("empresas");

                                    if (empresas != null && !empresas.isEmpty()) {

                                        for (model.Empresa empresa : empresas) {
                                %>

                                    <option value="<%= empresa.getIdEmpresa() %>">

                                        <%= empresa.getRazonSocial() %>
                                        - RUC: <%= empresa.getRuc() %>

                                    </option>

                                <%
                                        }

                                    }
                                %>

                            </select>

                            <div class="form-text">

                                Seleccione la empresa que será evaluada.

                            </div>

                        </div>


                        <div class="mb-3">

                            <label for="observaciones"
                                   class="form-label">

                                Observaciones

                            </label>

                            <textarea class="form-control"
                                      id="observaciones"
                                      name="observaciones"
                                      rows="4"
                                      placeholder="Ingrese observaciones de la evaluación...">
                            </textarea>

                        </div>

                    </div>


                    <div class="modal-footer">

                        <button type="button"
                                class="btn btn-secondary"
                                data-bs-dismiss="modal">

                            Cancelar

                        </button>

                        <button type="submit"
                                class="btn btn-primary">

                            Registrar evaluación

                        </button>

                    </div>

                </form>

            </div>

        </div>

    </div>


    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js">
    </script>

    <%@ include file="WEB-INF/ui/footer.jspf" %>
</body>
</html>
