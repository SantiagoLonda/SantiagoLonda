package mainclass;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ProductoDAO {
	// Metodo para obtener una lista de todos los productos registrados
	public List<Producto> obtenerTodosLosProductos() {
        List<Producto> productos = new ArrayList<>();
        String query = "SELECT * FROM Producto";
        try (Connection conn = MySQLConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
        	// Recorrer el ResultSet y crear objetos Producto con los datos obtenidos
            while (rs.next()) {
                Producto producto = new Producto(
                        rs.getInt("id_Producto"),
                        rs.getString("nombre"),
                        rs.getString("tipo"),
                        rs.getDouble("precio"),
                        rs.getInt("cantidad"),
                        rs.getInt("id_Proveedor")
                );
                productos.add(producto);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return productos;
    }

	public boolean registrarProducto(Producto producto) {
		// Metodo para registrar un nuevo producto en la base de datos
	    String query = "INSERT INTO Producto (nombre, tipo, precio, cantidad, id_Proveedor) VALUES (?, ?, ?, ?, ?)";
	    try (Connection conn = MySQLConnection.getConnection();
	         PreparedStatement stmt = conn.prepareStatement(query)) {
	    	// Asignar los valores de los parametros para la consulta SQL
	        stmt.setString(1, producto.getNombre());
	        stmt.setString(2, producto.getTipo());
	        stmt.setDouble(3, producto.getPrecio());
	        stmt.setInt(4, producto.getCantidadStock());
	        stmt.setInt(5, producto.getIdProveedor());
	        
	     // Ejecuta la consulta de insercion
	        int filasInsertadas = stmt.executeUpdate();
	        return filasInsertadas > 0;
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return false;
	}
	// Metodo para modificar un producto existente en la base de datos
	public boolean modificarProducto(Producto producto) {
	    String query = "UPDATE Producto SET nombre = ?, tipo = ?, precio = ?, cantidad = ?, id_Proveedor = ? WHERE id_Producto = ?";
	    try (Connection conn = MySQLConnection.getConnection();
	         PreparedStatement stmt = conn.prepareStatement(query)) {
	        stmt.setString(1, producto.getNombre());
	        stmt.setString(2, producto.getTipo());
	        stmt.setDouble(3, producto.getPrecio());
	        stmt.setInt(4, producto.getCantidadStock());
	        stmt.setInt(5, producto.getIdProveedor());  
	        stmt.setInt(6, producto.getIdProducto());

	        int filasActualizadas = stmt.executeUpdate();
	        return filasActualizadas > 0;
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return false;
	}

	// Metodo para eliminar un producto de la base de datos usando su ID
    public boolean eliminarProducto(int id_Producto) {
        String query = "DELETE FROM Producto WHERE id_Producto = ?";
        try (Connection conn = MySQLConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, id_Producto);

            int filasEliminadas = stmt.executeUpdate();
            return filasEliminadas > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    // Metodo para consultar un producto en base a su ID
    public Producto consultarProducto(int id_Producto) {
        String query = "SELECT * FROM Producto WHERE id_Producto = ?";
        Producto producto = null;
        try (Connection conn = MySQLConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, id_Producto);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                producto = new Producto(
                    rs.getInt("id_Producto"),
                    rs.getString("nombre"),
                    rs.getString("tipo"),
                    rs.getDouble("precio"),
                    rs.getInt("cantidad"),
                    rs.getInt("id_Proveedor")  
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return producto;
    }

}
