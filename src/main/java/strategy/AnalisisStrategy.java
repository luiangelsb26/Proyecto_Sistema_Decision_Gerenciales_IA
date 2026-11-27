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
public interface AnalisisStrategy {
    AnalisisIA generarAnalisis(
            ResultadoIndicador resultadoGeneral,
            List<ResultadoIndicador> resultadosIndicadores
    );
}
