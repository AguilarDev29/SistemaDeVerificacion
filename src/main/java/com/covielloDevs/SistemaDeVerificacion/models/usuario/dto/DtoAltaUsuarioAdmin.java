package com.covielloDevs.SistemaDeVerificacion.models.usuario.dto;

import com.covielloDevs.SistemaDeVerificacion.utils.enums.Rol;
import com.covielloDevs.SistemaDeVerificacion.utils.enums.Sexo;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDate;

public record DtoAltaUsuarioAdmin(
        @NotBlank(message = "El apellido es requerido")
        String apellido,
        @NotBlank(message = "El nombre es requerido")
        String nombre,
        @Size(min = 7, max = 8, message = "El DNI debe tener entre 7 y 8 caracteres")
        @NotBlank(message = "El DNI es requerido")

        String dni,
        @NotNull(message = "El sexo es requerido")
        Sexo sexo,
        @NotNull(message = "La fecha de nacimiento es requerida")
        LocalDate fechaNacimiento,
        @NotBlank(message = "El email es requerido")
        @Email(message = "El email no es válido")
        String email,
        String telefono,
        Rol rol
) {
}
