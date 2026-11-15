CREATE DATABASE IF NOT EXISTS vuelos;
USE vuelos;

CREATE TABLE personas (
                          id INT AUTO_INCREMENT PRIMARY KEY,
                          tipo_persona VARCHAR(20) NOT NULL,
                          nombre VARCHAR(100) NOT NULL,
                          apellido VARCHAR(100) NOT NULL,
                          genero VARCHAR(30) NOT NULL,
                          dni INT NOT NULL UNIQUE,
                          email VARCHAR(150) NULL,
                          legajo INT NULL UNIQUE
) ENGINE=InnoDB;


CREATE TABLE aviones (
                         id INT AUTO_INCREMENT PRIMARY KEY,
                         modelo VARCHAR(100) NOT NULL,
                         estado VARCHAR(30) NOT NULL,
                         capacidad INT NOT NULL
) ENGINE=InnoDB;


CREATE TABLE asientos (
                          id INT AUTO_INCREMENT PRIMARY KEY,
                          avion_id INT NOT NULL,
                          numero INT NOT NULL,
                          clase VARCHAR(30) NOT NULL,
                          disponible TINYINT(1) NOT NULL DEFAULT 1,
                          CONSTRAINT fk_asiento_avion FOREIGN KEY (avion_id) REFERENCES aviones(id) ON DELETE CASCADE,
                          CONSTRAINT uq_avion_numero UNIQUE (avion_id, numero)
) ENGINE=InnoDB;


CREATE TABLE vuelos (
                        id INT AUTO_INCREMENT PRIMARY KEY,
                        avion_id INT NOT NULL,
                        destino VARCHAR(30) NOT NULL,
                        salida DATETIME NOT NULL,
                        llegada DATETIME NOT NULL,
                        CONSTRAINT fk_vuelo_avion FOREIGN KEY (avion_id) REFERENCES aviones(id)
) ENGINE=InnoDB;


CREATE TABLE reservas (
                          id INT AUTO_INCREMENT PRIMARY KEY,
                          fecha_reserva DATETIME NOT NULL,
                          estado VARCHAR(30) NOT NULL,
                          asiento_id INT NOT NULL,
                          cliente_id INT NOT NULL,
                          metodo_pago VARCHAR(30) NOT NULL,
                          vuelo_id INT NOT NULL,
                          CONSTRAINT fk_reserva_asiento FOREIGN KEY (asiento_id) REFERENCES asientos(id),
                          CONSTRAINT fk_reserva_cliente FOREIGN KEY (cliente_id) REFERENCES personas(id),
                          CONSTRAINT fk_reserva_vuelo FOREIGN KEY (vuelo_id) REFERENCES vuelos(id)
) ENGINE=InnoDB;