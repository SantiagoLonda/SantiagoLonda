package mainclass;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class PedidoDAO {
	// Metodo para obtener una lista de todos los Pedidos registrados
    public List<Pedido> obtenerTodosLosPedidos() {
        List<Pedido> pedidos = new ArrayList<>();
        String query = "SELECT * FROM Pedido";
        try (Connection conn = MySQLConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Pedido pedido = new Pedido(
                        rs.getInt("id_Pedido"),
                        rs.getInt("id_Cliente"),
                        rs.getDate("fecha"),
                        rs.getString("estado")
                );
                pedidos.add(pedido);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return pedidos;
    }
 // Metodo para registrar un nuevo Pedido en la base de datos
    public int registrarPedido(Pedido pedido) {
        String query = "INSERT INTO Pedido (id_Cliente, fecha, estado) VALUES (?, ?, ?)";
        try (Connection conn = MySQLConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, pedido.getIdCliente());
            stmt.setDate(2, Date.valueOf(LocalDate.now())); 
            stmt.setString(3, pedido.getEstado());

            int filasInsertadas = stmt.executeUpdate();
            if (filasInsertadas > 0) {
                ResultSet generatedKeys = stmt.getGeneratedKeys();
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1; 
    }
    // Metodo para actualizar el estado de un Pedido en la base de datos
    public boolean actualizarEstadoPedido(int id_Pedido, String estado) {
        String query = "UPDATE Pedido SET estado = ? WHERE id_Pedido = ?";
        try (Connection conn = MySQLConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, estado);
            stmt.setInt(2, id_Pedido);

            int filasActualizadas = stmt.executeUpdate();
            return filasActualizadas > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    // Metodo para consultar un Pedido en la base de datos
    public Pedido consultarPedido(int id_Pedido) {
        String query = "SELECT * FROM Pedido WHERE id_pedido = ?";
        Pedido pedido = null;
        try (Connection conn = MySQLConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, id_Pedido);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                pedido = new Pedido(
                        rs.getInt("id_Pedido"),
                        rs.getInt("id_Cliente"),
                        rs.getDate("fecha"),
                        rs.getString("estado")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return pedido;
    }
    // Metodo para modificar un Pedido en la base de datos
    public boolean modificarPedido(Pedido pedido) {
        String query = "UPDATE Pedido SET id_Cliente = ?, fecha = ?, estado = ? WHERE id_Pedido = ?";
        try (Connection conn = MySQLConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, pedido.getIdCliente());
            stmt.setDate(2, new java.sql.Date(pedido.getFecha().getTime())); // Convertir java.util.Date a java.sql.Date
            stmt.setString(3, pedido.getEstado());
            stmt.setInt(4, pedido.getIdPedido());

            int filasActualizadas = stmt.executeUpdate();
            return filasActualizadas > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}

