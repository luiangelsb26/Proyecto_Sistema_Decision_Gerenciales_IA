/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author Humberto
 */
public class Respuesta {
    private int idRespuesta;
    private int idEvaluacion;
    private int idPregunta;
    private int valor;
    private String observacion;

    public Respuesta() {
    }

    public Respuesta(int idEvaluacion,
                     int idPregunta,
                     int valor,
                     String observacion) {

        this.idEvaluacion = idEvaluacion;
        this.idPregunta = idPregunta;
        this.valor = valor;
        this.observacion = observacion;
    }

    public int getIdRespuesta() {
        return idRespuesta;
    }

    public void setIdRespuesta(int idRespuesta) {
        this.idRespuesta = idRespuesta;
    }

    public int getIdEvaluacion() {
        return idEvaluacion;
    }

    public void setIdEvaluacion(int idEvaluacion) {
        this.idEvaluacion = idEvaluacion;
    }

    public int getIdPregunta() {
        return idPregunta;
    }

    public void setIdPregunta(int idPregunta) {
        this.idPregunta = idPregunta;
    }

    public int getValor() {
        return valor;
    }

    public void setValor(int valor) {
        this.valor = valor;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }
}
