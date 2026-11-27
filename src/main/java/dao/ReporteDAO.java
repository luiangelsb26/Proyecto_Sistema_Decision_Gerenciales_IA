/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import util.Conexion;
import model.ResultadoEvaluacion;

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
public class ReporteDAO {
    private Connection obtenerConexion() {
        return Conexion
                .getInstancia()
                .getConexion();
    }

    // ==========================================
    // LISTAR REPORTES CON FILTROS
    // ==========================================

    public List<ResultadoEvaluacion> listarReportes(
            int idEmpresa,
            String fechaInicio,
            String fechaFin) {

        List<ResultadoEvaluacion> lista =
                new ArrayList<>();

        StringBuilder sql = new StringBuilder();

        sql.append("SELECT ");
        sql.append("e.id_evaluacion, ");
        sql.append("em.razon_social, ");
        sql.append("e.fecha_evaluacion, ");
        sql.append("r.indice_general, ");
        sql.append("r.nivel ");

        sql.append("FROM evaluaciones e ");

        sql.append("INNER JOIN empresas em ");
        sql.append("ON e.id_empresa = em.id_empresa ");

        sql.append("INNER JOIN resultados r ");
        sql.append("ON e.id_evaluacion = r.id_evaluacion ");

        sql.append("WHERE 1 = 1 ");

        if (idEmpresa > 0) {
            sql.append("AND e.id_empresa = ? ");
        }

        if (fechaInicio != null
                && !fechaInicio.trim().isEmpty()) {

            sql.append(
                    "AND e.fecha_evaluacion >= ? "
            );
        }

        if (fechaFin != null
                && !fechaFin.trim().isEmpty()) {

            sql.append(
                    "AND e.fecha_evaluacion < "
                    + "DATE_ADD(?, INTERVAL 1 DAY) "
            );
        }

        sql.append(
                "ORDER BY e.fecha_evaluacion DESC"
        );

        try (Connection con = obtenerConexion();
             PreparedStatement ps =
                     con.prepareStatement(sql.toString())) {

            int parametro = 1;

            if (idEmpresa > 0) {
                ps.setInt(parametro++, idEmpresa);
            }

            if (fechaInicio != null
                    && !fechaInicio.trim().isEmpty()) {

                ps.setString(
                        parametro++,
                        fechaInicio
                );
            }

            if (fechaFin != null
                    && !fechaFin.trim().isEmpty()) {

                ps.setString(
                        parametro++,
                        fechaFin
                );
            }

            try (ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {

                    ResultadoEvaluacion resultado =
                            new ResultadoEvaluacion();

                    resultado.setIdEvaluacion(
                            rs.getInt("id_evaluacion")
                    );

                    resultado.setRazonSocial(
                            rs.getString("razon_social")
                    );

                    resultado.setFechaEvaluacion(
                            rs.getTimestamp(
                                    "fecha_evaluacion"
                            )
                    );

                    resultado.setIndiceGeneral(
                            rs.getDouble("indice_general")
                    );

                    resultado.setNivel(
                            rs.getString("nivel")
                    );

                    lista.add(resultado);
                }
            }

        } catch (SQLException e) {

            System.err.println(
                    "Error al listar reportes: "
                    + e.getMessage()
            );
        }

        return lista;
    }
}
