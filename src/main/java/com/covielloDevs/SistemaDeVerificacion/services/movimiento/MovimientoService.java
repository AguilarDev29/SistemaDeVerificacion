package com.covielloDevs.SistemaDeVerificacion.services.movimiento;

import com.covielloDevs.SistemaDeVerificacion.models.movimiento.Movimiento;
import com.covielloDevs.SistemaDeVerificacion.models.usuario.Usuario;
import com.covielloDevs.SistemaDeVerificacion.repositories.MovimientoRepository;
import com.covielloDevs.SistemaDeVerificacion.repositories.UsuarioRepository;
import com.covielloDevs.SistemaDeVerificacion.utils.enums.TipoIngreso;
import com.covielloDevs.SistemaDeVerificacion.utils.exceptions.MovimientoException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class MovimientoService implements IMovimientoService{

    private final MovimientoRepository movimientoRepository;
    private final UsuarioRepository usuarioRepository;

    public MovimientoService(MovimientoRepository movimientoRepository, UsuarioRepository usuarioRepository) {
        this.movimientoRepository = movimientoRepository;
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public Movimiento getMovimiento(Long id) {
        return movimientoRepository.findById(id)
                .orElseThrow(() -> new MovimientoException(HttpStatus.NOT_FOUND, "Movimiento no encontrado"));
    }

    @Override
    public Page<Movimiento> getAll(Pageable pageable) {
        return movimientoRepository.findAll(pageable);
    }

    @Override
    public Page<Movimiento> getAllByUser(Pageable pageable, Usuario usuario) {
        return movimientoRepository.findAllByUsuario(pageable, usuario);
    }

    @Override
    public Page<Movimiento> getAllByUserAdmin(Pageable pageable, Long id) {
        var user = usuarioRepository.findById(id);
        if(user.isEmpty())
            throw new MovimientoException(HttpStatus.NOT_FOUND, "Usuario no encontrado");

        return movimientoRepository.findAllByUsuario(pageable, user.get());
    }

    @Override
    public void createMovimiento(Usuario usuario, Movimiento movimiento) {
        movimiento.setUsuario(usuario);
        if(usuario.getIngresoActivo()){
            movimiento.setTipo(TipoIngreso.SALIDA);
            usuario.setIngresoActivo(false);
            usuarioRepository.save(usuario);
        }else{
            movimiento.setTipo(TipoIngreso.ENTRADA);
            usuario.setIngresoActivo(true);
            usuarioRepository.save(usuario);
        }

        movimientoRepository.save(movimiento);
    }

    @Override
    public void deleteMovimiento(Long id) {
        if(movimientoRepository.findById(id).isEmpty())
            throw new MovimientoException(HttpStatus.NOT_FOUND, "Movimiento no encontrado");

        movimientoRepository.deleteById(id);
    }
}