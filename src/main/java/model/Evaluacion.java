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
public class Evaluacion {
    private int idEvaluacion;
    private int idEmpresa;
    private int idUsuario;
    private Date fechaEvaluacion;
    private String observaciones;
    private String estado;
    private String nombreEmpresa;
    private String nombreUsuario;
    

    public Evaluacion() {
    }

    public Evaluacion(int idEmpresa, int idUsuario,
                      String observaciones) {

        this.idEmpresa = idEmpresa;
        this.idUsuario = idUsuario;
        this.observaciones = observaciones;
        this.estado = "PENDIENTE";
    }

    public Evaluacion(int idEvaluacion,
                      int idEmpresa,
                      int idUsuario,
                      Date fechaEvaluacion,
                      String observaciones,
                      String estado) {

        this.idEvaluacion = idEvaluacion;
        this.idEmpresa = idEmpresa;
        this.idUsuario = idUsuario;
        this.fechaEvaluacion = fechaEvaluacion;
        this.observaciones = observaciones;
        this.estado = estado;
    }

    public int getIdEvaluacion() {
        return idEvaluacion;
    }

    public void setIdEvaluacion(int idEvaluacion) {
        this.idEvaluacion = idEvaluacion;
    }

    public int getIdEmpresa() {
        return idEmpresa;
    }

    public void setIdEmpresa(int idEmpresa) {
        this.idEmpresa = idEmpresa;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public Date getFechaEvaluacion() {
        return fechaEvaluacion;
    }

    public void setFechaEvaluacion(Date fechaEvaluacion) {
        this.fechaEvaluacion = fechaEvaluacion;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
    
    public String getNombreEmpresa() {
        return nombreEmpresa;
    }

    public void setNombreEmpresa(String nombreEmpresa) {
        this.nombreEmpresa = nombreEmpresa;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    @Override
    public String toString() {

        return "Evaluacion{" +
                "idEvaluacion=" + idEvaluacion +
                ", idEmpresa=" + idEmpresa +
                ", idUsuario=" + idUsuario +
                ", fechaEvaluacion=" + fechaEvaluacion +
                ", observaciones='" + observaciones + '\'' +
                ", estado='" + estado + '\'' +
                '}';
    }
}
