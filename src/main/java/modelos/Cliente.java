package modelos;

public class Cliente extends Persona {

    private String email;

    public Cliente(int id, String nombre, String apellido, String genero, int dni, String email) {
        super(id, nombre, apellido, genero, dni);
        this.email = email;
    }

    public Cliente() {
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
                ", nombre='" + nombre + '\'' +
                ", apellido='" + apellido + '\'' +
                ", genero='" + genero + '\'' +
                ", dni=" + dni +
                '}';
    }
}
