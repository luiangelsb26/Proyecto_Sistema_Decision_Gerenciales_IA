/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import dao.IndicadorDAO;
import dao.PreguntaDAO;
import model.Indicador;
import model.Pregunta;
import model.Usuario;

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

@WebServlet(
    name = "EvaluacionCuestionarioServlet",
    urlPatterns = {"/EvaluacionCuestionarioServlet"}
)
public class EvaluacionCuestionarioServlet extends HttpServlet {
    private IndicadorDAO indicadorDAO;
    private PreguntaDAO preguntaDAO;

    @Override
    public void init() throws ServletException {

        indicadorDAO = new IndicadorDAO();
        preguntaDAO = new PreguntaDAO();
    }

    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        // ==========================================
        // VERIFICAR SESIÓN
        // ==========================================

        HttpSession session =
                request.getSession(false);

        if (session == null
                || session.getAttribute("usuarioLogueado") == null) {

            response.sendRedirect("login.jsp?error=2");
            return;
        }

        // ==========================================
        // OBTENER ID DE EVALUACIÓN
        // ==========================================

        String idEvaluacionTexto =
                request.getParameter("idEvaluacion");

        if (idEvaluacionTexto == null
                || idEvaluacionTexto.trim().isEmpty()) {

            response.sendRedirect(
                    "EvaluacionServlet?error=evaluacion"
            );
            return;
        }

        int idEvaluacion;

        try {

            idEvaluacion =
                    Integer.parseInt(idEvaluacionTexto);

        } catch (NumberFormatException e) {

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
        // OBTENER INDICADORES
        // ==========================================

        List<Indicador> indicadores =
                indicadorDAO.listarActivos();

        // ==========================================
        // OBTENER PREGUNTAS
        // ==========================================

        List<Pregunta> preguntas =
                preguntaDAO.listarTodasActivas();

        System.out.println("=================================");
        System.out.println("TOTAL DE PREGUNTAS: "
                + preguntas.size());

        for (Pregunta pregunta : preguntas) {

            System.out.println(
                "Pregunta ID: "
                + pregunta.getIdPregunta()
                + " | Indicador: "
                + pregunta.getIdIndicador()
                + " | Texto: "
                + pregunta.getPregunta()
            );
        }

        System.out.println("=================================");

        // ==========================================
        // ENVIAR DATOS AL JSP
        // ==========================================

        request.setAttribute(
                "idEvaluacion",
                idEvaluacion
        );

        request.setAttribute(
                "indicadores",
                indicadores
        );

        request.setAttribute(
                "preguntas",
                preguntas
        );

        // ==========================================
        // MOSTRAR CUESTIONARIO
        // ==========================================

        request.getRequestDispatcher(
                "cuestionario.jsp"
        ).forward(request, response);
    }
}
