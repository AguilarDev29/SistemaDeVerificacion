package com.covielloDevs.SistemaDeVerificacion.models.usuario;

import com.covielloDevs.SistemaDeVerificacion.models.base.Persona;
import com.covielloDevs.SistemaDeVerificacion.models.movimiento.Movimiento;
import com.covielloDevs.SistemaDeVerificacion.models.usuario.dto.DtoAltaUsuarioAdmin;
import com.covielloDevs.SistemaDeVerificacion.models.usuario.dto.DtoUpdateUsuario;
import com.covielloDevs.SistemaDeVerificacion.models.usuario.dto.DtoUpdateUsuarioAdmin;
import com.covielloDevs.SistemaDeVerificacion.utils.enums.Rol;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "usuarios")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class Usuario extends Persona implements UserDetails {

    private String username;
    private String password;
    @Enumerated(EnumType.STRING)
    @NotNull
    @Column(nullable = false)
    private Rol rol;
    private String foto;
    private String pin;
    private Boolean activo;
//    @OneToMany(mappedBy = "usuario", fetch = FetchType.LAZY,
//            orphanRemoval = true, cascade = CascadeType.ALL)
//    private Set<Movimiento> movimientos = new HashSet<>();
//    private LocalDateTime ultimoIngreso;
//    private LocalDateTime ultimoEgreso;
//    private Boolean ingresoActivo;


    public Usuario(DtoAltaUsuarioAdmin usuario) {
        this.setApellido(usuario.apellido());
        this.setNombre(usuario.nombre());
        this.setDni(usuario.dni());
        this.setEmail(usuario.email());
        this.setTelefono(usuario.telefono());
        this.rol = usuario.rol();
    }

    public Usuario(DtoUpdateUsuarioAdmin usuario) {
        this.setApellido(usuario.apellido());
        this.setNombre(usuario.nombre());
        this.setDni(usuario.dni());
        this.setEmail(usuario.email());
        this.setTelefono(usuario.telefono());
        this.rol = usuario.rol();
    }

    public Usuario(DtoUpdateUsuario usuario) {
        this.setApellido(usuario.apellido());
        this.setNombre(usuario.nombre());
        this.setEmail(usuario.email());
        this.setTelefono(usuario.telefono());
    }

    @PrePersist
    protected void onCreate(){
        this.activo = true;
        if(this.rol == null) this.rol = Rol.USER;
//        this.ingresoActivo = false;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + rol.name()));
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
