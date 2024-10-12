package mainclass;

import java.util.List;
import java.util.Scanner;



public class Main {
	//Instancias de las clases DAO que permiten la interaccion con la base de datos
    private static ProveedorDAO proveedorDAO = new ProveedorDAO();
    private static ClienteDAO clienteDAO = new ClienteDAO();
    private static ProductoDAO productoDAO = new ProductoDAO();
    private static PedidoDAO pedidoDAO = new PedidoDAO();
    private static DetallePedidoDAO detallePedidoDAO = new DetallePedidoDAO();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean salir = false;
        //Loop principal del Menu para que funcione la aplicacion
        while (!salir) {
            System.out.println("\n***** Menú Principal *****");
            System.out.println("1. Gestionar Proveedores");
            System.out.println("2. Gestionar Clientes");
            System.out.println("3. Gestionar Productos");
            System.out.println("4. Gestionar Pedidos");
            System.out.println("5. Gestionar Detalles de Pedido");
            System.out.println("6. Salir");
            System.out.print("Elija una opción: ");

            try {
                int opcion = Integer.parseInt(scanner.nextLine());

                switch (opcion) {
                    case 1:
                        menuProveedores(scanner);
                        break;
                    case 2:
                        menuClientes(scanner);
                        break;
                    case 3:
                        menuProductos(scanner);
                        break;
                    case 4:
                        menuPedidos(scanner);
                        break;
                    case 5:
                        menuDetallePedidos(scanner);
                        break;
                    case 6:
                        salir = true;
                        System.out.println("Saliendo del sistema...");
                        break;
                    default:
                        System.out.println("Opción no válida. Intente de nuevo.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Error: Debe ingresar un número. Intente de nuevo.");
            }
        }
    }

    // Menu de Proveedores y sus metodos
    private static void menuProveedores(Scanner scanner) {
        boolean volver = false;
        while (!volver) {
            System.out.println("\n*** Gestión de Proveedores ***");
            System.out.println("1. Registrar Proveedor");
            System.out.println("2. Modificar Proveedor");
            System.out.println("3. Eliminar Proveedor");
            System.out.println("4. Consultar Proveedor");
            System.out.println("5. Listar Todos los Proveedores");
            System.out.println("0. Volver al Menú Anterior");
            System.out.print("Elija una opción: ");

            try {
                int opcion = Integer.parseInt(scanner.nextLine());

                switch (opcion) {
                    case 1:
                        registrarProveedor(scanner);
                        break;
                    case 2:
                        modificarProveedor(scanner);
                        break;
                    case 3:
                        eliminarProveedor(scanner);
                        break;
                    case 4:
                        consultarProveedor(scanner);
                        break;
                    case 5:
                        listarTodosLosProveedores();
                        break;
                    case 0:
                        volver = true;  
                        break;
                    default:
                        System.out.println("Opción no válida.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Error: Debe ingresar un número. Intente de nuevo.");
            }
        }
    }


    private static void registrarProveedor(Scanner scanner) {
        System.out.print("Ingrese el nombre del proveedor: ");
        String nombre = scanner.nextLine();
        System.out.print("Ingrese el contacto del proveedor: ");
        String contacto = scanner.nextLine();
        System.out.print("Ingrese la dirección del proveedor: ");
        String direccion = scanner.nextLine();

        Proveedor proveedor = new Proveedor(0, nombre, contacto, direccion);
        boolean resultado = proveedorDAO.registrarProveedor(proveedor);
        if (resultado) {
            System.out.println("Proveedor registrado con éxito.");
        } else {
            System.out.println("Error al registrar el proveedor.");
        }
    }

    private static void modificarProveedor(Scanner scanner) {
        System.out.print("Ingrese el ID del proveedor a modificar: ");
        int id = Integer.parseInt(scanner.nextLine());
        Proveedor proveedor = proveedorDAO.consultarProveedor(id);
        if (proveedor == null) {
            System.out.println("Proveedor no encontrado.");
            return;
        }

        System.out.print("Ingrese el nuevo nombre del proveedor (actual: " + proveedor.getNombre() + "): ");
        String nuevoNombre = scanner.nextLine();
        System.out.print("Ingrese el nuevo contacto del proveedor (actual: " + proveedor.getContacto() + "): ");
        String nuevoContacto = scanner.nextLine();
        System.out.print("Ingrese la nueva dirección del proveedor (actual: " + proveedor.getDireccion() + "): ");
        String nuevaDireccion = scanner.nextLine();

        proveedor.setNombre(nuevoNombre);
        proveedor.setContacto(nuevoContacto);
        proveedor.setDireccion(nuevaDireccion);

        boolean resultado = proveedorDAO.modificarProveedor(proveedor);
        if (resultado) {
            System.out.println("Proveedor modificado con éxito.");
        } else {
            System.out.println("Error al modificar el proveedor.");
        }
    }

    private static void eliminarProveedor(Scanner scanner) {
        System.out.print("Ingrese el ID del proveedor a eliminar: ");
        int id = Integer.parseInt(scanner.nextLine());
        boolean resultado = proveedorDAO.eliminarProveedor(id);
        if (resultado) {
            System.out.println("Proveedor eliminado con éxito.");
        } else {
            System.out.println("Error al eliminar el proveedor.");
        }
    }

    private static void consultarProveedor(Scanner scanner) {
        System.out.print("Ingrese el ID del proveedor a consultar: ");
        int id = Integer.parseInt(scanner.nextLine());
        Proveedor proveedor = proveedorDAO.consultarProveedor(id);
        if (proveedor != null) {
            System.out.println("Proveedor encontrado:");
            System.out.println("ID: " + proveedor.getIdProveedor() + ", Nombre: " + proveedor.getNombre() +
                    ", Contacto: " + proveedor.getContacto() + ", Dirección: " + proveedor.getDireccion());
        } else {
            System.out.println("Proveedor no encontrado.");
        }
    }

    private static void listarTodosLosProveedores() {
        List<Proveedor> proveedores = proveedorDAO.obtenerTodosLosProveedores();
        if (proveedores.isEmpty()) {
            System.out.println("No hay proveedores registrados.");
        } else {
            System.out.println("Lista de Proveedores:");
            for (Proveedor proveedor : proveedores) {
                System.out.println("ID: " + proveedor.getIdProveedor() + ", Nombre: " + proveedor.getNombre() +
                        ", Contacto: " + proveedor.getContacto() + ", Dirección: " + proveedor.getDireccion());
            }
        }
    }

    // Menu de Clientes y sus metodos
    private static void menuClientes(Scanner scanner) {
        boolean volver = false;
        while (!volver) {
            System.out.println("\n*** Gestión de Clientes ***");
            System.out.println("1. Registrar Cliente");
            System.out.println("2. Modificar Cliente");
            System.out.println("3. Eliminar Cliente");
            System.out.println("4. Consultar Cliente");
            System.out.println("5. Listar Todos los Clientes");
            System.out.println("0. Volver al Menú Anterior");
            System.out.print("Elija una opción: ");

            try {
                int opcion = Integer.parseInt(scanner.nextLine());

                switch (opcion) {
                    case 1:
                        registrarCliente(scanner);
                        break;
                    case 2:
                        modificarCliente(scanner);
                        break;
                    case 3:
                        eliminarCliente(scanner);
                        break;
                    case 4:
                        consultarCliente(scanner);
                        break;
                    case 5:
                        listarTodosLosClientes();
                        break;
                    case 0:
                        volver = true;  
                        break;
                    default:
                        System.out.println("Opción no válida.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Error: Debe ingresar un número. Intente de nuevo.");
            }
        }
    }


    private static void registrarCliente(Scanner scanner) {
        System.out.print("Ingrese el nombre del cliente: ");
        String nombre = scanner.nextLine();
        System.out.print("Ingrese el contacto del cliente: ");
        String contacto = scanner.nextLine();
        System.out.print("Ingrese la dirección del cliente: ");
        String direccion = scanner.nextLine();

        Cliente cliente = new Cliente(0, nombre, contacto, direccion);
        boolean resultado = clienteDAO.registrarCliente(cliente);
        if (resultado) {
            System.out.println("Cliente registrado con éxito.");
        } else {
            System.out.println("Error al registrar el cliente.");
        }
    }

    private static void modificarCliente(Scanner scanner) {
        System.out.print("Ingrese el ID del cliente a modificar: ");
        int id = Integer.parseInt(scanner.nextLine());
        Cliente cliente = clienteDAO.consultarCliente(id);
        if (cliente == null) {
            System.out.println("Cliente no encontrado.");
            return;
        }

        System.out.print("Ingrese el nuevo nombre del cliente (actual: " + cliente.getNombre() + "): ");
        String nuevoNombre = scanner.nextLine();
        System.out.print("Ingrese el nuevo contacto del cliente (actual: " + cliente.getContacto() + "): ");
        String nuevoContacto = scanner.nextLine();
        System.out.print("Ingrese la nueva dirección del cliente (actual: " + cliente.getDireccion() + "): ");
        String nuevaDireccion = scanner.nextLine();

        cliente.setNombre(nuevoNombre);
        cliente.setContacto(nuevoContacto);
        cliente.setDireccion(nuevaDireccion);

        boolean resultado = clienteDAO.modificarCliente(cliente);
        if (resultado) {
            System.out.println("Cliente modificado con éxito.");
        } else {
            System.out.println("Error al modificar el cliente.");
        }
    }

    private static void eliminarCliente(Scanner scanner) {
        System.out.print("Ingrese el ID del cliente a eliminar: ");
        int id = Integer.parseInt(scanner.nextLine());
        boolean resultado = clienteDAO.eliminarCliente(id);
        if (resultado) {
            System.out.println("Cliente eliminado con éxito.");
        } else {
            System.out.println("Error al eliminar el cliente.");
        }
    }

    private static void consultarCliente(Scanner scanner) {
        System.out.print("Ingrese el ID del cliente a consultar: ");
        int id = Integer.parseInt(scanner.nextLine());
        Cliente cliente = clienteDAO.consultarCliente(id);
        if (cliente != null) {
            System.out.println("Cliente encontrado:");
            System.out.println("ID: " + cliente.getIdCliente() + ", Nombre: " + cliente.getNombre() +
                    ", Contacto: " + cliente.getContacto() + ", Dirección: " + cliente.getDireccion());
        } else {
            System.out.println("Cliente no encontrado.");
        }
    }

    private static void listarTodosLosClientes() {
        List<Cliente> clientes = clienteDAO.obtenerTodosLosClientes();
        if (clientes.isEmpty()) {
            System.out.println("No hay clientes registrados.");
        } else {
            System.out.println("Lista de Clientes:");
            for (Cliente cliente : clientes) {
                System.out.println("ID: " + cliente.getIdCliente() + ", Nombre: " + cliente.getNombre() +
                        ", Contacto: " + cliente.getContacto() + ", Dirección: " + cliente.getDireccion());
            }
        }
    }

    // Menu de Productos y sus metodos
    private static void menuProductos(Scanner scanner) {
        boolean volver = false;
        while (!volver) {
            System.out.println("\n*** Gestión de Productos ***");
            System.out.println("1. Registrar Producto");
            System.out.println("2. Modificar Producto");
            System.out.println("3. Eliminar Producto");
            System.out.println("4. Consultar Producto");
            System.out.println("5. Listar Todos los Productos");
            System.out.println("0. Volver al Menú Anterior");
            System.out.print("Elija una opción: ");

            try {
                int opcion = Integer.parseInt(scanner.nextLine());

                switch (opcion) {
                    case 1:
                        registrarProducto(scanner);
                        break;
                    case 2:
                        modificarProducto(scanner);
                        break;
                    case 3:
                        eliminarProducto(scanner);
                        break;
                    case 4:
                        consultarProducto(scanner);
                        break;
                    case 5:
                        listarTodosLosProductos();
                        break;
                    case 0:
                        volver = true;  
                        break;
                    default:
                        System.out.println("Opción no válida.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Error: Debe ingresar un número. Intente de nuevo.");
            }
        }
    }


    private static void registrarProducto(Scanner scanner) {
        System.out.print("Ingrese el nombre del producto: ");
        String nombre = scanner.nextLine();
        System.out.print("Ingrese el tipo (MP o PTerminado): ");
        String tipo = scanner.nextLine();
        System.out.print("Ingrese el precio: ");
        double precio = Double.parseDouble(scanner.nextLine());
        System.out.print("Ingrese la cantidad en stock: ");
        int cantidadStock = Integer.parseInt(scanner.nextLine());
        System.out.print("Ingrese el ID del proveedor: ");
        int idProveedor = Integer.parseInt(scanner.nextLine());

        Producto producto = new Producto(0, nombre, tipo, precio, cantidadStock, idProveedor);
        boolean resultado = productoDAO.registrarProducto(producto);
        if (resultado) {
            System.out.println("Producto registrado con éxito.");
        } else {
            System.out.println("Error al registrar el producto.");
        }
    }

    private static void modificarProducto(Scanner scanner) {
        System.out.print("Ingrese el ID del producto a modificar: ");
        int id = Integer.parseInt(scanner.nextLine());
        Producto producto = productoDAO.consultarProducto(id);
        if (producto == null) {
            System.out.println("Producto no encontrado.");
            return;
        }

        System.out.print("Ingrese el nuevo nombre del producto (actual: " + producto.getNombre() + "): ");
        String nuevoNombre = scanner.nextLine();
        System.out.print("Ingrese el nuevo tipo (actual: " + producto.getTipo() + "): ");
        String nuevoTipo = scanner.nextLine();
        System.out.print("Ingrese el nuevo precio (actual: " + producto.getPrecio() + "): ");
        double nuevoPrecio = Double.parseDouble(scanner.nextLine());
        System.out.print("Ingrese la nueva cantidad en stock (actual: " + producto.getCantidadStock() + "): ");
        int nuevaCantidadStock = Integer.parseInt(scanner.nextLine());
        System.out.print("Ingrese el nuevo ID del proveedor (actual: " + producto.getIdProveedor() + "): ");
        int nuevoIdProveedor = Integer.parseInt(scanner.nextLine());

        producto.setNombre(nuevoNombre);
        producto.setTipo(nuevoTipo);
        producto.setPrecio(nuevoPrecio);
        producto.setCantidadStock(nuevaCantidadStock);
        producto.setIdProveedor(nuevoIdProveedor);

        boolean resultado = productoDAO.modificarProducto(producto);
        if (resultado) {
            System.out.println("Producto modificado con éxito.");
        } else {
            System.out.println("Error al modificar el producto.");
        }
    }

    private static void eliminarProducto(Scanner scanner) {
        System.out.print("Ingrese el ID del producto a eliminar: ");
        int id = Integer.parseInt(scanner.nextLine());
        boolean resultado = productoDAO.eliminarProducto(id);
        if (resultado) {
            System.out.println("Producto eliminado con éxito.");
        } else {
            System.out.println("Error al eliminar el producto.");
        }
    }

    private static void consultarProducto(Scanner scanner) {
        System.out.print("Ingrese el ID del producto a consultar: ");
        int id = Integer.parseInt(scanner.nextLine());
        Producto producto = productoDAO.consultarProducto(id);
        if (producto != null) {
            System.out.println("Producto encontrado:");
            System.out.println("ID: " + producto.getIdProducto() + ", Nombre: " + producto.getNombre() +
                    ", Tipo: " + producto.getTipo() + ", Precio: " + producto.getPrecio() +
                    ", Stock: " + producto.getCantidadStock() + ", ID Proveedor: " + producto.getIdProveedor());
        } else {
            System.out.println("Producto no encontrado.");
        }
    }

    private static void listarTodosLosProductos() {
        List<Producto> productos = productoDAO.obtenerTodosLosProductos();
        if (productos.isEmpty()) {
            System.out.println("No hay productos registrados.");
        } else {
            System.out.println("Lista de Productos:");
            for (Producto producto : productos) {
                System.out.println("ID: " + producto.getIdProducto() + ", Nombre: " + producto.getNombre() +
                        ", Tipo: " + producto.getTipo() + ", Precio: " + producto.getPrecio() +
                        ", Stock: " + producto.getCantidadStock() + ", ID Proveedor: " + producto.getIdProveedor());
            }
        }
    }

    // Menu de Pedidos y sus metodos
    private static void menuPedidos(Scanner scanner) {
        boolean volver = false;
        while (!volver) {
            System.out.println("\n*** Gestión de Pedidos ***");
            System.out.println("1. Registrar Pedido");
            System.out.println("2. Consultar Pedido");
            System.out.println("3. Modificar Pedido");
            System.out.println("4. Listar Todos los Pedidos");
            System.out.println("0. Volver al Menú Anterior");
            System.out.print("Elija una opción: ");
            
            try {
                int opcion = Integer.parseInt(scanner.nextLine());

                switch (opcion) {
                    case 1:
                        registrarPedido(scanner);
                        break;
                    case 2:
                        consultarPedido(scanner);
                        break;
                    case 3:
                        modificarPedido(scanner);
                        break;
                    case 4:
                        listarTodosLosPedidos();
                        break;
                    case 0:
                        volver = true;  
                        break;
                    default:
                        System.out.println("Opción no válida.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Error: Debe ingresar un número. Intente de nuevo.");
            }
        }
    }



    private static void registrarPedido(Scanner scanner) {
        System.out.print("Ingrese el ID del cliente: ");
        int idCliente = Integer.parseInt(scanner.nextLine());
        Pedido pedido = new Pedido(0, idCliente, null, "Pendiente");
        int idPedidoGenerado = pedidoDAO.registrarPedido(pedido);
        if (idPedidoGenerado != -1) {
            System.out.println("Pedido registrado con éxito. ID del pedido: " + idPedidoGenerado);
        } else {
            System.out.println("Error al registrar el pedido.");
        }
    }

    private static void consultarPedido(Scanner scanner) {
        System.out.print("Ingrese el ID del pedido a consultar: ");
        int id = Integer.parseInt(scanner.nextLine());
        Pedido pedido = pedidoDAO.consultarPedido(id);
        if (pedido != null) {
            System.out.println("Pedido encontrado:");
            System.out.println("ID Pedido: " + pedido.getIdPedido() + ", ID Cliente: " + pedido.getIdCliente() +
                    ", Fecha: " + pedido.getFecha() + ", Estado: " + pedido.getEstado());
        } else {
            System.out.println("Pedido no encontrado.");
        }
    }

    private static void listarTodosLosPedidos() {
        List<Pedido> pedidos = pedidoDAO.obtenerTodosLosPedidos();
        if (pedidos.isEmpty()) {
            System.out.println("No hay pedidos registrados.");
        } else {
            System.out.println("Lista de Pedidos:");
            for (Pedido pedido : pedidos) {
                System.out.println("ID Pedido: " + pedido.getIdPedido() + ", ID Cliente: " + pedido.getIdCliente() +
                        ", Fecha: " + pedido.getFecha() + ", Estado: " + pedido.getEstado());
            }
        }
    
    }
    private static void modificarPedido(Scanner scanner) {
        System.out.print("Ingrese el ID del pedido a modificar: ");
        int id = Integer.parseInt(scanner.nextLine());
        Pedido pedido = pedidoDAO.consultarPedido(id);

        if (pedido == null) {
            System.out.println("Pedido no encontrado.");
            return;
        }

        System.out.print("Ingrese el nuevo ID del cliente (actual: " + pedido.getIdCliente() + "): ");
        int nuevoIdCliente = Integer.parseInt(scanner.nextLine());

        System.out.print("Ingrese la nueva fecha del pedido (actual: " + pedido.getFecha() + ") (Formato YYYY-MM-DD): ");
        String nuevaFechaStr = scanner.nextLine();
        java.util.Date nuevaFecha = java.sql.Date.valueOf(nuevaFechaStr); // Convertir la fecha ingresada

        System.out.print("Ingrese el nuevo estado del pedido (actual: " + pedido.getEstado() + "): ");
        String nuevoEstado = scanner.nextLine();

        
        pedido.setIdCliente(nuevoIdCliente);
        pedido.setFecha(nuevaFecha);
        pedido.setEstado(nuevoEstado);

        boolean resultado = pedidoDAO.modificarPedido(pedido);
        if (resultado) {
            System.out.println("Pedido modificado con éxito.");
        } else {
            System.out.println("Error al modificar el pedido.");
        }
    }

    // Menu de Detalles de Pedido y sus metodos
    private static void menuDetallePedidos(Scanner scanner) {
        boolean volver = false;
        while (!volver) {
            System.out.println("\n*** Gestión de Detalles de Pedidos ***");
            System.out.println("1. Registrar Detalle de Pedido");
            System.out.println("2. Eliminar Detalle de Pedido");
            System.out.println("3. Consultar Detalle de Pedido");
            System.out.println("4. Listar Todos los Detalles de Pedido");
            System.out.println("0. Volver al Menú Anterior");
            System.out.print("Elija una opción: ");
            
            try {
                int opcion = Integer.parseInt(scanner.nextLine());

                switch (opcion) {
                    case 1:
                        registrarDetallePedido(scanner);
                        break;
                    case 2:
                        eliminarDetallePedido(scanner);
                        break;
                    case 3:
                        consultarDetallePedido(scanner);
                        break;
                    case 4:
                        listarTodosLosDetallesDePedido();
                        break;
                    case 0:
                        volver = true;  
                        break;
                    default:
                        System.out.println("Opción no válida.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Error: Debe ingresar un número. Intente de nuevo.");
            }
        }
    }

    private static void eliminarDetallePedido(Scanner scanner) {
        System.out.print("Ingrese el ID del detalle de pedido a eliminar: ");
        int idDetalle = Integer.parseInt(scanner.nextLine());
        boolean resultado = detallePedidoDAO.eliminarDetallePedido(idDetalle);
        if (resultado) {
            System.out.println("Detalle de pedido eliminado y estado del pedido actualizado a 'Eliminado'.");
        } else {
            System.out.println("Error al eliminar el detalle de pedido.");
        }
    }


    private static void registrarDetallePedido(Scanner scanner) {
        try {
            System.out.print("Ingrese el ID del producto: ");
            int idProducto = Integer.parseInt(scanner.nextLine());
            System.out.print("Ingrese la cantidad: ");
            int cantidad = Integer.parseInt(scanner.nextLine());
            System.out.print("Ingrese el precio total: ");
            double precioTotal = Double.parseDouble(scanner.nextLine());

            DetallePedido detallePedido = new DetallePedido(0, idProducto, cantidad, precioTotal);
            boolean resultado = detallePedidoDAO.registrarDetallePedidoConPedido(detallePedido);
            if (resultado) {
                System.out.println("Pedido y Detalle registrados con éxito.");
            } else {
                System.out.println("Error al registrar el pedido o el detalle.");
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }


    private static void consultarDetallePedido(Scanner scanner) {
        System.out.print("Ingrese el ID del detalle de pedido a consultar: ");
        int idDetalle = Integer.parseInt(scanner.nextLine());
        DetallePedido detallePedido = detallePedidoDAO.consultarDetallePedido(idDetalle);
        if (detallePedido != null) {
            System.out.println("Detalle de Pedido encontrado:");
            System.out.println("ID Detalle: " + detallePedido.getIdDetalle() + ", ID Pedido: " + detallePedido.getIdPedido() +
                    ", ID Producto: " + detallePedido.getIdProducto() + ", Cantidad: " + detallePedido.getCantidad() +
                    ", Precio Total: " + detallePedido.getPrecioTotal());
        } else {
            System.out.println("Detalle de pedido no encontrado.");
        }
    }

    private static void listarTodosLosDetallesDePedido() {
        List<DetallePedido> detalles = detallePedidoDAO.obtenerTodosLosDetallesDePedido();
        if (detalles.isEmpty()) {
            System.out.println("No hay detalles de pedido registrados.");
        } else {
            System.out.println("Lista de Detalles de Pedido:");
            for (DetallePedido detalle : detalles) {
                System.out.println("ID Detalle: " + detalle.getIdDetalle() + ", ID Pedido: " + detalle.getIdPedido() +
                        ", ID Producto: " + detalle.getIdProducto() + ", Cantidad: " + detalle.getCantidad() +
                        ", Precio Total: " + detalle.getPrecioTotal());
            }
        }
    }
}


