package gestores;

import dao.PersonaDAO;
import interfaces.DAO;
import modelos.Persona;

public class GestorPersonas extends Gestor<Persona>{
    private  final DAO<Persona> dao =  new PersonaDAO();
}
