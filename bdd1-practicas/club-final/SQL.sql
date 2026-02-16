DROP DATABASE ClubFinal;
CREATE DATABASE ClubFinal;
USE ClubFinal;

DROP TABLE IF EXISTS SOCIO;
CREATE TABLE SOCIO (
	numero_socio INTEGER PRIMARY KEY,
    nombre_completo VARCHAR(20),
    fecha_nacimiento DATE
);

DROP TABLE IF EXISTS TELEFONO;
CREATE TABLE TELEFONO (
	numero_socio INTEGER,
    telefono INTEGER,
    
    PRIMARY KEY (numero_socio, telefono),
    FOREIGN KEY (numero_socio) REFERENCES SOCIO(numero_socio)
);

DROP TABLE IF EXISTS ENTRENADOR;
CREATE TABLE ENTRENADOR (
	legajo VARCHAR(20) PRIMARY KEY,
    nombre_completo VARCHAR(20),
    calle VARCHAR(20),
    numero INTEGER,
    ciudad VARCHAR(20),
    codigo_postal VARCHAR(20)
);

DROP TABLE IF EXISTS ENTRENADOR_PRINCIPAL;
CREATE TABLE ENTRENADOR_PRINCIPAL (
	legajo VARCHAR(20) PRIMARY KEY,
    
    fecha_nombramiento DATE,
    
    FOREIGN KEY (legajo) REFERENCES ENTRENADOR(legajo)
);

DROP TABLE IF EXISTS ENTRENADOR_ASISTENTE;
CREATE TABLE ENTRENADOR_ASISTENTE (
	legajo VARCHAR(20) PRIMARY KEY,
    horas_semanales INTEGER,
    legajo_principal VARCHAR(20),
    
    FOREIGN KEY (legajo) REFERENCES ENTRENADOR(legajo),
    FOREIGN KEY (legajo_principal) REFERENCES ENTRENADOR_PRINCIPAL(legajo)
);

-- 3. Realizar el sql para la creación de la tabla Clase.
-- Insertar 1 registro en cada una de las siguientes tablas: entrenador, clase.
DROP TABLE IF EXISTS CLASE;
CREATE TABLE CLASE (
	codigo_unico INTEGER PRIMARY KEY,
    nombre VARCHAR(20),
    costo_mensual INTEGER,
    intensidad VARCHAR(20),
    legajo VARCHAR(20),
    
    FOREIGN KEY (legajo) REFERENCES ENTRENADOR(legajo)
);

INSERT INTO ENTRENADOR (legajo, nombre_completo, calle, numero, ciudad, codigo_postal)
VALUES ("M-555", "Evangelina", "Broadway", 5, "Nueva York", "W 42nd St");

INSERT INTO CLASE(codigo_unico, nombre, costo_mensual, intensidad, legajo)
VALUES (5, "Primer clase", 100, "Suave", "M-555");

-- 4. Realizar un listado de los nombres de clases que no tienen socios inscriptos.
CREATE TABLE SOCIO_CLASE (
	numero_socio INTEGER,
    codigo_unico INTEGER,
    
    PRIMARY KEY (numero_socio, codigo_unico),
    FOREIGN KEY (numero_socio) REFERENCES SOCIO(numero_socio),
    FOREIGN KEY (codigo_unico) REFERENCES CLASE(codigo_unico)
);

INSERT INTO SOCIO VALUES (1, 'Evangelina 1', '2025-05-05');
INSERT INTO SOCIO VALUES (2, 'Evangelina 2', '2025-05-05');
INSERT INTO SOCIO VALUES (3, 'Evangelina 3', '2025-05-05');

INSERT INTO CLASE VALUES (10, 'Primer clase', 1000, 'Suave', NULL);
INSERT INTO CLASE VALUES (20, 'Segunda clase', 2000, 'Media', NULL);
INSERT INTO CLASE VALUES (30, 'Tercer clase', 3000, 'Fuerte', NULL);

INSERT INTO SOCIO_CLASE VALUES (1, 10);
INSERT INTO SOCIO_CLASE VALUES (2, 10);
INSERT INTO SOCIO_CLASE VALUES (2, 20);

-- Primera forma
SELECT cla.nombre
FROM CLASE cla
WHERE cla.codigo_unico NOT IN (
	SELECT sc.codigo_unico
    FROM SOCIO_CLASE sc
);

-- Segunda forma
SELECT cla.nombre
FROM CLASE cla
LEFT JOIN SOCIO_CLASE sc ON sc.codigo_unico = cla.codigo_unico
LEFT JOIN SOCIO soc ON sc.numero_socio = soc.numero_socio
WHERE sc.codigo_unico IS NULL;

-- 5. Realizar un listado de los entrenadores que dictan más de 5 clases.
DELETE FROM SOCIO_CLASE;
DELETE FROM CLASE;
DELETE FROM ENTRENADOR;
DELETE FROM SOCIO;

INSERT INTO ENTRENADOR (legajo, nombre_completo, calle, numero, ciudad, codigo_postal)
VALUES ("M-5551", "Evangelina 1", "Broadway 1", 1, "Nueva York 1", "W 42nd St 1");

INSERT INTO CLASE(codigo_unico, nombre, costo_mensual, intensidad, legajo)
VALUES (1, "Primera clase", 100, "Suave", "M-5551");

INSERT INTO ENTRENADOR (legajo, nombre_completo, calle, numero, ciudad, codigo_postal)
VALUES ("M-5552", "Evangelina 2", "Broadway 2", 2, "Nueva York 2", "W 42nd St 2");

INSERT INTO CLASE(codigo_unico, nombre, costo_mensual, intensidad, legajo)
VALUES (2, "Segunda clase", 100, "Suave", "M-5552");

INSERT INTO ENTRENADOR (legajo, nombre_completo, calle, numero, ciudad, codigo_postal)
VALUES ("M-5553", "Evangelina 3", "Broadway 3", 3, "Nueva York 3", "W 42nd St 3");

INSERT INTO CLASE(codigo_unico, nombre, costo_mensual, intensidad, legajo)
VALUES (3, "Tercera clase", 100, "Suave", "M-5553");

INSERT INTO ENTRENADOR (legajo, nombre_completo, calle, numero, ciudad, codigo_postal)
VALUES ("M-5554", "Evangelina 4", "Broadway 4", 5, "Nueva York 4", "W 42nd St 4");

INSERT INTO CLASE(codigo_unico, nombre, costo_mensual, intensidad, legajo)
VALUES (4, "Cuarta clase", 100, "Suave", "M-5554");

INSERT INTO ENTRENADOR (legajo, nombre_completo, calle, numero, ciudad, codigo_postal)
VALUES ("M-5555", "Evangelina 5", "Broadway 5", 5, "Nueva York 5", "W 42nd St 5");

INSERT INTO CLASE(codigo_unico, nombre, costo_mensual, intensidad, legajo)
VALUES (5, "Quinta clase", 100, "Suave", "M-5555");

SELECT ent.nombre_completo, COUNT(cla.legajo) AS "CANTIDAD TOTAL DE CLASES POR ENTRENADOR"
FROM ENTRENADOR ent
INNER JOIN CLASE cla ON cla.legajo = ent.legajo
GROUP BY (ent.nombre_completo)
HAVING COUNT(cla.legajo) >= 5;

-- 6. Realizar un listado indicando cuánto es lo recaudado por cada entrenador,
-- para eso es necesario calcular cuántos alumnos asisten a cada una de sus clases y multiplicarlo
-- por el costo de esa clase, ya que no todas las clases tienen el mismo abono mensual.
DELETE FROM SOCIO_CLASE;
DELETE FROM CLASE;
DELETE FROM ENTRENADOR;
DELETE FROM SOCIO;

SELECT ent.nombre_completo, SUM(sub.costo_mensual * sub.cantidad_alumnos_socios) AS "RECAUDACION"

FROM (
	SELECT cla.codigo_unico, cla.legajo, cla.costo_mensual, COUNT(sc.numero_socio) AS cantidad_alumnos_socios
    FROM CLASE cla
    LEFT JOIN SOCIO_CLASE sc ON sc.codigo_unico = cla.codigo_unico
    GROUP BY cla.codigo_unico, cla.legajo, cla.costo_mensual) AS sub
    
    INNER JOIN ENTRENADOR ent ON sub.legajo = ent.legajo
    GROUP BY ent.nombre_completo;
    
-- 7. Realizar un listado de los socios que están inscriptos en clases con intensidad Fuerte pero no están inscriptos en ninguna
-- clase de intensidad Suave.
DELETE FROM SOCIO_CLASE;
DELETE FROM CLASE;
DELETE FROM ENTRENADOR;
DELETE FROM SOCIO;

INSERT INTO ENTRENADOR VALUES ("E-555", "Evangelina 5", "Broadway 5", 5, "Nueva York 5", "W 42nd St 5");
INSERT INTO CLASE VALUES (5, "Quinta clase", 100, "Fuerte", "E-555");
INSERT INTO SOCIO VALUES(5, "Evangelina 5", '2025-05-05');
INSERT INTO SOCIO_CLASE VALUES (5, 5);

INSERT INTO ENTRENADOR VALUES ("E-556", "Evangelina 6", "Broadway 6", 6, "Nueva York 6", "W 42nd St 6");
INSERT INTO CLASE VALUES (6, "Sexta clase", 100, "Suave", "E-556");
INSERT INTO SOCIO VALUES(6, "Evangelina 6", '2025-05-05');
INSERT INTO SOCIO_CLASE VALUES (6, 6);

INSERT INTO ENTRENADOR VALUES ("E-557", "Evangelina 7", "Broadway 7", 7, "Nueva York 7", "W 42nd St 7");
INSERT INTO CLASE VALUES (7, "Septima clase", 100, "Fuerte", "E-557");
INSERT INTO SOCIO VALUES(7, "Evangelina 7", '2025-05-05');
INSERT INTO SOCIO_CLASE VALUES (7, 7);

SELECT soc.nombre_completo AS "NOMBRE COMPLETO"
FROM SOCIO soc
JOIN SOCIO_CLASE sc ON sc.numero_socio = soc.numero_socio
WHERE sc.codigo_unico IN (
	SELECT cla.codigo_unico
    FROM CLASE cla
    WHERE cla.intensidad = 'Fuerte'
) AND soc.numero_socio NOT IN (
	SELECT sc.numero_socio
	FROM SOCIO_CLASE sc
    JOIN CLASE cla ON sc.codigo_unico = cla.codigo_unico
    WHERE cla.intensidad = 'Suave'
);