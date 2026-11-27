/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author Humberto
 */
public class Empresa {
    private int idEmpresa;
    private String razonSocial;
    private String ruc;
    private String sector;
    private String direccion;
    private String telefono;
    private String correo;
    private boolean estado;

    public Empresa() {
    }

    public Empresa(String razonSocial,
                   String ruc,
                   String sector,
                   String direccion,
                   String telefono,
                   String correo) {

        this.razonSocial = razonSocial;
        this.ruc = ruc;
        this.sector = sector;
        this.direccion = direccion;
        this.telefono = telefono;
        this.correo = correo;
        this.estado = true;
    }

    public Empresa(int idEmpresa,
                   String razonSocial,
                   String ruc,
                   String sector,
                   String direccion,
                   String telefono,
                   String correo,
                   boolean estado) {

        this.idEmpresa = idEmpresa;
        this.razonSocial = razonSocial;
        this.ruc = ruc;
        this.sector = sector;
        this.direccion = direccion;
        this.telefono = telefono;
        this.correo = correo;
        this.estado = estado;
    }

    public int getIdEmpresa() {
        return idEmpresa;
    }

    public void setIdEmpresa(int idEmpresa) {
        this.idEmpresa = idEmpresa;
    }

    public String getRazonSocial() {
        return razonSocial;
    }

    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }

    public String getRuc() {
        return ruc;
    }

    public void setRuc(String ruc) {
        this.ruc = ruc;
    }

    public String getSector() {
        return sector;
    }

    public void setSector(String sector) {
        this.sector = sector;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    @Override
    public String toString() {

        return "Empresa{" +
                "idEmpresa=" + idEmpresa +
                ", razonSocial='" + razonSocial + '\'' +
                ", ruc='" + ruc + '\'' +
                ", sector='" + sector + '\'' +
                ", direccion='" + direccion + '\'' +
                ", telefono='" + telefono + '\'' +
                ", correo='" + correo + '\'' +
                ", estado=" + estado +
                '}';
    }
}
