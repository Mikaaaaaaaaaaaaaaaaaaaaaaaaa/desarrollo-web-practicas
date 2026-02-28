DROP DATABASE EntrenamientoUno;
CREATE DATABASE EntrenamientoUno;
USE EntrenamientoUno;

-- Crear una tabla llamada "Producto" con las columnas: id_producto (PK), nombre, precio, y categoría.
CREATE TABLE PRODUCTO (
	identificador_producto INTEGER PRIMARY KEY,
    nombre VARCHAR(50),
    precio DOUBLE,
    categoria VARCHAR(50)
);

-- Insertar 5 registros en la tabla "Producto".
INSERT INTO PRODUCTO (identificador_producto, nombre, precio, categoria)
VALUES (1, 'NOMBRE_PRODUCTO', 5000.0, 'CATEGORIA_PRODUCTO');

INSERT INTO PRODUCTO (identificador_producto, nombre, precio, categoria)
VALUES(2, 'NOMBRE_PRODUCTO', 50.0, 'CATEGORIA_PRODUCTO');

INSERT INTO PRODUCTO (identificador_producto, nombre, precio, categoria)
VALUES (3, 'NOMBRE_PRODUCTO', 50.0, 'CATEGORIA_PRODUCTO');

INSERT INTO PRODUCTO (identificador_producto, nombre, precio, categoria)
VALUES (4, 'NOMBRE_PRODUCTO', 40.0, 'Eléctronica');

INSERT INTO PRODUCTO (identificador_producto, nombre, precio, categoria)
VALUES (5, 'NOMBRE_PRODUCTO', 50.0, 'Eléctronica');

-- Actualizar el precio de todos los productos en la categoría "Electrónica" sumándoles un 15%.
UPDATE PRODUCTO pro
SET pro.precio = pro.precio * 1.15
WHERE pro.categoria = 'Eléctronica';

SELECT * FROM PRODUCTO;

-- Eliminar los productos que tengan un precio menor a 1000.
DELETE FROM PRODUCTO pro
WHERE pro.precio < 1000;

SELECT * FROM PRODUCTO;

-- Crear una tabla "Venta" con columnas: id_venta (PK), id_producto (FK), fecha, y cantidad.
CREATE TABLE VENTA (
	identificador_venta INTEGER PRIMARY KEY,
    identificador_producto INTEGER,
    fecha DATE,
    cantidad INTEGER,
    
    FOREIGN KEY (identificador_producto) REFERENCES PRODUCTO(identificador_producto)
);

-- Insertar una venta asociada a un producto específico.
INSERT INTO VENTA (identificador_venta, identificador_producto, fecha, cantidad)
VALUES (1, 1, '2004-04-04', 5);

-- Actualizar la cantidad de una venta específica según su id_venta.
UPDATE VENTA ven
SET ven.cantidad = 100
WHERE ven.identificador_venta = 1;

SELECT * FROM VENTA;

-- Agregar una columna "descuento" a la tabla "Venta" y luego actualizar un 10% de descuento en una venta específica.
ALTER TABLE VENTA
ADD COLUMN descuento DOUBLE;

UPDATE VENTA ven
SET ven.descuento = ven.descuento * 1.10
WHERE ven.identificador_venta = 1;