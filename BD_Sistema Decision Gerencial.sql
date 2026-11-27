CREATE DATABASE sistema_decision_gerencial
CHARACTER SET utf8mb4
COLLATE utf8mb4_unicode_ci;

USE sistema_decision_gerencial;

CREATE TABLE usuarios (
    id_usuario INT AUTO_INCREMENT PRIMARY KEY,
    nombres VARCHAR(100) NOT NULL,
    apellidos VARCHAR(100) NOT NULL,
    usuario VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    rol VARCHAR(30) NOT NULL DEFAULT 'GERENTE',
    estado TINYINT(1) NOT NULL DEFAULT 1,
    fecha_registro TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE empresas (
    id_empresa INT AUTO_INCREMENT PRIMARY KEY,
    razon_social VARCHAR(150) NOT NULL,
    ruc VARCHAR(11) NOT NULL UNIQUE,
    sector VARCHAR(100),
    direccion VARCHAR(200),
    telefono VARCHAR(20),
    correo VARCHAR(100),
    estado TINYINT(1) NOT NULL DEFAULT 1,
    fecha_registro TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

USE sistema_decision_gerencial;



CREATE TABLE evaluaciones (
    id_evaluacion INT AUTO_INCREMENT PRIMARY KEY,
    id_empresa INT NOT NULL,
    id_usuario INT NOT NULL,
    fecha_evaluacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    observaciones TEXT,
    estado VARCHAR(30) NOT NULL DEFAULT 'PENDIENTE',

    CONSTRAINT fk_evaluacion_empresa
        FOREIGN KEY (id_empresa)
        REFERENCES empresas(id_empresa),

    CONSTRAINT fk_evaluacion_usuario
        FOREIGN KEY (id_usuario)
        REFERENCES usuarios(id_usuario)
);

CREATE TABLE indicadores (
    id_indicador INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(150) NOT NULL,
    descripcion TEXT,
    categoria VARCHAR(100),
    peso DECIMAL(5,2) DEFAULT 1.00,
    estado TINYINT(1) NOT NULL DEFAULT 1,
    fecha_registro TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

INSERT INTO indicadores
(nombre, descripcion, categoria, peso)
VALUES
(
    'Gestión financiera',
    'Evalúa el manejo y control de los recursos financieros de la empresa.',
    'Finanzas',
    1.00
),
(
    'Gestión operativa',
    'Evalúa la eficiencia de los procesos y actividades internas.',
    'Operaciones',
    1.00
),
(
    'Gestión comercial',
    'Evalúa la gestión de ventas, clientes y estrategias comerciales.',
    'Comercial',
    1.00
),
(
    'Gestión tecnológica',
    'Evalúa el uso y aprovechamiento de herramientas tecnológicas.',
    'Tecnología',
    1.00
),
(
    'Gestión del talento humano',
    'Evalúa la gestión y organización del personal.',
    'Recursos Humanos',
    1.00
),
(
    'Planificación estratégica',
    'Evalúa la planificación, objetivos y seguimiento de la empresa.',
    'Estrategia',
    1.00
);

CREATE TABLE preguntas (
    id_pregunta INT AUTO_INCREMENT PRIMARY KEY,
    id_indicador INT NOT NULL,
    pregunta TEXT NOT NULL,
    peso DECIMAL(5,2) NOT NULL DEFAULT 1.00,
    estado TINYINT(1) NOT NULL DEFAULT 1,
    fecha_registro TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_pregunta_indicador
        FOREIGN KEY (id_indicador)
        REFERENCES indicadores(id_indicador)
);

CREATE TABLE respuestas (
    id_respuesta INT AUTO_INCREMENT PRIMARY KEY,
    id_evaluacion INT NOT NULL,
    id_pregunta INT NOT NULL,
    valor INT NOT NULL,
    observacion TEXT,

    CONSTRAINT fk_respuesta_evaluacion
        FOREIGN KEY (id_evaluacion)
        REFERENCES evaluaciones(id_evaluacion),

    CONSTRAINT fk_respuesta_pregunta
        FOREIGN KEY (id_pregunta)
        REFERENCES preguntas(id_pregunta),

    CONSTRAINT uq_respuesta_evaluacion_pregunta UNIQUE (id_evaluacion, id_pregunta),

    CONSTRAINT chk_valor_respuesta
        CHECK (valor BETWEEN 1 AND 5)
);


INSERT INTO preguntas
(id_indicador, pregunta, peso)
VALUES
(
    1,
    '¿La empresa registra periódicamente sus ingresos y gastos?',
    1.00
),
(
    1,
    '¿La empresa mantiene información actualizada sobre su situación financiera?',
    1.00
),
(
    1,
    '¿La empresa realiza seguimiento de su flujo de caja?',
    1.00
),
(
    1,
    '¿La empresa establece presupuestos para sus principales actividades?',
    1.00
),
(
    1,
    '¿La empresa utiliza información financiera para apoyar sus decisiones?',
    1.00
);


INSERT INTO preguntas
(id_indicador, pregunta, peso)
VALUES
(
    2,
    '¿La empresa tiene definidos sus principales procesos operativos?',
    1.00
),
(
    2,
    '¿La empresa realiza seguimiento al cumplimiento de sus actividades?',
    1.00
),
(
    2,
    '¿La empresa identifica y corrige problemas que afectan sus operaciones?',
    1.00
),
(
    2,
    '¿La empresa controla los tiempos necesarios para realizar sus actividades?',
    1.00
),
(
    2,
    '¿La empresa busca mejorar continuamente sus procesos internos?',
    1.00
);


INSERT INTO preguntas
(id_indicador, pregunta, peso)
VALUES
(
    3,
    '¿La empresa realiza seguimiento de sus ventas?',
    1.00
),
(
    3,
    '¿La empresa conoce las principales necesidades de sus clientes?',
    1.00
),
(
    3,
    '¿La empresa realiza seguimiento a la satisfacción de sus clientes?',
    1.00
),
(
    3,
    '¿La empresa establece objetivos comerciales?',
    1.00
),
(
    3,
    '¿La empresa utiliza información de ventas para tomar decisiones?',
    1.00
);


INSERT INTO preguntas
(id_indicador, pregunta, peso)
VALUES
(
    4,
    '¿La empresa utiliza herramientas tecnológicas para apoyar sus actividades?',
    1.00
),
(
    4,
    '¿La empresa utiliza sistemas para almacenar y consultar información?',
    1.00
),
(
    4,
    '¿La empresa aprovecha herramientas digitales para mejorar sus procesos?',
    1.00
),
(
    4,
    '¿La empresa protege adecuadamente la información que administra?',
    1.00
),
(
    4,
    '¿La empresa evalúa nuevas tecnologías que puedan mejorar su gestión?',
    1.00
);


INSERT INTO preguntas
(id_indicador, pregunta, peso)
VALUES
(
    5,
    '¿La empresa tiene definidas las funciones de sus trabajadores?',
    1.00
),
(
    5,
    '¿La empresa realiza seguimiento al desempeño de sus trabajadores?',
    1.00
),
(
    5,
    '¿La empresa brinda capacitación o formación a sus trabajadores?',
    1.00
),
(
    5,
    '¿La empresa mantiene una comunicación adecuada con sus trabajadores?',
    1.00
),
(
    5,
    '¿La empresa considera las capacidades del personal al asignar actividades?',
    1.00
);


INSERT INTO preguntas
(id_indicador, pregunta, peso)
VALUES
(
    6,
    '¿La empresa tiene definidos objetivos para orientar sus actividades?',
    1.00
),
(
    6,
    '¿La empresa establece acciones para alcanzar sus objetivos?',
    1.00
),
(
    6,
    '¿La empresa realiza seguimiento al cumplimiento de sus objetivos?',
    1.00
),
(
    6,
    '¿La empresa utiliza información para planificar sus actividades?',
    1.00
),
(
    6,
    '¿La empresa adapta sus planes cuando cambian sus condiciones internas o externas?',
    1.00
);

CREATE TABLE resultados (
    id_resultado INT AUTO_INCREMENT PRIMARY KEY,
    id_evaluacion INT NOT NULL,
    indice_general DECIMAL(5,2) NOT NULL,
    nivel VARCHAR(50) NOT NULL,
    fecha_resultado TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_resultado_evaluacion
        FOREIGN KEY (id_evaluacion)
        REFERENCES evaluaciones(id_evaluacion),

    CONSTRAINT uk_resultado_evaluacion
        UNIQUE (id_evaluacion)
);

CREATE TABLE resultado_indicadores (
    id_resultado_indicador INT AUTO_INCREMENT PRIMARY KEY,
    id_resultado INT NOT NULL,
    id_indicador INT NOT NULL,
    puntaje DECIMAL(5,2) NOT NULL,
    nivel VARCHAR(50) NOT NULL,

    CONSTRAINT fk_resultado_indicador_resultado
        FOREIGN KEY (id_resultado)
        REFERENCES resultados(id_resultado),

    CONSTRAINT fk_resultado_indicador_indicador
        FOREIGN KEY (id_indicador)
        REFERENCES indicadores(id_indicador),

    CONSTRAINT uk_resultado_indicador
        UNIQUE (id_resultado, id_indicador)
);

describe resultado_indicadores;

DESCRIBE evaluaciones;

SELECT * FROM indicadores;

SELECT * from preguntas;

SELECT 
    id_pregunta,
    id_indicador,
    pregunta,
    estado
FROM preguntas
ORDER BY id_indicador, id_pregunta;

SELECT COUNT(*) AS total
FROM preguntas
WHERE estado = 1;

SELECT
    id_respuesta,
    id_evaluacion,
    id_pregunta,
    valor,
    observacion
FROM respuestas
WHERE id_evaluacion = 1
ORDER BY id_pregunta;

SELECT COUNT(*)
FROM respuestas
WHERE id_evaluacion = 2;

SELECT
    id_evaluacion,
    estado
FROM evaluaciones
ORDER BY id_evaluacion DESC;

SELECT
    id_evaluacion,
    id_empresa,
    id_usuario,
    estado
FROM evaluaciones
ORDER BY id_evaluacion DESC;

SELECT
    id_evaluacion,
    COUNT(*) AS cantidad_respuestas
FROM respuestas
GROUP BY id_evaluacion
ORDER BY id_evaluacion DESC;

SELECT
    id_evaluacion,
    estado
FROM evaluaciones
ORDER BY id_evaluacion DESC;

SELECT
    id_evaluacion,
    COUNT(*) AS respuestas
FROM respuestas
GROUP BY id_evaluacion
ORDER BY id_evaluacion DESC;

SELECT
    id_resultado,
    id_evaluacion,
    indice_general,
    nivel
FROM resultados
ORDER BY id_resultado DESC;

SELECT
    ri.id_resultado_indicador,
    ri.id_resultado,
    i.nombre,
    ri.puntaje,
    ri.nivel
FROM resultado_indicadores ri
INNER JOIN indicadores i
    ON ri.id_indicador = i.id_indicador
ORDER BY ri.id_resultado_indicador DESC;

CREATE TABLE analisis_ia (
    id_analisis INT AUTO_INCREMENT PRIMARY KEY,
    id_resultado INT NOT NULL,
    resumen TEXT NOT NULL,
    fortalezas TEXT,
    debilidades TEXT,
    recomendaciones TEXT,
    fecha_analisis TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_analisis_resultado
        FOREIGN KEY (id_resultado)
        REFERENCES resultados(id_resultado),

    CONSTRAINT uk_analisis_resultado
        UNIQUE (id_resultado)
);

SELECT *
FROM analisis_ia;

SELECT *
FROM resultados;