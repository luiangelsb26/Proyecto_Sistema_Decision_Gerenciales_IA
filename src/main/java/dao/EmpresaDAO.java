/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import util.Conexion;
import model.Empresa;

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
public class EmpresaDAO {
    private final Connection conexion;

    public EmpresaDAO() {
        conexion = Conexion.getInstancia().getConexion();
    }

    // ==========================================
    // REGISTRAR EMPRESA
    // ==========================================

    public boolean registrar(Empresa empresa) {

        String sql = "INSERT INTO empresas "
                   + "(razon_social, ruc, sector, direccion, "
                   + "telefono, correo, estado) "
                   + "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement ps =
                     conexion.prepareStatement(sql)) {

            ps.setString(1, empresa.getRazonSocial());
            ps.setString(2, empresa.getRuc());
            ps.setString(3, empresa.getSector());
            ps.setString(4, empresa.getDireccion());
            ps.setString(5, empresa.getTelefono());
            ps.setString(6, empresa.getCorreo());
            ps.setBoolean(7, empresa.isEstado());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {

            System.err.println(
                    "Error al registrar empresa: "
                    + e.getMessage()
            );

            return false;
        }
    }

    // ==========================================
    // LISTAR EMPRESAS
    // ==========================================

    public List<Empresa> listar() {

        List<Empresa> lista = new ArrayList<>();

        String sql = "SELECT id_empresa, razon_social, ruc, "
                   + "sector, direccion, telefono, correo, estado "
                   + "FROM empresas "
                   + "WHERE estado = 1 "
                   + "ORDER BY razon_social";

        try (PreparedStatement ps =
                     conexion.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                Empresa empresa = new Empresa();

                empresa.setIdEmpresa(
                        rs.getInt("id_empresa")
                );

                empresa.setRazonSocial(
                        rs.getString("razon_social")
                );

                empresa.setRuc(
                        rs.getString("ruc")
                );

                empresa.setSector(
                        rs.getString("sector")
                );

                empresa.setDireccion(
                        rs.getString("direccion")
                );

                empresa.setTelefono(
                        rs.getString("telefono")
                );

                empresa.setCorreo(
                        rs.getString("correo")
                );

                empresa.setEstado(
                        rs.getBoolean("estado")
                );

                lista.add(empresa);
            }

        } catch (SQLException e) {

            System.err.println(
                    "Error al listar empresas: "
                    + e.getMessage()
            );
        }

        return lista;
    }

    // ==========================================
    // BUSCAR POR ID
    // ==========================================

    public Empresa buscarPorId(int idEmpresa) {

        String sql = "SELECT id_empresa, razon_social, ruc, "
                   + "sector, direccion, telefono, correo, estado "
                   + "FROM empresas "
                   + "WHERE id_empresa = ?";

        try (PreparedStatement ps =
                     conexion.prepareStatement(sql)) {

            ps.setInt(1, idEmpresa);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {

                    Empresa empresa = new Empresa();

                    empresa.setIdEmpresa(
                            rs.getInt("id_empresa")
                    );

                    empresa.setRazonSocial(
                            rs.getString("razon_social")
                    );

                    empresa.setRuc(
                            rs.getString("ruc")
                    );

                    empresa.setSector(
                            rs.getString("sector")
                    );

                    empresa.setDireccion(
                            rs.getString("direccion")
                    );

                    empresa.setTelefono(
                            rs.getString("telefono")
                    );

                    empresa.setCorreo(
                            rs.getString("correo")
                    );

                    empresa.setEstado(
                            rs.getBoolean("estado")
                    );

                    return empresa;
                }
            }

        } catch (SQLException e) {

            System.err.println(
                    "Error al buscar empresa: "
                    + e.getMessage()
            );
        }

        return null;
    }

    // ==========================================
    // ACTUALIZAR EMPRESA
    // ==========================================

    public boolean actualizar(Empresa empresa) {

        String sql = "UPDATE empresas "
                   + "SET razon_social = ?, "
                   + "ruc = ?, "
                   + "sector = ?, "
                   + "direccion = ?, "
                   + "telefono = ?, "
                   + "correo = ? "
                   + "WHERE id_empresa = ?";

        try (PreparedStatement ps =
                     conexion.prepareStatement(sql)) {

            ps.setString(1, empresa.getRazonSocial());
            ps.setString(2, empresa.getRuc());
            ps.setString(3, empresa.getSector());
            ps.setString(4, empresa.getDireccion());
            ps.setString(5, empresa.getTelefono());
            ps.setString(6, empresa.getCorreo());
            ps.setInt(7, empresa.getIdEmpresa());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {

            System.err.println(
                    "Error al actualizar empresa: "
                    + e.getMessage()
            );

            return false;
        }
    }

    // ==========================================
    // DESACTIVAR EMPRESA
    // ==========================================

    public boolean desactivar(int idEmpresa) {

        String sql = "UPDATE empresas "
                   + "SET estado = 0 "
                   + "WHERE id_empresa = ?";

        try (PreparedStatement ps =
                     conexion.prepareStatement(sql)) {

            ps.setInt(1, idEmpresa);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {

            System.err.println(
                    "Error al desactivar empresa: "
                    + e.getMessage()
            );

            return false;
        }
    }
}
