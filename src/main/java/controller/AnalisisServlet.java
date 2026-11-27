/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import model.AnalisisIA;
import model.ResultadoIndicador;

import dao.ResultadoDAO;
import dao.AnalisisIADAO;
import model.AnalisisEvaluacion;

import service.AnalisisIAService;

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
@WebServlet(name = "AnalisisServlet",
    urlPatterns = {"/AnalisisServlet"})
public class AnalisisServlet extends HttpServlet {
    private AnalisisIAService analisisIAService;
    private ResultadoDAO resultadoDAO;
    private AnalisisIADAO analisisIADAO;

    @Override
    public void init()
            throws ServletException {

        analisisIAService =
                new AnalisisIAService();

        resultadoDAO =
                new ResultadoDAO();
        
        analisisIADAO =
                new AnalisisIADAO();
    }

    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

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

        String idEvaluacionTexto =
                request.getParameter(
                        "idEvaluacion"
                );

        if (idEvaluacionTexto == null
                || idEvaluacionTexto
                        .trim()
                        .isEmpty()) {

            List<AnalisisEvaluacion>
                    evaluacionesDisponibles =
                    analisisIADAO
                            .listarEvaluacionesDisponibles();
        util.AccesoEvaluacion.filtrar(evaluacionesDisponibles, (model.Usuario) session.getAttribute("usuarioLogueado"));


            request.setAttribute(
                    "evaluacionesDisponibles",
                    evaluacionesDisponibles
            );

            request.getRequestDispatcher(
                    "analisis_lista.jsp"
            ).forward(
                    request,
                    response
            );

            return;
        }

        int idEvaluacion;

        try {

            idEvaluacion =
                    Integer.parseInt(
                            idEvaluacionTexto
                    );

        } catch (NumberFormatException e) {

            response.sendRedirect(
                    "ResultadoServlet"
            );

            return;
        }

        if (idEvaluacion <= 0) {

            response.sendRedirect(
                    "ResultadoServlet"
            );

            return;
        }

        if (!util.AccesoEvaluacion.permitida(idEvaluacion,
                (model.Usuario) session.getAttribute("usuarioLogueado"))) {
            response.sendError(403); return;
        }
        // =====================================
        // GENERAR O RECUPERAR ANÁLISIS
        // =====================================

        AnalisisIA analisis =
                analisisIAService
                        .generarAnalisis(
                                idEvaluacion
                        );

        if (analisis == null) {

            response.sendRedirect(
                    "ResultadoServlet"
                    + "?error=analisis"
            );

            return;
        }

        // =====================================
        // OBTENER RESULTADOS PARA MOSTRAR
        // =====================================

        ResultadoIndicador resultadoGeneral =
                resultadoDAO
                        .obtenerResultadoGeneral(
                                idEvaluacion
                        );

        List<ResultadoIndicador>
                resultadosIndicadores =
                resultadoDAO
                        .listarResultadosIndicadores(
                                idEvaluacion
                        );

        // =====================================
        // ENVIAR AL JSP
        // =====================================

        request.setAttribute(
                "idEvaluacion",
                idEvaluacion
        );

        request.setAttribute(
                "analisis",
                analisis
        );

        request.setAttribute(
                "resultadoGeneral",
                resultadoGeneral
        );

        request.setAttribute(
                "resultadosIndicadores",
                resultadosIndicadores
        );

        request.getRequestDispatcher(
                "analisis.jsp"
        ).forward(
                request,
                response
        );
    }
}
