package jar.Controller;

import jar.dao.ILibroDAO;
import jar.dao.IUsuarioDAO;
import jar.model.Estudiante;
import jar.model.Libro;
import jar.model.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class CatalogoController {

    @Autowired
    private ILibroDAO libroDAO;

    @Autowired
    private IUsuarioDAO usuarioDAO;

    // 1. Mostrar Catálogo Principal
    @GetMapping({"/", "/buscar-libros"})
    public String buscarLibrosView(@RequestParam(value = "criterio", required = false) String criterio,
                                   @RequestParam(value = "categoria", required = false) String categoria,
                                   Model model) {
        List<Libro> libros = buscarLibros(criterio, categoria);
        Usuario usuarioDemo = obtenerUsuarioDemo();

        model.addAttribute("libros", libros);
        model.addAttribute("usuario", usuarioDemo);
        return "buscar-libros";
    }

    // 2. Pantalla de Selección de Reserva por Código de Libro
    @GetMapping("/reservar/{id}")
    public String verPantallaReserva(@PathVariable("id") String idLibro, Model model) {
        Libro libroEncontrado = consultarDetalle(idLibro);
        Usuario usuarioDemo = obtenerUsuarioDemo();

        model.addAttribute("libro", libroEncontrado);
        model.addAttribute("usuario", usuarioDemo);
        return "reservar-libro";
    }

    public List<Libro> buscarLibros(String criterio, String categoria) {
        if ((criterio == null || criterio.isBlank()) && (categoria == null || categoria.isBlank())) {
            return libroDAO.obtenerTodos();
        }
        return libroDAO.buscarPorCriterio(criterio, categoria);
    }

    public Libro consultarDetalle(String codigoLibro) {
        return libroDAO.buscarPorCodigo(codigoLibro);
    }

    private Usuario obtenerUsuarioDemo() {
        Usuario usuarioDemo = usuarioDAO.buscarPorId(1);
        if (usuarioDemo == null) {
            Estudiante est = new Estudiante();
            est.setIdUsuario(1);
            est.setNombreCompleto("Stefany Vicente");
            est.setCodigoInstitucional("2022104567");
            est.setCarrera("Ingeniería de Sistemas");
            est.setCiclo(6);
            usuarioDemo = est;
        }
        return usuarioDemo;
    }
}