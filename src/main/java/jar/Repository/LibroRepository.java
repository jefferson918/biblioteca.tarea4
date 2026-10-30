package jar.Repository;

import jar.model.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LibroRepository extends JpaRepository<Libro, String> {
    @Query("SELECT l FROM Libro l WHERE (:criterio IS NULL OR l.titulo LIKE %:criterio% OR l.autor LIKE %:criterio%) AND (:categoria IS NULL OR l.categoria = :categoria)")
    List<Libro> buscarPorCriterioYCategoria(@Param("criterio") String criterio, @Param("categoria") String categoria);
}