package com.spring.todo.todo.controladorrest;

import com.spring.todo.todo.modelo.dto.TareaDTO;
import com.spring.todo.todo.modelo.entidad.Tarea;
import com.spring.todo.todo.servicio.TareaServicio;
import com.spring.todo.todo.util.MensajeRespuesta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.logging.Logger;

@Controller
@RequestMapping("/api/todo")
public class TareaCR {
    Logger logger = Logger.getLogger(com.spring.todo.todo.controlador.TareaControlador.class.getName());
    TareaServicio tareaServicio;

    @Autowired
    public TareaCR(TareaServicio tareaServicio) {
        this.tareaServicio = tareaServicio;
    }

    @GetMapping("/tareas")
    public ResponseEntity<?> verTareas() {
        try {
            List<Tarea> lista = tareaServicio.listar();
            if (lista.isEmpty()) {
                return new ResponseEntity<>("No hay tareas disponibles", HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(MensajeRespuesta.builder().mensaje("Tareas mostradas con éxito")
                    .objeto(lista).build(), HttpStatus.OK);
        } catch (Exception e) {
            logger.severe("Error al obtener tareas: " + e.getMessage());
            return new ResponseEntity<>("Error interno del servidor", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/tarea")
    public ResponseEntity<?> crearTarea(@RequestBody TareaDTO tareaDTO) {
        Tarea elementoCreado = null;
        try {
            elementoCreado = tareaServicio.crear(tareaDTO);
            return new ResponseEntity<>(MensajeRespuesta.builder().mensaje("Tarea creada con éxito")
                    .objeto(elementoCreado).build(), HttpStatus.OK);
        } catch (Exception e) {
            System.out.println("Error al crear la tarea: " + e.getMessage());
            return new ResponseEntity<>(MensajeRespuesta.builder().mensaje("Error interno del servidor: " +
                    e.getMessage()).objeto(null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/tarea/{id}")
    public ResponseEntity<?> eliminarTarea(@PathVariable int id) {
        try {
            tareaServicio.eliminar(tareaServicio.buscarPorId(id));
            return new ResponseEntity<>(MensajeRespuesta.builder().mensaje("Tarea eliminada exitosamente")
                    .objeto(tareaServicio.buscarPorId(id)).build(), HttpStatus.OK);
        } catch (DataAccessException e) {
            return new ResponseEntity<>(MensajeRespuesta.builder().mensaje(e.getMessage()).objeto(null),
                    HttpStatus.METHOD_NOT_ALLOWED);
        }

    }

    @GetMapping("/tarea/{id}")
    public ResponseEntity<?> encontrarPorId(@PathVariable int id) {
        Tarea tareaEncontrada = tareaServicio.buscarPorId(id);
        if (tareaEncontrada == null) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(MensajeRespuesta.builder().mensaje("Elemento con id: " + id + " encontrado exitosamente")
                .objeto(tareaEncontrada).build(), HttpStatus.OK);
    }

    @PutMapping("/tarea/{id}")
    public ResponseEntity<?> actualizarTarea(@PathVariable int id, @RequestBody TareaDTO tareaDTO) {
        Tarea tareaUpdate = null;
        try {
            if (tareaServicio.existePorId(id)) {
                tareaDTO.setIdTarea(id);
                tareaUpdate = tareaServicio.crear(tareaDTO);
                return new ResponseEntity<>(MensajeRespuesta.builder().mensaje("Tarea actualizada exitosamente")
                        .objeto(tareaUpdate).build(), HttpStatus.OK);
            }
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
