package com.covielloDevs.SistemaDeVerificacion.models.entrada;

import com.covielloDevs.SistemaDeVerificacion.utils.enums.EstadoEntrada;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "entradas")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Entrada {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private UUID token;
    private EstadoEntrada estado;

    @PrePersist
    public void onCreate(){
        this.token = UUID.randomUUID();
        this.estado = EstadoEntrada.PENDIENTE;
    }
}
