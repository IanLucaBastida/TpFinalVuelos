package modelos;

import interfaces.Identificable;

import java.util.Objects;

public abstract class Persona implements Identificable {

    private int id;
    protected String nombre;
    protected String apellido;
    protected String genero;
    protected int dni;


    public Persona(int id,String nombre, String apellido, String genero, int dni) {
        setId(id);
        this.nombre = nombre;
        this.apellido = apellido;
        this.genero = genero;
        this.dni = dni;
    }

    public Persona() {
    }


    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public int getDni() {
        return dni;
    }

    public void setDni(int dni) {
        this.dni = dni;
    }

    @Override
    public String toString() {
        return "Persona{" +
                "nombre='" + nombre + '\'' +
                ", apellido='" + apellido + '\'' +
                ", dni=" + dni +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Persona persona)) return false;
        return dni == persona.dni;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(dni);
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public int getId() {return this.id;
    }
}
