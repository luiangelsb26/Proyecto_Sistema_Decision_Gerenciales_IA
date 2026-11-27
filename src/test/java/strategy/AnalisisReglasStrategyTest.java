/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package strategy;

import model.AnalisisIA;
import model.ResultadoIndicador;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


/**
 *
 * @author Humberto
 */
public class AnalisisReglasStrategyTest {
    @Test
    public void testGenerarAnalisis() {

        // =====================================
        // ARRANGE
        // Preparar los datos
        // =====================================

        AnalisisReglasStrategy estrategia =
                new AnalisisReglasStrategy();

        ResultadoIndicador resultadoGeneral =
                new ResultadoIndicador();

        resultadoGeneral.setPuntaje(3.50);
        resultadoGeneral.setNivel("ALTO");

        List<ResultadoIndicador> indicadores =
                new ArrayList<>();


        // Gestión financiera = Fortaleza

        ResultadoIndicador financiero =
                new ResultadoIndicador();

        financiero.setNombreIndicador(
                "Gestión financiera"
        );

        financiero.setPuntaje(4.20);
        financiero.setNivel("ALTO");

        indicadores.add(financiero);


        // Gestión tecnológica = Debilidad

        ResultadoIndicador tecnologico =
                new ResultadoIndicador();

        tecnologico.setNombreIndicador(
                "Gestión tecnológica"
        );

        tecnologico.setPuntaje(2.20);
        tecnologico.setNivel("BAJO");

        indicadores.add(tecnologico);


        // Gestión comercial = Nivel medio

        ResultadoIndicador comercial =
                new ResultadoIndicador();

        comercial.setNombreIndicador(
                "Gestión comercial"
        );

        comercial.setPuntaje(3.00);
        comercial.setNivel("MEDIO");

        indicadores.add(comercial);


        // =====================================
        // ACT
        // Ejecutar la función
        // =====================================

        AnalisisIA analisis =
                estrategia.generarAnalisis(
                        resultadoGeneral,
                        indicadores
                );


        // =====================================
        // ASSERT
        // Comprobar resultados
        // =====================================

        assertNotNull(analisis);

        assertNotNull(
                analisis.getResumen()
        );

        assertTrue(
                analisis.getFortalezas()
                        .contains("Gestión financiera")
        );

        assertTrue(
                analisis.getDebilidades()
                        .contains("Gestión tecnológica")
        );

        assertTrue(
                analisis.getRecomendaciones()
                        .contains("estrategias comerciales")
        );

        assertTrue(
                analisis.getRecomendaciones()
                        .contains("herramientas digitales")
        );
    }
}
