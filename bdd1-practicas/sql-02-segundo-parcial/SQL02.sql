DROP DATABASE ParcialDos;
CREATE DATABASE ParcialDos;
USE ParcialDos;

DROP TABLE IF EXISTS PAIS;
CREATE TABLE PAIS (
	codigo INTEGER PRIMARY KEY,
    nombre VARCHAR(20),
    continente VARCHAR(20)
);

DROP TABLE IF EXISTS PROVINCIA;
CREATE TABLE PROVINCIA (
	codigo INTEGER PRIMARY KEY,
    nombre VARCHAR(20),
    pais INTEGER,
    
    FOREIGN KEY (pais) REFERENCES PAIS(codigo)
);

DROP TABLE IF EXISTS CIUDAD;
CREATE TABLE CIUDAD (
	codigo INTEGER PRIMARY KEY,
    nombre VARCHAR(20),
    codigo_postal VARCHAR(20),
    provincia INTEGER, 
    
    FOREIGN KEY (provincia) REFERENCES PROVINCIA(codigo)
);

DROP TABLE IF EXISTS JUGUETERIA;
CREATE TABLE JUGUETERIA (
	codigo INTEGER PRIMARY KEY,
    nombre VARCHAR(20),
    ciudad INTEGER,
    calle VARCHAR(20),
    numero INTEGER,
    
    FOREIGN KEY (ciudad) REFERENCES PROVINCIA(codigo)
);

DROP TABLE IF EXISTS FACTURA;
CREATE TABLE FACTURA (
	sucursal INTEGER,
    numero INTEGER,
    fecha DATE,
    jugueteria INTEGER,
    descuento INTEGER,
    
    PRIMARY KEY (sucursal, numero),
    
    FOREIGN KEY (jugueteria) REFERENCES JUGUETERIA(codigo)
);

DROP TABLE IF EXISTS PRECIO;
CREATE TABLE PRECIO (
	identificador INTEGER AUTO_INCREMENT PRIMARY KEY,
    valor INTEGER,
    desde DATE,
    hasta DATE
);

-- DDL
-- Crear únicamente la tabla Item_Venta.
DROP TABLE IF EXISTS ITEM_VENTA;
CREATE TABLE ITEM_VENTA (
	sucursal INTEGER,
    numero INTEGER,
    orden INTEGER,
    cantidad INTEGER,
    precio INTEGER,
    
    PRIMARY KEY (sucursal, numero, orden),
    
    FOREIGN KEY (sucursal, numero) REFERENCES FACTURA(sucursal, numero),
    FOREIGN KEY (precio) REFERENCES PRECIO(identificador)
);

-- DDL
-- El analista cometió un error en la tabla Lobebu.
-- Explícale qué le faltó hacer (vos que hiciste el primer parcial seguro te das cuenta antes) y cómo solucionarlo en SQL.
-- Pista: normalización.
DROP TABLE IF EXISTS OFICIO;
CREATE TABLE OFICIO (
	identificador INTEGER AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(20)
);

DROP TABLE IF EXISTS COLECCION;
CREATE TABLE COLECCION (
	identificador INTEGER AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(20)
);

DROP TABLE IF EXISTS RAREZA;
CREATE TABLE RAREZA (
	identificador INTEGER AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(20),
    cantidad_estrellas INTEGER
);

DROP TABLE IF EXISTS LOBEBU;
CREATE TABLE LOBEBU (
	identificador INTEGER AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(20),
    oficio INTEGER,
    coleccion INTEGER,
    rareza INTEGER,
    
    FOREIGN KEY (coleccion) REFERENCES COLECCION(identificador),
    FOREIGN KEY (rareza) REFERENCES RAREZA(identificador)
);

ALTER TABLE LOBEBU
ADD FOREIGN KEY (oficio) REFERENCES OFICIO(identificador);

-- ABM
-- Cambiar todas las ocurrencias de la calle "French" por "Domingo French" en la tabla Juguetería.
INSERT INTO PAIS (codigo, nombre, continente)
VALUES(1, 'nombre', 'continente');

INSERT INTO Provincia (codigo, nombre, pais)
VALUES (1, 'Buenos Aires', 1);

INSERT INTO JUGUETERIA (codigo, nombre, ciudad, calle, numero)
VALUES (1, 'JugueteriaNombre', 1, 'French', 5);

UPDATE JUGUETERIA
SET calle = 'Domingo French'
WHERE codigo = 1 AND calle = 'French';

SELECT * FROM JUGUETERIA;

-- ABM
-- Aumentar un 3% los valores de los precios cuya vigencia hasta es anterior al 1/12/2025.
INSERT INTO PRECIO (identificador, valor, desde, hasta)
VALUES (1, 15, '2025-01-01', '2025-05-05');

UPDATE PRECIO
SET valor = valor * 1.03
WHERE identificador = 1 AND hasta < '2026-12-01';

SELECT * FROM PRECIO;

-- DML
-- Buscar las listas de precio por Juguetería.
-- Mostrar cuit, lobebu, precio, vigencia desde y vigencia hasta.
DROP TABLE IF EXISTS VALORIZADO_POR;
CREATE TABLE VALORIZADO_POR (
	lobebu INTEGER,
    precio INTEGER,
    jugueteria INTEGER,
    
    PRIMARY KEY (lobebu, precio, jugueteria),
    
    FOREIGN KEY (lobebu) REFERENCES LOBEBU(identificador),
    FOREIGN KEY (precio) REFERENCES PRECIO(identificador),
    FOREIGN KEY (jugueteria) REFERENCES JUGUETERIA(codigo)
 );

SELECT j.codigo AS CUIT, l.nombre AS LOBEBU, p.valor AS PRECIO, p.desde AS DESDE, p.hasta AS HASTA
FROM VALORIZADO_POR vp
JOIN JUGUETERIA j ON vp.jugueteria = j.codigo
JOIN PRECIO p ON vp.precio = p.identificador
JOIN LOBEBU l ON vp.lobebu = l.identificador;

-- DML
-- Contar la cantidad de facturas emitidas por país, provincia y ciudad.
INSERT INTO FACTURA (sucursal, numero, fecha, jugueteria, descuento)
VALUES(1, 1, '2026-01-07', 1, 3);

INSERT INTO CIUDAD (codigo, nombre, codigo_postal, provincia)
VALUES(1, 'nombre', 'codigo_postal', 1);

SELECT COUNT(fac.numero) AS 'CANTIDAD DE FACTURAS'
FROM FACTURA fac
JOIN JUGUETERIA jug ON fac.jugueteria = jug.codigo
JOIN CIUDAD ciu ON jug.ciudad = ciu.codigo
JOIN PROVINCIA prov ON ciu.provincia = prov.codigo
JOIN PAIS ps ON prov.pais = ps.codigo;

-- DML
-- ¿Cuántos Lobebu's se han vendido, por rareza?.
SELECT rar.nombre, SUM(iv.cantidad) AS 'CANTIDAD VENDIDA'
FROM ITEM_VENTA iv
JOIN FACTURA f ON iv.sucursal = f.sucursal AND iv.numero = f.numero
JOIN PRECIO pre ON iv.precio = pre.identificador
JOIN VALORIZADO_POR vp ON vp.precio = pre.identificador
JOIN LOBEBU lob ON vp.lobebu = lob.identificador
JOIN RAREZA rar ON lob.rareza = rar.identificador
GROUP BY rar.nombre;

-- DML
-- Crear una vista con el total facturado por factura y cliente.
INSERT INTO ITEM_VENTA (sucursal, numero, orden, cantidad, precio)
VALUES (1, 1, 1, 5, 1);

DROP TABLE IF EXISTS total_facturado;
CREATE VIEW total_facturado AS
SELECT fac.sucursal, fac.numero, fac.jugueteria AS cliente, SUM(iv.cantidad * pre.valor) AS TOTAL_FACTURADO
FROM FACTURA fac
JOIN ITEM_VENTA iv ON iv.sucursal = fac.sucursal AND iv.numero = fac.numero
JOIN PRECIO pre ON iv.precio = pre.identificador
GROUP BY fac.sucursal, fac.numero, fac.jugueteria;

SELECT * FROM total_facturado;

-- DML
-- Utilizar la vista anterior para calcular el volumen en ARS por país, provincia y ciudad.
SELECT pa.nombre AS PAIS, prov.nombre AS PROVINCIA, ciu.nombre AS CIUDAD, SUM(tf.total_facturado) AS 'TOTAL ARS'
FROM total_facturado tf
JOIN Jugueteria j ON tf.cliente = j.codigo
JOIN Ciudad ciu ON j.ciudad = ciu.codigo
JOIN Provincia prov ON ciu.provincia = prov.codigo
JOIN Pais pa ON prov.pais = pa.codigo
GROUP BY pa.nombre, prov.nombre, ciu.nombre;

-- DML
-- ¿Cuáles son las jugueterías que han aplicado al menos 5% de descuento en todas sus facturas?.
INSERT INTO JUGUETERIA (codigo, nombre, ciudad, calle, numero)
VALUES (5, 'JugueteriaTest', 1, 'French', 5);

INSERT INTO FACTURA (sucursal, numero, fecha, jugueteria, descuento)
VALUES(15, 15, '2026-01-01', 5, 10);

SELECT J.nombre
FROM JUGUETERIA j
WHERE NOT EXISTS (
	SELECT 1
	FROM Factura f
	WHERE f.jugueteria = j.codigo AND f.descuento < 5
);