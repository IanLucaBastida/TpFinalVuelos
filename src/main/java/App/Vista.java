package App;

import enums.Destinos;
import enums.EstadoAvion;
import enums.MetodoPago;
import exceptions.*;
import gestores.GestorAviones;
import gestores.GestorPersonas;
import gestores.GestorReservas;
import gestores.GestorVuelos;
import modelos.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

    public class Vista {

        private final Scanner scanner;
        private final GestorPersonas gestorPersonas;
        private final GestorAviones gestorAviones;
        private final GestorVuelos gestorVuelos;
        private final GestorReservas gestorReservas;
        private final DateTimeFormatter formatter;

        public Vista() {
            this.scanner = new Scanner(System.in);
            this.gestorPersonas = new GestorPersonas();
            this.gestorAviones = new GestorAviones();
            this.gestorVuelos = new GestorVuelos();
            this.gestorReservas = new GestorReservas();
            this.formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        }

        public void iniciar() {
            int opcion;
            do {
                System.out.println("\n==========================================");
                System.out.println("   SISTEMA DE GESTION DE VUELOS Y RESERVAS");
                System.out.println("==========================================");
                System.out.println("1. Ingresar con DNI");
                System.out.println("2. Registrarse como Cliente");
                System.out.println("3. Registrar Recepcionista");
                System.out.println("0. Salir");
                opcion = leerEntero("Seleccione una opcion: ");

                switch (opcion) {
                    case 1 -> iniciarSesion();
                    case 2 -> registrarCliente();
                    case 3 -> registrarRecepcionista();
                    case 0 -> System.out.println("Saliendo del sistema...");
                    default -> System.out.println("Opcion invalida.");
                }
            } while (opcion != 0);
        }

        // ==================== ACCESO Y REGISTRO ====================

        private void iniciarSesion() {
            int dni = leerEntero("Ingrese su DNI: ");
            try {
                Persona persona = gestorPersonas.buscarPorDni(dni);
                System.out.println("\nBienvenido/a, " + persona.getNombre() + " " + persona.getApellido() + "!");

                if (persona instanceof Cliente cliente) {
                    menuCliente(cliente);
                } else if (persona instanceof Recepcionista recepcionista) {
                    menuRecepcionista(recepcionista);
                }
            } catch (ElementoNoEncontradoException e) {
                System.out.println("No existe ningun usuario registrado con ese DNI.");
            } catch (AccesoDatosException e) {
                System.out.println("Error de base de datos: " + e.getMessage());
            }
        }

        private void registrarCliente() {
            System.out.println("\n--- REGISTRO DE CLIENTE ---");
            String nombre = leerTexto("Nombre: ");
            String apellido = leerTexto("Apellido: ");
            String genero = leerTexto("Genero: ");
            int dni = leerEntero("DNI: ");
            String email = leerTexto("Email: ");

            Cliente nuevo = new Cliente(0, nombre, apellido, genero, dni, email);
            try {
                gestorPersonas.registrarPersona(nuevo);
                System.out.println("Cliente registrado con exito. ID asignado: " + nuevo.getId());
            } catch (IdYaExisteException e) {
                System.out.println("Error: Ya existe una persona registrada con ese DNI.");
            } catch (DatosInvalidosException | AccesoDatosException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }

        private void registrarRecepcionista() {
            System.out.println("\n--- REGISTRO DE RECEPCIONISTA ---");
            String nombre = leerTexto("Nombre: ");
            String apellido = leerTexto("Apellido: ");
            String genero = leerTexto("Genero: ");
            int dni = leerEntero("DNI: ");
            int legajo = leerEntero("Numero de legajo: ");

            Recepcionista nuevo = new Recepcionista(0, nombre, apellido, genero, dni, legajo);
            try {
                gestorPersonas.registrarPersona(nuevo);
                System.out.println("Recepcionista registrado con exito. ID asignado: " + nuevo.getId());
            } catch (IdYaExisteException e) {
                System.out.println("Error: Ya existe una persona registrada con ese DNI.");
            } catch (DatosInvalidosException | AccesoDatosException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }

        // ==================== MENU CLIENTE ====================

        private void menuCliente(Cliente cliente) {
            int opcion;
            do {
                System.out.println("\n--- MENU CLIENTE (" + cliente.getNombre() + ") ---");
                System.out.println("1. Consultar vuelos disponibles");
                System.out.println("2. Realizar una reserva");
                System.out.println("3. Ver mis reservas");
                System.out.println("4. Cancelar una de mis reservas");
                System.out.println("0. Cerrar sesion");
                opcion = leerEntero("Seleccione una opcion: ");

                switch (opcion) {
                    case 1 -> listarVuelosDisponibles();
                    case 2 -> realizarReserva(cliente);
                    case 3 -> verReservasCliente(cliente);
                    case 4 -> cancelarReserva(cliente);
                    case 0 -> System.out.println("Sesion cerrada.");
                    default -> System.out.println("Opcion invalida.");
                }
            } while (opcion != 0);
        }

        private void listarVuelosDisponibles() {
            try {
                List<Vuelo> vuelos = gestorVuelos.listarVuelosDisponibles();
                if (vuelos.isEmpty()) {
                    System.out.println("No hay vuelos disponibles en este momento.");
                    return;
                }
                System.out.println("\n--- VUELOS DISPONIBLES ---");
                for (Vuelo v : vuelos) {
                    System.out.println("ID: " + v.getId()
                            + " | Destino: " + v.getDestino()
                            + " | Salida: " + v.getSalida().format(formatter)
                            + " | Llegada: " + v.getLlegada().format(formatter)
                            + " | Avion: " + v.getAvion().getModelo()
                            + " | Precio base: $" + v.calcularPrecioBase());
                }
            } catch (AccesoDatosException e) {
                System.out.println("Error al consultar vuelos: " + e.getMessage());
            }
        }

        private void realizarReserva(Cliente cliente) {
            try {
                List<Vuelo> disponibles = gestorVuelos.listarVuelosDisponibles();
                if (disponibles.isEmpty()) {
                    System.out.println("No hay vuelos disponibles para reservar en este momento.");
                    return;
                }
            } catch (AccesoDatosException e) {
                System.out.println("Error al consultar vuelos: " + e.getMessage());
                return;
            }

            listarVuelosDisponibles();
            int vueloId = leerEntero("\nIngrese el ID del vuelo que desea reservar (0 para cancelar): ");
            if (vueloId == 0) return;

            try {
                Vuelo vuelo = gestorVuelos.buscarEnBD(vueloId);
                List<Asiento> libres = gestorVuelos.obtenerAsientosDisponibles(vueloId);

                if (libres.isEmpty()) {
                    System.out.println("El vuelo seleccionado no tiene asientos disponibles.");
                    return;
                }

                System.out.println("\nAsientos disponibles en el vuelo:");
                for (Asiento a : libres) {
                    double precioEstimado = vuelo.calcularPrecioBase() * a.getClase().getRecargo();
                    System.out.println("Asiento Nro: " + a.getNumero()
                            + " | Clase: " + a.getClase()
                            + " | Precio final: $" + precioEstimado);
                }

                int numAsiento = leerEntero("Ingrese el NUMERO de asiento deseado: ");
                Asiento asientoElegido = null;
                for (Asiento a : libres) {
                    if (a.getNumero() == numAsiento) {
                        asientoElegido = a;
                        break;
                    }
                }

                if (asientoElegido == null) {
                    System.out.println("Numero de asiento invalido o no disponible.");
                    return;
                }

                MetodoPago metodoPago = seleccionarMetodoPago();
                Reserva reserva = gestorReservas.crearReserva(cliente, vuelo, asientoElegido, metodoPago);

                System.out.println("\nReserva confirmada con exito!");
                System.out.println("Codigo de reserva: " + reserva.getId()
                        + " | Total abonado: $" + reserva.calcularTotal());

            } catch (ElementoNoEncontradoException | AsientoOcupadoException | DatosInvalidosException | AccesoDatosException e) {
                System.out.println("Error al realizar la reserva: " + e.getMessage());
            }
        }

        private void verReservasCliente(Cliente cliente) {
            try {
                List<Reserva> reservas = gestorReservas.listarPorCliente(cliente.getId());
                if (reservas.isEmpty()) {
                    System.out.println("Usted no posee reservas registradas.");
                    return;
                }
                System.out.println("\n--- MIS RESERVAS ---");
                for (Reserva r : reservas) {
                    imprimirReserva(r);
                }
            } catch (AccesoDatosException e) {
                System.out.println("Error al obtener sus reservas: " + e.getMessage());
            }
        }

        private void cancelarReserva(Persona usuario) {
            if (usuario instanceof Cliente c) {
                verReservasCliente(c);
            } else {
                listarTodasLasReservas();
            }

            int reservaId = leerEntero("\nIngrese el ID de la reserva a cancelar (0 para volver): ");
            if (reservaId == 0) return;

            try {
                gestorReservas.cancelarReserva(reservaId, usuario);
                System.out.println("Reserva #" + reservaId + " cancelada correctamente y asiento liberado.");
            } catch (ElementoNoEncontradoException | DatosInvalidosException | AccesoDatosException e) {
                System.out.println("No se pudo cancelar la reserva: " + e.getMessage());
            }
        }

        // ==================== MENU RECEPCIONISTA (ABCM) ====================

        private void menuRecepcionista(Recepcionista recepcionista) {
            int opcion;
            do {
                System.out.println("\n--- MENU RECEPCIONISTA (" + recepcionista.getNombre() + " - Legajo " + recepcionista.getLegajo() + ") ---");
                System.out.println("1. Gestion de Aviones");
                System.out.println("2. Gestion de Vuelos");
                System.out.println("3. Gestion de Personas");
                System.out.println("4. Gestion de Reservas");
                System.out.println("0. Cerrar sesion");
                opcion = leerEntero("Seleccione una opcion: ");

                switch (opcion) {
                    case 1 -> subMenuAviones();
                    case 2 -> subMenuVuelos();
                    case 3 -> subMenuPersonas(recepcionista);
                    case 4 -> subMenuReservas();
                    case 0 -> System.out.println("Sesion cerrada.");
                    default -> System.out.println("Opcion invalida.");
                }
            } while (opcion != 0);
        }

        private void subMenuAviones() {
            System.out.println("\n--- GESTION DE AVIONES ---");
            System.out.println("1. Registrar nuevo avion (genera asientos automaticamente)");
            System.out.println("2. Listar aviones");
            System.out.println("3. Cambiar estado de avion");
            System.out.println("0. Volver");
            int op = leerEntero("Opcion: ");

            try {
                switch (op) {
                    case 1 -> {
                        String modelo = leerTexto("Modelo del avion (ej. Boeing 737): ");
                        int capacidad = leerEntero("Capacidad total de asientos: ");
                        Avion avion = gestorAviones.crearAvionConAsientos(modelo, capacidad);
                        System.out.println("Avion creado con ID " + avion.getId() + " y " + avion.getAsientos().size() + " asientos cargados.");
                    }
                    case 2 -> {
                        List<Avion> aviones = gestorAviones.listarDesdeBD();
                        for (Avion a : aviones) {
                            System.out.println("ID: " + a.getId() + " | Modelo: " + a.getModelo()
                                    + " | Capacidad: " + a.getCapacidad() + " | Estado: " + a.getEstado());
                        }
                    }
                    case 3 -> {
                        int id = leerEntero("ID del avion: ");
                        EstadoAvion estado = seleccionarEstadoAvion();
                        gestorAviones.cambiarEstado(id, estado);
                        System.out.println("Estado actualizado.");
                    }
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }

        private void subMenuVuelos() {
            System.out.println("\n--- GESTION DE VUELOS ---");
            System.out.println("1. Crear nuevo vuelo");
            System.out.println("2. Listar todos los vuelos");
            System.out.println("3. Eliminar vuelo");
            System.out.println("0. Volver");
            int op = leerEntero("Opcion: ");

            try {
                switch (op) {
                    case 1 -> {
                        List<Avion> aviones = gestorAviones.listarDesdeBD();
                        if (aviones.isEmpty()) {
                            System.out.println("Primero debe registrar un avion.");
                            return;
                        }
                        System.out.println("Aviones disponibles:");
                        for (Avion a : aviones) {
                            System.out.println("ID: " + a.getId() + " | " + a.getModelo() + " (" + a.getEstado() + ")");
                        }
                        int avionId = leerEntero("ID del avion asignado: ");
                        Avion avion = gestorAviones.buscarEnBD(avionId);

                        Destinos destino = seleccionarDestino();
                        LocalDateTime salida = leerFechaHora("Fecha y hora de SALIDA (dd/MM/yyyy HH:mm): ");
                        LocalDateTime llegada = leerFechaHora("Fecha y hora de LLEGADA (dd/MM/yyyy HH:mm): ");

                        // Orden del constructor de Vuelo: (id, avion, destino, llegada, salida)
                        Vuelo vuelo = new Vuelo(0, avion, destino, llegada, salida);
                        gestorVuelos.crearVuelo(vuelo);
                        System.out.println("Vuelo creado con exito. ID: " + vuelo.getId());
                    }
                    case 2 -> {
                        List<Vuelo> vuelos = gestorVuelos.listarDesdeBD();
                        for (Vuelo v : vuelos) {
                            System.out.println("ID: " + v.getId() + " | Destino: " + v.getDestino()
                                    + " | Salida: " + v.getSalida().format(formatter)
                                    + " | Llegada: " + v.getLlegada().format(formatter)
                                    + " | Avion: " + v.getAvion().getModelo());
                        }
                    }
                    case 3 -> {
                        int id = leerEntero("ID del vuelo a eliminar: ");
                        gestorVuelos.eliminarDeBD(id);
                        System.out.println("Vuelo eliminado.");
                    }
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }

        private void subMenuPersonas(Recepcionista recepcionista) {
            System.out.println("\n--- GESTION DE PERSONAS ---");
            System.out.println("1. Listar clientes");
            System.out.println("2. Registrar nuevo cliente");
            System.out.println("3. Eliminar persona por ID");
            System.out.println("0. Volver");
            int op = leerEntero("Opcion: ");

            try {
                switch (op) {
                    case 1 -> {
                        List<Cliente> clientes = gestorPersonas.listarClientes();
                        for (Cliente c : clientes) {
                            System.out.println("ID: " + c.getId() + " | " + c.getNombre() + " " + c.getApellido()
                                    + " | DNI: " + c.getDni() + " | Email: " + c.getEmail());
                        }
                    }
                    case 2 -> registrarCliente();
                    case 3 -> {
                        int id = leerEntero("ID de la persona a eliminar: ");
                        gestorPersonas.eliminarPersona(id, recepcionista);
                        System.out.println("Persona eliminada.");
                    }
                }
            } catch (AccesoDatosException e) {
                System.out.println("No se puede eliminar la persona (verifique que no tenga reservas asociadas).");
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }

        private void subMenuReservas() {
            System.out.println("\n--- GESTION DE RESERVAS ---");
            System.out.println("1. Listar todas las reservas");
            System.out.println("2. Cancelar una reserva");
            System.out.println("0. Volver");
            int op = leerEntero("Opcion: ");

            if (op == 1) {
                listarTodasLasReservas();
            } else if (op == 2) {
                cancelarReserva(null);
            }
        }

        private void listarTodasLasReservas() {
            try {
                List<Reserva> reservas = gestorReservas.listarDesdeBD();
                if (reservas.isEmpty()) {
                    System.out.println("No hay reservas registradas en el sistema.");
                    return;
                }
                System.out.println("\n--- TODAS LAS RESERVAS ---");
                for (Reserva r : reservas) {
                    imprimirReserva(r);
                }
            } catch (AccesoDatosException e) {
                System.out.println("Error al listar reservas: " + e.getMessage());
            }
        }

        private void imprimirReserva(Reserva r) {
            System.out.println("Reserva ID: " + r.getId()
                    + " | Estado: " + r.getEstado()
                    + " | Cliente: " + r.getCliente().getNombre() + " " + r.getCliente().getApellido()
                    + " | Destino: " + r.getVuelo().getDestino()
                    + " | Salida: " + r.getVuelo().getSalida().format(formatter)
                    + " | Asiento Nro: " + r.getAsiento().getNumero() + " (" + r.getAsiento().getClase() + ")"
                    + " | Pago: " + r.getMetodoPago()
                    + " | Total: $" + r.calcularTotal()
                    + " | Fecha compra: " + r.getFechaReserva().format(formatter));
        }

        // ==================== HELPERS DE LECTURA Y ENUMS ====================

        private Destinos seleccionarDestino() {
            Destinos[] valores = Destinos.values();
            while (true) {
                System.out.println("Destinos disponibles:");
                for (int i = 0; i < valores.length; i++) {
                    System.out.println((i + 1) + ". " + valores[i] + " ($" + valores[i].getPrecioBase() + ")");
                }
                int idx = leerEntero("Seleccione destino: ") - 1;
                if (idx >= 0 && idx < valores.length) {
                    return valores[idx];
                }
                System.out.println("Seleccion invalida.");
            }
        }

        private MetodoPago seleccionarMetodoPago() {
            MetodoPago[] valores = MetodoPago.values();
            while (true) {
                System.out.println("Metodos de pago:");
                for (int i = 0; i < valores.length; i++) {
                    System.out.println((i + 1) + ". " + valores[i]);
                }
                int idx = leerEntero("Seleccione metodo de pago: ") - 1;
                if (idx >= 0 && idx < valores.length) {
                    return valores[idx];
                }
                System.out.println("Seleccion invalida.");
            }
        }

        private EstadoAvion seleccionarEstadoAvion() {
            EstadoAvion[] valores = EstadoAvion.values();
            while (true) {
                System.out.println("Estados de avion:");
                for (int i = 0; i < valores.length; i++) {
                    System.out.println((i + 1) + ". " + valores[i]);
                }
                int idx = leerEntero("Seleccione estado: ") - 1;
                if (idx >= 0 && idx < valores.length) {
                    return valores[idx];
                }
                System.out.println("Seleccion invalida.");
            }
        }

        private int leerEntero(String mensaje) {
            while (true) {
                System.out.print(mensaje);
                String entrada = scanner.nextLine();
                try {
                    return Integer.parseInt(entrada.trim());
                } catch (NumberFormatException e) {
                    System.out.println("Por favor, ingrese un numero entero valido.");
                }
            }
        }

        private String leerTexto(String mensaje) {
            while (true) {
                System.out.print(mensaje);
                String entrada = scanner.nextLine().trim();
                if (!entrada.isEmpty()) {
                    return entrada;
                }
                System.out.println("El campo no puede estar vacio.");
            }
        }

        private LocalDateTime leerFechaHora(String mensaje) {
            while (true) {
                System.out.print(mensaje);
                String entrada = scanner.nextLine().trim();
                try {
                    return LocalDateTime.parse(entrada, formatter);
                } catch (DateTimeParseException e) {
                    System.out.println("Formato invalido. Use dd/MM/yyyy HH:mm (ejemplo: 25/11/2026 14:30).");
                }
            }
        }
    }
