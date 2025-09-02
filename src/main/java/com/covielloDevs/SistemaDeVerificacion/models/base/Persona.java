package com.covielloDevs.SistemaDeVerificacion.models.base;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@MappedSuperclass
@Getter
@Setter
@EqualsAndHashCode(of = "id")
public class Persona {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String apellido;
    private String nombre;
    @Column(unique = true)
    private String dni;
    @Column(unique = true)
    private String email;
    private String telefono;
    private LocalDate fechaNacimiento;
}
