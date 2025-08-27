package com.covielloDevs.SistemaDeVerificacion.services.movimiento;

import com.covielloDevs.SistemaDeVerificacion.models.movimiento.Movimiento;
import com.covielloDevs.SistemaDeVerificacion.models.usuario.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IMovimientoService {

    Movimiento getMovimiento(Long id);

    Page<Movimiento> getAll(Pageable pageable);

    Page<Movimiento> getAllByUser(Pageable pageable, Usuario usuario);

    Page<Movimiento> getAllByUserAdmin(Pageable pageable, Long id);

    void createMovimiento(Usuario usuario, Movimiento movimiento);

    void deleteMovimiento(Long id);
}
