package com.covielloDevs.SistemaDeVerificacion.config;

import com.covielloDevs.SistemaDeVerificacion.utils.exceptions.movimiento.MovimientoNotFoundException;
import com.covielloDevs.SistemaDeVerificacion.utils.exceptions.token.TokenBadRequestException;
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
    @ExceptionHandler(value = UsuarioDniDuplicateException.class)
    public ResponseEntity<String> usuarioDniDuplicateException(UsuarioDniDuplicateException e){
        return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(value = UsuarioNotFoundException.class)
    public ResponseEntity<String> usuarioException(UsuarioEmailDuplicateException e){
        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = MovimientoNotFoundException.class)
    public ResponseEntity<String> movimientoException(MovimientoNotFoundException e){
        return new ResponseEntity<String>(e.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = TokenBadRequestException.class)
    public ResponseEntity<String> tokenBadRequestException(TokenBadRequestException e){
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
