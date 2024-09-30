INSERT INTO basta_lola.Proveedor (Nombre, Contacto, Direccion) 
VALUES 
('Basta Lola Microbrewery', 'info@bastalola.com', 'Chascomus 4010, Buenos Aires'),
('Distribuidora Cervecera S.A.', 'info@dcervecera.com', 'Av. San Martin 1234, Buenos Aires'),
('Cerveza Craft Proveedores', 'contacto@craftproveedores.com', 'Av. Rivadavia 9876, Buenos Aires');

INSERT INTO basta_lola.Cliente (Nombre, Contacto, Direccion) 
VALUES 
('Cerveceria Patagonia', 'ventas@patagoniabeer.com', 'Calle Los Maquis 456, Villa La Angostura'),
('Bar La Birra', 'contacto@labirra.com', 'Calle Corrientes 789, Buenos Aires'),
('Tienda de Cervezas Artesanales', 'info@cervezasartesanales.com', 'Av. Libertador 4321, Mendoza'),
('Bar El Lupulo', 'contacto@ellupulo.com', 'Calle Mitre 654, Bariloche');


INSERT INTO basta_lola.Producto (ID_Proveedor, Nombre, Tipo, Precio, Cantidad) 
VALUES 
('2', 'Malta Pilsen', 'MP', 15.50, 500),
('3', 'Lupulo Cascade', 'MP', 25.75, 300),
('1', 'Cerveza IPA', 'PT', 80.00, 150),
('1', 'Cerveza Stout', 'PT', 90.00, 100);


INSERT INTO basta_lola.Pedido (ID_Cliente, Fecha, Estado) 
VALUES 
(1, '2024-09-01 10:30:00', 'Pendiente'),
(2, '2024-09-05 14:00:00', 'Entregado'),
(3, '2024-09-10 09:45:00', 'Pendiente'),
(4, '2024-09-15 13:20:00', 'Entregado');


INSERT INTO basta_lola.DetallePedido (ID_Pedido, ID_Producto, Cantidad, Precio_Total) 
VALUES 
(1, 1, 50, 775.00),  
(1, 2, 30, 772.50),  
(2, 3, 10, 800.00),  
(2, 4, 5, 450.00),   
(3, 1, 25, 387.50),  
(3, 3, 15, 1200.00), 
(4, 2, 20, 515.00),  
(4, 4, 8, 720.00);   
