package jar.Controller;

import jar.dao.ILibroDAO;
import jar.dao.IReservaDAO;
import jar.dao.IUsuarioDAO;
import jar.model.Estudiante;
import jar.model.Libro;
import jar.model.Reserva;
import jar.model.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@Transactional
public class ReservaController {

    @Autowired
    private ILibroDAO libroDAO;

    @Autowired
    private IUsuarioDAO usuarioDAO;

    @Autowired
    private IReservaDAO reservaDAO;

    @PostMapping("/confirmar-reserva")
    @ResponseBody
    public ResponseEntity<String> procesarReserva(@RequestParam(value = "codigoLibro", required = false) String codigoLibro,
                                                  @RequestParam(value = "sedeRecogida", required = false) String sedeRecogida,
                                                  @RequestParam(value = "fechaRecogida", required = false) String fechaRecogida) {
        
        if (codigoLibro == null || codigoLibro.isBlank()) {
            codigoLibro = "LIB-001";
        }

        try {
            Libro libro = libroDAO.buscarPorCodigo(codigoLibro);
            if (libro == null) {
                List<Libro> todos = libroDAO.obtenerTodos();
                if (todos != null && !todos.isEmpty()) {
                    libro = todos.get(0);
                }
            }

            Usuario usuario = obtenerUsuarioDemo();

            if (libro != null && usuario != null) {
                Reserva nuevaReserva = new Reserva();
                nuevaReserva.setCodigoReserva("RES-" + System.currentTimeMillis());
                nuevaReserva.setLibro(libro);
                nuevaReserva.setUsuario(usuario);
                
                Date fechaActual = new Date();
                nuevaReserva.setFechaSolicitud(fechaActual);

                Date fechaRecogidaParsed = fechaActual;
                if (fechaRecogida != null && !fechaRecogida.isBlank()) {
                    try {
                        SimpleDateFormat sdf = new SimpleDateFormat("dd 'de' MMMM 'de' yyyy");
                        fechaRecogidaParsed = sdf.parse(fechaRecogida);
                    } catch (Exception parseEx) {
                        fechaRecogidaParsed = new Date(fechaActual.getTime() + (3L * 24 * 60 * 60 * 1000));
                    }
                } else {
                    fechaRecogidaParsed = new Date(fechaActual.getTime() + (3L * 24 * 60 * 60 * 1000));
                }

                nuevaReserva.setFechaRecogida(fechaRecogidaParsed);
                nuevaReserva.setUbicacionRecogida(sedeRecogida != null && !sedeRecogida.isBlank() ? sedeRecogida : "Biblioteca Sede A (Campus Belaunde)");
                nuevaReserva.setEstadoReserva("Aprobada");

                boolean exito = reservaDAO.insertar(nuevaReserva);
                if (exito) {
                    return ResponseEntity.ok("OK");
                }
            }
            return ResponseEntity.status(400).body("Error al crear reserva");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Error en el servidor: " + e.getMessage());
        }
    }

    @PostMapping("/cancelar-reserva")
    public String cancelarReserva(@RequestParam("idReserva") String idReserva) {
        try {
            reservaDAO.actualizarEstado(idReserva, "Cancelada");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:/mis-reservas";
    }

    @GetMapping("/detalle-reserva")
    @ResponseBody
    public ResponseEntity<?> obtenerDetalleReserva(@RequestParam("codigo") String codigo) {
        Map<String, Object> data = new HashMap<>();
        try {
            Reserva r = reservaDAO.buscarPorCodigo(codigo);
            if (r != null) {
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
                SimpleDateFormat sdfDate = new SimpleDateFormat("dd/MM/yyyy");

                data.put("codigo", r.getCodigoReserva());
                data.put("titulo", r.getLibro() != null ? r.getLibro().getTitulo() : "N/A");
                data.put("autor", r.getLibro() != null ? r.getLibro().getAutor() : "N/A");
                data.put("codigoLibro", r.getLibro() != null ? r.getLibro().getCodigoLibro() : "N/A");
                data.put("usuario", r.getUsuario() != null ? r.getUsuario().getNombreCompleto() : "N/A");
                data.put("codigoInstitucional", r.getUsuario() != null ? r.getUsuario().getCodigoInstitucional() : "N/A");
                data.put("fechaSolicitud", r.getFechaSolicitud() != null ? sdf.format(r.getFechaSolicitud()) : "N/A");
                data.put("fechaRecogida", r.getFechaRecogida() != null ? sdfDate.format(r.getFechaRecogida()) : "N/A");
                data.put("ubicacion", r.getUbicacionRecogida() != null ? r.getUbicacionRecogida() : "Sede La Molina");
                data.put("estado", r.getEstadoReserva() != null ? r.getEstadoReserva() : "Aprobada");

                return ResponseEntity.ok(data);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(404).body(null);
    }

    @GetMapping("/mis-reservas")
    public String verMisReservas(Model model) {
        Usuario usuarioDemo = obtenerUsuarioDemo();
        List<Reserva> reservas = new ArrayList<>();

        try {
            reservas = reservaDAO.obtenerTodas();
            if (reservas == null) {
                reservas = new ArrayList<>();
            }
        } catch (Exception e) {
            e.printStackTrace();
            reservas = new ArrayList<>();
        }

        model.addAttribute("reservas", reservas);
        model.addAttribute("usuario", usuarioDemo);
        return "mis-reservas";
    }

    private Usuario obtenerUsuarioDemo() {
        Usuario usuarioDemo = null;
        try {
            usuarioDemo = usuarioDAO.buscarPorId(1);
        } catch (Exception e) {
            System.err.println("Buscando usuario demo id=1: " + e.getMessage());
        }

        if (usuarioDemo == null) {
            Estudiante est = new Estudiante();
            est.setIdUsuario(1);
            est.setNombreCompleto("Stefany Vicente");
            est.setCodigoInstitucional("2022104567");
            est.setCarrera("Ingeniería de Sistemas");
            est.setCiclo(6);

            try {
                usuarioDAO.insertar(est);
                usuarioDemo = usuarioDAO.buscarPorId(1);
            } catch (Exception e) {
                usuarioDemo = est;
            }
        }
        return usuarioDemo;
    }
}