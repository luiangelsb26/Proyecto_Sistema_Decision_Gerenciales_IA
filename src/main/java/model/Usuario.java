/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author Humberto
 */
public class Usuario {
    private int idUsuario;
    private String nombres;
    private String apellidos;
    private String usuario;
    private String password;
    private String rol;
    private boolean estado;
    
    public Usuario()
    {
        
    }
    
    public Usuario(String nombres, String apellidos,
            String usuario, String password, String rol){
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.usuario = usuario;
        this.password = password;
        this.rol = rol;
        this.estado = true;
    }
    
    public Usuario(int idUsuario, String nombres, String apellidos,
            String usuario, String password, String rol,
            boolean estado) {
        this.idUsuario = idUsuario;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.usuario = usuario;
        this.password = password;
        this.rol = rol;
        this.estado = estado;
    }
    
    public int getIdUsuario(){
        return idUsuario;
    }
    
    public void setIdUsuario(int idUsuario){
        this.idUsuario = idUsuario;
    }
    
    public String getNombres(){
        return nombres;
    }
    
    public void setNombres(String nombres){
        this.nombres = nombres;
    }
    
    public String getApellidos(){
        return apellidos;
    }
    
    public void setApellidos(String apellidos){
        this.apellidos = apellidos;
    }
    
    public String getUsuario(){
        return usuario;
    }
    
    public void setUsuario(String usuario){
        this.usuario = usuario;
    }
    
    public String getPassword(){
        return password;
    }
    
    public void setPassword(String password){
        this.password = password;
    }
    
    public String getRol(){
        return rol;
    }
    
    public void setRol(String rol){
        this.rol = rol;
    }
    
    public boolean isEstado(){
        return estado;
    }
    
    public void setEstado(boolean estado){
        this.estado = estado;
    }
    
    @Override
    public String toString() {
        return "Usuario{" +
                "idUsuario=" + idUsuario +
                ", nombres='" + nombres + '\'' +
                ", apellidos='" + apellidos + '\'' +
                ", usuario='" + usuario + '\''
                + ", rol='" + rol + '\'' +
                ", estado=" + estado + '}'; }
}
