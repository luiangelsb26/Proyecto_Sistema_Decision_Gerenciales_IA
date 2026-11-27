/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dao.EmpresaDAO;
import dao.EvaluacionDAO;
import dao.ResultadoDAO;
import dao.AnalisisIADAO;

import model.Empresa;
import model.Evaluacion;
import model.ResultadoIndicador;
import model.AnalisisIA;

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
@WebServlet(name = "ReporteDetalleServlet",
            urlPatterns = {"/ReporteDetalleServlet"})
public class ReporteDetalleServlet extends HttpServlet {
    private EmpresaDAO empresaDAO;
    private EvaluacionDAO evaluacionDAO;
    private ResultadoDAO resultadoDAO;
    private AnalisisIADAO analisisIADAO;

    @Override
    public void init() throws ServletException {

        empresaDAO = new EmpresaDAO();
        evaluacionDAO = new EvaluacionDAO();
        resultadoDAO = new ResultadoDAO();
        analisisIADAO = new AnalisisIADAO();
    }

    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        // ==========================================
        // VALIDAR SESIÓN
        // ==========================================

        HttpSession session = request.getSession(false);

        if (session == null
                || session.getAttribute("usuarioLogueado") == null) {

            response.sendRedirect("login.jsp?error=2");
            return;
        }

        // ==========================================
        // VALIDAR ID DE EVALUACIÓN
        // ==========================================

        String idTexto = request.getParameter("idEvaluacion");

        if (idTexto == null || idTexto.trim().isEmpty()) {

            response.sendRedirect("ReporteServlet?error=evaluacion");
            return;
        }

        int idEvaluacion;

        try {
            idEvaluacion = Integer.parseInt(idTexto);
        } catch (NumberFormatException e) {

            response.sendRedirect("ReporteServlet?error=evaluacion");
            return;
        }

        if (idEvaluacion <= 0) {

            response.sendRedirect("ReporteServlet?error=evaluacion");
            return;
        }

        if (!util.AccesoEvaluacion.permitida(idEvaluacion,
                (model.Usuario) session.getAttribute("usuarioLogueado"))) {
            response.sendError(403); return;
        }
        // ==========================================
        // OBTENER EVALUACIÓN
        // ==========================================

        Evaluacion evaluacion =
                evaluacionDAO.buscarPorId(idEvaluacion);

        if (evaluacion == null) {

            response.sendRedirect("ReporteServlet?error=noexiste");
            return;
        }

        // ==========================================
        // OBTENER EMPRESA
        // ==========================================

        Empresa empresa =
                empresaDAO.buscarPorId(
                        evaluacion.getIdEmpresa()
                );

        if (empresa == null) {

            response.sendRedirect("ReporteServlet?error=empresa");
            return;
        }

        // ==========================================
        // OBTENER RESULTADO GENERAL
        // ==========================================

        ResultadoIndicador resultadoGeneral =
                resultadoDAO.obtenerResultadoGeneral(
                        idEvaluacion
                );

        if (resultadoGeneral == null) {

            response.sendRedirect("ReporteServlet?error=resultado");
            return;
        }

        // ==========================================
        // OBTENER RESULTADOS POR INDICADOR
        // ==========================================

        List<ResultadoIndicador> resultadosIndicadores =
                resultadoDAO.listarResultadosIndicadores(
                        idEvaluacion
                );

        // ==========================================
        // OBTENER ID DEL RESULTADO
        // ==========================================

        int idResultado =
                resultadoDAO.obtenerIdResultadoPorEvaluacion(
                        idEvaluacion
                );

        // ==========================================
        // OBTENER ANÁLISIS IA
        // ==========================================

        AnalisisIA analisis = null;

        if (idResultado > 0) {

            analisis =
                    analisisIADAO.buscarPorResultado(
                            idResultado
                    );
        }

        // ==========================================
        // ENVIAR DATOS A JSP
        // ==========================================

        request.setAttribute("empresa", empresa);

        request.setAttribute("evaluacion", evaluacion);

        request.setAttribute(
                "resultadoGeneral",
                resultadoGeneral
        );

        request.setAttribute(
                "resultadosIndicadores",
                resultadosIndicadores
        );

        request.setAttribute("analisis", analisis);

        request.getRequestDispatcher(
                "reporte_detalle.jsp"
        ).forward(request, response);
    }
}
