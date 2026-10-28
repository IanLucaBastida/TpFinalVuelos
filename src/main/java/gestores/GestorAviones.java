package gestores;

import dao.AvionDAO;
import interfaces.DAO;
import modelos.Avion;

public class GestorAviones extends Gestor<Avion>{
    private final DAO<Avion> dao =  new AvionDAO();
}
