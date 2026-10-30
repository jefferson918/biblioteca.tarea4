package jar.dao.impl;

import jar.dao.ILibroDAO;
import jar.model.Libro;
import jar.Repository.LibroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class LibroDAOImpl implements ILibroDAO {

    @Autowired
    private LibroRepository libroRepository;

    @Override
    public boolean insertar(Libro libro) {
        libroRepository.save(libro);
        return true;
    }

    @Override
    public boolean actualizar(Libro libro) {
        libroRepository.save(libro);
        return true;
    }

    @Override
    public boolean eliminar(String codigoLibro) {
        libroRepository.deleteById(codigoLibro);
        return true;
    }

    @Override
    public Libro buscarPorCodigo(String codigoLibro) {
        return 
        libroRepository.findById(codigoLibro).orElse(null);
    }

    @Override
    public List<Libro> buscarPorCriterio(String criterio, String categoria) {
        return libroRepository.buscarPorCriterioYCategoria(criterio, categoria);
    }

    @Override
    public List<Libro> obtenerTodos() {
        return libroRepository.findAll();
    }
}