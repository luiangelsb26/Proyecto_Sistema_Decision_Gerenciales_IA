/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import model.AnalisisIA;
import util.Conexion;

import model.AnalisisEvaluacion;

import java.util.ArrayList;
import java.util.List;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Humberto
 */
public class AnalisisIADAO {
    private Connection obtenerConexion() {
        return Conexion
                .getInstancia()
                .getConexion();
    }

    public boolean registrar(AnalisisIA analisis) {

        String sql =
                "INSERT INTO analisis_ia "
                + "(id_resultado, resumen, fortalezas, "
                + "debilidades, recomendaciones) "
                + "VALUES (?, ?, ?, ?, ?) ON DUPLICATE KEY UPDATE id_resultado=id_resultado";

        try (Connection con = obtenerConexion();
             PreparedStatement ps =
                     con.prepareStatement(sql)) {

            ps.setInt(
                    1,
                    analisis.getIdResultado()
            );

            ps.setString(
                    2,
                    analisis.getResumen()
            );

            ps.setString(
                    3,
                    analisis.getFortalezas()
            );

            ps.setString(
                    4,
                    analisis.getDebilidades()
            );

            ps.setString(
                    5,
                    analisis.getRecomendaciones()
            );

            ps.executeUpdate();
            return true;

        } catch (SQLException e) {

            System.err.println(
                    "Error al registrar análisis IA: "
                    + e.getMessage()
            );

            return false;
        }
    }

    public AnalisisIA buscarPorResultado(
            int idResultado) {

        String sql =
                "SELECT "
                + "id_analisis, "
                + "id_resultado, "
                + "resumen, "
                + "fortalezas, "
                + "debilidades, "
                + "recomendaciones, "
                + "fecha_analisis "
                + "FROM analisis_ia "
                + "WHERE id_resultado = ?";

        try (Connection con = obtenerConexion();
             PreparedStatement ps =
                     con.prepareStatement(sql)) {

            ps.setInt(1, idResultado);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {

                    AnalisisIA analisis =
                            new AnalisisIA();

                    analisis.setIdAnalisis(
                            rs.getInt("id_analisis")
                    );

                    analisis.setIdResultado(
                            rs.getInt("id_resultado")
                    );

                    analisis.setResumen(
                            rs.getString("resumen")
                    );

                    analisis.setFortalezas(
                            rs.getString("fortalezas")
                    );

                    analisis.setDebilidades(
                            rs.getString("debilidades")
                    );

                    analisis.setRecomendaciones(
                            rs.getString("recomendaciones")
                    );

                    analisis.setFechaAnalisis(
                            rs.getTimestamp("fecha_analisis")
                    );

                    return analisis;
                }
            }

        } catch (SQLException e) {

            System.err.println(
                    "Error al buscar análisis IA: "
                    + e.getMessage()
            );
        }

        return null;
    }
    public List<AnalisisEvaluacion>
            listarEvaluacionesDisponibles() {

        List<AnalisisEvaluacion> lista =
                new ArrayList<>();

        String sql =
                "SELECT "
                + "e.id_evaluacion, "
                + "r.id_resultado, "
                + "em.razon_social, "
                + "e.fecha_evaluacion, "
                + "r.indice_general, "
                + "r.nivel, "
                + "CASE "
                + "WHEN a.id_analisis IS NULL THEN 0 "
                + "ELSE 1 "
                + "END AS tiene_analisis "
                + "FROM evaluaciones e "
                + "INNER JOIN empresas em "
                + "ON e.id_empresa = em.id_empresa "
                + "INNER JOIN resultados r "
                + "ON e.id_evaluacion = r.id_evaluacion "
                + "LEFT JOIN analisis_ia a "
                + "ON r.id_resultado = a.id_resultado "
                + "ORDER BY e.fecha_evaluacion DESC";

        try (Connection con = obtenerConexion();
             PreparedStatement ps =
                     con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                AnalisisEvaluacion evaluacion =
                        new AnalisisEvaluacion();

                evaluacion.setIdEvaluacion(
                        rs.getInt("id_evaluacion")
                );

                evaluacion.setIdResultado(
                        rs.getInt("id_resultado")
                );

                evaluacion.setRazonSocial(
                        rs.getString("razon_social")
                );

                evaluacion.setFechaEvaluacion(
                        rs.getTimestamp("fecha_evaluacion")
                );

                evaluacion.setIndiceGeneral(
                        rs.getDouble("indice_general")
                );

                evaluacion.setNivel(
                        rs.getString("nivel")
                );

                evaluacion.setTieneAnalisis(
                        rs.getInt("tiene_analisis") == 1
                );

                lista.add(evaluacion);
            }

        } catch (SQLException e) {

            System.err.println(
                    "Error al listar evaluaciones "
                    + "disponibles para análisis: "
                    + e.getMessage()
            );
        }

        return lista;
    }
    
}
