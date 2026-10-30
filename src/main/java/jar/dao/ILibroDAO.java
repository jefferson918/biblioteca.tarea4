package jar.dao;

import jar.model.Libro;
import java.util.List;

public interface ILibroDAO {
    boolean insertar(Libro libro);
    boolean actualizar(Libro libro);
    boolean eliminar(String codigoLibro);
    Libro buscarPorCodigo(String codigoLibro);
    List<Libro> buscarPorCriterio(String criterio, String categoria);
    List<Libro> obtenerTodos();
}