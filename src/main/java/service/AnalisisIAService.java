/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

import dao.AnalisisIADAO;
import dao.ResultadoDAO;

import model.AnalisisIA;
import model.ResultadoIndicador;

import strategy.AnalisisReglasStrategy;
import strategy.AnalisisStrategy;

import java.util.List;

/**
 *
 * @author Humberto
 */
public class AnalisisIAService {
     private final ResultadoDAO resultadoDAO;
    private final AnalisisIADAO analisisIADAO;
    private AnalisisStrategy estrategia;

    public AnalisisIAService() {

        resultadoDAO =
                new ResultadoDAO();

        analisisIADAO =
                new AnalisisIADAO();

        estrategia =
                new AnalisisReglasStrategy();
    }

    public AnalisisIA generarAnalisis(
            int idEvaluacion) {

        // =====================================
        // OBTENER RESULTADO GENERAL
        // =====================================

        ResultadoIndicador resultadoGeneral =
                resultadoDAO
                        .obtenerResultadoGeneral(
                                idEvaluacion
                        );

        if (resultadoGeneral == null) {

            System.err.println(
                    "No existe resultado general "
                    + "para la evaluación."
            );

            return null;
        }

        // =====================================
        // OBTENER RESULTADOS POR INDICADOR
        // =====================================

        List<ResultadoIndicador>
                resultadosIndicadores =
                resultadoDAO
                        .listarResultadosIndicadores(
                                idEvaluacion
                        );

        if (resultadosIndicadores == null
                || resultadosIndicadores.isEmpty()) {

            System.err.println(
                    "No existen resultados por indicador."
            );

            return null;
        }

        // =====================================
        // OBTENER ID DEL RESULTADO
        // =====================================

        int idResultado =
                resultadoDAO
                        .obtenerIdResultadoPorEvaluacion(
                                idEvaluacion
                        );

        if (idResultado <= 0) {

            System.err.println(
                    "No se encontró el resultado "
                    + "de la evaluación."
            );

            return null;
        }

        // =====================================
        // COMPROBAR SI YA EXISTE ANÁLISIS
        // =====================================

        AnalisisIA analisisExistente =
                analisisIADAO
                        .buscarPorResultado(
                                idResultado
                        );

        if (analisisExistente != null) {

            return analisisExistente;
        }

        // =====================================
        // APLICAR STRATEGY
        // =====================================

        AnalisisIA analisis =
                estrategia.generarAnalisis(
                        resultadoGeneral,
                        resultadosIndicadores
                );

        if (analisis == null) {
            return null;
        }

        analisis.setIdResultado(
                idResultado
        );

        // =====================================
        // GUARDAR ANÁLISIS
        // =====================================

        boolean guardado =
                analisisIADAO.registrar(
                        analisis
                );

        if (!guardado) {

            System.err.println(
                    "No se pudo almacenar "
                    + "el análisis."
            );

            return null;
        }

        /*
         * Lo recuperamos para obtener también
         * id_analisis y fecha_analisis.
         */
        return analisisIADAO
                .buscarPorResultado(
                        idResultado
                );
    }

    public void setEstrategia(
            AnalisisStrategy estrategia) {

        if (estrategia != null) {
            this.estrategia = estrategia;
        }
    }
}
