package com.covielloDevs.SistemaDeVerificacion.repositories;

import com.covielloDevs.SistemaDeVerificacion.models.codigoVerificacion.CodigoVerificacion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface CodigoVerificacionRepository extends JpaRepository<CodigoVerificacion, Long> {

    Boolean existsByCodigo(String codigo);

    Optional<CodigoVerificacion> findByCodigo(String codigo);

    List<CodigoVerificacion> findByActivoTrueAndExpiracionBefore(LocalDateTime localDateTime);

}
