create database basta_lola;

CREATE TABLE basta_lola.Cliente (
    ID_Cliente INT AUTO_INCREMENT PRIMARY KEY,
    Nombre VARCHAR(255) NOT NULL,
    Contacto VARCHAR(100),
    Direccion VARCHAR(255)
);

CREATE TABLE basta_lola.Proveedor (
    ID_Proveedor INT AUTO_INCREMENT PRIMARY KEY,
    Nombre VARCHAR(255) NOT NULL,
    Contacto VARCHAR(100),
    Direccion VARCHAR(255)
);


CREATE TABLE basta_lola.Producto (
    ID_Producto INT AUTO_INCREMENT PRIMARY KEY,
    ID_Proveedor INT NOT NULL,
    Nombre VARCHAR(255) NOT NULL,
    Tipo ENUM('MP', 'PT') NOT NULL,
    Precio DECIMAL(10,2) NOT NULL,
    Cantidad INT NOT NULL,
    FOREIGN KEY (ID_Proveedor) REFERENCES Proveedor(ID_Proveedor)
);


CREATE TABLE basta_lola.Pedido (
    ID_Pedido INT AUTO_INCREMENT PRIMARY KEY,
    ID_Cliente INT,
    Fecha DATETIME NOT NULL,
    Estado ENUM('Entregado', 'Pendiente') NOT NULL,
    FOREIGN KEY (ID_Cliente) REFERENCES Cliente(ID_Cliente)
);


CREATE TABLE basta_lola.DetallePedido (
    ID_Detalle INT AUTO_INCREMENT PRIMARY KEY,
    ID_Pedido INT,
    ID_Producto INT,
    Cantidad INT NOT NULL,
    Precio_Total DECIMAL(10,2) NOT NULL,
    FOREIGN KEY (ID_Pedido) REFERENCES Pedido(ID_Pedido),
    FOREIGN KEY (ID_Producto) REFERENCES Producto(ID_Producto)
);
