DROP DATABASE IF EXISTS EntrenamientoDos;
CREATE DATABASE EntrenamientoDos;
USE EntrenamientoDos;

CREATE TABLE CLIENTE(
	documento INTEGER PRIMARY KEY,
    nombre VARCHAR(50),
    ciudad VARCHAR(50)
);

CREATE TABLE PEDIDO (
	identificador_pedido INTEGER PRIMARY KEY,
    documento INTEGER,
    fecha DATE,
    total DOUBLE,
    
    FOREIGN KEY (documento) REFERENCES CLIENTE(documento)
);

CREATE TABLE DETALLE (
	identificador_detalle INTEGER PRIMARY KEY,
    identificador_pedido INTEGER,
    producto INTEGER,
    cantidad INTEGER,
    precio_unitario INTEGER,
    
    FOREIGN KEY (identificador_pedido) REFERENCES PEDIDO(identificador_pedido)
);

-- Mostrar el total facturado por cada cliente.
SELECT cli.nombre, SUM(ped.total) AS TOTAL_FACTURADO
FROM CLIENTE cli
JOIN PEDIDO ped ON ped.documento = cli.documento
GROUP BY cli.nombre;

-- Mostrar los clientes que realizaron más de 3 pedidos.
SELECT cli.nombre, COUNT(ped.identificador_pedido) AS CANTIDAD_CLIENTES_PEDIDOS
FROM CLIENTE cli
JOIN PEDIDO ped ON cli.documento = ped.identificador_pedido
GROUP BY cli.nombre
HAVING COUNT(identificador_pedido) > 3;

-- Mostrar el promedio del total de pedidos.
SELECT AVG(ped.total) AS PROMEDIO_PEDIDOS
FROM PEDIDO ped;

-- Mostrar el pedido con mayor total.
SELECT MAX(ped.total) AS PEDIDO_MAYOR
FROM PEDIDO ped;

-- Mostrar la cantidad total de productos vendidos (sumando cantidad).
SELECT det.producto, SUM(det.cantidad) AS CANTIDAD_PRODUCTOS_VENDIDOS
FROM DETALLE det
GROUP BY det.producto;

-- Mostrar el nombre del cliente y la cantidad total de pedidos realizados, solo para aquellos que hicieron más de 2.
SELECT cli. documento, cli.nombre, COUNT(ped.identificador_pedido) AS CANTIDAD_TOTAL_PEDIDOS_REALIZADOS
FROM CLIENTE cli
JOIN PEDIDO ped ON cli.documento = cli.documento
GROUP BY cli. documento, cli.nombre
HAVING COUNT(ped.identificador_pedido) > 2;

-- Mostrar el total vendido por ciudad.
SELECT cli.ciudad, SUM(ped.total) AS TOTAL_VENDIDO_CIUDAD
FROM CLIENTE cli
JOIN PEDIDO ped ON cli.documento = ped.documento
GROUP BY cli.ciudad;

-- Mostrar el cliente que más gastó en total.
SELECT cli.nombre, SUM(ped.total) AS CLIENTE_MAS_GASTO_TOTAL
FROM CLIENTE cli
JOIN PEDIDO ped ON cli.documento = ped.documento
GROUP BY cli.nombre
HAVING SUM(ped.total) = (
	SELECT MAX(TOTAL_CLIENTES_AGRUPADOS)
    FROM (
		  SELECT SUM(ped.total) AS TOTAL_CLIENTES_AGRUPADOS
          FROM PEDIDO ped
          GROUP BY ped.documento
    ) AS E_M
);

-- Mostrar los pedidos cuya suma de cantidades sea mayor a 10.
-- (Ojo: esto requiere agrupar por id_pedido y usar HAVING).
SELECT ped.identificador_pedido AS NÚMERO_PEDIDOS, SUM(det.cantidad) AS CANTIDAD
FROM DETALLE det
JOIN PEDIDO ped ON det.identificador_pedido = ped.identificador_pedido
GROUP BY det.identificador_pedido
HAVING SUM(det.cantidad) > 10;

-- Mostrar el producto más vendido (en cantidad total).
SELECT det.producto AS NUMERO_PRODUCTO, SUM(det.cantidad) AS CANTIDAD_MAXIMA
FROM DETALLE det
GROUP BY det.producto
HAVING SUM(det.cantidad) = (
	   SELECT MAX(CANTIDADES_TOTALES)
       FROM (
			 SELECT SUM(det.cantidad) AS CANTIDADES_TOTALES
             FROM DETALLE det
             GROUP BY det.producto
       ) AS E_M
);