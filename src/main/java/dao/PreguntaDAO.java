/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import util.Conexion;
import model.Pregunta;

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
public class PreguntaDAO {
    private Connection obtenerConexion() {
        return Conexion
                .getInstancia()
                .getConexion();
    }

    public List<Pregunta> listarActivasPorIndicador(
            int idIndicador) {

        List<Pregunta> lista =
                new ArrayList<>();

        String sql =
                "SELECT id_pregunta, "
                + "id_indicador, "
                + "pregunta, "
                + "peso, "
                + "estado, "
                + "fecha_registro "
                + "FROM preguntas "
                + "WHERE id_indicador = ? "
                + "AND estado = 1 "
                + "ORDER BY id_pregunta";

        try (Connection con = obtenerConexion();
             PreparedStatement ps =
                     con.prepareStatement(sql)) {

            ps.setInt(1, idIndicador);

            try (ResultSet rs =
                         ps.executeQuery()) {

                while (rs.next()) {

                    Pregunta pregunta =
                            new Pregunta();

                    pregunta.setIdPregunta(
                            rs.getInt("id_pregunta")
                    );

                    pregunta.setIdIndicador(
                            rs.getInt("id_indicador")
                    );

                    pregunta.setPregunta(
                            rs.getString("pregunta")
                    );

                    pregunta.setPeso(
                            rs.getDouble("peso")
                    );

                    pregunta.setEstado(
                            rs.getBoolean("estado")
                    );

                    pregunta.setFechaRegistro(
                            rs.getTimestamp("fecha_registro")
                    );

                    lista.add(pregunta);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }

    public List<Pregunta> listarTodasActivas() {

        List<Pregunta> lista =
                new ArrayList<>();

        String sql =
                "SELECT id_pregunta, "
                + "id_indicador, "
                + "pregunta, "
                + "peso, "
                + "estado, "
                + "fecha_registro "
                + "FROM preguntas "
                + "WHERE estado = 1 "
                + "ORDER BY id_indicador, id_pregunta";

        try (Connection con = obtenerConexion();
             PreparedStatement ps =
                     con.prepareStatement(sql);
             ResultSet rs =
                     ps.executeQuery()) {

            while (rs.next()) {

                Pregunta pregunta =
                        new Pregunta();

                pregunta.setIdPregunta(
                        rs.getInt("id_pregunta")
                );

                pregunta.setIdIndicador(
                        rs.getInt("id_indicador")
                );

                pregunta.setPregunta(
                        rs.getString("pregunta")
                );

                pregunta.setPeso(
                        rs.getDouble("peso")
                );

                pregunta.setEstado(
                        rs.getBoolean("estado")
                );

                pregunta.setFechaRegistro(
                        rs.getTimestamp("fecha_registro")
                );

                lista.add(pregunta);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }
}
