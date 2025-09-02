package com.covielloDevs.SistemaDeVerificacion.models.evento;

import com.covielloDevs.SistemaDeVerificacion.models.cliente.Cliente;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "eventos")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Evento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private String descripcion;
    private LocalDate fecha;
    private LocalTime hora;
    private String foto;
    @ManyToOne
    @JoinColumn(name = "anfitrion_id")
    private Cliente anfitrion;
}
