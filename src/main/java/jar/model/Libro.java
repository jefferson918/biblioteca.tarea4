package jar.model;

import jakarta.persistence.*;

@Entity
@Table(name = "libros")
public class Libro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idLibro;
    private String codigoLibro;
    private String titulo;
    private String autor;
    private String categoria;
    private String editorial;
    private String descripcion;
    private String estadoDisponibilidad;

    public Long getIdLibro() { return idLibro; }
    public void setIdLibro(Long idLibro) { this.idLibro = idLibro; }
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
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    public String getEstadoDisponibilidad() { return estadoDisponibilidad; }
    public void setEstadoDisponibilidad(String estadoDisponibilidad) { this.estadoDisponibilidad = estadoDisponibilidad; }
}