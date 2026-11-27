/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dao.ResultadoDAO;
import model.ResultadoIndicador;
import model.Usuario;
import model.ResultadoEvaluacion;

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
@WebServlet(name = "ResultadoServlet", urlPatterns = {"/ResultadoServlet"})
public class ResultadoServlet extends HttpServlet {
    
    private ResultadoDAO resultadoDAO;

    @Override
    public void init() throws ServletException {
        resultadoDAO = new ResultadoDAO();
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
                || idEvaluacionTexto.trim().isEmpty()) {

            List<ResultadoEvaluacion> evaluacionesConResultados =
                    resultadoDAO.listarEvaluacionesConResultados();
        util.AccesoEvaluacion.filtrar(evaluacionesConResultados, (model.Usuario) session.getAttribute("usuarioLogueado"));


            request.setAttribute(
                    "evaluacionesConResultados",
                    evaluacionesConResultados
            );

            request.getRequestDispatcher(
                    "resultados_lista.jsp"
            ).forward(request, response);

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
                    "EvaluacionServlet?error=evaluacion"
            );

            return;
        }

        if (idEvaluacion <= 0) {

            response.sendRedirect(
                    "EvaluacionServlet?error=evaluacion"
            );

            return;
        }

        if (!util.AccesoEvaluacion.permitida(idEvaluacion,
                (model.Usuario) session.getAttribute("usuarioLogueado"))) {
            response.sendError(403); return;
        }
        // ==========================================
        // OBTENER RESULTADO GENERAL
        // ==========================================

        ResultadoIndicador resultadoGeneral =
                resultadoDAO.obtenerResultadoGeneral(
                        idEvaluacion
                );

        if (resultadoGeneral == null) {

            response.sendRedirect(
                    "EvaluacionServlet?error=resultado"
            );

            return;
        }

        // ==========================================
        // OBTENER RESULTADOS POR INDICADOR
        // ==========================================

        List<ResultadoIndicador>
                resultadosIndicadores =
                resultadoDAO
                        .listarResultadosIndicadores(
                                idEvaluacion
                        );

        // ==========================================
        // ENVIAR DATOS A JSP
        // ==========================================

        request.setAttribute(
                "idEvaluacion",
                idEvaluacion
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
                "resultados.jsp"
        ).forward(request, response);
    }    
}
