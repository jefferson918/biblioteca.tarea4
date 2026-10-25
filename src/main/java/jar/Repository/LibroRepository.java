package jar.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import jar.model.Libro;

import java.util.List;

public interface LibroRepository extends JpaRepository<Libro, Long> {
    List<Libro> findByTituloContainingIgnoreCaseOrAutorContainingIgnoreCaseOrCategoriaContainingIgnoreCase(String t, String a, String c);
}