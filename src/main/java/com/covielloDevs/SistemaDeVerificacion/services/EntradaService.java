package com.covielloDevs.SistemaDeVerificacion.services;

import com.covielloDevs.SistemaDeVerificacion.models.entrada.Entrada;
import com.covielloDevs.SistemaDeVerificacion.repositories.EntradaRepository;
import com.covielloDevs.SistemaDeVerificacion.utils.enums.EstadoEntrada;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class EntradaService {

    private final EntradaRepository entradaRepository;

    public EntradaService(EntradaRepository entradaRepository) {
        this.entradaRepository = entradaRepository;
    }

    public void createEntrada(){
        entradaRepository.save(new Entrada());
    }

    public void useEntrada(String token){

        UUID tokenUUID = UUID.fromString(token);
        var entrada = entradaRepository.findByToken(tokenUUID);

        if(entradaRepository.existsByTokenAndEstado(tokenUUID, EstadoEntrada.PENDIENTE))
            throw new RuntimeException("Entrada ya usada");

        entrada.ifPresentOrElse(e -> e.setEstado(EstadoEntrada.USADO),
                () -> entrada.orElseThrow(() -> new RuntimeException("Entrada no encontrada")));


    }
}
