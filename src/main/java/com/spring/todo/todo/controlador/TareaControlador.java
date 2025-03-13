package com.spring.todo.todo.controlador;

import com.spring.todo.todo.modelo.dto.TareaDTO;
import com.spring.todo.todo.modelo.entidad.Tarea;
import com.spring.todo.todo.servicio.TareaServicio;
import com.spring.todo.todo.util.MensajeRespuesta;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.logging.Logger;

@Controller
@RequestMapping("/tareas")
public class TareaControlador {
    Logger logger = Logger.getLogger(TareaControlador.class.getName());
    TareaServicio tareaServicio;

    @Autowired
    public TareaControlador(TareaServicio tareaServicio) {
        this.tareaServicio = tareaServicio;
    }

    @GetMapping
    public String verTareas(Model model) {
        try {
            TareaDTO tareaDTO = new TareaDTO();
            List<Tarea> lista = tareaServicio.listar();
            //Es más práctico hacer la lógica aca que en thymeleaf
            if (!lista.isEmpty()) {
                model.addAttribute("tareas", lista);
            }
        } catch (Exception e) {
            logger.severe("Error al obtener tareas: " + e.getMessage());
            //return new ResponseEntity<>("Error interno del servidor", HttpStatus.INTERNAL_SERVER_ERROR);
            model.addAttribute("tareas", "Hubo un error al consultar las tareas");
        }
        return "index";
    }

    @PostMapping
    public String crearTarea(@Valid @ModelAttribute("tarea") TareaDTO tareaDTO, BindingResult br, Model model) {
        try {
            if (br.hasErrors()) {
                return "agregar";
            }
            tareaServicio.crear(tareaDTO);
            verTareas(model);
        } catch (Exception e) {
            System.out.println("Error al crear la tarea: " + e.getMessage());
        }
        return "index";
    }

    //INTERMEDIARIOS
    @GetMapping("/registroCrearTarea")
    public String mostrarFormularioCrearTarea(Model model) {
        model.addAttribute("nuevaTarea", new TareaDTO());
        return "agregar";
    }

    @GetMapping("/registroActualizarTarea/{id}")
    public String mostrarFormularioActualizarTarea(@PathVariable int id, Model model) {
        try {
            Tarea tarea = tareaServicio.buscarPorId(id);
            if (tarea == null) {
                return "index";
            }
            model.addAttribute("tareaObtenida", tarea);

        } catch (Exception e) {
            System.out.println("Error al obtener tarea: " + e.getMessage());
        }
        return "actualizar";
    }

    @DeleteMapping("/{id}")
    public String eliminarTarea(@PathVariable int id) {
        try {
            tareaServicio.eliminar(tareaServicio.buscarPorId(id));
        } catch (DataAccessException e) {
            System.out.println("Error al eliminar la tarea: " + e.getMessage());
        }
        return "index";
    }

    @GetMapping("/{id}")
    public String encontrarPorId(@PathVariable int id, Model model) {
        Tarea tareaEncontrada = tareaServicio.buscarPorId(id);
        if (tareaEncontrada == null) {
            model.addAttribute("tareaEspeficica", false);
            return "index";
        }
        model.addAttribute("tareaEspeficica", true);
        model.addAttribute("tarea", tareaEncontrada);
        return "index";
    }

    /**
     * El @Valid activará la validación de la entidad tareaDTO, el cual tiene seteado los atributos brindados a través del formulario. Si encuentra algun error lo almacenará en el BindingResult.
     * Siempre debe de ir el objeto a evaluar si tiene error, antes del BindingResult.
     *
     * @param tareaDTO --> Objeto que fue creado con anterioridad, por medio de una función intermedia y, por medio del th:field="*{nombre_atributo}" tiene seteado sus atributos
     * @param br       -> Obtendrá los errores
     * @param model    -> Permite agregar propiedades al archivo html
     * @return -> Retorna la vista principal con todos los elementos
     */
    @PutMapping("/{id}")
    public String actualizarTarea(@Valid @ModelAttribute TareaDTO tareaDTO, BindingResult br, Model model) {
        try {
            if (tareaServicio.existePorId(tareaDTO.getIdTarea())) {
                if (br.hasErrors()) {
                    return "actualizar";
                }
                tareaServicio.crear(tareaDTO);
                verTareas(model);
                return "index";
            }
            return "actualizar";
        } catch (Exception e) {
            return "actualizar";
        }
    }
}
