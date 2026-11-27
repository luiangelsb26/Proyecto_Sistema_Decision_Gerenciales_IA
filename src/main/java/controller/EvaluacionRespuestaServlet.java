/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import dao.EvaluacionDAO;
import dao.RespuestaDAO;
import service.ResultadoService;
import model.Respuesta;
import model.Usuario;

import java.io.IOException;
import java.util.ArrayList;
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
    name = "EvaluacionRespuestaServlet",
    urlPatterns = {"/EvaluacionRespuestaServlet"}
)
public class EvaluacionRespuestaServlet extends HttpServlet {
    private RespuestaDAO respuestaDAO;
    private EvaluacionDAO evaluacionDAO;
    private ResultadoService resultadoService;

    @Override
    public void init() throws ServletException {

        respuestaDAO = new RespuestaDAO();
        evaluacionDAO = new EvaluacionDAO();
        resultadoService = new ResultadoService();
    }

    @Override
    protected void doPost(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

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

        Usuario usuario =
                (Usuario) session.getAttribute(
                        "usuarioLogueado"
                );

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
        // CREAR LISTA DE RESPUESTAS
        // ==========================================

        List<Respuesta> respuestas =
                new ArrayList<>();

        // ==========================================
        // LEER LAS 30 RESPUESTAS
        // ==========================================

        for (int idPregunta = 1;
             idPregunta <= 30;
             idPregunta++) {

            String parametro =
                    "pregunta_" + idPregunta;

            String valorTexto =
                    request.getParameter(parametro);

            // ------------------------------
            // Validar respuesta
            // ------------------------------

            if (valorTexto == null
                    || valorTexto.trim().isEmpty()) {

                response.sendRedirect(
                        "EvaluacionCuestionarioServlet"
                        + "?idEvaluacion="
                        + idEvaluacion
                        + "&error=respuestas"
                );

                return;
            }

            int valor;

            try {

                valor =
                        Integer.parseInt(valorTexto);

            } catch (NumberFormatException e) {

                response.sendRedirect(
                        "EvaluacionCuestionarioServlet"
                        + "?idEvaluacion="
                        + idEvaluacion
                        + "&error=respuestas"
                );

                return;
            }

            // ------------------------------
            // Validar rango
            // ------------------------------

            if (valor < 1 || valor > 5) {

                response.sendRedirect(
                        "EvaluacionCuestionarioServlet"
                        + "?idEvaluacion="
                        + idEvaluacion
                        + "&error=respuestas"
                );

                return;
            }

            // ------------------------------
            // Crear respuesta
            // ------------------------------

            Respuesta respuesta =
                    new Respuesta();

            respuesta.setIdEvaluacion(
                    idEvaluacion
            );

            respuesta.setIdPregunta(
                    idPregunta
            );

            respuesta.setValor(
                    valor
            );

            respuesta.setObservacion(
                    null
            );

            respuestas.add(respuesta);
        }

        // ==========================================
        // VERIFICAR CANTIDAD
        // ==========================================

        if (respuestas.size() != 30) {

            response.sendRedirect(
                    "EvaluacionCuestionarioServlet"
                    + "?idEvaluacion="
                    + idEvaluacion
                    + "&error=respuestas"
            );

            return;
        }

        // ==========================================
        // GUARDAR TODAS LAS RESPUESTAS
        // ==========================================

        boolean guardado =
                respuestaDAO.registrarTodas(
                        respuestas
                );

        if (guardado) {

            // ==========================================
            // ACTUALIZAR ESTADO DE LA EVALUACIÓN
            // ==========================================

            boolean estadoActualizado =
                    evaluacionDAO.actualizarEstado(
                            idEvaluacion,
                            "COMPLETADA"
                    );

            if (!estadoActualizado) {

                response.sendRedirect(
                        "EvaluacionCuestionarioServlet"
                        + "?idEvaluacion="
                        + idEvaluacion
                        + "&error=estado"
                );

                return;
            }

            // ==========================================
            // CALCULAR Y GUARDAR RESULTADOS
            // ==========================================

            boolean resultadoGenerado =
                    resultadoService.calcularYGuardar(
                            idEvaluacion
                    );

            if (resultadoGenerado) {

                response.sendRedirect(
                        "EvaluacionServlet"
                        + "?mensaje=respuestas_guardadas"
                );

            } else {

                response.sendRedirect(
                        "EvaluacionServlet"
                        + "?error=resultado"
                );
            }

        } else {

            response.sendRedirect(
                    "EvaluacionCuestionarioServlet"
                    + "?idEvaluacion="
                    + idEvaluacion
                    + "&error=registro"
            );
        }
    }
}
