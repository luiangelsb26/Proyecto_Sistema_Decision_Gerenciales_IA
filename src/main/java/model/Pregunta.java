/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.util.Date;

/**
 *
 * @author Humberto
 */
public class Pregunta {
    private int idPregunta;
    private int idIndicador;
    private String pregunta;
    private double peso;
    private boolean estado;
    private Date fechaRegistro;

    public Pregunta() {
    }

    public Pregunta(int idIndicador,
                    String pregunta,
                    double peso) {

        this.idIndicador = idIndicador;
        this.pregunta = pregunta;
        this.peso = peso;
        this.estado = true;
    }

    public int getIdPregunta() {
        return idPregunta;
    }

    public void setIdPregunta(int idPregunta) {
        this.idPregunta = idPregunta;
    }

    public int getIdIndicador() {
        return idIndicador;
    }

    public void setIdIndicador(int idIndicador) {
        this.idIndicador = idIndicador;
    }

    public String getPregunta() {
        return pregunta;
    }

    public void setPregunta(String pregunta) {
        this.pregunta = pregunta;
    }

    public double getPeso() {
        return peso;
    }

    public void setPeso(double peso) {
        this.peso = peso;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public Date getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(Date fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }
}
