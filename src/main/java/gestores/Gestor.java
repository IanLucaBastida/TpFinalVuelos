package gestores;

import interfaces.Identificable;

import java.util.ArrayList;
import java.util.List;

public class Gestor <T extends Identificable>{

    protected List<T> elementos;

    public Gestor() {
        this.elementos = new ArrayList<>();
    }

    public boolean agregar(T elemento){
     return false;
    }
    public boolean actualizar(T elemento){
        return false;
    }
    public boolean eliminar(T elemento){
        return false;
    }
    public List<T> listar() {
        return elementos;
    }
    public T buscar(String id){
        return null;
    }
    public int cantidad(){return elementos.size();}
    public boolean existe(T elemento){return false;}

}
