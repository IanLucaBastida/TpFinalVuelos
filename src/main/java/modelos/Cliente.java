package modelos;

import java.util.Scanner;

public class Cliente extends Persona {

    private String email;
    private double millasRecorridas;


    public Cliente(String nombre, String apellido, String genero, int dni, String email, double millasRecorridas) {
        super(nombre, apellido, genero, dni);
        this.millasRecorridas = 0;
        this.email = email;
    }


    public Cliente() {
    }

    public double getMillasRecorridas() {
        return millasRecorridas;
    }

    public void setMillasRecorridas(double millasRecorridas) {
        this.millasRecorridas = millasRecorridas;
    }

    @Override
    public String getGenero() {
        return super.getGenero();
    }

    @Override
    public void setGenero(String genero) {
        super.setGenero(genero);
    }

    @Override
    public String getNombre() {
        return super.getNombre();
    }

    @Override
    public void setNombre(String nombre) {
        super.setNombre(nombre);
    }

    @Override
    public String getApellido() {
        return super.getApellido();
    }

    @Override
    public void setApellido(String apellido) {
        super.setApellido(apellido);
    }

    @Override
    public int getDni() {
        return super.getDni();
    }

    @Override
    public void setDni(int dni) {
        super.setDni(dni);
    }

    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "Cliente{" +
                "email='" + email + '\'' +
                ", millasRecorridas=" + millasRecorridas +
                '}';
    }



}
