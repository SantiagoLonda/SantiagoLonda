SELECT * FROM basta_lola.Pedido; -- Ver todos los pedidos

SELECT * FROM basta_lola.Cliente; -- Ver todos los clientes

SELECT * FROM basta_lola.Producto WHERE tipo = 'MP'; -- Verificar Stock Materias Primas

SELECT * FROM basta_lola.Pedido WHERE estado = 'Pendiente'; -- Verificar Pedidos pendientes de entrega

SELECT * FROM basta_lola.DetallePedido WHERE id_pedido = (select max(id_pedido) from basta_lola.DetallePedido); -- Ver detalle del ultimo pedido registrado


-- Ver nombre de cliente que tiene pedidos de Productos Terminados (PT) pendientes de entrega que sean de elaboracion propia de Basta Lola
-- Esta query utiliza las 5 tablas del diseño para verificar que el mismo funciona de manera correcta

SELECT p.id_proveedor, cl.nombre, pr.id_producto, de.id_pedido from basta_lola.proveedor p 
join basta_lola.producto pr ON pr.id_proveedor = p.id_proveedor
join basta_lola.detallepedido de ON de.id_producto = pr.ID_Producto
join basta_lola.pedido pe ON pe.id_pedido = de.id_pedido
join basta_lola.cliente cl ON cl.id_cliente = pe.id_cliente
where p.id_proveedor = 1
AND pr.tipo = 'PT'
AND pe.estado = 'Pendiente';
