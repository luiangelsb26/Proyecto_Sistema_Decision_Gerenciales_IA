/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import dao.EmpresaDAO;
import dao.EvaluacionDAO;
import model.Empresa;
import model.Evaluacion;
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

@WebServlet(name = "EvaluacionServlet", urlPatterns = {"/EvaluacionServlet"})
public class EvaluacionServlet extends HttpServlet {
    
    private EvaluacionDAO evaluacionDAO;
    private EmpresaDAO empresaDAO;

    @Override
    public void init() throws ServletException {
        evaluacionDAO = new EvaluacionDAO();
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

        // Obtener evaluaciones registradas
        List<Evaluacion> evaluaciones =
                evaluacionDAO.listar();

        // Obtener empresas activas
        List<Empresa> empresas =
                empresaDAO.listar();

        // Enviar información al JSP
        util.AccesoEvaluacion.filtrar(evaluaciones, (model.Usuario) session.getAttribute("usuarioLogueado"));

        request.setAttribute("evaluaciones", evaluaciones);
        request.setAttribute("empresas", empresas);

        request.getRequestDispatcher(
                "evaluaciones.jsp"
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

        Usuario usuario =
                (Usuario) session.getAttribute("usuarioLogueado");

        String accion =
                request.getParameter("accion");

        // ==========================================
        // REGISTRAR EVALUACIÓN
        // ==========================================

        if ("registrar".equals(accion)) {

            String idEmpresaTexto =
                    request.getParameter("idEmpresa");

            String observaciones =
                    request.getParameter("observaciones");

            // Validar empresa
            if (idEmpresaTexto == null
                    || idEmpresaTexto.trim().isEmpty()) {

                response.sendRedirect(
                        "EvaluacionServlet?error=empresa"
                );
                return;
            }

            try {

                int idEmpresa =
                        Integer.parseInt(idEmpresaTexto);

                // Crear evaluación
                Evaluacion evaluacion =
                        new Evaluacion();

                evaluacion.setIdEmpresa(idEmpresa);
                evaluacion.setIdUsuario(
                        usuario.getIdUsuario()
                );
                evaluacion.setObservaciones(
                        observaciones
                );
                evaluacion.setEstado("PENDIENTE");

                // Registrar
                boolean registrado =
                        evaluacionDAO.registrar(
                                evaluacion
                        );

                if (registrado) {

                    response.sendRedirect(
                            "EvaluacionServlet?mensaje=registrado"
                    );

                } else {

                    response.sendRedirect(
                            "EvaluacionServlet?error=registro"
                    );
                }

            } catch (NumberFormatException e) {

                response.sendRedirect(
                        "EvaluacionServlet?error=empresa"
                );
            }

            return;
        }

        response.sendRedirect("EvaluacionServlet");
    }
}
