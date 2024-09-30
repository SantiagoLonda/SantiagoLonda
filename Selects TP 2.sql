SELECT * FROM basta_lola.Pedido; -- Ver todos los pedidos

SELECT * FROM basta_lola.Cliente; -- Ver todos los clientes

SELECT * FROM basta_lola.Producto WHERE tipo = 'MP'; -- Verificar Stock Materias Primas

SELECT * FROM basta_lola.Pedido WHERE estado = 'Pendiente'; -- Verificar Pedidos pendientes de entrega

SELECT * FROM basta_lola.DetallePedido WHERE id_pedido = (select max(id_pedido) from basta_lola.DetallePedido); -- Ver detalle del ultimo pedido registrado