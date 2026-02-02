DROP DATABASE ParcialCuatro;
CREATE DATABASE ParcialCuatro;
USE ParcialCuatro;

DROP TABLE IF EXISTS OFICIO;
CREATE TABLE OFICIO (
	numero INTEGER PRIMARY KEY,
    nombre VARCHAR(50),
    habitat VARCHAR(50)
);

DROP TABLE IF EXISTS LOBEBU;
CREATE TABLE LOBEBU (
	codigo INTEGER PRIMARY KEY,
    nombre VARCHAR(50),
    descripcion VARCHAR(50),
    oficio INTEGER,
    
    FOREIGN KEY (oficio) REFERENCES OFICIO(numero)
);

DROP TABLE IF EXISTS ACCESORIO;
CREATE TABLE ACCESORIO (
	identificador INTEGER PRIMARY KEY,
    descripcion VARCHAR(50),
    tamaño VARCHAR(50)
);

DROP TABLE IF EXISTS TIENE_ACCESORIO;
CREATE TABLE TIENE_ACCESORIO (
	lobebu INTEGER,
    identificador_accesorio INTEGER,
    color VARCHAR(50),
    
    PRIMARY KEY (lobebu, identificador_accesorio),
    
    FOREIGN KEY (lobebu) REFERENCES LOBEBU(codigo),
    FOREIGN KEY (identificador_accesorio) REFERENCES ACCESORIO(identificador)
);

DROP TABLE IF EXISTS CONTENIDO;
CREATE TABLE CONTENIDO (
	identificador INTEGER PRIMARY KEY,
    descripcion VARCHAR(50),
    cantidad_likes INTEGER,
    formato VARCHAR(50),
    archivo VARCHAR(50)
);

DROP TABLE IF EXISTS APARECE;
CREATE TABLE APARECE (
	contenido INTEGER,
    identificador_accesorio INTEGER,
    lobebu INTEGER,
    
    PRIMARY KEY (contenido, identificador_accesorio, lobebu),
    
    FOREIGN KEY (contenido) REFERENCES CONTENIDO(identificador),
    FOREIGN KEY (identificador_accesorio) REFERENCES ACCESORIO(identificador),
    FOREIGN KEY (lobebu) REFERENCES LOBEBU(codigo)
);

DROP TABLE IF EXISTS USUARIO;
CREATE TABLE USUARIO (
	email VARCHAR(50) PRIMARY KEY,
    nombre VARCHAR(50),
    biografia VARCHAR(50),
    puntos INTEGER
);

DROP TABLE IF EXISTS PAIS;
CREATE TABLE PAIS (
	identificador INTEGER PRIMARY KEY,
    nombre VARCHAR(50)
);

DROP TABLE IF EXISTS PROVINCIA;
CREATE TABLE PROVINCIA (
	identificador INTEGER PRIMARY KEY,
    nombre VARCHAR(50),
    pais INTEGER,
    
    FOREIGN KEY (pais) REFERENCES PAIS(identificador)
);

DROP TABLE IF EXISTS LOCALIDAD;
CREATE TABLE LOCALIDAD (
	identificador INTEGER PRIMARY KEY,
    nombre VARCHAR(50),
    provincia INTEGER,
    
    FOREIGN KEY (provincia) REFERENCES PROVINCIA(identificador)
);

DROP TABLE IF EXISTS COLECCIONADO;
CREATE TABLE COLECCIONADO (
	usuario VARCHAR(50),
    lobebu INTEGER,
    cantidad INTEGER,
    
    PRIMARY KEY (usuario, lobebu),
    
    FOREIGN KEY (usuario) REFERENCES USUARIO(email),
    FOREIGN KEY (lobebu) REFERENCES LOBEBU(codigo)
);

-- DDL
-- Crear únicamente la tabla FOTO_USUARIO.
DROP TABLE IF EXISTS FOTO_USUARIO;
CREATE TABLE FOTO_USUARIO (
	identificador INTEGER PRIMARY KEY,
    foto VARCHAR(50),
    fecha DATE,
    lugar VARCHAR(50),
    localidad INTEGER,
    nivel_privacidad VARCHAR(50),
    usuario VARCHAR(50),
    
    FOREIGN KEY (identificador) REFERENCES CONTENIDO(identificador),
    FOREIGN KEY (localidad) REFERENCES LOCALIDAD(identificador),
    FOREIGN KEY (usuario) REFERENCES USUARIO(email)
);

-- DDL
-- Si quisiéramos normalizar el campo TieneAccesorio.color conservando el nombre del color (sería PK) e incorporando el código RGB,
-- ¿qué código SQL debemos ejecutar?.
DROP TABLE IF EXISTS COLOR;
CREATE TABLE COLOR (
	nombre VARCHAR(50) PRIMARY KEY,
    rgb VARCHAR(50)
);

ALTER TABLE TIENE_ACCESORIO
DROP COLUMN color,
ADD color VARCHAR(50),
ADD FOREIGN KEY (color) REFERENCES COLOR(nombre);

-- ABM
-- Dese de alta usted mismo como usuario del sitio.
INSERT INTO USUARIO (email, nombre, biografia, puntos)
VALUES ('Evangelina@gmail.com', 'Evangelina', 'Biografia', 15);

-- ABM
-- Haga que todas sus fotos desde la semana pasada ahora sean privadas.
UPDATE FOTO_USUARIO
SET nivel_privacidad = 'privada'
WHERE usuario = 'Evangelina@gmail.com' AND fecha BETWEEN '2026-01-24' AND '2026-01-31';

-- DML
-- Mostrar los usuarios que hayan subido en el último mes, ordenados alfabéticamente por nombre.
SELECT DISTINCT usu.nombre
FROM USUARIO usu
JOIN FOTO_USUARIO ft
WHERE ft.fecha BETWEEN '2026-01-01' AND '2026-01-31'
ORDER BY usu.nombre DESC;

-- DML
-- Armar un ranking de muñecos con contenido publicado con más likes a menos likes indicando cuántos.
SELECT lob.codigo, lob.nombre, SUM(con.cantidad_likes) AS cantidad_likes
FROM LOBEBU lob
JOIN APARECE apa ON apa.lobebu = lob.codigo
JOIN CONTENIDO con ON apa.contenido = con.identificador
GROUP BY lob.codigo, lob.nombre
ORDER BY cantidad_likes DESC;

-- DML
-- Comparar la cantidad de fotos por usuario versus la cantidad de muñecos coleccionados por usuario.
SELECT COUNT(DISTINCT ft.identificador) AS fotos_subidas_usuario,
	   COUNT(DISTINCT coll.lobebu) AS cantidad_muñecos_usuario
FROM USUARIO usu
LEFT JOIN FOTO_USUARIO ft ON ft.usuario = usu.email
LEFT JOIN COLECCIONADO coll ON coll.usuario = usu.email
GROUP BY usu.nombre;

-- DML
-- ¿Cuáles son/es los/el color/es más predominante/s entre todos los muñecos?.
SELECT lob.nombre, ta.color
FROM TIENE_ACCESORIO ta
JOIN LOBEBU lob ON ta.lobebu = lob.codigo
GROUP BY lob.nombre, ta.color
ORDER BY COUNT(*) DESC
LIMIT 1;

-- DML
-- Armar un reporte geográfico donde se muestren por provincia y por mes las fotos con nivel de privacidad públicas.
SELECT prov.nombre AS provincia, MONTH(ft.fecha) AS mes, COUNT(*) AS cantidad
FROM FOTO_USUARIO ft
JOIN LOCALIDAD loc ON ft.localidad = loc.identificador
JOIN PROVINCIA prov ON loc.provincia = prov.identificador
WHERE ft.nivel_privacidad = 'publica'
GROUP BY provincia, mes;

-- DML
-- ¿Cuáles son los usuarios que han coleccionado todos los muñecos disponibles al momento?.
SELECT col.usuario
FROM COLECCIONADO col
GROUP BY col.usuario
HAVING COUNT(DISTINCT col.lobebu) = (
SELECT COUNT(*) FROM LOBEBU
);