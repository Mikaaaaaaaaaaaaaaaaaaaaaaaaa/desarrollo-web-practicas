DROP DATABASE ParcialCinco;
CREATE DATABASE ParcialCinco;
USE ParcialCinco;

CREATE TABLE PAIS (
	codigo VARCHAR(50) PRIMARY KEY,
    nombre VARCHAR(50)
);

CREATE TABLE JUGADOR (
	documento VARCHAR(50) PRIMARY KEY,
    nombre_completo VARCHAR(50),
    fecha_nacimiento DATE,
    biografia VARCHAR(50),
    pais VARCHAR(50),
    
    FOREIGN KEY (pais) REFERENCES PAIS(codigo)
);

CREATE TABLE SOFTWARE (
	documento VARCHAR(50) PRIMARY KEY,
    empresa VARCHAR(50),
    version INTEGER,

	FOREIGN KEY (documento) REFERENCES JUGADOR(documento)
);

CREATE TABLE PARTIDA (
	codigo VARCHAR(50) PRIMARY KEY,
    pais VARCHAR(50),
    fecha DATE,
    duracion INTEGER,
    
    FOREIGN KEY (pais) REFERENCES PAIS(codigo)
);

-- 1. DDL
-- a. Crear únicamente la tabla JUEGA.
CREATE TABLE JUEGA (
	jugador VARCHAR(50),
    partida VARCHAR(50),
    
    PRIMARY KEY (jugador, partida),
    
    FOREIGN KEY (jugador) REFERENCES JUGADOR(documento),
    FOREIGN KEY (partida) REFERENCES PARTIDA(codigo)
);

CREATE TABLE PIEZA (
	abrev VARCHAR(50) PRIMARY KEY,
    nombre VARCHAR(50)
);

CREATE TABLE MOVIMIENTO (
	codigo VARCHAR(50),
    numero INTEGER,
    pos_desde DATE,
    pos_hasta DATE,
    tiempo VARCHAR(50),
    
    jugador VARCHAR(50),
    mueve VARCHAR(50),
    come VARCHAR(50),
    
    jaque VARCHAR(50),
    mate VARCHAR(50),
    
    PRIMARY KEY (codigo, numero),
    
    FOREIGN KEY (codigo) REFERENCES PARTIDA(codigo),
    FOREIGN KEY (jugador) REFERENCES JUGADOR(documento),
    FOREIGN KEY (mueve) REFERENCES PIEZA(abrev),
    FOREIGN KEY (come) REFERENCES PIEZA(abrev)
);

CREATE TABLE ANALISIS (
	identificador INTEGER PRIMARY KEY,
    partida VARCHAR(50),
    titulo VARCHAR(50),
    fecha DATE,
    autor VARCHAR(50),
    
    FOREIGN KEY (partida) REFERENCES PARTIDA(codigo)
);

CREATE TABLE PARRAFO (
	identificador INTEGER,
    orden INTEGER,
    texto VARCHAR(50),
    codigo_mov VARCHAR(50),
    numero_mov INTEGER,
    
    PRIMARY KEY(identificador, orden),
    FOREIGN KEY (codigo_mov, numero_mov) REFERENCES MOVIMIENTO(codigo, numero)
);

-- 1. DDL
-- b. Si queremos agregar una tabla POSICION(letra, numero), como deberemos alterar la tabla MOVIMIENTO.
CREATE TABLE POSICION (
	letra_posicion CHAR(1),
    numero_posicion INTEGER,
    
    PRIMARY KEY (letra_posicion, numero_posicion)
);

ALTER TABLE MOVIMIENTO
ADD letra_agregada CHAR(1),
ADD numero_agregado INTEGER,

ADD FOREIGN KEY (letra_agregada, numero_agregado) REFERENCES POSICION(letra_posicion, numero_posicion);

-- 2. ABM
-- c. Ingrésese a sí mismo como jugador (omita la biografía).
INSERT INTO PAIS(codigo, nombre)
VALUES("Argentina", "Evangelina");

INSERT INTO JUGADOR(documento, nombre_completo, fecha_nacimiento, biografia, pais)
VALUES (123456789, "Evangelina", "2004-04-04", "Steve jobs", "Argentina");

-- 2. ABM
-- 2. Actualice el autor de todos los análisis hechos por quienes se apellidan Perez para que comiencen con "Lic."
INSERT INTO PAIS(codigo, nombre)
VALUES ("Argentina5", "Evangelina");

INSERT INTO PARTIDA(codigo, pais, fecha, duracion)
VALUES("Codigo-1", "Argentina5", "2004-04-04", 5);

INSERT INTO ANALISIS (identificador, partida, titulo, fecha, autor)
VALUES(1, "Codigo-1", "Titulo", "2004-04-04", "Evangelina Perez");

UPDATE ANALISIS
SET autor = 'Lic.'
WHERE autor LIKE '%Perez%';

-- 3. DML
-- e. Mostrar la partida, jugador, movimiento y pieza que haya un jaque en los primeros 10 movimientos.
SELECT mov.codigo AS 'PARTIDA',
	   mov.jugador AS 'JUGADOR', 
       mov.mueve AS 'PIEZA_MUEVE',
       mov.come AS 'PIEZA_COME'
FROM MOVIMIENTO mov
WHERE mov.jaque = 'SI' AND mov.numero <= 10;

-- 3. DML
-- f. Para cada jugador, mostrar su nombre completo y las estadísticas de: tiempo promedio de duración de partida,
-- tiempo del movimiento más veloz, cantidad de jaques y de jaque mate.
SELECT jug.nombre_completo,
	   AVG(par.duracion) AS PROMEDIO_DURACION,
       MIN(mov.tiempo) AS TIEMPO_MOVIMIENTO,
       SUM(mov.jaque) AS CANTIDAD_JAQUES,
       SUM(mov.mate) AS CANTIDAD_MATE
FROM JUGADOR jug
LEFT JOIN MOVIMIENTO mov ON mov.codigo = jug.documento
LEFT JOIN PARTIDA par ON mov.codigo = par.codigo
GROUP BY jug.nombre_completo;

-- 3. DML
-- g. Nombre, fecha de nacimiento y país de los jugadores que hayan jugado en todos los países del mundo.
SELECT jug.nombre_completo, jug.fecha_nacimiento, jug.pais AS PAIS_ORIGEN
FROM JUGADOR jug
JOIN JUEGA jue ON jug.documento = jue.jugador
JOIN PARTIDA par ON jue.partida = par.codigo
GROUP BY jug.nombre_completo, jug.fecha_nacimiento, jug.pais
HAVING COUNT(DISTINCT par.pais) = (SELECT COUNT(*) FROM PAIS);

-- 3. DML
-- h. Listar los análisis y texto del párrafo en los que un peón se haya comido a la reina.
SELECT ana.autor, parr.texto
FROM ANALISIS ana
JOIN PARRAFO parr ON parr.identificador = ana.identificador
JOIN PARTIDA par ON ana.partida = par.codigo
JOIN MOVIMIENTO mov ON mov.codigo = par.codigo
WHERE mov.mueve = "PEÓN" AND mov.come = "REINA";

-- 3. DML
-- i. El jugador Esteban King es conocido por mover el rey muy pocas veces.
-- Queremos un listado de todas las partidas en las cuales no haya movido su rey ni una sola vez.
SELECT par.codigo AS 'CÓDIGO', jug.nombre_completo
FROM PARTIDA par
JOIN MOVIMIENTO mov ON mov.codigo = par.codigo
JOIN JUGADOR jug ON mov.jugador = jug.documento
WHERE jug.nombre_completo = 'Esteban King' AND mov.mueve != 'Rey';

-- 3. DML
-- Mostrar el nombre, empresa y versión del primer software que haya una partida, y también el código, fecha y lugar de esa partida.
SELECT soft.documento, soft.empresa, soft.version
FROM SOFTWARE soft
JOIN JUGADOR jug ON soft.documento = jug.documento
JOIN JUEGA jue ON jue.jugador = jug.documento
JOIN PARTIDA par ON jue.partida = par.codigo
WHERE par.fecha = (
	SELECT MIN(par2.fecha)
    FROM SOFTWARE soft2
    JOIN JUGADOR jug2 ON soft2.documento = jug2.documento
    JOIN JUEGA jue2 ON jue2.jugador = jug2.documento
    JOIN PARTIDA par2 ON jue2.partida = par2.codigo
    LIMIT 1
    );