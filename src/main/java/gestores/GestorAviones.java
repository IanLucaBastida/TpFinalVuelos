package gestores;

import dao.AvionDAO;

import enums.ClaseAsiento;
import enums.EstadoAvion;
import exceptions.AccesoDatosException;
import exceptions.AvionLlenoException;
import exceptions.DatosInvalidosException;
import exceptions.ElementoNoEncontradoException;
import interfaces.DAO;
import modelos.Asiento;
import modelos.Avion;

import java.util.List;

public class GestorAviones extends Gestor<Avion>{
    public GestorAviones() {
        super(new AvionDAO());
    }

    public Avion crearAvionConAsientos(String modelo, int capacidad)
            throws AccesoDatosException, AvionLlenoException, ElementoNoEncontradoException, DatosInvalidosException {
        if (modelo == null || modelo.isBlank() || capacidad <= 0 || capacidad > 300) {
            throw new DatosInvalidosException("Modelo invalido o capacidad fuera de rango (1 a 300 asientos).");
        }

        Avion avion = new Avion(0, modelo, capacidad);

        for (int i = 1; i <= capacidad; i++) {
            ClaseAsiento clase;
            if (i <= capacidad * 0.1) {
                clase = ClaseAsiento.PRIMERA;
            } else if (i <= capacidad * 0.25) {
                clase = ClaseAsiento.EJECUTIVA;
            } else if (i <= capacidad * 0.45) {
                clase = ClaseAsiento.ECONOMICA_PREMIUM;
            } else {
                clase = ClaseAsiento.ECONOMICA;
            }
            avion.agregarAsiento(new Asiento(0, i, clase));
        }

        guardarEnBD(avion);
        return avion;
    }

    public void cambiarEstado(int avionId, EstadoAvion nuevoEstado)
            throws AccesoDatosException, ElementoNoEncontradoException {
        Avion avion = buscarEnBD(avionId);
        avion.setEstado(nuevoEstado);
        actualizarEnBD(avion);
    }





}
