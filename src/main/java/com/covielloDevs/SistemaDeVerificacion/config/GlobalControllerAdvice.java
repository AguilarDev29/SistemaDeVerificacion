package com.covielloDevs.SistemaDeVerificacion.config;

import com.covielloDevs.SistemaDeVerificacion.utils.exceptions.HorarioException;
import com.covielloDevs.SistemaDeVerificacion.utils.exceptions.MovimientoException;
import com.covielloDevs.SistemaDeVerificacion.utils.exceptions.UsuarioException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalControllerAdvice {

    @ExceptionHandler(UsuarioException.class)
    public ResponseEntity<String> usuarioException(UsuarioException e){
        return new ResponseEntity<String>(e.getErrorMessage(), e.getErrorCode());
    }

    @ExceptionHandler(HorarioException.class)
    public ResponseEntity<String> horarioException(HorarioException e){
        return new ResponseEntity<String>(e.getErrorMessage(), e.getErrorCode());
    }

    @ExceptionHandler(MovimientoException.class)
    public ResponseEntity<String> movimientoException(MovimientoException e){
        return new ResponseEntity<String>(e.getErrorMessage(), e.getErrorCode());
    }
}
