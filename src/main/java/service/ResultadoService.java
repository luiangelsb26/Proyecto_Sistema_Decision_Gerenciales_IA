/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

import dao.RespuestaDAO;
import dao.ResultadoDAO;
import model.ResultadoIndicador;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Humberto
 */
public class ResultadoService {
    private final RespuestaDAO respuestaDAO;
    private final ResultadoDAO resultadoDAO;

    public ResultadoService() {
        respuestaDAO = new RespuestaDAO();
        resultadoDAO = new ResultadoDAO();
    }

    // ==========================================
    // CALCULAR RESULTADOS
    // ==========================================

    public boolean calcularYGuardar(int idEvaluacion) {

        List<ResultadoIndicador> datos =
                respuestaDAO.obtenerDatosParaCalculo(
                        idEvaluacion
                );

        if (datos == null || datos.isEmpty()) {

            System.err.println(
                    "No existen respuestas para la evaluación."
            );

            return false;
        }

        // ==========================================
        // AGRUPAR POR INDICADOR
        // ==========================================

        if (datos.size() != 30) return false;
        java.util.Set<Integer> ids = new java.util.HashSet<>();
        int[] cantidades = new int[7];
        for (ResultadoIndicador d : datos) {
            if (d == null || d.getIdPregunta() < 1 || d.getIdPregunta() > 30
                    || !ids.add(d.getIdPregunta()) || d.getIdIndicador() < 1
                    || d.getIdIndicador() > 6 || !Double.isFinite(d.getPuntaje())
                    || d.getPuntaje() < 1 || d.getPuntaje() > 5
                    || !Double.isFinite(d.getPeso()) || d.getPeso() <= 0
                    || !Double.isFinite(d.getPesoPregunta()) || d.getPesoPregunta() <= 0) return false;
            cantidades[d.getIdIndicador()]++;
        }
        for (int i = 1; i <= 6; i++) if (cantidades[i] != 5) return false;
        Map<Integer, List<ResultadoIndicador>> grupos =
                new LinkedHashMap<>();

        for (ResultadoIndicador dato : datos) {

            grupos.computeIfAbsent(
                    dato.getIdIndicador(),
                    k -> new ArrayList<>()
            ).add(dato);
        }

        // ==========================================
        // CALCULAR CADA INDICADOR
        // ==========================================

        List<ResultadoIndicador> resultados =
                new ArrayList<>();

        double sumaPonderada = 0.0;
        double sumaPesos = 0.0;

        for (Map.Entry<Integer,
                List<ResultadoIndicador>> entry
                : grupos.entrySet()) {

            List<ResultadoIndicador> preguntas =
                    entry.getValue();

            ResultadoIndicador primero =
                    preguntas.get(0);

            double sumaValores = 0.0;
            double sumaPesosPreguntas = 0.0;

            for (ResultadoIndicador pregunta : preguntas) {

                double pesoPregunta =
                        pregunta.getPesoPregunta();

                sumaValores +=
                        pregunta.getPuntaje()
                        * pesoPregunta;

                sumaPesosPreguntas +=
                        pesoPregunta;
            }

            double puntajeIndicador =
                    sumaValores
                    / sumaPesosPreguntas;

            double pesoIndicador =
                    primero.getPeso();

            String nivel =
                    determinarNivel(redondear(puntajeIndicador));

            ResultadoIndicador resultado =
                    new ResultadoIndicador();

            resultado.setIdIndicador(
                    primero.getIdIndicador()
            );

            resultado.setNombreIndicador(
                    primero.getNombreIndicador()
            );

            resultado.setPuntaje(
                    redondear(puntajeIndicador)
            );

            resultado.setPeso(
                    pesoIndicador
            );

            resultado.setNivel(nivel);

            resultados.add(resultado);

            // ======================================
            // ACUMULAR PARA ÍNDICE GENERAL
            // ======================================

            sumaPonderada +=
                    puntajeIndicador
                    * pesoIndicador;

            sumaPesos += pesoIndicador;
        }

        // ==========================================
        // CALCULAR ÍNDICE GENERAL
        // ==========================================

        if (sumaPesos == 0) {
            return false;
        }

        double indiceGeneral =
                sumaPonderada / sumaPesos;

        indiceGeneral =
                redondear(indiceGeneral);

        String nivelGeneral =
                determinarNivel(indiceGeneral);

        // ==========================================
        // GUARDAR RESULTADO GENERAL
        // ==========================================

        return resultadoDAO.guardarCompleto(idEvaluacion, indiceGeneral, nivelGeneral, resultados);
    }

    // ==========================================
    // DETERMINAR NIVEL
    // ==========================================

    private String determinarNivel(double puntaje) {

        if (puntaje <= 1.80) {
            return "MUY BAJO";
        }

        if (puntaje <= 2.60) {
            return "BAJO";
        }

        if (puntaje <= 3.40) {
            return "MEDIO";
        }

        if (puntaje <= 4.20) {
            return "ALTO";
        }

        return "MUY ALTO";
    }

    // ==========================================
    // REDONDEAR
    // ==========================================

    private double redondear(double valor) {

        return Math.round(valor * 100.0) / 100.0;
    }
}
