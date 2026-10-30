package jar.dao.impl;

import jar.dao.IUsuarioDAO;
import jar.model.Usuario;
import jar.Repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class UsuarioDAOImpl implements IUsuarioDAO {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public boolean insertar(Usuario usuario) {
        usuarioRepository.save(usuario);
        return true;
    }

    @Override
    public Usuario buscarPorCodigo(String codigo) {
        return usuarioRepository.findByCodigoInstitucional(codigo);
    }

    @Override
    public Usuario validarCredenciales(String codigo, String clave) {
        Usuario u = usuarioRepository.findByCodigoInstitucional(codigo);
        if (u != null && u.validarCredenciales(clave)) {
            return u;
        }
        return null;
    }

    @Override
    public Usuario buscarPorId(int idUsuario) {
        return usuarioRepository.findById(idUsuario).orElse(null);
    }
}