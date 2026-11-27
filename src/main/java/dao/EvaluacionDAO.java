/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import util.Conexion;
import model.Evaluacion;

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
public class EvaluacionDAO {
    private final Connection conexion;

    public EvaluacionDAO() {
        conexion = Conexion.getInstancia().getConexion();
    }

    // ==========================================
    // REGISTRAR EVALUACIÓN
    // ==========================================

    public boolean registrar(Evaluacion evaluacion) {

        String sql = "INSERT INTO evaluaciones "
                   + "(id_empresa, id_usuario, observaciones, estado) "
                   + "VALUES (?, ?, ?, ?)";

        try (PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setInt(1, evaluacion.getIdEmpresa());
            ps.setInt(2, evaluacion.getIdUsuario());
            ps.setString(3, evaluacion.getObservaciones());
            ps.setString(4, evaluacion.getEstado());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {

            System.err.println(
                    "Error al registrar evaluación: "
                    + e.getMessage()
            );

            return false;
        }
    }

    // ==========================================
    // LISTAR EVALUACIONES
    // ==========================================

    public List<Evaluacion> listar() {

        List<Evaluacion> lista = new ArrayList<>();

        String sql = "SELECT id_evaluacion, id_empresa, id_usuario, "
                   + "fecha_evaluacion, observaciones, estado "
                   + "FROM evaluaciones "
                   + "ORDER BY fecha_evaluacion DESC";

        try (PreparedStatement ps = conexion.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                Evaluacion evaluacion = new Evaluacion();

                evaluacion.setIdEvaluacion(
                        rs.getInt("id_evaluacion")
                );

                evaluacion.setIdEmpresa(
                        rs.getInt("id_empresa")
                );

                evaluacion.setIdUsuario(
                        rs.getInt("id_usuario")
                );

                evaluacion.setFechaEvaluacion(
                        rs.getTimestamp("fecha_evaluacion")
                );

                evaluacion.setObservaciones(
                        rs.getString("observaciones")
                );

                evaluacion.setEstado(
                        rs.getString("estado")
                );

                lista.add(evaluacion);
            }

        } catch (SQLException e) {

            System.err.println(
                    "Error al listar evaluaciones: "
                    + e.getMessage()
            );
        }

        return lista;
    }

    // ==========================================
    // BUSCAR POR ID
    // ==========================================

    public Evaluacion buscarPorId(int idEvaluacion) {

        String sql = "SELECT id_evaluacion, id_empresa, id_usuario, "
                   + "fecha_evaluacion, observaciones, estado "
                   + "FROM evaluaciones "
                   + "WHERE id_evaluacion = ?";

        try (PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setInt(1, idEvaluacion);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {

                    Evaluacion evaluacion = new Evaluacion();

                    evaluacion.setIdEvaluacion(
                            rs.getInt("id_evaluacion")
                    );

                    evaluacion.setIdEmpresa(
                            rs.getInt("id_empresa")
                    );

                    evaluacion.setIdUsuario(
                            rs.getInt("id_usuario")
                    );

                    evaluacion.setFechaEvaluacion(
                            rs.getTimestamp("fecha_evaluacion")
                    );

                    evaluacion.setObservaciones(
                            rs.getString("observaciones")
                    );

                    evaluacion.setEstado(
                            rs.getString("estado")
                    );

                    return evaluacion;
                }
            }

        } catch (SQLException e) {

            System.err.println(
                    "Error al buscar evaluación: "
                    + e.getMessage()
            );
        }

        return null;
    }

    // ==========================================
    // ACTUALIZAR EVALUACIÓN
    // ==========================================

    public boolean actualizar(Evaluacion evaluacion) {

        String sql = "UPDATE evaluaciones "
                   + "SET id_empresa = ?, "
                   + "observaciones = ?, "
                   + "estado = ? "
                   + "WHERE id_evaluacion = ?";

        try (PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setInt(1, evaluacion.getIdEmpresa());
            ps.setString(2, evaluacion.getObservaciones());
            ps.setString(3, evaluacion.getEstado());
            ps.setInt(4, evaluacion.getIdEvaluacion());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {

            System.err.println(
                    "Error al actualizar evaluación: "
                    + e.getMessage()
            );

            return false;
        }
    }

    // ==========================================
    // CAMBIAR ESTADO
    // ==========================================

    public boolean cambiarEstado(int idEvaluacion,
                                 String estado) {

        String sql = "UPDATE evaluaciones "
                   + "SET estado = ? "
                   + "WHERE id_evaluacion = ?";

        try (PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setString(1, estado);
            ps.setInt(2, idEvaluacion);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {

            System.err.println(
                    "Error al cambiar estado de evaluación: "
                    + e.getMessage()
            );

            return false;
        }
    }
    
    public boolean actualizarEstado(int idEvaluacion, String estado) {

        String sql =
                "UPDATE evaluaciones "
                + "SET estado = ? "
                + "WHERE id_evaluacion = ?";

        try (PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setString(1, estado);
            ps.setInt(2, idEvaluacion);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {

            e.printStackTrace();
            return false;
        }
    }
}
