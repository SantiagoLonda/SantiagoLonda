package mainclass;

import java.util.Date;

public class Pedido {
    private int idPedido;
    private int idCliente; // Relacion con Cliente
    private Date fecha;
    private String estado;  // Pendiente, Entregado o Eliminado

    // Constructor
    public Pedido(int idPedido, int idCliente, Date fecha, String estado) {
        this.idPedido = idPedido;
        this.idCliente = idCliente;
        this.fecha = fecha;
        this.estado = estado;
    }

    // Getters y Setters
    public int getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(int idPedido) {
        this.idPedido = idPedido;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
