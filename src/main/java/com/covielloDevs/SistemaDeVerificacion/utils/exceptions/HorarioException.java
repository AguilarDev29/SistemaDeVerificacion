package com.covielloDevs.SistemaDeVerificacion.utils.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class HorarioException extends RuntimeException {
    private HttpStatus errorCode;
    private String errorMessage;
}
