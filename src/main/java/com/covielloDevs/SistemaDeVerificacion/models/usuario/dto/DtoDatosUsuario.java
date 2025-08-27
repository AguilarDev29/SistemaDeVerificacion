package com.covielloDevs.SistemaDeVerificacion.models.usuario.dto;


import com.covielloDevs.SistemaDeVerificacion.models.usuario.Usuario;
import com.covielloDevs.SistemaDeVerificacion.utils.enums.Rol;

public record DtoDatosUsuario(
        String apellido,
        String nombre,
        String email,
        Rol rol,
        String dni,
        String estado
) {
    public DtoDatosUsuario(Usuario usuario) {
        this(usuario.getApellido(),
                usuario.getNombre(),
                usuario.getEmail(),
                usuario.getRol(),
                usuario.getDni(),
                usuario.getActivo() ? "Activo" : "Inactivo");
    }
}
