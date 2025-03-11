package com.spring.todo.todo.servicio.interfaces;

import com.spring.todo.todo.modelo.dto.TareaDTO;
import com.spring.todo.todo.modelo.entidad.Tarea;

import java.util.List;

public interface ITarea {
    List<Tarea> listar();

    void eliminar(Tarea tarea);

    Tarea buscarPorId(int id);

    Tarea crear(TareaDTO tareaDTO);

    boolean existePorId(int id);
}
