package gestores;

import dao.AsientoDAO;
import interfaces.DAO;
import modelos.Asiento;

public class GestorAsientos extends Gestor<Asiento>{
    private final DAO<Asiento> dao =  new AsientoDAO();
    public GestorAsientos(){}

}
