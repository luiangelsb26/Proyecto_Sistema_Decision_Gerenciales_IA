# Despliegue del proyecto Java en Railway

Estos archivos preparan el proyecto; su presencia en GitHub no significa que exista un despliegue activo. Docker no se ha ejecutado en el equipo de preparación porque no está instalado.

El contenedor compila con Maven y Java 21 y sirve el WAR mediante Tomcat 11. La aplicación ocupa la raíz del dominio: `/login.jsp` y `/InicioServlet`. No se incluye el nombre del proyecto como prefijo en esta instalación. El entorno local de NetBeans conserva su contexto habitual.

## Servicios y conexión

1. Conectar Railway y dar acceso a este repositorio privado. Seleccionar el proyecto y un plan acorde al presupuesto; revisar los costos antes de contratar.
2. Crear un servicio MySQL con almacenamiento persistente y un servicio de aplicación desde este repositorio, rama `main`. El Dockerfile se detecta automáticamente.
3. Configurar `PORT=8080` en el servicio de aplicación y el puerto de destino del dominio en 8080. Tomcat escucha en ese puerto fijo.
4. Configurar como variables privadas del servicio de aplicación:
   - `SYSDB_URL`: URL JDBC del MySQL del mismo proyecto, con el formato `jdbc:mysql://HOST_INTERNO:3306/sistema_decision_gerencial?serverTimezone=America/Lima&connectTimeout=10000&socketTimeout=30000`. Ajustar el puerto al valor real. Configurar TLS según el servicio; no copiar parámetros que deshabiliten TLS desde la configuración local.
   - `SYSDB_USER`: usuario de la base de la nube.
   - `SYSDB_PASSWORD`: contraseña de la base de la nube, mediante una referencia privada de Railway.

No utilizar `localhost` para la base de la nube. No subir variables ni contraseñas a GitHub. No exponer MySQL públicamente para que la aplicación se conecte: utilizar la red privada del proyecto.

## Inicializar la base y el acceso

Ejecutar `BD_Sistema Decision Gerencial.sql` una sola vez contra una base nueva de la nube mediante una sesión administrativa privada. El archivo crea el esquema `sistema_decision_gerencial` y sus catálogos; ajustar el nombre si el proveedor impone otro esquema. No importar ni modificar la base local del usuario para este paso. No repetir el script sobre una instalación que ya tenga tablas.

Desde una terminal interactiva con acceso privado al servicio de aplicación, crear un administrador:

```sh
java -cp '/usr/local/tomcat/webapps/ROOT/WEB-INF/classes:/usr/local/tomcat/webapps/ROOT/WEB-INF/lib/*' util.CrearAdministrador admin
```

Tomcat debe haber expandido el WAR. La utilidad solicita la contraseña de forma interactiva; no hay una contraseña predefinida. Esta cuenta es independiente del administrador local.

## Publicación y verificación

Generar el dominio HTTPS del servicio de aplicación. Comprobar el inicio de sesión, panel, creación de una empresa de prueba, cuestionario y reporte. El chequeo HTTP de `/login.jsp` solo confirma que Tomcat sirve la pantalla; no verifica la base ni el acceso completo. No considerar finalizado el despliegue hasta probar el recorrido con MySQL.

La base empieza con los catálogos del SQL, sin datos empresariales ni cuentas locales. La aplicación usa una única instancia con sesiones en memoria. Reiniciar el servicio requiere iniciar sesión de nuevo. Antes de almacenar información real, configurar copias de seguridad y controlar el acceso a las cuentas.

Referencias: [Dockerfiles](https://docs.railway.com/builds/dockerfiles), [MySQL](https://docs.railway.com/databases/mysql), [planes](https://railway.com/pricing).
