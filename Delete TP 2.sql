-- Se inserta un nuevo cliente y se confirma que existe
INSERT INTO Cliente (Nombre, Contacto, Direccion) 
VALUES ('Cerveceria El Barril', 'ventas@elbarril.com', 'Calle Falsa 123, Buenos Aires');

SELECT * FROM Cliente WHERE Nombre = 'Cerveceria El Barril';

-- Se borra el cliente recien insertado y se confirma que fue eliminado
DELETE FROM Cliente WHERE Nombre = 'Cerveceria El Barril';

SELECT * FROM Cliente WHERE Nombre = 'Cerveceria El Barril';

