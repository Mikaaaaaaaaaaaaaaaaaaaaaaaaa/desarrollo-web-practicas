DROP DATABASE WhiteoutSurvivalFinal;
CREATE DATABASE WhiteoutSurvivalFinal;
USE WhiteoutSurvivalFinal;

CREATE TABLE CUENTA (
	email VARCHAR(50) PRIMARY KEY,
    celular VARCHAR(20),
    nombre_usuario VARCHAR(50),
    contrasenia VARCHAR(20),
    fecha_creacion_cuenta DATE,
    hora_ultima_conexion DATETIME
);

CREATE TABLE GOBERNADOR (
	identificador_gobernador INTEGER PRIMARY KEY,
    nombre VARCHAR(20),
    avatar VARCHAR(20),
    alianza INTEGER,
    region INTEGER
);

CREATE TABLE COLONIA (
	identificador_colonia INTEGER PRIMARY KEY,
    descripcion VARCHAR(20),
    email VARCHAR(50),
    identificador_gobernador INTEGER,
    
    FOREIGN KEY (email) REFERENCES CUENTA(email),
    FOREIGN KEY (identificador_gobernador) REFERENCES GOBERNADOR(identificador_gobernador)
);

CREATE TABLE HEROE (
	identificador_gobernador INTEGER,
    codigo_heroe INTEGER,
    especialidad VARCHAR(20),
    talento VARCHAR(20),
    nombre VARCHAR(20),
    nivel_alcanzado INTEGER,
    seniority VARCHAR(20),
    arma VARCHAR(20),
    cantidad_estrellas INTEGER,
    cantidad_escoltas INTEGER,
    capacidad_tropas INTEGER,
    
    PRIMARY KEY (identificador_gobernador, codigo_heroe),
    FOREIGN KEY (identificador_gobernador) REFERENCES GOBERNADOR(identificador_gobernador)
);

CREATE TABLE TIPO_RAREZA_EQUIPO_ESPECIFICO (
	identificador_tipo_rareza INTEGER PRIMARY KEY,
    descripcion VARCHAR(20)
);

CREATE TABLE EQUIPO_ESPECIFICO (
	identificador_equipo_especifico INTEGER PRIMARY KEY,
    cantidad_estrellas INTEGER,
    nivel_talisman INTEGER,
    identificador_gobernador INTEGER,
    identificador_tipo_rareza INTEGER,
    
	FOREIGN KEY (identificador_gobernador) REFERENCES GOBERNADOR (identificador_gobernador),
    FOREIGN KEY (identificador_tipo_rareza) REFERENCES TIPO_RAREZA_EQUIPO_ESPECIFICO (identificador_tipo_rareza)
);

CREATE TABLE TIPO_RAREZA (
	identificador_rareza INTEGER PRIMARY KEY,
    nombre_rareza VARCHAR(20),
    color_rareza VARCHAR(20)
);

CREATE TABLE EQUIPO_HEROE (
	identificador_gobernador INTEGER PRIMARY KEY,
    tipo_equipo VARCHAR(20),
    especialidad VARCHAR(20),
    nivel INTEGER,
    codigo_heroe INTEGER,
    identificador_rareza INTEGER,
    
	FOREIGN KEY (identificador_gobernador, codigo_heroe) REFERENCES HEROE (identificador_gobernador, codigo_heroe),
    FOREIGN KEY (identificador_rareza) REFERENCES TIPO_RAREZA (identificador_rareza)
);

CREATE TABLE CALENTADOR (
	identificador_colonia INTEGER PRIMARY KEY,
    nivel_calor INTEGER,
    potencia_maxima INTEGER,
    encendido BOOLEAN,
    
    FOREIGN KEY (identificador_colonia) REFERENCES COLONIA(identificador_colonia)
);

-- SQL
-- Generar una vista con el tiempo transcurrido sin conexión para la cuenta propia.
-- Se interpreta como el tiempo ocurrido desde la última conexión hasta el momento.
-- Mostrar además el nombre del gobernador y un mensaje de bienvenida.
INSERT INTO CUENTA (email, celular, nombre_usuario, contrasenia, fecha_creacion_cuenta, hora_ultima_conexion)
VALUES ('Evangelina@gmail.com', '999999999', 'Evangelina', 'Contrasenia', '2026-01-01', '2026-02-10 15:30:00');

INSERT INTO GOBERNADOR (identificador_gobernador, nombre, avatar, alianza, region)
VALUES (1, 'Evangelina', 'Avatar_fuego', 5, 5);

INSERT INTO COLONIA (identificador_colonia, descripcion, email, identificador_gobernador)
VALUES (1, 'descripcion', 'Evangelina@gmail.com', 1);

CREATE VIEW TIEMPO_TRANSCURRIDO_SIN_CONEXION AS
	SELECT CONCAT('Bienvenido ', gob.nombre) AS mensaje,
    NOW() - cue.hora_ultima_conexion AS tiempo_transcurrido
FROM CUENTA cue
JOIN COLONIA col ON col.email = cue.email
JOIN GOBERNADOR gob ON col.identificador_gobernador = gob.identificador_gobernador;

SELECT * FROM TIEMPO_TRANSCURRIDO_SIN_CONEXION;

-- Crear la tabla que guarde los requerimientos de los personajes o héroes.
CREATE TABLE REQUERIMIENTO_HEROE (
	identificador_requerimiento INTEGER PRIMARY KEY,
    descripcion VARCHAR(20)
);

-- Mostrar los gobernadores y cuentas que tengan todos sus equipos con nivel mayor a 2.
INSERT INTO TIPO_RAREZA_EQUIPO_ESPECIFICO (identificador_tipo_rareza, descripcion)
VALUES (1, 'raro');

INSERT INTO EQUIPO_ESPECIFICO (identificador_equipo_especifico, cantidad_estrellas, nivel_talisman, identificador_gobernador, identificador_tipo_rareza)
VALUES (1, 1, 5, 1, 1);

-- Primera forma.
SELECT gob.nombre, cue.nombre_usuario
FROM CUENTA cue
JOIN COLONIA col ON col.email = cue.email
JOIN GOBERNADOR gob ON col.identificador_gobernador = gob.identificador_gobernador
JOIN EQUIPO_ESPECIFICO ee ON ee.identificador_gobernador = gob.identificador_gobernador
GROUP BY gob.nombre, cue.nombre_usuario
HAVING MIN(ee.nivel_talisman) > 2;

-- Segunda forma.
SELECT gob.nombre, cue.nombre_usuario
FROM CUENTA cue
JOIN COLONIA col ON col.email = cue.email
JOIN GOBERNADOR gob ON col.identificador_gobernador = gob.identificador_gobernador
WHERE NOT EXISTS (
	SELECT 1
    FROM EQUIPO_ESPECIFICO ee
    WHERE ee.identificador_gobernador = gob.identificador_gobernador AND ee.nivel_talisman <= 2
);

-- Encontrar a los gobernadores cuyos principales 5 héroes más poderosos, sean los 5 más poderosos de toda la región #1060.
INSERT INTO GOBERNADOR (identificador_gobernador, nombre, avatar, alianza, region)
VALUES (15, 'EvangelinaUno', 'Avatar_fuego', 15, 1060);

INSERT INTO HEROE (identificador_gobernador, codigo_heroe, especialidad, talento, nombre, nivel_alcanzado, seniority, arma, cantidad_estrellas, cantidad_escoltas, capacidad_tropas)
VALUES (15, 105, 'Programador', 'Sigilo', 'MikaUno', 5, 'Senior', 'AK47', 15, 10, 50);

INSERT INTO GOBERNADOR (identificador_gobernador, nombre, avatar, alianza, region)
VALUES (20, 'EvangelinaDos', 'Avatar_fuego', 15, 1060);

INSERT INTO HEROE (identificador_gobernador, codigo_heroe, especialidad, talento, nombre, nivel_alcanzado, seniority, arma, cantidad_estrellas, cantidad_escoltas, capacidad_tropas)
VALUES (20, 105, 'Programador', 'Sigilo', 'MikaDos', 5, 'Senior', 'AK47', 10, 10, 50);


SELECT gob.nombre AS NOMBRE_GOBERNADOR, h.nombre as NOMBRE_HEROE
FROM GOBERNADOR gob
JOIN HEROE h ON h.identificador_gobernador = gob.identificador_gobernador
WHERE gob.region = 1060
ORDER BY h.cantidad_estrellas DESC
LIMIT 5;

-- Mostrar los gobernadores y las cuentas, que posean todos los equipos de gobernador disponibles (sin importar el nivel) pero que sean épicos.

INSERT INTO CUENTA (email, celular, nombre_usuario, contrasenia, fecha_creacion_cuenta, hora_ultima_conexion)
VALUES ('Evangelina15@gmail.com', '999999999', 'Evangelina', 'Contrasenia', '2026-01-01', '2026-02-10 15:30:00');

INSERT INTO GOBERNADOR (identificador_gobernador, nombre, avatar, alianza, region)
VALUES (500, 'Evangelina', 'Avatar_fuego', 5, 5);

INSERT INTO COLONIA (identificador_colonia, descripcion, email, identificador_gobernador)
VALUES (555, 'descripcion', 'Evangelina15@gmail.com', 500);

INSERT INTO TIPO_RAREZA_EQUIPO_ESPECIFICO (identificador_tipo_rareza, descripcion)
VALUES (999, 'epico');

INSERT INTO EQUIPO_ESPECIFICO (identificador_equipo_especifico, cantidad_estrellas, nivel_talisman, identificador_gobernador, identificador_tipo_rareza)
VALUES (99999, 5, 5, 500, 999);

-- Primera forma.
SELECT gob.nombre, cue.nombre_usuario
FROM CUENTA cue
JOIN COLONIA col ON col.email = cue.email
JOIN GOBERNADOR gob ON col.identificador_gobernador = gob.identificador_gobernador
JOIN EQUIPO_ESPECIFICO ee ON ee.identificador_gobernador = gob.identificador_gobernador
JOIN TIPO_RAREZA_EQUIPO_ESPECIFICO tr ON ee.identificador_tipo_rareza = tr.identificador_tipo_rareza
WHERE tr.descripcion = 'epico'
GROUP BY gob.nombre, cue.nombre_usuario
HAVING COUNT(ee.identificador_equipo_especifico) = COUNT(ee.identificador_equipo_especifico);

-- Segunda forma.
SELECT gob.nombre, cue.nombre_usuario
FROM CUENTA cue
JOIN COLONIA col ON col.email = cue.email
JOIN GOBERNADOR gob ON col.identificador_gobernador = gob.identificador_gobernador
WHERE NOT EXISTS (
	SELECT 1
    FROM EQUIPO_ESPECIFICO ee
    JOIN TIPO_RAREZA_EQUIPO_ESPECIFICO tr ON ee.identificador_tipo_rareza = tr.identificador_tipo_rareza
    WHERE ee.identificador_gobernador = gob.identificador_gobernador AND tr.descripcion != 'epico'
);
