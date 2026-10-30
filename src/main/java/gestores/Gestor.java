package gestores;

import exceptions.AccesoDatosException;
import interfaces.Identificable;
import exceptions.ElementoNoEncontradoException;
import java.util.ArrayList;
import java.util.List;

public abstract class Gestor <T extends Identificable>{

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
    public boolean eliminar(T elemento){return false;}
    public List<T> listar() {
        return elementos;
    }
    public T buscar(String id){
        return null;
    }
    public int cantidad(){return elementos.size();}
    public boolean existe(T elemento){return false;}

    public void guardarEnBD(T t)throws AccesoDatosException {

    }

    public abstract List<T> listarDesdeBD() throws AccesoDatosException;

    public abstract T buscarEnBD(String codigo) throws AccesoDatosException, ElementoNoEncontradoException;

    public abstract void actualizarEnBD(T t) throws AccesoDatosException, ElementoNoEncontradoException;

    public abstract void eliminarDeBD(String codigo) throws AccesoDatosException, ElementoNoEncontradoException;
}
