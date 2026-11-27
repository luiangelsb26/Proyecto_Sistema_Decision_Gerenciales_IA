/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

import util.Conexion;
import java.sql.Connection;
/**
 *
 * @author Humberto
 */
public class Test {
    public static void main(String[] args) {
        Connection conexion =
                Conexion.getInstancia().getConexion();

        if (conexion != null) {
            System.out.println("================================");
            System.out.println("CONEXIÓN EXITOSA CON MYSQL");
            System.out.println("================================");
        } else {
            System.out.println("================================");
            System.out.println("ERROR DE CONEXIÓN CON MYSQL");
            System.out.println("================================");
        }
    }
}
