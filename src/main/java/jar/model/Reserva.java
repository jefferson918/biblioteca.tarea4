package jar.model;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "reservas")
public class Reserva {

    @Id
    @Column(name = "codigo_reserva")
    private String codigoReserva;

    @ManyToOne
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "codigo_libro", nullable = false)
    private Libro libro;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "fecha_solicitud")
    private Date fechaSolicitud;

    @Temporal(TemporalType.DATE)
    @Column(name = "fecha_recogida")
    private Date fechaRecogida;

    @Column(name = "ubicacion_recogida")
    private String ubicacionRecogida;

    @Column(name = "estado_reserva")
    private String estadoReserva;

    public Reserva() {}

    public boolean crearReserva() {
        this.fechaSolicitud = new Date();
        this.estadoReserva = "Pendiente";
        return true;
    }

    public boolean cancelarReserva() {
        this.estadoReserva = "Cancelada";
        return true;
    }

    public void cambiarEstado(String estado) {
        this.estadoReserva = estado;
    }

    // Getters y Setters
    public String getCodigoReserva() { return codigoReserva; }
    public void setCodigoReserva(String codigoReserva) { this.codigoReserva = codigoReserva; }

    public Usuario getUsuario() { return usuario; }
    public void setUsuario(Usuario usuario) { this.usuario = usuario; }

    public Libro getLibro() { return libro; }
    public void setLibro(Libro libro) { this.libro = libro; }

    public Date getFechaSolicitud() { return fechaSolicitud; }
    public void setFechaSolicitud(Date fechaSolicitud) { this.fechaSolicitud = fechaSolicitud; }

    public Date getFechaRecogida() { return fechaRecogida; }
    public void setFechaRecogida(Date fechaRecogida) { this.fechaRecogida = fechaRecogida; }

    public String getUbicacionRecogida() { return ubicacionRecogida; }
    public void setUbicacionRecogida(String ubicacionRecogida) { this.ubicacionRecogida = ubicacionRecogida; }

    public String getEstadoReserva() { return estadoReserva; }
    public void setEstadoReserva(String estadoReserva) { this.estadoReserva = estadoReserva; }
}