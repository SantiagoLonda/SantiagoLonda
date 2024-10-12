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

public class ProveedorDAO {
	// Metodo para obtener una lista de todos los proveedores registrados
	 public List<Proveedor> obtenerTodosLosProveedores() {
	        List<Proveedor> proveedores = new ArrayList<>();
	        String query = "SELECT * FROM Proveedor";
	        try (Connection conn = MySQLConnection.getConnection();
	             PreparedStatement stmt = conn.prepareStatement(query);
	             ResultSet rs = stmt.executeQuery()) {

	        	// Recorre el ResultSet y crea objetos Proveedor con los datos obtenidos
	            while (rs.next()) {
	                Proveedor proveedor = new Proveedor(
	                        rs.getInt("id_Proveedor"),
	                        rs.getString("nombre"),
	                        rs.getString("contacto"),
	                        rs.getString("direccion")
	                );
	                proveedores.add(proveedor);
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	        return proveedores;
	    }
	// Metodo para registrar un nuevo proveedor en la base de datos
    public boolean registrarProveedor(Proveedor proveedor) {
        String query = "INSERT INTO Proveedor (nombre, contacto, direccion) VALUES (?, ?, ?)";
        try (Connection conn = MySQLConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, proveedor.getNombre());
            stmt.setString(2, proveedor.getContacto());
            stmt.setString(3, proveedor.getDireccion());

            int filasInsertadas = stmt.executeUpdate();
            return filasInsertadas > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
 // Metodo para modificar un proveedor en la base de datos
    public boolean modificarProveedor(Proveedor proveedor) {
        String query = "UPDATE Proveedor SET nombre = ?, contacto = ?, direccion = ? WHERE id_Proveedor = ?";
        try (Connection conn = MySQLConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, proveedor.getNombre());
            stmt.setString(2, proveedor.getContacto());
            stmt.setString(3, proveedor.getDireccion());
            stmt.setInt(4, proveedor.getIdProveedor());

            int filasActualizadas = stmt.executeUpdate();
            return filasActualizadas > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    // Metodo para eliminar un proveedor en la base de datos
    public boolean eliminarProveedor(int id_Proveedor) {
        String query = "DELETE FROM Proveedor WHERE id_Proveedor = ?";
        try (Connection conn = MySQLConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, id_Proveedor);

            int filasEliminadas = stmt.executeUpdate();
            return filasEliminadas > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
 // Metodo para consultar un proveedor en la base de datos
    public Proveedor consultarProveedor(int id_Proveedor) {
        String query = "SELECT * FROM Proveedor WHERE id_Proveedor = ?";
        Proveedor proveedor = null;
        try (Connection conn = MySQLConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, id_Proveedor);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                proveedor = new Proveedor(
                        rs.getInt("id_Proveedor"),
                        rs.getString("nombre"),
                        rs.getString("contacto"),
                        rs.getString("direccion")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return proveedor;
    }
}
