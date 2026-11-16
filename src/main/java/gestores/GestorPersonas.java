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
        if (persona.getDni() <= 0) {
            throw new DatosInvalidosException("El DNI debe ser un numero positivo.");
        }
        if (persona instanceof Recepcionista r) {
            if (r.getLegajo() <= 0) {
                throw new DatosInvalidosException("El legajo debe ser un numero positivo.");
            }
            for (Recepcionista existente : listarRecepcionistas()) {
                if (existente.getLegajo() == r.getLegajo()) {
                    throw new DatosInvalidosException("Ya existe un recepcionista con el legajo " + r.getLegajo());
                }
            }
        }

        try {
            Persona existente = personaDAO.buscarPorDni(persona.getDni());
            throw new IdYaExisteException(existente.getDni());
        } catch (ElementoNoEncontradoException e) {

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

    public void eliminarPersona(int idAEliminar, Persona usuarioLogueado)
            throws AccesoDatosException, ElementoNoEncontradoException, DatosInvalidosException {
        if (usuarioLogueado != null && idAEliminar == usuarioLogueado.getId()) {
            throw new DatosInvalidosException("No puede eliminar su propio usuario mientras tiene la sesion activa.");
        }
        eliminarDeBD(idAEliminar);
    }




}
