package gestores;

import exceptions.AccesoDatosException;
import exceptions.DatosInvalidosException;
import exceptions.IdYaExisteException;
import interfaces.Identificable;
import exceptions.ElementoNoEncontradoException;
import java.util.ArrayList;
import java.util.List;

public abstract class Gestor <T extends Identificable>{

    protected List<T> elementos;

    public Gestor() {
        this.elementos = new ArrayList<>();
    }

    public boolean agregar(T elemento) throws IdYaExisteException, DatosInvalidosException {
        if (elemento == null){
            throw new DatosInvalidosException("ERROR");
        }
        if (existe(elemento)){
            throw new IdYaExisteException(elemento.getId());
        }
        return elementos.add(elemento);
    }
    public boolean existe(T elemento) {
        for (T t : elementos){
            if(elemento.getId() == t.getId()){
                return true;
            }
        }
        return false;
    }
    public T buscar(int id) throws ElementoNoEncontradoException {
        for (T elemento : elementos) {
            if (elemento.getId() == id) {
                return elemento;
            }
        }
        throw new ElementoNoEncontradoException(id);
    }
    public boolean eliminar(T  elemento) throws ElementoNoEncontradoException {
        if(elemento == null || !existe(elemento)){
            throw new ElementoNoEncontradoException(elemento.getId());
        }
        return elementos.remove(elemento);
    }
    public  List<T> listar() {
        List<T> lista = new ArrayList<>();
        for (T elemento : elementos) {
            lista.add(elemento);
        }
        return  lista;
    }
    public int cantidad() {
        return elementos.size();
    }

    public abstract void guardarEnBD(T t)throws AccesoDatosException;

    public abstract List<T> listarDesdeBD() throws AccesoDatosException;

    public abstract T buscarEnBD(int codigo) throws AccesoDatosException, ElementoNoEncontradoException;

    public abstract void actualizarEnBD(T t) throws AccesoDatosException, ElementoNoEncontradoException;

    public abstract void eliminarDeBD(int codigo) throws AccesoDatosException, ElementoNoEncontradoException;
}
