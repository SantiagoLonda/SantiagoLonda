package mainclass;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DetallePedidoDAO {
	
    public List<DetallePedido> obtenerTodosLosDetallesDePedido() {
        List<DetallePedido> detalles = new ArrayList<>();
        String query = "SELECT * FROM DetallePedido";
        try (Connection conn = MySQLConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                DetallePedido detalle = new DetallePedido(
                        rs.getInt("id_Detalle"),
                        rs.getInt("id_Pedido"),
                        rs.getInt("id_Producto"),
                        rs.getInt("cantidad"),
                        rs.getDouble("precio_Total")
                );
                detalles.add(detalle);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return detalles;
    }

    // Metodo para registrar un nuevo Detalle de Pedido
    public boolean registrarDetallePedidoConPedido(DetallePedido detallePedido) {
        Connection conn = null;
        PreparedStatement stmtPedido = null;
        PreparedStatement stmtDetalle = null;
        PreparedStatement stmtActualizarProducto = null; // Para actualizar el stock de producto

        try {
            conn = MySQLConnection.getConnection();
            conn.setAutoCommit(false); // Deshabilitar autocommit para manejar la transacción

            // Se inserta en la Tabla Pedido el detalle de Pedido
            String insertPedido = "INSERT INTO Pedido (id_Cliente, fecha, estado) VALUES (?, ?, ?)";
            stmtPedido = conn.prepareStatement(insertPedido, PreparedStatement.RETURN_GENERATED_KEYS);

            // Logica para buscar el cliente o gestionar la informacion del pedido
            stmtPedido.setInt(1, 1);  
            stmtPedido.setDate(2, new java.sql.Date(System.currentTimeMillis())); // Se carga pedido con la fecha actual
            stmtPedido.setString(3, "Pendiente"); // Se carga pedido con el estado Pendiente

            int filasInsertadasPedido = stmtPedido.executeUpdate();
            if (filasInsertadasPedido == 0) {
                conn.rollback();
                return false;
            }

            // Obtiene el ID del pedido recien insertado
            ResultSet generatedKeys = stmtPedido.getGeneratedKeys();
            int idPedidoGenerado = -1;
            if (generatedKeys.next()) {
                idPedidoGenerado = generatedKeys.getInt(1);
            }

            // Inserta el detalle del pedido con el ID de pedido generado
            String insertDetalle = "INSERT INTO DetallePedido (id_Pedido, id_Producto, cantidad, precio_Total) VALUES (?, ?, ?, ?)";
            stmtDetalle = conn.prepareStatement(insertDetalle);
            stmtDetalle.setInt(1, idPedidoGenerado);
            stmtDetalle.setInt(2, detallePedido.getIdProducto());
            stmtDetalle.setInt(3, detallePedido.getCantidad());
            stmtDetalle.setDouble(4, detallePedido.getPrecioTotal());

            int filasInsertadasDetalle = stmtDetalle.executeUpdate();
            if (filasInsertadasDetalle == 0) {
                conn.rollback();
                return false;
            }

            // Actualizar la cantidad de producto en stock (disminuir)
            String actualizarProducto = "UPDATE Producto SET cantidad = cantidad - ? WHERE id_Producto = ?";
            stmtActualizarProducto = conn.prepareStatement(actualizarProducto);
            stmtActualizarProducto.setInt(1, detallePedido.getCantidad());
            stmtActualizarProducto.setInt(2, detallePedido.getIdProducto());

            int filasActualizadasProducto = stmtActualizarProducto.executeUpdate();
            if (filasActualizadasProducto == 0) {
                conn.rollback();
                return false;
            }

            conn.commit();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            return false;
        } finally {
            try {
                if (stmtPedido != null) stmtPedido.close();
                if (stmtDetalle != null) stmtDetalle.close();
                if (stmtActualizarProducto != null) stmtActualizarProducto.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }


    // Metodo para eliminar DetallePedido por ID
    public boolean eliminarDetallePedido(int id_Detalle) {
        Connection conn = null;
        PreparedStatement stmtActualizarPedido = null;
        PreparedStatement stmtEliminarDetalle = null;
        PreparedStatement stmtActualizarProducto = null; // Para actualizar el stock de producto

        try {
            conn = MySQLConnection.getConnection();
            conn.setAutoCommit(false); 

            // Primero, obtener la cantidad y el id_Producto del detalle de pedido
            String queryObtenerDetalle = "SELECT cantidad, id_Producto FROM DetallePedido WHERE id_Detalle = ?";
            PreparedStatement stmtObtenerDetalle = conn.prepareStatement(queryObtenerDetalle);
            stmtObtenerDetalle.setInt(1, id_Detalle);
            ResultSet rsDetalle = stmtObtenerDetalle.executeQuery();

            int cantidad = 0;
            int idProducto = 0;
            if (rsDetalle.next()) {
                cantidad = rsDetalle.getInt("cantidad");
                idProducto = rsDetalle.getInt("id_Producto");
            } else {
                conn.rollback(); // Si el detalle no existe, se cancela la operación
                return false;
            }

            // Actualizar el estado en Pedido a "Eliminado"
            String queryActualizarPedido = "UPDATE Pedido SET estado = 'Eliminado' WHERE id_Pedido = (SELECT id_Pedido FROM DetallePedido WHERE id_Detalle = ?)";
            stmtActualizarPedido = conn.prepareStatement(queryActualizarPedido);
            stmtActualizarPedido.setInt(1, id_Detalle);
            int filasActualizadas = stmtActualizarPedido.executeUpdate();

            if (filasActualizadas == 0) {
                conn.rollback();
                return false;
            }

            // Luego, eliminar el detalle del pedido
            String queryEliminarDetalle = "DELETE FROM DetallePedido WHERE id_Detalle = ?";
            stmtEliminarDetalle = conn.prepareStatement(queryEliminarDetalle);
            stmtEliminarDetalle.setInt(1, id_Detalle);
            int filasEliminadas = stmtEliminarDetalle.executeUpdate();

            if (filasEliminadas == 0) {
                conn.rollback();
                return false;
            }

            // Actualizar la cantidad del producto en stock (aumentar)
            String actualizarProducto = "UPDATE Producto SET cantidad = cantidad + ? WHERE id_Producto = ?";
            stmtActualizarProducto = conn.prepareStatement(actualizarProducto);
            stmtActualizarProducto.setInt(1, cantidad);  // Aumentar la cantidad que estaba en el detalle
            stmtActualizarProducto.setInt(2, idProducto);

            int filasActualizadasProducto = stmtActualizarProducto.executeUpdate();
            if (filasActualizadasProducto == 0) {
                conn.rollback();
                return false;
            }

            conn.commit();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            return false;
        } finally {
            try {
                if (stmtActualizarPedido != null) stmtActualizarPedido.close();
                if (stmtEliminarDetalle != null) stmtEliminarDetalle.close();
                if (stmtActualizarProducto != null) stmtActualizarProducto.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }


    // Metodo para consultar DetallePedido
    public DetallePedido consultarDetallePedido(int id_detalle) {
        String query = "SELECT * FROM DetallePedido WHERE id_Detalle = ?";
        DetallePedido detallePedido = null;
        try (Connection conn = MySQLConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, id_detalle);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                detallePedido = new DetallePedido(
                        rs.getInt("id_detalle"),
                        rs.getInt("id_Pedido"),
                        rs.getInt("id_Producto"),
                        rs.getInt("cantidad"),
                        rs.getDouble("precio_Total")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return detallePedido;
    }
}

