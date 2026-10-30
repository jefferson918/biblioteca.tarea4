package jar.dao;

import jar.model.Usuario;

public interface IUsuarioDAO {
    boolean insertar(Usuario usuario);
    Usuario buscarPorCodigo(String codigo);
    Usuario validarCredenciales(String codigo, String clave);
    Usuario buscarPorId(int idUsuario);
}