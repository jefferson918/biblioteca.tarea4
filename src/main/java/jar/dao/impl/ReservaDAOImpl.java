package jar.dao.impl;

import jar.dao.IReservaDAO;
import jar.model.Reserva;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public class ReservaDAOImpl implements IReservaDAO {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public boolean insertar(Reserva reserva) {
        try {
            entityManager.merge(reserva); // Usar merge previene fallos si la PK String ya trae valor
            entityManager.flush();
            return true;
        } catch (Exception e) {
            System.err.println(">>> ERROR EN PERSISTENCIA DE RESERVA:");
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean actualizarEstado(String idReserva, String nuevoEstado) {
        try {
            Reserva reserva = entityManager.find(Reserva.class, idReserva);
            if (reserva != null) {
                reserva.setEstadoReserva(nuevoEstado);
                entityManager.merge(reserva);
                return true;
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Reserva buscarPorCodigo(String codigoReserva) {
        try {
            return entityManager.find(Reserva.class, codigoReserva);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<Reserva> listarPorUsuario(int idUsuario) {
        try {
            return entityManager.createQuery(
                "SELECT r FROM Reserva r JOIN FETCH r.libro JOIN FETCH r.usuario WHERE r.usuario.idUsuario = :idUsuario", Reserva.class)
                .setParameter("idUsuario", idUsuario)
                .getResultList();
        } catch (Exception e) {
            return obtenerTodas();
        }
    }

    @Override
    public List<Reserva> obtenerTodas() {
        try {
            return entityManager.createQuery(
                "SELECT r FROM Reserva r JOIN FETCH r.libro JOIN FETCH r.usuario", Reserva.class)
                .getResultList();
        } catch (Exception e) {
            return List.of();
        }
    }
}