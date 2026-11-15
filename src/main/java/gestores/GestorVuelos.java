package gestores;

import dao.VueloDAO;
import enums.EstadoAvion;
import exceptions.AccesoDatosException;
import exceptions.DatosInvalidosException;
import exceptions.ElementoNoEncontradoException;
import interfaces.DAO;
import modelos.Asiento;
import modelos.Vuelo;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class GestorVuelos extends Gestor<Vuelo>{
    public GestorVuelos() {
        super(new VueloDAO());
    }

    public void crearVuelo(Vuelo vuelo) throws AccesoDatosException, DatosInvalidosException {
        if (vuelo == null || vuelo.getAvion() == null || vuelo.getDestino() == null) {
            throw new DatosInvalidosException("Faltan datos obligatorios para crear el vuelo.");
        }
        if (!vuelo.getSalida().isBefore(vuelo.getLlegada())) {
            throw new DatosInvalidosException("La fecha de salida debe ser anterior a la de llegada.");
        }
        if (vuelo.getAvion().getEstado() != EstadoAvion.DISPONIBLE) {
            throw new DatosInvalidosException("El avión seleccionado no está disponible.");
        }
        guardarEnBD(vuelo);
    }

    public List<Vuelo> listarVuelosDisponibles() throws AccesoDatosException {
        List<Vuelo> disponibles = new ArrayList<>();
        LocalDateTime ahora = LocalDateTime.now();

        for (Vuelo v : listarDesdeBD()) {
            boolean tieneAsientosLibres = false;
            for (Asiento a : v.getAvion().getAsientos()) {
                if (a.isDisponible()) {
                    tieneAsientosLibres = true;
                    break;
                }
            }
            if (v.getSalida().isAfter(ahora) && tieneAsientosLibres) {
                disponibles.add(v);
            }
        }
        return disponibles;
    }

    public List<Asiento> obtenerAsientosDisponibles(int vueloId)
            throws AccesoDatosException, ElementoNoEncontradoException {
        Vuelo vuelo = buscarEnBD(vueloId);
        List<Asiento> libres = new ArrayList<>();
        for (Asiento a : vuelo.getAvion().getAsientos()) {
            if (a.isDisponible()) {
                libres.add(a);
            }
        }
        return libres;
    }


}
