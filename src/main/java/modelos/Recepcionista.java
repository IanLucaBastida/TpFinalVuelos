package modelos;

public class Recepcionista extends Persona {

    protected int legajo;

    public Recepcionista(String nombre, String apellido, String genero, int dni, int legajo) {
        super(nombre, apellido, genero, dni);
        this.legajo = legajo;
    }

    public Recepcionista() {
    }

    public int getLegajo() {
        return legajo;

    }
    public void setLegajo(int legajo) {
        this.legajo = legajo;
    }

    @Override
    public String toString() {
        return super.toString() + "Recepcionista{" + "legajo=" + legajo + '}';
    }
}
