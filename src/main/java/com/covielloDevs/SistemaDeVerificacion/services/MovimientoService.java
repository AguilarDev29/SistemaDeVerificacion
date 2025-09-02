package com.covielloDevs.SistemaDeVerificacion.services;

import com.covielloDevs.SistemaDeVerificacion.models.movimiento.Movimiento;
import com.covielloDevs.SistemaDeVerificacion.models.usuario.Usuario;
import com.covielloDevs.SistemaDeVerificacion.repositories.MovimientoRepository;
import com.covielloDevs.SistemaDeVerificacion.repositories.UsuarioRepository;
import com.covielloDevs.SistemaDeVerificacion.utils.exceptions.movimiento.MovimientoNotFoundException;
import com.covielloDevs.SistemaDeVerificacion.utils.exceptions.usuario.UsuarioNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class MovimientoService{

    private final MovimientoRepository movimientoRepository;
    private final UsuarioRepository usuarioRepository;

    public MovimientoService(MovimientoRepository movimientoRepository, UsuarioRepository usuarioRepository) {
        this.movimientoRepository = movimientoRepository;
        this.usuarioRepository = usuarioRepository;
    }

    public Movimiento getMovimiento(Long id) {
        return movimientoRepository.findById(id)
                .orElseThrow(() -> new MovimientoNotFoundException(String
                        .format("Movimimiento con el id %s no encontado", id)));
    }

    public Page<Movimiento> getAll(Pageable pageable) {
        return movimientoRepository.findAll(pageable);
    }

    public Page<Movimiento> getAllByUser(Pageable pageable, Usuario usuario) {
        return movimientoRepository.findAllByUsuario(pageable, usuario);
    }

    public Page<Movimiento> getAllByUserAdmin(Pageable pageable, Long id) {
        var user = usuarioRepository.findById(id);
        if(user.isEmpty())
            throw new UsuarioNotFoundException(String.format("Usuario con el id %s no encontrado", id));

        return movimientoRepository.findAllByUsuario(pageable, user.get());
    }

//    public void createMovimiento(Usuario usuario, Movimiento movimiento) {
//        movimiento.setUsuario(usuario);
//        if (usuario.getIngresoActivo()) {
//            movimiento.setTipo(TipoIngreso.SALIDA);
//            usuario.setIngresoActivo(false);
//            usuarioRepository.save(usuario);
//        } else {
//            movimiento.setTipo(TipoIngreso.ENTRADA);
//            usuario.setIngresoActivo(true);
//            usuarioRepository.save(usuario);
//        }
//
//        movimientoRepository.save(movimiento);
//    }

    public void deleteMovimiento(Long id) {
        if(movimientoRepository.findById(id).isEmpty())
            throw new MovimientoNotFoundException(String.format("Movimiento con el id %s no encontrado", id));

        movimientoRepository.deleteById(id);
    }
}