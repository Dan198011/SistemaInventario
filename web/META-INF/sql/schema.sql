CREATE DATABASE IF NOT EXISTS sistema_inventario;
USE sistema_inventario;

CREATE TABLE IF NOT EXISTS usuario (
  id INT AUTO_INCREMENT PRIMARY KEY,
  nombre VARCHAR(100) NOT NULL,
  username VARCHAR(50) UNIQUE NOT NULL,
  password VARCHAR(255) NOT NULL,
  rol ENUM('ADMIN','PRODUCCION','ALMACEN') NOT NULL,
  creado TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS material (
  id INT AUTO_INCREMENT PRIMARY KEY,
  tipo VARCHAR(100) NOT NULL,
  descripcion VARCHAR(255),
  existencia DECIMAL(10,2) DEFAULT 0,
  minimo DECIMAL(10,2) DEFAULT 1,
  unidad VARCHAR(20) DEFAULT 'kg',
  lote VARCHAR(50)
);

CREATE TABLE IF NOT EXISTS movimiento (
  id INT AUTO_INCREMENT PRIMARY KEY,
  material_id INT NOT NULL,
  usuario_id INT,
  tipo ENUM('ENTRADA','SALIDA') NOT NULL,
  cantidad DECIMAL(10,2) NOT NULL,
  fecha DATETIME DEFAULT CURRENT_TIMESTAMP,
  referencia VARCHAR(100),
  proveedor VARCHAR(100),
  lote VARCHAR(50),
  orden_id INT,
  FOREIGN KEY (material_id) REFERENCES material(id) ON DELETE CASCADE,
  FOREIGN KEY (usuario_id) REFERENCES usuario(id) ON DELETE SET NULL
);

CREATE TABLE IF NOT EXISTS bitacora (
  id INT AUTO_INCREMENT PRIMARY KEY,
  usuario_id INT,
  accion VARCHAR(255),
  fecha DATETIME DEFAULT CURRENT_TIMESTAMP,
  FOREIGN KEY (usuario_id) REFERENCES usuario(id) ON DELETE SET NULL
);

CREATE TABLE IF NOT EXISTS orden_produccion (
  id INT AUTO_INCREMENT PRIMARY KEY,
  codigo VARCHAR(50) UNIQUE NOT NULL,
  descripcion VARCHAR(255),
  fecha_inicio DATE,
  fecha_entrega DATE,
  estado ENUM('PENDIENTE','EN_PRODUCCION','FINALIZADO') DEFAULT 'PENDIENTE'
);

CREATE TABLE IF NOT EXISTS orden_material (
  id INT AUTO_INCREMENT PRIMARY KEY,
  orden_id INT NOT NULL,
  material_id INT NOT NULL,
  cantidad DECIMAL(10,2) NOT NULL,
  FOREIGN KEY (orden_id) REFERENCES orden_produccion(id) ON DELETE CASCADE,
  FOREIGN KEY (material_id) REFERENCES material(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS producto_terminado (
  id INT AUTO_INCREMENT PRIMARY KEY,
  codigo VARCHAR(50) UNIQUE,
  descripcion VARCHAR(255),
  cantidad DECIMAL(10,2) DEFAULT 0
);

-- Usuario admin ejemplo (hash bcrypt de 'admin123')
INSERT INTO usuario (nombre, username, password, rol)
VALUES ('Administrador', 'admin', '$2a$10$u1qV7JYz6rPq8fJp8e1jTO8G7b3R5WqO8Zq8Yw8V2zK7G8x0Y1qQe', 'ADMIN')
ON DUPLICATE KEY UPDATE nombre=VALUES(nombre);
