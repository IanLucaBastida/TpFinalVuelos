
    
    <img width="8192" height="6242" alt="Flight Booking Domain Model-2026-11-16-020841" src="https://github.com/user-attachments/assets/a50282e9-8ab9-4a53-ada9-f185f809ab13" />

    
    %% ==================== INTERFACES ====================
    class Identificable {
        <<interface>>
        +getId() int
    }

    class Disponible {
        <<interface>>
        +isDisponible() boolean
        +setDisponible(boolean) void
    }

    class DAO~T~ {
        <<interface>>
        +insertar(T) void
        +listarTodos() List~T~
        +buscarPorId(int) T
        +actualizar(T) void
        +eliminar(int) void
    }

    %% ==================== ENUMS ====================
    class ClaseAsiento {
        <<enumeration>>
        ECONOMICA
        ECONOMICA_PREMIUM
        EJECUTIVA
        PRIMERA
        -double recargo
        +getRecargo() double
    }

    class Destinos {
        <<enumeration>>
        TOKYO
        BUENOS_AIRES
        PARIS
        ROMA
        MADRID
        -double precioBase
        +getPrecioBase() double
    }

    class EstadoAvion {
        <<enumeration>>
        DISPONIBLE
        LLENO
        EN_VUELO
        EN_REPARACION
    }

    class EstadoReserva {
        <<enumeration>>
        PENDIENTE
        RESERVADA
        CANCELADA
        FINALIZADA
    }

    class MetodoPago {
        <<enumeration>>
        DEBITO
        CREDITO
        TRANSFERENCIA
    }

    %% ==================== MODELOS ====================
    class Persona {
        <<abstract>>
        -int id
        -String nombre
        -String apellido
        -String genero
        -int dni
    }

    class Cliente {
        -String email
    }

    class Recepcionista {
        -int legajo
    }

    class Avion {
        -int id
        -String modelo
        -EstadoAvion estado
        -int capacidad
        -List~Asiento~ asientos
        +agregarAsiento(Asiento) void
    }

    class Asiento {
        -int id
        -int numero
        -ClaseAsiento clase
        -boolean disponible
    }

    class Vuelo {
        -int id
        -Avion avion
        -Destinos destino
        -LocalDateTime salida
        -LocalDateTime llegada
        +calcularPrecioBase() double
    }

    class Reserva {
        -int id
        -LocalDateTime fechaReserva
        -EstadoReserva estado
        -Asiento asiento
        -Cliente cliente
        -MetodoPago metodoPago
        -Vuelo vuelo
        +calcularTotal() double
    }

    %% ==================== GESTORES ====================
    class Gestor~T~ {
        <<abstract>>
        #List~T~ elementos
        #DAO~T~ dao
        +agregar(T) boolean
        +existe(T) boolean
        +buscar(int) T
        +eliminar(T) boolean
        +listar() List~T~
        +guardarEnBD(T) void
        +listarDesdeBD() List~T~
        +buscarEnBD(int) T
        +actualizarEnBD(T) void
        +eliminarDeBD(int) void
    }

    class GestorPersonas {
        +registrarPersona(Persona) void
        +buscarPorDni(int) Persona
        +listarClientes() List~Cliente~
        +listarRecepcionistas() List~Recepcionista~
        +eliminarPersona(int, Persona) void
    }

    class GestorAviones {
        +crearAvionConAsientos(String, int) Avion
        +cambiarEstado(int, EstadoAvion) void
    }

    class GestorAsientos {
        +listarPorAvion(int) List~Asiento~
    }

    class GestorVuelos {
        +crearVuelo(Vuelo) void
        +listarVuelosDisponibles() List~Vuelo~
        +obtenerAsientosDisponibles(int) List~Asiento~
        +eliminarVuelo(int) void
    }

    class GestorReservas {
        +crearReserva(Cliente, Vuelo, Asiento, MetodoPago) Reserva
        +cancelarReserva(int, Persona) void
        +listarPorCliente(int) List~Reserva~
    }

    %% ==================== PERSISTENCIA & CONFIG ====================
    class ConexionDB {
        -String URL$
        -String USER$
        -String PASSWORD$
        +getConnection()$ Connection
    }

    class PersonaDAO {
        +buscarPorDni(int) Persona
    }
    class AvionDAO
    class AsientoDAO {
        +insertar(Asiento, int) void
        +listarPorAvion(int) List~Asiento~
    }
    class VueloDAO
    class ReservaDAO {
        +listarPorCliente(int) List~Reserva~
    }

    %% ==================== APP ====================
    class Main {
        +main(String[])$ void
    }

    class Vista {
        -Scanner scanner
        -GestorPersonas gestorPersonas
        -GestorAviones gestorAviones
        -GestorVuelos gestorVuelos
        -GestorReservas gestorReservas
        +iniciar() void
    }

    %% ==================== RELACIONES ====================
    %% Herencia e Interfaces en Modelos
    Persona <|-- Cliente
    Persona <|-- Recepcionista
    Identificable <|.. Persona
    Identificable <|.. Avion
    Identificable <|.. Asiento
    Identificable <|.. Vuelo
    Identificable <|.. Reserva
    Disponible <|.. Asiento

    %% Asociaciones y Composición de Dominio
    Avion "1" *-- "*" Asiento : contiene
    Avion --> EstadoAvion
    Asiento --> ClaseAsiento
    Vuelo "*" --> "1" Avion : utiliza
    Vuelo --> Destinos
    Reserva "*" --> "1" Cliente : pertenece a
    Reserva "*" --> "1" Vuelo : reservado en
    Reserva "*" --> "1" Asiento : ocupa
    Reserva --> EstadoReserva
    Reserva --> MetodoPago

    %% Capa Lógica (Gestores)
    Gestor~T~ <|-- GestorPersonas
    Gestor~T~ <|-- GestorAviones
    Gestor~T~ <|-- GestorAsientos
    Gestor~T~ <|-- GestorVuelos
    Gestor~T~ <|-- GestorReservas
    Gestor~T~ o-- DAO~T~ : delega persistencia

    %% Capa Persistencia (DAOs)
    DAO~T~ <|.. PersonaDAO
    DAO~T~ <|.. AvionDAO
    DAO~T~ <|.. AsientoDAO
    DAO~T~ <|.. VueloDAO
    DAO~T~ <|.. ReservaDAO
    AvionDAO --> AsientoDAO
    VueloDAO --> AvionDAO
    ReservaDAO --> AsientoDAO
    ReservaDAO --> PersonaDAO
    ReservaDAO --> VueloDAO
    PersonaDAO ..> ConexionDB
    AvionDAO ..> ConexionDB
    AsientoDAO ..> ConexionDB
    VueloDAO ..> ConexionDB
    ReservaDAO ..> ConexionDB

    %% Capa Presentación
    Main --> Vista
    Vista --> GestorPersonas
    Vista --> GestorAviones
    Vista --> GestorVuelos
    Vista --> GestorReservas
    Vista --> GestorReservas
