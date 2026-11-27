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
public class ResultadoEvaluacion {
    private int idEvaluacion;
    private String razonSocial;
    private Date fechaEvaluacion;
    private double indiceGeneral;
    private String nivel;

    public int getIdEvaluacion() {
        return idEvaluacion;
    }

    public void setIdEvaluacion(int idEvaluacion) {
        this.idEvaluacion = idEvaluacion;
    }

    public String getRazonSocial() {
        return razonSocial;
    }

    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }

    public Date getFechaEvaluacion() {
        return fechaEvaluacion;
    }

    public void setFechaEvaluacion(Date fechaEvaluacion) {
        this.fechaEvaluacion = fechaEvaluacion;
    }

    public double getIndiceGeneral() {
        return indiceGeneral;
    }

    public void setIndiceGeneral(double indiceGeneral) {
        this.indiceGeneral = indiceGeneral;
    }

    public String getNivel() {
        return nivel;
    }

    public void setNivel(String nivel) {
        this.nivel = nivel;
    }
}
