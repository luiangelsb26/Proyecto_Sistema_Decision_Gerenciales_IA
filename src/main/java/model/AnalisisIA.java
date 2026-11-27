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
public class AnalisisIA {
    private int idAnalisis;
    private int idResultado;

    private String resumen;
    private String fortalezas;
    private String debilidades;
    private String recomendaciones;

    private Date fechaAnalisis;

    public AnalisisIA() {
    }

    public int getIdAnalisis() {
        return idAnalisis;
    }

    public void setIdAnalisis(int idAnalisis) {
        this.idAnalisis = idAnalisis;
    }

    public int getIdResultado() {
        return idResultado;
    }

    public void setIdResultado(int idResultado) {
        this.idResultado = idResultado;
    }

    public String getResumen() {
        return resumen;
    }

    public void setResumen(String resumen) {
        this.resumen = resumen;
    }

    public String getFortalezas() {
        return fortalezas;
    }

    public void setFortalezas(String fortalezas) {
        this.fortalezas = fortalezas;
    }

    public String getDebilidades() {
        return debilidades;
    }

    public void setDebilidades(String debilidades) {
        this.debilidades = debilidades;
    }

    public String getRecomendaciones() {
        return recomendaciones;
    }

    public void setRecomendaciones(String recomendaciones) {
        this.recomendaciones = recomendaciones;
    }

    public Date getFechaAnalisis() {
        return fechaAnalisis;
    }

    public void setFechaAnalisis(Date fechaAnalisis) {
        this.fechaAnalisis = fechaAnalisis;
    }
}
