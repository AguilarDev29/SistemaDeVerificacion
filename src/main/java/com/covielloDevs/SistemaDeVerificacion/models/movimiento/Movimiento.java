package com.covielloDevs.SistemaDeVerificacion.models.movimiento;

import com.covielloDevs.SistemaDeVerificacion.models.usuario.Usuario;
import com.covielloDevs.SistemaDeVerificacion.utils.enums.TipoIngreso;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "movimientos")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")

public class Movimiento{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime horario;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo")
    private TipoIngreso tipo;

    public Movimiento(Usuario usuario) {
        this.usuario = usuario;
    }


    @PrePersist
    protected void onCreate(){
        this.horario = LocalDateTime.now();
    }
}