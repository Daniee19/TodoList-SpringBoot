package com.spring.todo.todo.modelo.entidad;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;

@Data
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tarea")
public class Tarea implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull
    private int idTarea;
    private String titulo;
    private String descripcion;
    /*En Java-> Es la mejor opción si solo se quiere la fecha
    En SQL -> Tendría que ir Date si solo se quiere la fecha
    * */
    private LocalDate fechaCreacion;
    private boolean completado;
}
