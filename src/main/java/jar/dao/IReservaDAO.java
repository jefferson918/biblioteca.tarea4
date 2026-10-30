package jar.dao;

import jar.model.Reserva;
import java.util.List;

public interface IReservaDAO {

    boolean insertar(Reserva reserva);

    boolean actualizarEstado(String idReserva, String nuevoEstado);

    Reserva buscarPorCodigo(String codigoReserva);

    List<Reserva> listarPorUsuario(int idUsuario);

    List<Reserva> obtenerTodas();
}