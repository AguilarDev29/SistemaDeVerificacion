package com.covielloDevs.SistemaDeVerificacion.models.codigoVerificacion;

import com.covielloDevs.SistemaDeVerificacion.models.usuario.Usuario;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
@Entity
@Table(name = "codigos_verificacion")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class CodigoVerificacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "BIGINT UNSIGNED")
    private Long id;
    @Column(unique = true)
    private String codigo;
    private LocalDateTime expiracion;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;
    private Boolean activo;

    public CodigoVerificacion(String codigo){
        this.codigo = codigo;
    }

    @PrePersist
    protected void onCreate(){
        this.expiracion = LocalDateTime.now().plusMinutes(15);
        this.activo = true;
    }
}
