/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import util.Conexion;
import model.ResultadoIndicador;
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
public class ResultadoDAO {
    private Connection obtenerConexion() {
        return Conexion
                .getInstancia()
                .getConexion();
    }

    // ==========================================
    // REGISTRAR RESULTADO GENERAL
    // ==========================================

    public int registrarResultado(
            int idEvaluacion,
            double indiceGeneral,
            String nivel) {

        String sql =
                "INSERT INTO resultados "
                + "(id_evaluacion, indice_general, nivel) "
                + "VALUES (?, ?, ?)";

        try (Connection con = obtenerConexion();
             PreparedStatement ps =
                     con.prepareStatement(
                             sql,
                             PreparedStatement.RETURN_GENERATED_KEYS
                     )) {

            ps.setInt(1, idEvaluacion);
            ps.setDouble(2, indiceGeneral);
            ps.setString(3, nivel);

            int filas = ps.executeUpdate();

            if (filas > 0) {

                try (ResultSet rs =
                             ps.getGeneratedKeys()) {

                    if (rs.next()) {
                        return rs.getInt(1);
                    }
                }
            }

        } catch (SQLException e) {

            System.err.println(
                    "Error al registrar resultado: "
                    + e.getMessage()
            );
        }

        return -1;
    }

    // ==========================================
    // REGISTRAR RESULTADO DE INDICADOR
    // ==========================================

    public boolean registrarResultadoIndicador(
            int idResultado,
            int idIndicador,
            double puntaje,
            String nivel) {

        String sql =
                "INSERT INTO resultado_indicadores "
                + "(id_resultado, id_indicador, puntaje, nivel) "
                + "VALUES (?, ?, ?, ?)";

        try (Connection con = obtenerConexion();
             PreparedStatement ps =
                     con.prepareStatement(sql)) {

            ps.setInt(1, idResultado);
            ps.setInt(2, idIndicador);
            ps.setDouble(3, puntaje);
            ps.setString(4, nivel);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {

            System.err.println(
                    "Error al registrar resultado del indicador: "
                    + e.getMessage()
            );

            return false;
        }
    }

    // ==========================================
    // OBTENER ID DE RESULTADO POR EVALUACIÓN
    // ==========================================

    public int obtenerIdResultadoPorEvaluacion(
            int idEvaluacion) {

        String sql =
                "SELECT id_resultado "
                + "FROM resultados "
                + "WHERE id_evaluacion = ?";

        try (Connection con = obtenerConexion();
             PreparedStatement ps =
                     con.prepareStatement(sql)) {

            ps.setInt(1, idEvaluacion);

            try (ResultSet rs =
                         ps.executeQuery()) {

                if (rs.next()) {
                    return rs.getInt("id_resultado");
                }
            }

        } catch (SQLException e) {

            System.err.println(
                    "Error al buscar resultado: "
                    + e.getMessage()
            );
        }

        return -1;
    }

    // ==========================================
    // OBTENER RESULTADO GENERAL
    // ==========================================

    public ResultadoIndicador obtenerResultadoGeneral(
            int idEvaluacion) {

        String sql =
                "SELECT "
                + "r.id_resultado, "
                + "r.id_evaluacion, "
                + "r.indice_general, "
                + "r.nivel, "
                + "r.fecha_resultado "
                + "FROM resultados r "
                + "WHERE r.id_evaluacion = ?";

        try (Connection con = obtenerConexion();
             PreparedStatement ps =
                     con.prepareStatement(sql)) {

            ps.setInt(1, idEvaluacion);

            try (ResultSet rs =
                         ps.executeQuery()) {

                if (rs.next()) {

                    ResultadoIndicador resultado =
                            new ResultadoIndicador();

                    resultado.setPuntaje(
                            rs.getDouble("indice_general")
                    );

                    resultado.setNivel(
                            rs.getString("nivel")
                    );

                    return resultado;
                }
            }

        } catch (SQLException e) {

            System.err.println(
                    "Error al obtener resultado general: "
                    + e.getMessage()
            );
        }

        return null;
    }

    // ==========================================
    // LISTAR RESULTADOS POR INDICADOR
    // ==========================================

    public List<ResultadoIndicador>
            listarResultadosIndicadores(
                    int idEvaluacion) {

        List<ResultadoIndicador> lista =
                new ArrayList<>();

        String sql =
                "SELECT "
                + "i.id_indicador, "
                + "i.nombre AS nombre_indicador, "
                + "ri.puntaje, "
                + "ri.nivel "
                + "FROM resultado_indicadores ri "
                + "INNER JOIN resultados r "
                + "ON ri.id_resultado = r.id_resultado "
                + "INNER JOIN indicadores i "
                + "ON ri.id_indicador = i.id_indicador "
                + "WHERE r.id_evaluacion = ? "
                + "ORDER BY i.id_indicador";

        try (Connection con = obtenerConexion();
             PreparedStatement ps =
                     con.prepareStatement(sql)) {

            ps.setInt(1, idEvaluacion);

            try (ResultSet rs =
                         ps.executeQuery()) {

                while (rs.next()) {

                    ResultadoIndicador resultado =
                            new ResultadoIndicador();

                    resultado.setIdIndicador(
                            rs.getInt("id_indicador")
                    );

                    resultado.setNombreIndicador(
                            rs.getString(
                                    "nombre_indicador"
                            )
                    );

                    resultado.setPuntaje(
                            rs.getDouble("puntaje")
                    );

                    resultado.setNivel(
                            rs.getString("nivel")
                    );

                    lista.add(resultado);
                }
            }

        } catch (SQLException e) {

            System.err.println(
                    "Error al listar resultados "
                    + "por indicador: "
                    + e.getMessage()
            );
        }

        return lista;
    }
    
    public List<ResultadoEvaluacion> listarEvaluacionesConResultados() {

        List<ResultadoEvaluacion> lista = new ArrayList<>();

        String sql =
                "SELECT "
                + "e.id_evaluacion, "
                + "em.razon_social, "
                + "e.fecha_evaluacion, "
                + "r.indice_general, "
                + "r.nivel "
                + "FROM evaluaciones e "
                + "INNER JOIN empresas em "
                + "ON e.id_empresa = em.id_empresa "
                + "INNER JOIN resultados r "
                + "ON e.id_evaluacion = r.id_evaluacion "
                + "ORDER BY r.fecha_resultado DESC";

        try (Connection con = obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

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
                        rs.getTimestamp("fecha_evaluacion")
                );

                resultado.setIndiceGeneral(
                        rs.getDouble("indice_general")
                );

                resultado.setNivel(
                        rs.getString("nivel")
                );

                lista.add(resultado);
            }

        } catch (SQLException e) {
            System.err.println(
                    "Error al listar evaluaciones con resultados: "
                    + e.getMessage()
            );
        }

        return lista;
    }
    public boolean guardarCompleto(int idEvaluacion, double indice, String nivel,
            List<ResultadoIndicador> detalles) {
        try (Connection con = obtenerConexion()) {
            con.setAutoCommit(false);
            try {
                int id;
                try (PreparedStatement ps = con.prepareStatement(
                        "INSERT INTO resultados (id_evaluacion, indice_general, nivel) VALUES (?, ?, ?)",
                        PreparedStatement.RETURN_GENERATED_KEYS)) {
                    ps.setInt(1, idEvaluacion); ps.setDouble(2, indice); ps.setString(3, nivel);
                    ps.executeUpdate();
                    try (ResultSet rs = ps.getGeneratedKeys()) {
                        if (!rs.next()) throw new SQLException("Resultado sin identificador");
                        id = rs.getInt(1);
                    }
                }
                try (PreparedStatement ps = con.prepareStatement(
                        "INSERT INTO resultado_indicadores (id_resultado, id_indicador, puntaje, nivel) VALUES (?, ?, ?, ?)")) {
                    for (ResultadoIndicador d : detalles) {
                        ps.setInt(1, id); ps.setInt(2, d.getIdIndicador());
                        ps.setDouble(3, d.getPuntaje()); ps.setString(4, d.getNivel()); ps.addBatch();
                    }
                    ps.executeBatch();
                }
                con.commit(); return true;
            } catch (SQLException e) {
                con.rollback(); return false;
            } finally { con.setAutoCommit(true); }
        } catch (SQLException e) { return false; }
    }
}