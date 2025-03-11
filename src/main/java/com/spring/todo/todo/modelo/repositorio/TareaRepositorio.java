package com.spring.todo.todo.modelo.repositorio;

import com.spring.todo.todo.modelo.entidad.Tarea;
import org.springframework.data.repository.CrudRepository;

public interface TareaRepositorio extends CrudRepository<Tarea, Integer> {

}
