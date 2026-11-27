package util;

import java.io.Console;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Arrays;
import org.mindrot.jbcrypt.BCrypt;

/** Creates an administrator explicitly; no default password is distributed. */
public final class CrearAdministrador {
    private CrearAdministrador() { }

    public static void main(String[] args) throws Exception {
        if (args.length != 1 || args[0].trim().isEmpty() || args[0].length() > 50) {
            throw new IllegalArgumentException("Uso: util.CrearAdministrador nombre_de_usuario");
        }
        Console console = System.console();
        if (console == null) throw new IllegalStateException("Ejecute esta utilidad desde una terminal interactiva.");
        char[] password = console.readPassword("Contraseña del nuevo administrador: ");
        char[] confirmation = console.readPassword("Repita la contraseña: ");
        try {
            if (password == null || confirmation == null || password.length < 8
                    || !Arrays.equals(password, confirmation)) {
                throw new IllegalArgumentException("Las contraseñas deben coincidir y tener al menos 8 caracteres.");
            }
            String hash = BCrypt.hashpw(new String(password), BCrypt.gensalt(12));
            try (Connection con = Conexion.getInstancia().getConexion();
                    PreparedStatement ps = con.prepareStatement("INSERT INTO usuarios "
                            + "(nombres,apellidos,usuario,password,rol,estado) VALUES (?,?,?,?,?,1)")) {
                ps.setString(1, "Administrador"); ps.setString(2, "Sistema");
                ps.setString(3, args[0].trim()); ps.setString(4, hash); ps.setString(5, "ADMIN");
                ps.executeUpdate();
            }
            console.printf("Administrador creado correctamente.%n");
        } finally {
            if (password != null) Arrays.fill(password, '\0');
            if (confirmation != null) Arrays.fill(confirmation, '\0');
        }
    }
}
