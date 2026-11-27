/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import dao.EmpresaDAO;
import model.Empresa;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 *
 * @author Humberto
 */

@WebServlet(name = "EmpresaServlet", urlPatterns = {"/EmpresaServlet"})
public class EmpresaServlet extends HttpServlet {
    private EmpresaDAO empresaDAO;

    @Override
    public void init() throws ServletException {
        empresaDAO = new EmpresaDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        if (session == null
                || session.getAttribute("usuarioLogueado") == null) {

            response.sendRedirect("login.jsp?error=2");
            return;
        }

        String accion = request.getParameter("accion");

        // ==========================================
        // MOSTRAR FORMULARIO DE EDICIÓN
        // ==========================================

        if ("editar".equals(accion)) {

            String idTexto =
                    request.getParameter("id");

            try {

                int idEmpresa =
                        Integer.parseInt(idTexto);

                Empresa empresa =
                        empresaDAO.buscarPorId(idEmpresa);

                if (empresa != null) {

                    request.setAttribute(
                            "empresaEditar",
                            empresa
                    );
                }

            } catch (NumberFormatException e) {

                response.sendRedirect(
                        "EmpresaServlet?error=id"
                );

                return;
            }
        }

        // ==========================================
        // LISTAR EMPRESAS
        // ==========================================

        List<Empresa> empresas =
                empresaDAO.listar();

        request.setAttribute(
                "empresas",
                empresas
        );

        request.getRequestDispatcher(
                "empresas.jsp"
        ).forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request,
                           HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        HttpSession session = request.getSession(false);

        if (session == null
                || session.getAttribute("usuarioLogueado") == null) {

            response.sendRedirect("login.jsp?error=2");
            return;
        }

        String accion =
                request.getParameter("accion");

        // ==========================================
        // REGISTRAR
        // ==========================================

        if ("registrar".equals(accion)) {

            Empresa empresa = obtenerEmpresaDesdeRequest(request);

            if (!datosValidos(empresa)) {

                response.sendRedirect(
                        "EmpresaServlet?error=datos"
                );

                return;
            }

            boolean registrado =
                    empresaDAO.registrar(empresa);

            if (registrado) {

                response.sendRedirect(
                        "EmpresaServlet?mensaje=registrado"
                );

            } else {

                response.sendRedirect(
                        "EmpresaServlet?error=registro"
                );
            }

            return;
        }

        // ==========================================
        // ACTUALIZAR
        // ==========================================

        if ("actualizar".equals(accion)) {

            try {

                int idEmpresa =
                        Integer.parseInt(
                                request.getParameter(
                                        "idEmpresa"
                                )
                        );

                Empresa empresa =
                        obtenerEmpresaDesdeRequest(request);

                empresa.setIdEmpresa(idEmpresa);

                if (!datosValidos(empresa)) {

                    response.sendRedirect(
                            "EmpresaServlet?error=datos"
                    );

                    return;
                }

                boolean actualizado =
                        empresaDAO.actualizar(empresa);

                if (actualizado) {

                    response.sendRedirect(
                            "EmpresaServlet?mensaje=actualizado"
                    );

                } else {

                    response.sendRedirect(
                            "EmpresaServlet?error=actualizacion"
                    );
                }

            } catch (NumberFormatException e) {

                response.sendRedirect(
                        "EmpresaServlet?error=id"
                );
            }

            return;
        }

        // ==========================================
        // DESACTIVAR
        // ==========================================

        if ("desactivar".equals(accion)) {

            try {

                int idEmpresa =
                        Integer.parseInt(
                                request.getParameter(
                                        "idEmpresa"
                                )
                        );

                boolean desactivado =
                        empresaDAO.desactivar(idEmpresa);

                if (desactivado) {

                    response.sendRedirect(
                            "EmpresaServlet?mensaje=desactivado"
                    );

                } else {

                    response.sendRedirect(
                            "EmpresaServlet?error=desactivacion"
                    );
                }

            } catch (NumberFormatException e) {

                response.sendRedirect(
                        "EmpresaServlet?error=id"
                );
            }

            return;
        }

        response.sendRedirect("EmpresaServlet");
    }

    // ==========================================
    // OBTENER EMPRESA DEL FORMULARIO
    // ==========================================

    private Empresa obtenerEmpresaDesdeRequest(
            HttpServletRequest request) {

        String razonSocial =
                request.getParameter("razonSocial");

        String ruc =
                request.getParameter("ruc");

        String sector =
                request.getParameter("sector");

        String direccion =
                request.getParameter("direccion");

        String telefono =
                request.getParameter("telefono");

        String correo =
                request.getParameter("correo");

        return new Empresa(
                razonSocial,
                ruc,
                sector,
                direccion,
                telefono,
                correo
        );
    }

    // ==========================================
    // VALIDACIÓN BÁSICA
    // ==========================================

    private boolean datosValidos(Empresa empresa) {

        if (empresa == null) {
            return false;
        }

        if (empresa.getRazonSocial() == null
                || empresa.getRazonSocial().trim().isEmpty()) {

            return false;
        }

        if (empresa.getRuc() == null
                || empresa.getRuc().trim().isEmpty()) {

            return false;
        }

        return true;
    }
}
