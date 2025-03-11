package com.spring.todo.todo.modelo.dto;

import lombok.*;
/**
 * El Date solo permite fecha, el TimeStamp en SQL permite fecha y hora
 */
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class TareaDTO {
    private int idTarea;
    private String titulo;
    private String descripcion;
    private LocalDate fechaCreacion;
}