package gestores;

import dao.VueloDAO;
import interfaces.DAO;
import modelos.Vuelo;

public class GestorVuelos extends Gestor<Vuelo>{
    private  final DAO<Vuelo> dao =  new VueloDAO();
}
