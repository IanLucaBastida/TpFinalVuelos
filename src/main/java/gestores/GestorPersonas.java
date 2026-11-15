package gestores;

import dao.PersonaDAO;
import exceptions.*;
import interfaces.DAO;
import modelos.Cliente;
import modelos.Persona;
import modelos.Recepcionista;

import java.util.ArrayList;
import java.util.List;

public class GestorPersonas extends Gestor<Persona>{

    private final PersonaDAO personaDAO;

    public GestorPersonas() {
        super(new PersonaDAO());
        this.personaDAO = (PersonaDAO) this.dao;
    }

    public void registrarPersona(Persona persona) throws AccesoDatosException, IdYaExisteException, DatosInvalidosException {
        if (persona == null || persona.getNombre() == null || persona.getNombre().isBlank()) {
            throw new DatosInvalidosException("Los datos de la persona son obligatorios.");
        }
        try {
            Persona existente = personaDAO.buscarPorDni(persona.getDni());
            throw new IdYaExisteException(existente.getDni());
        } catch (ElementoNoEncontradoException e) {
            // Si no existe ese DNI en la BD, se puede registrar
            guardarEnBD(persona);
        }
    }

    public Persona buscarPorDni(int dni) throws AccesoDatosException, ElementoNoEncontradoException {
        return personaDAO.buscarPorDni(dni);
    }

    public List<Cliente> listarClientes() throws AccesoDatosException {
        List<Cliente> clientes = new ArrayList<>();
        for (Persona p : listarDesdeBD()) {
            if (p instanceof Cliente c) {
                clientes.add(c);
            }
        }
        return clientes;
    }

    public List<Recepcionista> listarRecepcionistas() throws AccesoDatosException {
        List<Recepcionista> recepcionistas = new ArrayList<>();
        for (Persona p : listarDesdeBD()) {
            if (p instanceof Recepcionista r) {
                recepcionistas.add(r);
            }
        }
        return recepcionistas;
    }




}
