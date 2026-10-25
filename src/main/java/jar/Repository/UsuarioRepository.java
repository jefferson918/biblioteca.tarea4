package jar.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import jar.model.Usuario;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByCodigoInstitucional(String codigoInstitucional);
}