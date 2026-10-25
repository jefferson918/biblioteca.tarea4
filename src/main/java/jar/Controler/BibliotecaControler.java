package jar.Controler;

import jar.model.Reserva;
import jar.Repository.ReservaRepository;
import jar.Repository.LibroRepository;
import jar.Repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;


@Controller
public class BibliotecaControler {

    @Autowired
    private ReservaRepository reservaRepository;

    @Autowired
    private LibroRepository libroRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @GetMapping({"/", "/buscar-libros"})
    public String buscarLibros(Model model) {
        model.addAttribute("libros", libroRepository.findAll());
        model.addAttribute("usuario", usuarioRepository.findById(1L).orElse(null));
        return "buscar-libros";
    }

    @GetMapping("/reservar/{id}")
    public String verPantallaReserva(@PathVariable("id") Long idLibro, Model model) {
        model.addAttribute("libro", libroRepository.findById(idLibro).orElse(null));
        model.addAttribute("usuario", usuarioRepository.findById(1L).orElse(null));
        return "reservar-libro";
    }

    @PostMapping("/confirmar-reserva")
    public String guardarReservaInDB(@RequestParam("idLibro") Long idLibro,
                                     @RequestParam("sedeRecogida") String sedeRecogida,
                                     @RequestParam("fechaRecogida") String fechaRecogida) {
        Reserva nuevaReserva = new Reserva();
        nuevaReserva.setIdUsuario(1L);
        nuevaReserva.setIdLibro(idLibro);
        nuevaReserva.setSedeRecogida(sedeRecogida);
        
        try {
            nuevaReserva.setFechaRecogida(LocalDate.parse(fechaRecogida));
        } catch (Exception e) {
            nuevaReserva.setFechaRecogida(LocalDate.now().plusDays(2));
        }
        
        nuevaReserva.setEstadoReserva("Pendiente");
        reservaRepository.save(nuevaReserva);

        return "redirect:/mis-reservas";
    }

    @GetMapping("/mis-reservas")
    public String verMisReservas(Model model) {
        model.addAttribute("reservas", reservaRepository.findByIdUsuario(1L));
        model.addAttribute("usuario", usuarioRepository.findById(1L).orElse(null));
        return "mis-reservas";
    }

    // Endpoint para cancelar la reserva en MySQL
    @PostMapping("/cancelar-reserva")
    public String cancelarReserva(@RequestParam("idReserva") Long idReserva) {
        Reserva reserva = reservaRepository.findById(idReserva).orElse(null);
        if (reserva != null) {
            reserva.setEstadoReserva("Cancelada");
            reservaRepository.save(reserva);
        }
        return "redirect:/mis-reservas";
    }
}
