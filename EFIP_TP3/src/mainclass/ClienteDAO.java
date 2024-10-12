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

public class ClienteDAO {
	// Metodo para obtener una lista de todos los clientes registrados
	 public List<Cliente> obtenerTodosLosClientes() {
	        List<Cliente> clientes = new ArrayList<>();
	        String query = "SELECT * FROM Cliente";
	        try (Connection conn = MySQLConnection.getConnection();
	             PreparedStatement stmt = conn.prepareStatement(query);
	             ResultSet rs = stmt.executeQuery()) {

	        	// Recorre el ResultSet y crea objetos Cliente con los datos obtenidos
	            while (rs.next()) {
	                Cliente cliente = new Cliente(
	                        rs.getInt("id_Cliente"),
	                        rs.getString("nombre"),
	                        rs.getString("contacto"),
	                        rs.getString("direccion")
	                );
	                clientes.add(cliente);
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	        return clientes;
	    }
	// Metodo para registrar un nuevo cliente en la base de datos
    public boolean registrarCliente(Cliente cliente) {
        String query = "INSERT INTO Cliente (nombre, contacto, direccion) VALUES (?, ?, ?)";
        try (Connection conn = MySQLConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, cliente.getNombre());
            stmt.setString(2, cliente.getContacto());
            stmt.setString(3, cliente.getDireccion());

            int filasInsertadas = stmt.executeUpdate();
            return filasInsertadas > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
   // Metodo para modificar un cliente en la base de datos
    public boolean modificarCliente(Cliente cliente) {
        String query = "UPDATE Cliente SET nombre = ?, contacto = ?, direccion = ? WHERE id_Cliente = ?";
        try (Connection conn = MySQLConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, cliente.getNombre());
            stmt.setString(2, cliente.getContacto());
            stmt.setString(3, cliente.getDireccion());
            stmt.setInt(4, cliente.getIdCliente());

            int filasActualizadas = stmt.executeUpdate();
            return filasActualizadas > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    // Metodo para eliminar un cliente en la base de datos
    public boolean eliminarCliente(int id_Cliente) {
        String query = "DELETE FROM Cliente WHERE id_Cliente = ?";
        try (Connection conn = MySQLConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, id_Cliente);

            int filasEliminadas = stmt.executeUpdate();
            return filasEliminadas > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    // Metodo para consultar un cliente en la base de datos
    public Cliente consultarCliente(int id_Cliente) {
        String query = "SELECT * FROM Cliente WHERE id_Cliente = ?";
        Cliente cliente = null;
        try (Connection conn = MySQLConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, id_Cliente);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                cliente = new Cliente(
                        rs.getInt("id_Cliente"),
                        rs.getString("nombre"),
                        rs.getString("contacto"),
                        rs.getString("direccion")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cliente;
    }
}
