package gestores;

import exceptions.AccesoDatosException;
import exceptions.DatosInvalidosException;
import exceptions.IdYaExisteException;
import interfaces.DAO;
import interfaces.Identificable;
import exceptions.ElementoNoEncontradoException;
import java.util.ArrayList;
import java.util.List;

public abstract class Gestor <T extends Identificable> {

    protected List<T> elementos;
    protected final DAO<T> dao;

    public Gestor(DAO<T> dao) {
        this.elementos = new ArrayList<>();
        this.dao = dao;
    }

    public void sincronizarDesdeBD() throws AccesoDatosException {
        this.elementos = dao.listarTodos();
    }

    public boolean agregar(T elemento) throws IdYaExisteException, DatosInvalidosException {
        if (elemento == null) {
            throw new DatosInvalidosException("El elemento no puede ser nulo.");
        }
        if (elemento.getId() != 0 && existe(elemento)) {
            throw new IdYaExisteException(elemento.getId());
        }
        return elementos.add(elemento);
    }

    public boolean existe(T elemento) {
        if (elemento == null) return false;
        for (T t : elementos) {
            if (elemento.getId() == t.getId()) {
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

    public boolean eliminar(T elemento) throws ElementoNoEncontradoException {
        if (elemento == null) {
            throw new DatosInvalidosException("El elemento a eliminar no puede ser nulo.");
        }
        if (!existe(elemento)) {
            throw new ElementoNoEncontradoException(elemento.getId());
        }
        return elementos.remove(elemento);
    }

    public List<T> listar() {
        return new ArrayList<>(elementos);
    }

    public int cantidad() {
        return elementos.size();
    }

    public void guardarEnBD(T t) throws AccesoDatosException {
        dao.insertar(t);
        if (!existe(t)) {
            elementos.add(t);
        }
    }

    public List<T> listarDesdeBD() throws AccesoDatosException {
        this.elementos = dao.listarTodos();
        return listar();
    }

    public T buscarEnBD(int id) throws AccesoDatosException, ElementoNoEncontradoException {
        return dao.buscarPorId(id);
    }

    public void actualizarEnBD(T t) throws AccesoDatosException, ElementoNoEncontradoException {
        dao.actualizar(t);
        sincronizarDesdeBD();
    }

    public void eliminarDeBD(int id) throws AccesoDatosException, ElementoNoEncontradoException {
        dao.eliminar(id);
        for (int i = 0; i < elementos.size(); i++) {
            if (elementos.get(i).getId() == id) {
                elementos.remove(i);
                break;
            }
        }
    }
}

