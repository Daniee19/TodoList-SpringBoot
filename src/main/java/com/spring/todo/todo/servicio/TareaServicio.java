package com.spring.todo.todo.servicio;

import com.spring.todo.todo.modelo.dto.TareaDTO;
import com.spring.todo.todo.modelo.entidad.Tarea;
import com.spring.todo.todo.modelo.repositorio.TareaRepositorio;
import com.spring.todo.todo.servicio.interfaces.ITarea;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class TareaServicio implements ITarea {

    @Autowired
    private TareaRepositorio repositorio;

    @Override
    public List<Tarea> listar() {

        List<Tarea> lista = (List<Tarea>) repositorio.findAll();

        if (lista.isEmpty()) {
            return Collections.emptyList();
        }
        return lista;

    }

    @Transactional
    @Override
    public void eliminar(Tarea tarea) {
        repositorio.deleteById(tarea.getIdTarea());
    }

    @Transactional
    @Override
    public Tarea buscarPorId(int id) {
        return repositorio.findById(id).orElse(null);
    }

    @Transactional
    @Override
    public Tarea crear(TareaDTO tareaDTO) {
/**
 * Tarea tarea = new Tarea();
 * tarea.setIdTarea(tareaDTO.getIdTarea());
 * Se tiene que llamar a la clase Tarea.builder para construir la instancia de la clase Tarea
 */
/**
 * Es importante implementar el idTarea para poder ver si existe y poder editarlo.
 * -> Si ya existe ese id en la base de datos, en lugar de crear un nuevo registro, se procede a actualizar en el registro de ese id.
 */
        Tarea tarea = Tarea.builder().
                idTarea(tareaDTO.getIdTarea()).
                titulo(tareaDTO.getTitulo()).
                descripcion(tareaDTO.getDescripcion()).
                fechaCreacion(tareaDTO.getFechaCreacion()).
                build();
        return repositorio.save(tarea);
    }

    @Override
    public boolean existePorId(int id) {
        return repositorio.existsById(id);
    }

}
