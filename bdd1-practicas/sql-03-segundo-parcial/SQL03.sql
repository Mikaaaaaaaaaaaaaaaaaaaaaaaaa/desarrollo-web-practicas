DROP DATABASE ParcialTres;
CREATE DATABASE ParcialTres;
USE ParcialTres;

DROP TABLE IF EXISTS TIPO_TERMINO;
CREATE TABLE TIPO_TERMINO (
	idTipo INTEGER AUTO_INCREMENT PRIMARY KEY,
    tipo VARCHAR(20)
);

DROP TABLE IF EXISTS CARACTERISTICA;
CREATE TABLE CARACTERISTICA (
	idCaracteristica INTEGER AUTO_INCREMENT PRIMARY KEY,
    etapa VARCHAR(20),
    numero INTEGER
);

DROP TABLE IF EXISTS TECNICO;
CREATE TABLE TECNICO (
	idTecnico INTEGER AUTO_INCREMENT PRIMARY KEY,
    descripcion VARCHAR(20),
    idCaracteristica INTEGER,
    
    FOREIGN KEY (idCaracteristica) REFERENCES CARACTERISTICA(idCaracteristica)
);

DROP TABLE IF EXISTS SENTIDO_BIOLOGICO;
CREATE TABLE SENTIDO_BIOLOGICO (
	codigo INTEGER AUTO_INCREMENT PRIMARY KEY,
    descripcion VARCHAR(20)
);

DROP TABLE IF EXISTS TIPO_CONFLICTO;
CREATE TABLE TIPO_CONFLICTO (
	idTipo INTEGER AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(20)
);

DROP TABLE IF EXISTS CONFLICTO;
CREATE TABLE CONFLICTO (
	idConflicto INTEGER AUTO_INCREMENT PRIMARY KEY,
    conflicto VARCHAR(20),
    idTipo INTEGER,
    
    FOREIGN KEY (idTipo) REFERENCES TIPO_CONFLICTO(idTipo)
);

-- DDL
-- Construir las tablas Termino y Sinonimo en el modelo físico en AnsiSQL.
DROP TABLE IF EXISTS TERMINO;
CREATE TABLE TERMINO (
	idTermino INTEGER AUTO_INCREMENT PRIMARY KEY,
	nombre VARCHAR(20),
	descripcion VARCHAR(20),
	favorito_desde DATE,
	fecha_ultima_busqueda DATE,
	idTecnico INTEGER,
	codigo INTEGER,
	idConflicto INTEGER,
	idTipo INTEGER,

	FOREIGN KEY (idTecnico) REFERENCES TECNICO(idTecnico),
    FOREIGN KEY (codigo) REFERENCES SENTIDO_BIOLOGICO(codigo),
    FOREIGN KEY (idConflicto) REFERENCES CONFLICTO(idConflicto),
    FOREIGN KEY (idTipo) REFERENCES TIPO_TERMINO(idTipo)
);

DROP TABLE IF EXISTS SINONIMO;
CREATE TABLE SINONIMO (
	idTermino INTEGER,
    idSinonimo INTEGER,
    
    PRIMARY KEY (idTermino, idSinonimo),
    
    FOREIGN KEY (idTermino) REFERENCES TERMINO(idTermino),
    FOREIGN KEY (idSinonimo) REFERENCES  TERMINO(idTermino)
);

-- DDL
-- Si se agregara una fecha en la tabla Sinonimo para saber desde cuándo un término es sinónimo de otro, qué
-- sentencia DDL habría que agregar teniendo en cuenta que la tabla ya ha sido creada pero no populada de datos.
ALTER TABLE SINONIMO
ADD COLUMN fecha DATE;

SELECT * FROM SINONIMO;

-- ABM
-- Escribir una consulta para convertir a un término en favorito.
UPDATE TERMINO
SET favorito_desde = '2026-01-30'
WHERE idTermino = 5;

SELECT * FROM TERMINO;

-- ABM
-- Eliminar el sinónimo 3 para el término 2.
DELETE FROM SINONIMO
WHERE idTermino = 3 AND idSinonimo = 2;

SELECT * FROM SINONIMO;

-- ABM
-- ¿Si se decide eliminar el tipo de conflicto 4 de todo el modelo, qué consultas hay que escribir?.
DELETE FROM CONFLICTO
WHERE idTipo = 4;

DELETE FROM TIPO_CONFLICTO
WHERE idTipo = 4;

SELECT * FROM TIPO_CONFLICTO;

-- DML
-- Mostrar los términos favoritos del consultor (nombre y descripción).
SELECT nombre, descripcion, favorito_desde AS HOLA
FROM TERMINO
WHERE favorito_desde IS NOT NULL;

-- DML
-- Determinar todos los términos que siendo favoritos desde el 2023, hayan sido buscados en el último mes.
SELECT nombre, tipo
FROM TERMINO ter
JOIN TIPO_TERMINO tter ON ter.idTipo = tter.idTipo
WHERE favorito_desde >= '2023-01-01' AND fecha_ultima_busqueda >= '2023-02-01';

-- DML
-- Listar los términos con 3 o más sinónimos, cuyo tipo de conflicto sea de "Desvalorización".
SELECT ter.idTermino, ter.nombre
FROM TERMINO ter
JOIN SINONIMO sin ON sin.idTermino = ter.idTermino
JOIN CONFLICTO con ON ter.idConflicto = con.idConflicto
JOIN TIPO_CONFLICTO tcon ON con.idTipo = tcon.idTipo
WHERE tcon.nombre = 'Desvalorización'
GROUP BY ter.idTermino, ter.nombre
HAVING COUNT(sin.idSinonimo) >= 3;

-- DML
-- Listar las características más buscadas (sin repetir).
SELECT car.idCaracteristica, car.etapa, car.numero, COUNT(*) AS cantidad_busquedas
FROM TERMINO ter
JOIN TECNICO tec ON ter.idTecnico = tec.idTecnico
JOIN CARACTERISTICA car ON tec.idCaracteristica = car.idCaracteristica
WHERE ter.fecha_ultima_busqueda IS NOT NULL
GROUP BY car.idCaracteristica, car.etapa, car.numero
ORDER BY cantidad_busquedas DESC;

-- DML
-- Listar los términos que empiecen con A, con la cantidad de sinónimos que tienen.
SELECT ter.nombre, COUNT(sin.idSinonimo) AS cantidad
FROM SINONIMO sin
LEFT JOIN TERMINO ter ON sin.idTermino = ter.idTermino
WHERE ter.nombre LIKE 'A%'
GROUP BY ter.nombre, sin.idTermino;

-- DML
-- Confeccionar un listado que haga de índice, indicando la cantidad de términos que hay por cada letra del alfabeto con
-- la que empiezan los términos. Sería una lista de Primer carácter del término, cantidad de términos. Sin repetir letras.
SELECT LEFT(nombre, 1) AS letra, COUNT(*) AS cantidad
FROM TERMINO
GROUP BY letra
ORDER BY letra ASC;

-- DML
-- Crear una vista que muestre cúantas consultas en promedio realiza el consultor por mes.
-- Puede utilizar la función month(campo_fecha).
-- Tener en cuenta todas las búsquedas realizadas al momento de ejecutar la vista.
CREATE VIEW promedio_consultas_por_mes AS
SELECT MONTH(ter.fecha_ultima_busqueda) AS mes, COUNT(DISTINCT MONTH(ter.fecha_ultima_busqueda)) AS promedio_consultas_realizadas
FROM TERMINO ter
WHERE ter.fecha_ultima_busqueda IS NOT NULL
GROUP BY mes;

SELECT * FROM promedio_consultas_por_mes;