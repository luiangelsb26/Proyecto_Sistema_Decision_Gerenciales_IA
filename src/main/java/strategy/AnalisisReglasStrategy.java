/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package strategy;

import java.util.List;

import model.AnalisisIA;
import model.ResultadoIndicador;

/**
 *
 * @author Humberto
 */
public class AnalisisReglasStrategy
        implements AnalisisStrategy {

    @Override
    public AnalisisIA generarAnalisis(
            ResultadoIndicador resultadoGeneral,
            List<ResultadoIndicador> resultadosIndicadores) {

        AnalisisIA analisis = new AnalisisIA();

        if (resultadoGeneral == null
                || resultadosIndicadores == null
                || resultadosIndicadores.isEmpty()) {

            analisis.setResumen(
                    "No existe información suficiente "
                    + "para realizar el análisis."
            );

            analisis.setFortalezas(
                    "No identificadas."
            );

            analisis.setDebilidades(
                    "No identificadas."
            );

            analisis.setRecomendaciones(
                    "Complete la evaluación antes de "
                    + "generar el análisis."
            );

            return analisis;
        }

        validar(resultadoGeneral);
        for (ResultadoIndicador indicador : resultadosIndicadores) validar(indicador);
        String resumen =
                generarResumen(resultadoGeneral);

        String fortalezas =
                identificarFortalezas(
                        resultadosIndicadores
                );

        String debilidades =
                identificarDebilidades(
                        resultadosIndicadores
                );

        String recomendaciones =
                generarRecomendaciones(
                        resultadosIndicadores
                );

        analisis.setResumen(resumen);
        analisis.setFortalezas(fortalezas);
        analisis.setDebilidades(debilidades);
        analisis.setRecomendaciones(recomendaciones);

        return analisis;
    }

    private String generarResumen(
            ResultadoIndicador resultadoGeneral) {

        double indice =
                resultadoGeneral.getPuntaje();

        String nivel =
                resultadoGeneral.getNivel();

        return "La empresa obtuvo un índice general de "
                + indice
                + " sobre 5.00, correspondiente al nivel "
                + nivel
                + ". El resultado permite identificar "
                + "fortalezas y áreas que requieren "
                + "acciones de mejora para apoyar la "
                + "toma de decisiones gerenciales.";
    }

    private String identificarFortalezas(
            List<ResultadoIndicador> resultados) {

        StringBuilder fortalezas =
                new StringBuilder();

        for (ResultadoIndicador resultado
                : resultados) {

            if (resultado.getPuntaje() >= 3.41) {

                fortalezas.append("- ")
                        .append(
                                resultado
                                        .getNombreIndicador()
                        )
                        .append(" (")
                        .append(
                                resultado.getPuntaje()
                        )
                        .append(")")
                        .append("\n");
            }
        }

        if (fortalezas.length() == 0) {

            return "No se identificaron indicadores "
                    + "con nivel alto o muy alto.";
        }

        return fortalezas.toString();
    }

    private String identificarDebilidades(
            List<ResultadoIndicador> resultados) {

        StringBuilder debilidades =
                new StringBuilder();

        for (ResultadoIndicador resultado
                : resultados) {

            if (resultado.getPuntaje() <= 2.60) {

                debilidades.append("- ")
                        .append(
                                resultado
                                        .getNombreIndicador()
                        )
                        .append(" (")
                        .append(
                                resultado.getPuntaje()
                        )
                        .append(")")
                        .append("\n");
            }
        }

        if (debilidades.length() == 0) {

            return "No se identificaron indicadores "
                    + "en niveles bajo o muy bajo.";
        }

        return debilidades.toString();
    }

    private String generarRecomendaciones(
            List<ResultadoIndicador> resultados) {

        StringBuilder recomendaciones =
                new StringBuilder();

        for (ResultadoIndicador resultado
                : resultados) {

            if (resultado.getPuntaje() <= 3.40) {

                recomendaciones
                        .append("- ")
                        .append(
                                obtenerRecomendacion(
                                        resultado
                                                .getNombreIndicador()
                                )
                        )
                        .append("\n");
            }
        }

        if (recomendaciones.length() == 0) {

            return "Mantener las prácticas actuales "
                    + "y realizar evaluaciones periódicas "
                    + "para monitorear el desempeño.";
        }

        return recomendaciones.toString();
    }

    private String obtenerRecomendacion(
            String indicador) {

        if (indicador == null) {

            return "Revisar el indicador y establecer "
                    + "acciones de mejora.";
        }

        switch (indicador.toLowerCase(java.util.Locale.ROOT)) {

            case "gestión financiera":
                return "Fortalecer el control financiero, "
                        + "el seguimiento de ingresos y gastos "
                        + "y la planificación presupuestaria.";

            case "gestión operativa":
                return "Revisar los procesos operativos, "
                        + "identificar actividades ineficientes "
                        + "y establecer mecanismos de control.";

            case "gestión comercial":
                return "Fortalecer las estrategias comerciales, "
                        + "el conocimiento de los clientes y "
                        + "el seguimiento de las ventas.";

            case "gestión tecnológica":
                return "Evaluar la incorporación de herramientas "
                        + "digitales que permitan automatizar "
                        + "procesos y mejorar la gestión de "
                        + "información.";

            case "gestión del talento humano":
                return "Fortalecer la capacitación del personal, "
                        + "la definición de responsabilidades y "
                        + "la evaluación del desempeño.";

            case "planificación estratégica":
                return "Definir objetivos empresariales medibles, "
                        + "indicadores de seguimiento y acciones "
                        + "alineadas con las prioridades del negocio.";

            default:
                return "Analizar las causas del desempeño del "
                        + "indicador y establecer acciones de "
                        + "mejora específicas.";
        }
    }
    private void validar(ResultadoIndicador r) {
        if (r == null || !Double.isFinite(r.getPuntaje())
                || r.getPuntaje() < 1 || r.getPuntaje() > 5)
            throw new IllegalArgumentException("Puntaje fuera de la escala de 1 a 5");
    }
}