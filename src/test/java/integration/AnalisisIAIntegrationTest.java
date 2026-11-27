/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package integration;

import dao.AnalisisIADAO;
import dao.ResultadoDAO;

import model.AnalisisIA;

import service.AnalisisIAService;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author Humberto
 */
@org.junit.jupiter.api.condition.EnabledIfEnvironmentVariable(
        named = "MYPES_RUN_DB_TESTS", matches = "true")
public class AnalisisIAIntegrationTest {
    @Test
    public void testGenerarGuardarYRecuperarAnalisis() {

        // =====================================
        // ARRANGE
        // =====================================

        int idEvaluacion = 3;

        ResultadoDAO resultadoDAO =
                new ResultadoDAO();

        AnalisisIADAO analisisIADAO =
                new AnalisisIADAO();

        AnalisisIAService analisisIAService =
                new AnalisisIAService();


        // =====================================
        // COMPROBAR PRECONDICIÓN
        // =====================================

        int idResultado =
                resultadoDAO
                        .obtenerIdResultadoPorEvaluacion(
                                idEvaluacion
                        );

        assertTrue(
                idResultado > 0,
                "La evaluación debe tener un resultado registrado."
        );


        // =====================================
        // ACT
        // =====================================

        AnalisisIA analisisGenerado =
                analisisIAService
                        .generarAnalisis(
                                idEvaluacion
                        );


        // =====================================
        // ASSERT 1
        // EL SERVICIO GENERÓ EL ANÁLISIS
        // =====================================

        assertNotNull(
                analisisGenerado,
                "El análisis no debería ser null."
        );

        assertNotNull(
                analisisGenerado.getResumen(),
                "El análisis debe contener un resumen."
        );

        assertNotNull(
                analisisGenerado.getFortalezas(),
                "El análisis debe contener fortalezas."
        );

        assertNotNull(
                analisisGenerado.getDebilidades(),
                "El análisis debe contener debilidades."
        );

        assertNotNull(
                analisisGenerado.getRecomendaciones(),
                "El análisis debe contener recomendaciones."
        );


        // =====================================
        // ASSERT 2
        // COMPROBAR PERSISTENCIA
        // =====================================

        AnalisisIA analisisGuardado =
                analisisIADAO
                        .buscarPorResultado(
                                idResultado
                        );

        assertNotNull(
                analisisGuardado,
                "El análisis debería existir en la base de datos."
        );


        // =====================================
        // ASSERT 3
        // COMPROBAR RELACIÓN
        // =====================================

        assertEquals(
                idResultado,
                analisisGuardado.getIdResultado(),
                "El análisis debe pertenecer al resultado correcto."
        );


        // =====================================
        // ASSERT 4
        // COMPROBAR INFORMACIÓN
        // =====================================

        assertEquals(
                analisisGenerado.getResumen(),
                analisisGuardado.getResumen(),
                "El resumen almacenado debe coincidir con el generado."
        );

        assertEquals(
                analisisGenerado.getRecomendaciones(),
                analisisGuardado.getRecomendaciones(),
                "Las recomendaciones almacenadas deben coincidir."
        );
    }
}
