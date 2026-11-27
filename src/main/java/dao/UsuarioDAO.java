package dao;
import util.Conexion;
import model.Usuario;
import java.sql.*;
import org.mindrot.jbcrypt.BCrypt;
public class UsuarioDAO {
    public Usuario autenticar(String usuario, String password) {
        if (usuario == null || password == null) return null;
        try (Connection con = Conexion.getInstancia().getConexion();
                PreparedStatement ps = con.prepareStatement("SELECT * FROM usuarios WHERE usuario=? AND estado=1")) {
            ps.setString(1, usuario);
            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) return null;
                String hash = rs.getString("password");
                if (hash == null || !hash.startsWith("$2a$") || !BCrypt.checkpw(password, hash)) return null;
                Usuario u = new Usuario();
                u.setIdUsuario(rs.getInt("id_usuario")); u.setNombres(rs.getString("nombres"));
                u.setApellidos(rs.getString("apellidos")); u.setUsuario(rs.getString("usuario"));
                u.setRol(rs.getString("rol")); u.setEstado(rs.getBoolean("estado")); return u;
            }
        } catch (SQLException | IllegalArgumentException e) { return null; }
    }
}
