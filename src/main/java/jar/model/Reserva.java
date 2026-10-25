package jar.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "reservas")
public class Reserva {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_reserva")
    private Long idReserva;

    @Column(name = "id_usuario")
    private Long idUsuario;

    @Column(name = "id_libro")
    private Long idLibro;

    @Column(name = "sede_recogida")
    private String sedeRecogida;

    @Column(name = "fecha_recogida")
    private LocalDate fechaRecogida;

    @Column(name = "estado_reserva")
    private String estadoReserva;

    public Reserva() {}

    public Long getIdReserva() { return idReserva; }
    public void setIdReserva(Long idReserva) { this.idReserva = idReserva; }

    public Long getIdUsuario() { return idUsuario; }
    public void setIdUsuario(Long idUsuario) { this.idUsuario = idUsuario; }

    public Long getIdLibro() { return idLibro; }
    public void setIdLibro(Long idLibro) { this.idLibro = idLibro; }

    public String getSedeRecogida() { return sedeRecogida; }
    public void setSedeRecogida(String sedeRecogida) { this.sedeRecogida = sedeRecogida; }

    public LocalDate getFechaRecogida() { return fechaRecogida; }
    public void setFechaRecogida(LocalDate fechaRecogida) { this.fechaRecogida = fechaRecogida; }

    public String getEstadoReserva() { return estadoReserva; }
    public void setEstadoReserva(String estadoReserva) { this.estadoReserva = estadoReserva; }
}