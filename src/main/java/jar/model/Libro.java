package jar.model;

import jakarta.persistence.*;

@Entity
@Table(name = "libros")
public class Libro {

    @Id
    @Column(name = "codigo_libro")
    private String codigoLibro;

    @Column(name = "titulo", nullable = false)
    private String titulo;

    @Column(name = "autor", nullable = false)
    private String autor;

    @Column(name = "categoria")
    private String categoria;

    @Column(name = "editorial")
    private String editorial;

    @Column(name = "anio_publicacion")
    private int anioPublicacion;

    @Column(name = "cantidad_ejemplares")
    private int cantidadEjemplares;

    @Column(name = "estado_disponibilidad")
    private String estadoDisponibilidad;

    public Libro() {}

    public boolean verificarDisponibilidad() {
        return "Disponible".equalsIgnoreCase(this.estadoDisponibilidad) && this.cantidadEjemplares > 0;
    }

    public void actualizarEstado(String nuevoEstado) {
        this.estadoDisponibilidad = nuevoEstado;
    }

    // Getters y Setters
    public String getCodigoLibro() { return codigoLibro; }
    public void setCodigoLibro(String codigoLibro) { this.codigoLibro = codigoLibro; }

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public String getAutor() { return autor; }
    public void setAutor(String autor) { this.autor = autor; }

    public String getCategoria() { return categoria; }
    public void setCategoria(String categoria) { this.categoria = categoria; }

    public String getEditorial() { return editorial; }
    public void setEditorial(String editorial) { this.editorial = editorial; }

    public int getAnioPublicacion() { return anioPublicacion; }
    public void setAnioPublicacion(int anioPublicacion) { this.anioPublicacion = anioPublicacion; }

    public int getCantidadEjemplares() { return cantidadEjemplares; }
    public void setCantidadEjemplares(int cantidadEjemplares) { this.cantidadEjemplares = cantidadEjemplares; }

    public String getEstadoDisponibilidad() { return estadoDisponibilidad; }
    public void setEstadoDisponibilidad(String estadoDisponibilidad) { this.estadoDisponibilidad = estadoDisponibilidad; }
}