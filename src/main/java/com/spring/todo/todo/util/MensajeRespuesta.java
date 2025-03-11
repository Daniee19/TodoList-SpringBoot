package com.spring.todo.todo.util;


import lombok.*;

import java.io.Serializable;

@Data
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MensajeRespuesta implements Serializable {
    private String mensaje;
    private Object objeto;
}
