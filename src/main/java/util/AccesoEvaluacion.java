package util;
import java.sql.*;
import java.util.List;
import model.Usuario;
public final class AccesoEvaluacion {
    private AccesoEvaluacion() {}
    public static boolean permitida(int id, Usuario usuario) {
        if (usuario == null || id <= 0) return false;
        try (Connection con = Conexion.getInstancia().getConexion();
                PreparedStatement ps = con.prepareStatement("SELECT id_usuario FROM evaluaciones WHERE id_evaluacion=?")) {
            ps.setInt(1,id);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() && ("ADMIN".equals(usuario.getRol()) || rs.getInt(1) == usuario.getIdUsuario());
            }
        } catch (SQLException | RuntimeException e) { return false; }
    }
    public static <T> void filtrar(List<T> filas, Usuario usuario) {
        filas.removeIf(f -> {
            try { return !permitida((Integer) f.getClass().getMethod("getIdEvaluacion").invoke(f), usuario); }
            catch (ReflectiveOperationException | RuntimeException e) { return true; }
        });
    }
}
