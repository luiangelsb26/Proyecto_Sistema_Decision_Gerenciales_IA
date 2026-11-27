/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import model.Respuesta;
import model.ResultadoIndicador;
import util.Conexion;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author Humberto
 */
public class RespuestaDAO {
    private Connection obtenerConexion() {
        return Conexion
                .getInstancia()
                .getConexion();
    }

    /**
     * Registra una respuesta individual.
     */
    public boolean registrar(Respuesta respuesta) {

        String sql =
                "INSERT INTO respuestas "
                + "(id_evaluacion, id_pregunta, valor, observacion) "
                + "VALUES (?, ?, ?, ?)";

        try (Connection con = obtenerConexion();
             PreparedStatement ps =
                     con.prepareStatement(sql)) {

            ps.setInt(
                    1,
                    respuesta.getIdEvaluacion()
            );

            ps.setInt(
                    2,
                    respuesta.getIdPregunta()
            );

            ps.setInt(
                    3,
                    respuesta.getValor()
            );

            ps.setString(
                    4,
                    respuesta.getObservacion()
            );

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {

            e.printStackTrace();
            return false;
        }
    }

    /**
     * Registra todas las respuestas de una evaluación
     * utilizando una sola transacción.
     */
    public boolean registrarTodas(
            List<Respuesta> respuestas) {

        if (respuestas == null
                || respuestas.isEmpty()) {

            return false;
        }

        if (respuestas.size() != 30 || respuestas.get(0) == null) return false;
        int evaluacion = respuestas.get(0).getIdEvaluacion();
        java.util.Set<Integer> preguntas = new java.util.HashSet<>();
        for (Respuesta r : respuestas) {
            if (r == null || evaluacion <= 0 || r.getIdEvaluacion() != evaluacion
                    || r.getIdPregunta() < 1 || r.getIdPregunta() > 30
                    || r.getValor() < 1 || r.getValor() > 5
                    || !preguntas.add(r.getIdPregunta())) return false;
        }
        String sql =
                "INSERT INTO respuestas "
                + "(id_evaluacion, id_pregunta, valor, observacion) "
                + "VALUES (?, ?, ?, ?)";

        Connection con = null;

        try {

            con = obtenerConexion();

            // Desactivar autocommit
            con.setAutoCommit(false);

            try (PreparedStatement ps =
                         con.prepareStatement(sql)) {

                for (Respuesta respuesta : respuestas) {

                    ps.setInt(
                            1,
                            respuesta.getIdEvaluacion()
                    );

                    ps.setInt(
                            2,
                            respuesta.getIdPregunta()
                    );

                    ps.setInt(
                            3,
                            respuesta.getValor()
                    );

                    ps.setString(
                            4,
                            respuesta.getObservacion()
                    );

                    ps.addBatch();
                }

                ps.executeBatch();
            }

            // Confirmar todas las respuestas
            con.commit();

            return true;

        } catch (SQLException e) {

            e.printStackTrace();

            // Revertir si ocurrió un error
            if (con != null) {

                try {
                    con.rollback();

                } catch (SQLException rollbackError) {

                    rollbackError.printStackTrace();
                }
            }

            return false;

        } finally {

            if (con != null) {

                try {

                    con.setAutoCommit(true);
                    con.close();

                } catch (SQLException e) {

                    e.printStackTrace();
                }
            }
        }
    }
    
    /**
    * Obtiene todas las respuestas de una evaluación,
    * incluyendo la pregunta y el indicador al que pertenece.
    */
    public List<Respuesta> listarPorEvaluacion(int idEvaluacion) {

        List<Respuesta> lista = new ArrayList<>();

        String sql =
                "SELECT "
                + "r.id_respuesta, "
                + "r.id_evaluacion, "
                + "r.id_pregunta, "
                + "r.valor, "
                + "r.observacion "
                + "FROM respuestas r "
                + "INNER JOIN preguntas p "
                + "ON r.id_pregunta = p.id_pregunta "
                + "INNER JOIN indicadores i "
                + "ON p.id_indicador = i.id_indicador "
                + "WHERE r.id_evaluacion = ? "
                + "ORDER BY i.id_indicador, p.id_pregunta";

        try (Connection con = obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idEvaluacion);

            try (ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {

                    Respuesta respuesta = new Respuesta();

                    respuesta.setIdRespuesta(
                            rs.getInt("id_respuesta")
                    );

                    respuesta.setIdEvaluacion(
                            rs.getInt("id_evaluacion")
                    );

                    respuesta.setIdPregunta(
                            rs.getInt("id_pregunta")
                    );

                    respuesta.setValor(
                            rs.getInt("valor")
                    );

                    respuesta.setObservacion(
                            rs.getString("observacion")
                    );

                    lista.add(respuesta);
                }
            }

        } catch (SQLException e) {

            System.err.println(
                    "Error al listar respuestas de la evaluación: "
                    + e.getMessage()
            );
        }

        return lista;
    }
    
    /**
    * Obtiene las respuestas de una evaluación agrupables
    * por indicador, incluyendo pesos y datos del indicador.
    */
    public List<ResultadoIndicador> obtenerDatosParaCalculo(
            int idEvaluacion) {

        List<ResultadoIndicador> lista = new ArrayList<>();

        String sql =
                "SELECT "
                + "i.id_indicador, "
                + "i.nombre AS nombre_indicador, "
                + "i.peso AS peso_indicador, "
                + "p.id_pregunta, p.peso AS peso_pregunta, "
                + "r.valor "
                + "FROM respuestas r "
                + "INNER JOIN preguntas p "
                + "ON r.id_pregunta = p.id_pregunta "
                + "INNER JOIN indicadores i "
                + "ON p.id_indicador = i.id_indicador "
                + "WHERE r.id_evaluacion = ? "
                + "ORDER BY i.id_indicador, p.id_pregunta";

        try (Connection con = obtenerConexion();
             PreparedStatement ps =
                     con.prepareStatement(sql)) {

            ps.setInt(1, idEvaluacion);

            try (ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {

                    ResultadoIndicador resultado =
                            new ResultadoIndicador();

                    resultado.setIdPregunta(rs.getInt("id_pregunta"));
                    resultado.setIdIndicador(
                            rs.getInt("id_indicador")
                    );

                    resultado.setNombreIndicador(
                            rs.getString("nombre_indicador")
                    );

                    resultado.setPeso(
                            rs.getDouble("peso_indicador")
                    );

                    /*
                     * Guardamos temporalmente el valor
                     * de la respuesta como puntaje.
                     *
                     * El cálculo definitivo se realizará
                     * posteriormente en ResultadoService.
                     */
                    resultado.setPuntaje(
                            rs.getDouble("valor")
                    );

                    resultado.setPesoPregunta(
                            rs.getDouble("peso_pregunta")
                    );

                    lista.add(resultado);
                }
            }

        } catch (SQLException e) {

            System.err.println(
                    "Error al obtener datos para cálculo: "
                    + e.getMessage()
            );
        }

        return lista;
    }
    
}
