package com.covielloDevs.SistemaDeVerificacion.models.cliente;

import com.covielloDevs.SistemaDeVerificacion.models.base.Persona;
import com.covielloDevs.SistemaDeVerificacion.models.evento.Evento;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Entity
@Table(name = "clientes")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Cliente extends Persona {

    @OneToMany(mappedBy = "anfitrion", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Evento> eventos;
}
