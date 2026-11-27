# Instalación y configuración

[Volver al proyecto](../README.md)

## Instalación

### 1. Abrir el proyecto

Clona el repositorio. En NetBeans selecciona **File → Open Project** y abre la carpeta que contiene `pom.xml`. Instala o configura JDK 17 y registra tu servidor Tomcat en NetBeans.

### 2. Crear la base de datos

En MySQL Workbench abre `BD_Sistema Decision Gerencial.sql` y ejecútalo sobre una instancia de desarrollo. Crea el esquema, sus restricciones y el catálogo de seis dimensiones y 30 preguntas.

El script está pensado para una instalación nueva. No lo ejecutes sobre una base existente sin revisar su contenido. No incluye respuestas de empresas, datos personales ni una cuenta con contraseña predeterminada.

### 3. Configurar la conexión de forma privada

En Windows, desde PowerShell situado en la carpeta del proyecto:

```powershell
New-Item -ItemType Directory -Force "$env:USERPROFILE\.mypes"
Copy-Item ".\config\database.example.properties" "$env:USERPROFILE\.mypes\database.properties"
notepad "$env:USERPROFILE\.mypes\database.properties"
```

Completa `db.user` y `db.password` con los datos de tu MySQL. Ajusta `db.url` si cambian el host, el puerto o el esquema. Este archivo permanece fuera del repositorio y no se incorpora al WAR. Cada equipo o cuenta que ejecute Tomcat necesita su propia configuración.

También puedes usar variables de entorno `SYSDB_URL`, `SYSDB_USER` y `SYSDB_PASSWORD` en el proceso que ejecuta la aplicación. Las variables de entorno tienen prioridad sobre el archivo. `SYSDB_CONFIG` permite indicar otra ruta de archivo privado. Las propiedades Java `sysdb.url`, `sysdb.user`, `sysdb.password` y `sysdb.config` tienen prioridad sobre las variables equivalentes. Reinicia NetBeans o Tomcat si necesitas que reciban variables de entorno nuevas.

La URL de ejemplo corresponde a un entorno local de desarrollo. La configuración de red, TLS y credenciales de un servidor publicado debe ajustarse al entorno de despliegue.

### 4. Compilar

En NetBeans utiliza **Clean and Build**. También puedes ejecutar:

```text
mvn clean package
```

El archivo desplegable se genera en `target/Proyecto_Sistema_Decision_Gerenciales_IA-1.0-SNAPSHOT.war`.

### 5. Crear el primer administrador

El script distribuido no crea cuentas de acceso. Después de compilar, abre una terminal interactiva en la carpeta del proyecto y ejecuta en Windows:

```text
java -cp "target/classes;target/Proyecto_Sistema_Decision_Gerenciales_IA-1.0-SNAPSHOT/WEB-INF/lib/*" util.CrearAdministrador admin
```

La utilidad solicita una contraseña y su confirmación, y guarda un hash BCrypt. No escribas la contraseña como argumento del comando ni dentro del SQL. El nombre de usuario debe ser único. En Linux o macOS utiliza `:` como separador del classpath y adapta las rutas.

La cuenta de la aplicación es independiente de la cuenta de MySQL. En una instalación ya existente, las cuentas y sus contraseñas permanecen en la base local; subir el código no modifica esa base.

### 6. Ejecutar

En NetBeans selecciona **Run**, asigna el servidor Tomcat y abre:

```text
http://localhost:8080/Proyecto_Sistema_Decision_Gerenciales_IA/
```

También puedes desplegar el WAR en un Tomcat configurado por separado. Si el nombre de contexto es diferente, ajusta la dirección del navegador. Bootstrap y los iconos utilizados por algunas pantallas se descargan desde CDN; se requiere acceso a esos recursos para su presentación completa.

## Pruebas

La prueba unitaria del motor se ejecuta durante `mvn test`. La prueba de integración histórica está desactivada por defecto porque requiere una base de pruebas preparada con una evaluación de identificador 3 y sus resultados.

Para ejecutarla explícitamente en PowerShell:

```powershell
$env:MYPES_RUN_DB_TESTS = "true"
mvn test
Remove-Item Env:MYPES_RUN_DB_TESTS
```

Utiliza una base de pruebas aislada y configura su conexión; la integración escribe un análisis. Una compilación con esa prueba omitida no equivale a una verificación de integración aprobada.

## Alcance de GitHub

Este repositorio conserva el código y el SQL de instalación. No aloja el servidor Java ni la base MySQL, no publica la aplicación por sí mismo y no contiene credenciales de la instalación local. Los parámetros privados deben configurarse por separado en cada equipo.
