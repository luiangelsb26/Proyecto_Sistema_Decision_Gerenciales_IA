/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author Humberto
 */
public class ResultadoIndicador {
    private int idPregunta;
    public int getIdPregunta() { return idPregunta; }
    public void setIdPregunta(int idPregunta) { this.idPregunta = idPregunta; }
    private int idIndicador;
    private String nombreIndicador;
    private double puntaje;
    private double peso;
    private double pesoPregunta;
    private String nivel;

    public ResultadoIndicador() {
    }

    public int getIdIndicador() {
        return idIndicador;
    }

    public void setIdIndicador(int idIndicador) {
        this.idIndicador = idIndicador;
    }

    public String getNombreIndicador() {
        return nombreIndicador;
    }

    public void setNombreIndicador(String nombreIndicador) {
        this.nombreIndicador = nombreIndicador;
    }

    public double getPuntaje() {
        return puntaje;
    }

    public void setPuntaje(double puntaje) {
        this.puntaje = puntaje;
    }

    public double getPeso() {
        return peso;
    }

    public void setPeso(double peso) {
        this.peso = peso;
    }
    
    public double getPesoPregunta() {
        return pesoPregunta;
    }

    public void setPesoPregunta(double pesoPregunta) {
        this.pesoPregunta = pesoPregunta;
    }

    public String getNivel() {
        return nivel;
    }

    public void setNivel(String nivel) {
        this.nivel = nivel;
    }
}
