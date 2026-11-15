package gestores;

import dao.AsientoDAO;
import exceptions.AccesoDatosException;

import modelos.Asiento;

import java.util.List;

public class GestorAsientos extends Gestor<Asiento>{

    private final AsientoDAO asientoDAO;

    public GestorAsientos() {
        super(new AsientoDAO());
        this.asientoDAO = (AsientoDAO) this.dao;
    }

    public List<Asiento> listarPorAvion(int avionId) throws AccesoDatosException {
        return asientoDAO.listarPorAvion(avionId);
    }

}
