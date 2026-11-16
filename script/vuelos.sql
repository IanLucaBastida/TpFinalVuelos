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


INSERT INTO personas (tipo_persona, nombre, apellido, genero, dni, email, legajo) VALUES
    ('RECEPCIONISTA', 'Laura', 'Gomez', 'Femenino', 11111111, NULL, 1001),
    ('CLIENTE', 'Martin', 'Perez', 'Masculino', 22222222, 'martin.perez@mail.com', NULL),
    ('CLIENTE', 'Sofia', 'Fernandez', 'Femenino', 33333333, 'sofia.fernandez@mail.com', NULL);


INSERT INTO aviones (modelo, estado, capacidad) VALUES
    ('Boeing 737-800', 'DISPONIBLE', 10),
    ('Airbus A330-200', 'DISPONIBLE', 10),
    ('Boeing 787 Dreamliner', 'DISPONIBLE', 8);


INSERT INTO asientos (avion_id, numero, clase, disponible) VALUES
    (1, 1, 'PRIMERA', 0),
    (1, 2, 'EJECUTIVA', 1),
    (1, 3, 'ECONOMICA_PREMIUM', 1),
    (1, 4, 'ECONOMICA_PREMIUM', 1),
    (1, 5, 'ECONOMICA', 1),
    (1, 6, 'ECONOMICA', 1),
    (1, 7, 'ECONOMICA', 1),
    (1, 8, 'ECONOMICA', 1),
    (1, 9, 'ECONOMICA', 1),
    (1, 10, 'ECONOMICA', 1);


INSERT INTO asientos (avion_id, numero, clase, disponible) VALUES
    (2, 1, 'PRIMERA', 1),
    (2, 2, 'EJECUTIVA', 1),
    (2, 3, 'ECONOMICA_PREMIUM', 1),
    (2, 4, 'ECONOMICA_PREMIUM', 1),
    (2, 5, 'ECONOMICA', 1),
    (2, 6, 'ECONOMICA', 1),
    (2, 7, 'ECONOMICA', 1),
    (2, 8, 'ECONOMICA', 1),
    (2, 9, 'ECONOMICA', 1),
    (2, 10, 'ECONOMICA', 1);


INSERT INTO asientos (avion_id, numero, clase, disponible) VALUES
    (3, 1, 'PRIMERA', 1),
    (3, 2, 'EJECUTIVA', 1),
    (3, 3, 'ECONOMICA_PREMIUM', 1),
    (3, 4, 'ECONOMICA_PREMIUM', 1),
    (3, 5, 'ECONOMICA', 1),
    (3, 6, 'ECONOMICA', 1),
    (3, 7, 'ECONOMICA', 1),
    (3, 8, 'ECONOMICA', 1);


INSERT INTO vuelos (avion_id, destino, salida, llegada) VALUES
    (1, 'BUENOS_AIRES', '2026-12-10 08:30:00', '2026-12-10 10:00:00'),
    (2, 'MADRID', '2026-12-15 22:00:00', '2026-12-16 14:30:00');


INSERT INTO reservas (fecha_reserva, estado, asiento_id, cliente_id, metodo_pago, vuelo_id) VALUES
    ('2026-11-15 18:00:00', 'RESERVADA', 1, 2, 'CREDITO', 1);