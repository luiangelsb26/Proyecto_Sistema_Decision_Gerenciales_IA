/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dao.ReporteDAO;
import dao.EmpresaDAO;

import model.Empresa;
import model.ResultadoEvaluacion;

import java.io.IOException;
import java.io.PrintWriter;
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
@WebServlet(name = "ReporteServlet",
        urlPatterns = {"/ReporteServlet"})
public class ReporteServlet extends HttpServlet {
    private ReporteDAO reporteDAO;
    private EmpresaDAO empresaDAO;

    @Override
    public void init() throws ServletException {

        reporteDAO = new ReporteDAO();
        empresaDAO = new EmpresaDAO();
    }

    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        // ==========================================
        // VALIDAR SESIÓN
        // ==========================================

        HttpSession session =
                request.getSession(false);

        if (session == null
                || session.getAttribute(
                        "usuarioLogueado"
                ) == null) {

            response.sendRedirect(
                    "login.jsp?error=2"
            );

            return;
        }

        // ==========================================
        // OBTENER FILTROS
        // ==========================================

        String idEmpresaTexto =
                request.getParameter("idEmpresa");

        String fechaInicio =
                request.getParameter("fechaInicio");

        String fechaFin =
                request.getParameter("fechaFin");

        int idEmpresa = 0;

        if (idEmpresaTexto != null
                && !idEmpresaTexto.trim().isEmpty()) {

            try {

                idEmpresa = Integer.parseInt(
                        idEmpresaTexto
                );

                if (idEmpresa < 0) {
                    idEmpresa = 0;
                }

            } catch (NumberFormatException e) {

                idEmpresa = 0;
            }
        }

        // ==========================================
        // VALIDAR RANGO DE FECHAS
        // ==========================================

        if (fechaInicio != null
                && fechaFin != null
                && !fechaInicio.isEmpty()
                && !fechaFin.isEmpty()
                && fechaInicio.compareTo(fechaFin) > 0) {

            request.setAttribute(
                    "errorFecha",
                    "La fecha de inicio no puede "
                    + "ser posterior a la fecha de fin."
            );

            fechaInicio = "";
            fechaFin = "";
        }

        // ==========================================
        // LISTAR EMPRESAS
        // ==========================================

        List<Empresa> empresas =
                empresaDAO.listar();

        // ==========================================
        // CONSULTAR REPORTES
        // ==========================================

        List<ResultadoEvaluacion> reportes =
                reporteDAO.listarReportes(
                        idEmpresa,
                        fechaInicio,
                        fechaFin
                );

        // ==========================================
        // ENVIAR DATOS A JSP
        // ==========================================

        request.setAttribute(
                "empresas",
                empresas
        );
        util.AccesoEvaluacion.filtrar(reportes, (model.Usuario) session.getAttribute("usuarioLogueado"));


        request.setAttribute(
                "reportes",
                reportes
        );

        request.setAttribute(
                "idEmpresaSeleccionada",
                idEmpresa
        );

        request.setAttribute(
                "fechaInicio",
                fechaInicio
        );

        request.setAttribute(
                "fechaFin",
                fechaFin
        );

        request.getRequestDispatcher(
                "reportes.jsp"
        ).forward(request, response);
    }
}
