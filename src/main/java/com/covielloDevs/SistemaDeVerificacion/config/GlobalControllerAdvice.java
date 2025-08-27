package com.covielloDevs.SistemaDeVerificacion.config;

import com.covielloDevs.SistemaDeVerificacion.utils.exceptions.HorarioException;
import com.covielloDevs.SistemaDeVerificacion.utils.exceptions.MovimientoException;
import com.covielloDevs.SistemaDeVerificacion.utils.exceptions.usuario.UsuarioDniDuplicateException;
import com.covielloDevs.SistemaDeVerificacion.utils.exceptions.usuario.UsuarioEmailDuplicateException;
import com.covielloDevs.SistemaDeVerificacion.utils.exceptions.usuario.UsuarioNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalControllerAdvice {

    @ExceptionHandler(value = UsuarioEmailDuplicateException.class)

    public ResponseEntity<String> usuarioEmailDuplicateException(UsuarioEmailDuplicateException e){
        return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
    }

    public ResponseEntity<String> usuarioDniDuplicateException(UsuarioDniDuplicateException e){
        return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(value = UsuarioNotFoundException.class)
    public ResponseEntity<String> usuarioException(UsuarioEmailDuplicateException e){
        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = HorarioException.class)
    public ResponseEntity<String> horarioException(HorarioException e){
        return new ResponseEntity<String>(e.getErrorMessage(), e.getErrorCode());
    }

    @ExceptionHandler(value = MovimientoException.class)
    public ResponseEntity<String> movimientoException(MovimientoException e){
        return new ResponseEntity<String>(e.getErrorMessage(), e.getErrorCode());
    }
}
