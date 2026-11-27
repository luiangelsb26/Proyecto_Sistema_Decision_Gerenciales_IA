package util;

import java.io.IOException;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/** Opens one JDBC connection per call, using private configuration outside Git. */
public final class Conexion {
    private static final Conexion INSTANCIA = new Conexion();
    private static final String DEFAULT_URL = "jdbc:mysql://localhost:3306/sistema_decision_gerencial"
            + "?useSSL=false&serverTimezone=America/Lima&allowPublicKeyRetrieval=true";

    private Conexion() { }

    public static Conexion getInstancia() { return INSTANCIA; }

    public Connection getConexion() {
        Properties local = cargarConfiguracionLocal();
        String url = valor("sysdb.url", "SYSDB_URL", "db.url", local, DEFAULT_URL);
        String user = valor("sysdb.user", "SYSDB_USER", "db.user", local, null);
        String password = valor("sysdb.password", "SYSDB_PASSWORD", "db.password", local, null);
        if (url == null || url.trim().isEmpty() || user == null || user.trim().isEmpty()
                || password == null) {
            throw new IllegalStateException("Configure MySQL con SYSDB_URL, SYSDB_USER y SYSDB_PASSWORD "
                    + "o en el archivo privado .mypes/database.properties de su usuario.");
        }
        try {
            return DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            throw new IllegalStateException("No se pudo conectar con MySQL. Revise la configuración privada.", e);
        }
    }

    private Properties cargarConfiguracionLocal() {
        String custom = System.getProperty("sysdb.config");
        if (custom == null || custom.trim().isEmpty()) custom = System.getenv("SYSDB_CONFIG");
        Path file = custom == null || custom.trim().isEmpty()
                ? Paths.get(System.getProperty("user.home"), ".mypes", "database.properties")
                : Paths.get(custom);
        Properties values = new Properties();
        if (Files.exists(file)) {
            try (Reader reader = Files.newBufferedReader(file, StandardCharsets.UTF_8)) {
                values.load(reader);
            } catch (IOException e) {
                throw new IllegalStateException("No se pudo leer la configuración privada de MySQL.", e);
            }
        }
        return values;
    }

    private String valor(String property, String environment, String key,
            Properties local, String defaultValue) {
        String value = System.getProperty(property);
        if (value == null) value = System.getenv(environment);
        if (value == null) value = local.getProperty(key);
        return value == null ? defaultValue : value;
    }
}
