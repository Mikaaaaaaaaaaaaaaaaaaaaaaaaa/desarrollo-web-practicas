DROP DATABASE ParcialUno;
CREATE DATABASE ParcialUno;
USE ParcialUno;

DROP TABLE IF EXISTS COMPETENCIA;
CREATE TABLE COMPETENCIA (
    identificador INTEGER AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(20),
    fecha_realizacion DATE,
    lugar VARCHAR(20),
    reglas INTEGER
);

DROP TABLE IF EXISTS PERRO;
CREATE TABLE PERRO (
	codigo INTEGER PRIMARY KEY,
    nombre VARCHAR(20),
    fecha_nacimiento DATE,
    estimacion_error VARCHAR(20)
);

INSERT INTO PERRO (codigo, nombre, fecha_nacimiento, estimacion_error)
VALUES (5, 'Fido', '2020-01-01', 0);

-- DDL
-- Crear únicamente la tabla PODIO.
DROP TABLE IF EXISTS PODIO;
CREATE TABLE PODIO (
	identificador INTEGER,
    posicion INTEGER,
    foto BLOB,
    tiempo TIME,
    perro INTEGER,
    
    PRIMARY KEY (identificador, posicion),
    
    FOREIGN KEY (identificador) REFERENCES COMPETENCIA(identificador),
    FOREIGN KEY (perro) REFERENCES PERRO(codigo)
);

-- DDL
-- Si queremos agregar una tabla SEXO (id, descripcion) ¿cómo deberemos alterar el modelo? Escribir todos los SQL.
DROP TABLE IF EXISTS SEXO;
CREATE TABLE SEXO (
	identificador INTEGER PRIMARY KEY,
	descripcion VARCHAR(50)
);

ALTER TABLE PERRO
ADD COLUMN sexo INTEGER;

ALTER TABLE PERRO
ADD FOREIGN KEY (sexo) REFERENCES SEXO(identificador);

SELECT * FROM PERRO;

-- ABM
-- Ingresar una nueva enfermedad degenerativa que no sea visible y con marca de no obligatoria.
DROP TABLE IF EXISTS CARACTERISTICA;
CREATE TABLE CARACTERISTICA (
	codigo INTEGER PRIMARY KEY,
    descripcion VARCHAR(50),
    marca VARCHAR(50)
);

INSERT INTO CARACTERISTICA(codigo, descripcion, marca)
VALUES(5, 'Enfermedad no degenerativa no visible.', 'No obligatoria.');

SELECT * FROM CARACTERISTICA;

DROP TABLE IF EXISTS FISICA;
CREATE TABLE FISICA (
	codigo INTEGER PRIMARY KEY,
    visibilidad BOOLEAN,
    
    FOREIGN KEY (codigo) REFERENCES CARACTERISTICA(codigo) 
);

INSERT INTO FISICA (codigo, visibilidad)
VALUES (5, FALSE);

SELECT * FROM FISICA;

DROP TABLE IF EXISTS ENFERMEDAD;
CREATE TABLE ENFERMEDAD (
	codigo INTEGER PRIMARY KEY,
    explicacion VARCHAR(50),
    tratamiento VARCHAR(50),
    
    FOREIGN KEY (codigo) REFERENCES FISICA(codigo)
);

INSERT INTO ENFERMEDAD (codigo, explicacion, tratamiento)
VALUES (5, 'Enfermedad no degenerativa.', 'Tratamiento no obligatorio.');

SELECT * FROM ENFERMEDAD;

-- ABM
-- ¿Qué sucede si quiero borrar el color rojo? Explicar detalladamente cómo lograrlo.
DROP TABLE IF EXISTS COLOR;
CREATE TABLE COLOR (
	identificador INTEGER PRIMARY KEY,
    descripcion VARCHAR(50)
);

INSERT INTO COLOR (identificador, descripcion)
VALUES (5, 'Rojo');

SELECT * FROM COLOR;

DROP TABLE IF EXISTS PERRO_FISICA;
CREATE TABLE PERRO_FISICA (
	codigo INTEGER,
    perro INTEGER,
    color INTEGER,
    detalle VARCHAR(50),
    
    PRIMARY KEY (codigo, perro, color),
    
    FOREIGN KEY (codigo) REFERENCES FISICA(codigo), 
    FOREIGN KEY (perro) REFERENCES PERRO(codigo),
    FOREIGN KEY (color) REFERENCES COLOR(identificador)
);

INSERT INTO PERRO_FISICA (codigo, perro, color, detalle)
VALUES (5, 5, 5, 'Detalle...');

DELETE FROM PERRO_FISICA
WHERE color = 5;

DELETE FROM COLOR
WHERE identificador = 5;

SELECT * FROM PERRO_FISICA;
SELECT * FROM COLOR;

-- DML
-- Mostrar los perros cuya fecha de nacimiento tenga el mínimo error.
INSERT INTO PERRO (codigo, nombre, fecha_nacimiento, estimacion_error)
VALUES (1, 'Fido', '2020-01-01', 'Bajo'),
	   (2, 'Rex',  '2019-06-01', 'Alto'),
	   (3, 'Luna', '2021-03-01', 'Medio');

SELECT p.nombre, p.fecha_nacimiento
FROM PERRO p
WHERE estimacion_error = (SELECT MIN(estimacion_error) FROM PERRO);

-- DML
-- Realizar un reporte donde se muestren todas las enfermedades de cada perro.
INSERT INTO CARACTERISTICA(codigo, descripcion, marca)
VALUES (1, 'Enfermedad degenerativa', 'No obligatoria');

INSERT INTO FISICA(codigo, visibilidad)
VALUES (1, TRUE);

INSERT INTO PERRO(codigo, nombre, fecha_nacimiento, estimacion_error, sexo)
VALUES (10, 'Fido', '2020-05-10', '0.05', NULL);

INSERT INTO COLOR(identificador, descripcion)
VALUES (1, 'Rojo');

INSERT INTO PERRO_FISICA(codigo, perro, color, detalle)
VALUES (1, 10, 1, 'Detalle...');

INSERT INTO ENFERMEDAD(codigo, explicacion, tratamiento)
VALUES (1, 'Problema degenerativo', 'Tratamiento no obligatorio');

SELECT pr.nombre AS Perro, enf.explicacion AS Enfermedad, enf.tratamiento AS Tratamiento
FROM PERRO pr
JOIN PERRO_FISICA pf ON pf.perro = pr.codigo
JOIN FISICA f ON pf.codigo = f.codigo
JOIN CARACTERISTICA car ON f.codigo = car.codigo
JOIN ENFERMEDAD enf ON enf.codigo = f.codigo;

-- DML
-- ¿Cómo podemos averiguar el nombre de los perros que hayan participado de todas las competencias?.
DROP TABLE IF EXISTS PARTICIPAN;
CREATE TABLE PARTICIPAN (
	codigo INTEGER,
    identificador INTEGER,
    
    PRIMARY KEY (codigo, identificador)
);

SELECT p.nombre AS 'Perros que participaron'
FROM PERRO p
WHERE NOT EXISTS (
	SELECT com.identificador
    FROM COMPETENCIA com
    WHERE NOT EXISTS (
		SELECT 1
        FROM PARTICIPAN par
        WHERE par.codigo = p.codigo AND par.identificador = com.identificador
    )
);

-- DML
-- ¿Cuáles son las competencias con más de 20 perros participantes que se hayan realizado durante el último año?.
SELECT com.nombre
FROM COMPETENCIA com
JOIN PARTICIPAN par ON par.identificador = com.identificador
WHERE com.fecha_realizacion BETWEEN '2024-12-30' AND '2025-11-23'
GROUP BY com.nombre
HAVING COUNT(par.codigo) > 20;

-- DML
-- ¿Es verdad que los perros que ganan en las competencias son dóciles? Considerar que tenga la conducta sin importar con cuántas estrellas.
CREATE TABLE CONDUCTA (
	codigo INTEGER PRIMARY KEY,
    alto BOOLEAN,
    bajo BOOLEAN,
    
    FOREIGN KEY (codigo) REFERENCES CARACTERISTICA(codigo)
);

CREATE TABLE PERRO_CONDUCTA (
	codigo INTEGER,
    perro INTEGER,
    estrellas INTEGER,
    
    PRIMARY KEY (codigo, perro),
    FOREIGN KEY (codigo) REFERENCES CONDUCTA(codigo),
    FOREIGN KEY (perro) REFERENCES PERRO(codigo)
);

SELECT DISTINCT perr.nombre
FROM PERRO perr
JOIN PODIO pod ON pod.perro = perr.codigo
JOIN COMPETENCIA com ON pod.identificador = com.identificador
JOIN PERRO_CONDUCTA pcon ON pcon.perro = perr.codigo
JOIN CONDUCTA con ON pcon.codigo = con.codigo
JOIN CARACTERISTICA car ON con.codigo = car.codigo
WHERE POSICION = 1 AND car.descripcion = 'Dócil';

-- DML
-- Si la pregunta anterior es no ¿qué otras características de conducta suelen tener?.
SELECT DISTINCT car.descripcion
FROM PERRO perr
JOIN PODIO pod ON pod.perro = perr.codigo
JOIN COMPETENCIA com ON pod.identificador = com.identificador
JOIN PERRO_CONDUCTA pcon ON pcon.perro = perr.codigo
JOIN CONDUCTA con ON pcon.codigo = con.codigo
JOIN CARACTERISTICA car ON con.codigo = car.codigo
WHERE pod.posicion = 1 AND car.descripcion != 'Dócil';

-- DML
-- ¿Cuál es la edad promedio de los perros que están en adopción?
-- Crear una vista PERRO_EDAD que considere edad= (hoy – fecha_nacimiento)*(1 + error), y luego usar la vista para resolver la pregunta.
CREATE VIEW PERRO_EDAD AS
SELECT codigo, nombre,
(YEAR(NOW()) - YEAR(fecha_nacimiento)) * (1 + estimacion_error) AS EDAD
FROM PERRO;

SELECT AVG(EDAD) AS edad_promedio
FROM PERRO_EDAD;