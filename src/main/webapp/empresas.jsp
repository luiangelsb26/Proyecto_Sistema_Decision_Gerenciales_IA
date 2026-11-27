<%-- 
    Document   : empresas
    Created on : 11 set. 2026, 11:51:56 a. m.
    Author     : Humberto
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ page import="java.util.List" %>
<%@ page import="model.Empresa" %>
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

List<Empresa> empresas =
        (List<Empresa>) request.getAttribute(
                "empresas"
        );

Empresa empresaEditar =
        (Empresa) request.getAttribute(
                "empresaEditar"
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

        <title>Empresas - Sistema de Decisión Gerencial</title>

        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
              rel="stylesheet">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/app.css?v=20261127-1">
</head>

    <body class="sdg-app" data-section="empresas" data-page="empresas">


    <!-- ========================================== -->
    <!-- MENÚ SUPERIOR -->
    <!-- ========================================== -->

    <% request.setAttribute("uiSection", "empresas"); request.setAttribute("uiTitle", "Empresas"); %>
<%@ include file="WEB-INF/ui/shell.jspf" %>


    <!-- ========================================== -->
    <!-- CONTENIDO -->
    <!-- ========================================== -->

    <main id="contenido-principal" class="container py-5">

        <div class="d-flex justify-content-between
                    align-items-center mb-4">

            <div>

                <h1 class="fw-bold">

                    Gestión de Empresas

                </h1>

                <p class="text-muted mb-0">

                    Registro y administración de las empresas
                    que utilizarán el sistema.

                </p>

            </div>

            <button class="btn btn-primary"
                    data-bs-toggle="modal"
                    data-bs-target="#modalNuevaEmpresa">

                Nueva empresa

            </button>

        </div>


        <!-- ========================================== -->
        <!-- MENSAJES -->
        <!-- ========================================== -->

        <% if ("registrado".equals(mensaje)) { %>

            <div class="alert alert-success">

                Empresa registrada correctamente.

            </div>

        <% } %>


        <% if ("actualizado".equals(mensaje)) { %>

            <div class="alert alert-success">

                Empresa actualizada correctamente.

            </div>

        <% } %>


        <% if ("desactivado".equals(mensaje)) { %>

            <div class="alert alert-success">

                Empresa desactivada correctamente.

            </div>

        <% } %>


        <% if ("datos".equals(error)) { %>

            <div class="alert alert-danger">

                La razón social y el RUC son obligatorios.

            </div>

        <% } %>


        <% if ("registro".equals(error)) { %>

            <div class="alert alert-danger">

                No fue posible registrar la empresa.
                Verifica que el RUC no esté registrado.

            </div>

        <% } %>


        <% if ("actualizacion".equals(error)) { %>

            <div class="alert alert-danger">

                No fue posible actualizar la empresa.

            </div>

        <% } %>


        <!-- ========================================== -->
        <!-- TABLA DE EMPRESAS -->
        <!-- ========================================== -->

        <div class="card shadow-sm">

            <div class="card-body">

                <h5 class="card-title mb-4">

                    Empresas registradas

                </h5>

                <div class="table-responsive">

                    <table class="table table-hover align-middle">

                        <thead class="table-light">

                            <tr>

                                <th>ID</th>
                                <th>Razón social</th>
                                <th>RUC</th>
                                <th>Sector</th>
                                <th>Teléfono</th>
                                <th>Correo</th>
                                <th>Acciones</th>

                            </tr>

                        </thead>

                        <tbody>

                        <% if (empresas != null
                                && !empresas.isEmpty()) { %>

                            <% for (Empresa empresa
                                    : empresas) { %>

                                <tr>

                                    <td>

                                        <%= empresa.getIdEmpresa() %>

                                    </td>

                                    <td>

                                        <%= empresa.getRazonSocial() %>

                                    </td>

                                    <td>

                                        <%= empresa.getRuc() %>

                                    </td>

                                    <td>

                                        <%= empresa.getSector()
                                                != null
                                                ? empresa.getSector()
                                                : "-" %>

                                    </td>

                                    <td>

                                        <%= empresa.getTelefono()
                                                != null
                                                ? empresa.getTelefono()
                                                : "-" %>

                                    </td>

                                    <td>

                                        <%= empresa.getCorreo()
                                                != null
                                                ? empresa.getCorreo()
                                                : "-" %>

                                    </td>

                                    <td>

                                        <a href="EmpresaServlet?accion=editar&id=<%= empresa.getIdEmpresa() %>"
                                           class="btn btn-sm btn-outline-primary">

                                            Editar

                                        </a>

                                        <form action="EmpresaServlet"
                                              method="POST"
                                              class="d-inline">

                                            <input type="hidden"
                                                   name="accion"
                                                   value="desactivar">

                                            <input type="hidden"
                                                   name="idEmpresa"
                                                   value="<%= empresa.getIdEmpresa() %>">

                                            <button type="submit"
                                                    class="btn btn-sm btn-outline-danger"
                                                    onclick="return confirm('¿Deseas desactivar esta empresa?');">

                                                Desactivar

                                            </button>

                                        </form>

                                    </td>

                                </tr>

                            <% } %>

                        <% } else { %>

                            <tr>

                                <td colspan="7"
                                    class="text-center text-muted py-4">

                                    No existen empresas registradas.

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
    <!-- MODAL NUEVA EMPRESA -->
    <!-- ========================================== -->

    <div class="modal fade"
         id="modalNuevaEmpresa"
         tabindex="-1"
         aria-hidden="true">

        <div class="modal-dialog modal-lg">

            <div class="modal-content">

                <div class="modal-header">

                    <h5 class="modal-title">

                        Registrar nueva empresa

                    </h5>

                    <button type="button"
                            class="btn-close"
                            data-bs-dismiss="modal">

                    </button>

                </div>


                <form action="EmpresaServlet"
                      method="POST">

                    <input type="hidden"
                           name="accion"
                           value="registrar">


                    <div class="modal-body">

                        <div class="row">

                            <div class="col-md-8 mb-3">

                                <label class="form-label">

                                    Razón social *

                                </label>

                                <input type="text"
                                       class="form-control"
                                       name="razonSocial"
                                       maxlength="150"
                                       required>

                            </div>


                            <div class="col-md-4 mb-3">

                                <label class="form-label">

                                    RUC *

                                </label>

                                <input type="text"
                                       class="form-control"
                                       name="ruc"
                                       maxlength="11"
                                       minlength="11"
                                       pattern="[0-9]{11}"
                                       required>

                            </div>


                            <div class="col-md-6 mb-3">

                                <label class="form-label">

                                    Sector

                                </label>

                                <input type="text"
                                       class="form-control"
                                       name="sector"
                                       maxlength="100">

                            </div>


                            <div class="col-md-6 mb-3">

                                <label class="form-label">

                                    Teléfono

                                </label>

                                <input type="text"
                                       class="form-control"
                                       name="telefono"
                                       maxlength="20">

                            </div>


                            <div class="col-12 mb-3">

                                <label class="form-label">

                                    Dirección

                                </label>

                                <input type="text"
                                       class="form-control"
                                       name="direccion"
                                       maxlength="200">

                            </div>


                            <div class="col-12 mb-3">

                                <label class="form-label">

                                    Correo electrónico

                                </label>

                                <input type="email"
                                       class="form-control"
                                       name="correo"
                                       maxlength="100">

                            </div>

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

                            Registrar empresa

                        </button>

                    </div>

                </form>

            </div>

        </div>

    </div>


    <!-- ========================================== -->
    <!-- MODAL EDITAR EMPRESA -->
    <!-- ========================================== -->

    <% if (empresaEditar != null) { %>

        <div class="modal fade"
             id="modalEditarEmpresa"
             tabindex="-1"
             aria-hidden="true">

            <div class="modal-dialog modal-lg">

                <div class="modal-content">

                    <div class="modal-header">

                        <h5 class="modal-title">

                            Editar empresa

                        </h5>

                        <button type="button"
                                class="btn-close"
                                data-bs-dismiss="modal">

                        </button>

                    </div>


                    <form action="EmpresaServlet"
                          method="POST">

                        <input type="hidden"
                               name="accion"
                               value="actualizar">

                        <input type="hidden"
                               name="idEmpresa"
                               value="<%= empresaEditar.getIdEmpresa() %>">


                        <div class="modal-body">

                            <div class="row">

                                <div class="col-md-8 mb-3">

                                    <label class="form-label">

                                        Razón social *

                                    </label>

                                    <input type="text"
                                           class="form-control"
                                           name="razonSocial"
                                           value="<%= empresaEditar.getRazonSocial() %>"
                                           maxlength="150"
                                           required>

                                </div>


                                <div class="col-md-4 mb-3">

                                    <label class="form-label">

                                        RUC *

                                    </label>

                                    <input type="text"
                                           class="form-control"
                                           name="ruc"
                                           value="<%= empresaEditar.getRuc() %>"
                                           maxlength="11"
                                           minlength="11"
                                           pattern="[0-9]{11}"
                                           required>

                                </div>


                                <div class="col-md-6 mb-3">

                                    <label class="form-label">

                                        Sector

                                    </label>

                                    <input type="text"
                                           class="form-control"
                                           name="sector"
                                           value="<%= empresaEditar.getSector() != null
                                                   ? empresaEditar.getSector()
                                                   : "" %>"
                                           maxlength="100">

                                </div>


                                <div class="col-md-6 mb-3">

                                    <label class="form-label">

                                        Teléfono

                                    </label>

                                    <input type="text"
                                           class="form-control"
                                           name="telefono"
                                           value="<%= empresaEditar.getTelefono() != null
                                                   ? empresaEditar.getTelefono()
                                                   : "" %>"
                                           maxlength="20">

                                </div>


                                <div class="col-12 mb-3">

                                    <label class="form-label">

                                        Dirección

                                    </label>

                                    <input type="text"
                                           class="form-control"
                                           name="direccion"
                                           value="<%= empresaEditar.getDireccion() != null
                                                   ? empresaEditar.getDireccion()
                                                   : "" %>"
                                           maxlength="200">

                                </div>


                                <div class="col-12 mb-3">

                                    <label class="form-label">

                                        Correo electrónico

                                    </label>

                                    <input type="email"
                                           class="form-control"
                                           name="correo"
                                           value="<%= empresaEditar.getCorreo() != null
                                                   ? empresaEditar.getCorreo()
                                                   : "" %>"
                                           maxlength="100">

                                </div>

                            </div>

                        </div>


                        <div class="modal-footer">

                            <a href="EmpresaServlet"
                               class="btn btn-secondary">

                                Cancelar

                            </a>

                            <button type="submit"
                                    class="btn btn-primary">

                                Guardar cambios

                            </button>

                        </div>

                    </form>

                </div>

            </div>

        </div>

    <% } %>


    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js">
    </script>


    <!-- ========================================== -->
    <!-- ABRIR MODAL DE EDICIÓN -->
    <!-- ========================================== -->

    <% if (empresaEditar != null) { %>

        <script>

            document.addEventListener("DOMContentLoaded", function () {

                var modalEditar =
                    new bootstrap.Modal(
                        document.getElementById("modalEditarEmpresa")
                    );

                modalEditar.show();

            });

        </script>

    <% } %>


    <%@ include file="WEB-INF/ui/footer.jspf" %>
</body>

</html>
