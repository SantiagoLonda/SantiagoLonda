package mainclass;

public class Producto {
    private int idProducto;
    private String nombre;
    private String tipo;  // Materia Prima o Producto Terminado (MP/PT)
    private double precio;
    private int cantidadStock;
    private int idProveedor;  // Relacion con Proveedor

    // Constructor
    public Producto(int idProducto, String nombre, String tipo, double precio, int cantidadStock, int idProveedor) {
        this.idProducto = idProducto;
        this.nombre = nombre;
        this.tipo = tipo;
        this.precio = precio;
        this.cantidadStock = cantidadStock;
        this.idProveedor = idProveedor;  
    }

    // Getters y Setters
    public int getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public int getCantidadStock() {
        return cantidadStock;
    }

    public void setCantidadStock(int cantidadStock) {
        this.cantidadStock = cantidadStock;
    }

    public int getIdProveedor() {
        return idProveedor;
    }

    public void setIdProveedor(int idProveedor) {
        this.idProveedor = idProveedor;
    }
}
