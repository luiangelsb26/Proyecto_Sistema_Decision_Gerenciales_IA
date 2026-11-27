/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import util.Conexion;
import model.Indicador;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Humberto
 */
public class IndicadorDAO {
    private Connection obtenerConexion() {
        return Conexion
                .getInstancia()
                .getConexion();
    }

    public List<Indicador> listarActivos() {

        List<Indicador> lista =
                new ArrayList<>();

        String sql =
                "SELECT id_indicador, "
                + "nombre, "
                + "descripcion, "
                + "categoria, "
                + "peso, "
                + "estado, "
                + "fecha_registro "
                + "FROM indicadores "
                + "WHERE estado = 1 "
                + "ORDER BY id_indicador";

        try (Connection con = obtenerConexion();
             PreparedStatement ps =
                     con.prepareStatement(sql);
             ResultSet rs =
                     ps.executeQuery()) {

            while (rs.next()) {

                Indicador indicador =
                        new Indicador();

                indicador.setIdIndicador(
                        rs.getInt("id_indicador")
                );

                indicador.setNombre(
                        rs.getString("nombre")
                );

                indicador.setDescripcion(
                        rs.getString("descripcion")
                );

                indicador.setCategoria(
                        rs.getString("categoria")
                );

                indicador.setPeso(
                        rs.getDouble("peso")
                );

                indicador.setEstado(
                        rs.getBoolean("estado")
                );

                indicador.setFechaRegistro(
                        rs.getTimestamp("fecha_registro")
                );

                lista.add(indicador);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }

    public Indicador buscarPorId(int idIndicador) {

        String sql =
                "SELECT id_indicador, "
                + "nombre, "
                + "descripcion, "
                + "categoria, "
                + "peso, "
                + "estado, "
                + "fecha_registro "
                + "FROM indicadores "
                + "WHERE id_indicador = ?";

        try (Connection con = obtenerConexion();
             PreparedStatement ps =
                     con.prepareStatement(sql)) {

            ps.setInt(1, idIndicador);

            try (ResultSet rs =
                         ps.executeQuery()) {

                if (rs.next()) {

                    Indicador indicador =
                            new Indicador();

                    indicador.setIdIndicador(
                            rs.getInt("id_indicador")
                    );

                    indicador.setNombre(
                            rs.getString("nombre")
                    );

                    indicador.setDescripcion(
                            rs.getString("descripcion")
                    );

                    indicador.setCategoria(
                            rs.getString("categoria")
                    );

                    indicador.setPeso(
                            rs.getDouble("peso")
                    );

                    indicador.setEstado(
                            rs.getBoolean("estado")
                    );

                    indicador.setFechaRegistro(
                            rs.getTimestamp("fecha_registro")
                    );

                    return indicador;
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
}
