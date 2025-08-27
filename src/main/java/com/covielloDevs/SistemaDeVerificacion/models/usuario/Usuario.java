package com.covielloDevs.SistemaDeVerificacion.models.usuario;

import com.covielloDevs.SistemaDeVerificacion.models.movimiento.Movimiento;
import com.covielloDevs.SistemaDeVerificacion.models.usuario.dto.DtoAltaUsuarioAdmin;
import com.covielloDevs.SistemaDeVerificacion.models.usuario.dto.DtoUpdateUsuario;
import com.covielloDevs.SistemaDeVerificacion.models.usuario.dto.DtoUpdateUsuarioAdmin;
import com.covielloDevs.SistemaDeVerificacion.utils.enums.Rol;
import com.covielloDevs.SistemaDeVerificacion.utils.enums.Sexo;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
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
@EqualsAndHashCode(of = "id")
@ToString
public class Usuario implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String password;
    @Enumerated(EnumType.STRING)
    @NotNull
    @Column(nullable = false)
    private Rol rol;
    @NotBlank
    private String apellido;
    @NotBlank
    private String nombre;
    @NotBlank
    @Column(unique = true)
    private String dni;
    @Email
    @Column(unique = true)
    private String email;
    private String telefono;
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "direccion_id")
//    private Direccion direccion;
    @Enumerated(EnumType.STRING)
    @NotNull
    @Column(nullable = false)
    private Sexo sexo;
    @NotNull
    @Column(nullable = false)
    private LocalDate fechaNacimiento;
    private String foto;
    private String pin;
    private Boolean activo;
    @OneToMany(mappedBy = "usuario", fetch = FetchType.LAZY,
            orphanRemoval = true, cascade = CascadeType.ALL)
    private Set<Movimiento> movimientos = new HashSet<>();
    private LocalDateTime ultimoIngreso;
    private LocalDateTime ultimoEgreso;
    private Boolean ingresoActivo;

    public Usuario(DtoAltaUsuarioAdmin usuario) {
        this.apellido = usuario.apellido();
        this.nombre = usuario.nombre();
        this.dni = usuario.dni();
        this.sexo = usuario.sexo();
        this.fechaNacimiento = usuario.fechaNacimiento();
        this.email = usuario.email();
        this.telefono = usuario.telefono();
        this.rol = usuario.rol();
    }

    public Usuario(DtoUpdateUsuarioAdmin usuario) {
        this.apellido = usuario.apellido();
        this.nombre = usuario.nombre();
        this.dni = usuario.dni();
        this.email = usuario.email();
        this.telefono = usuario.telefono();
        this.fechaNacimiento = usuario.fechaNacimiento();
        this.sexo = usuario.sexo();
        this.rol = usuario.rol();
    }

    public Usuario(DtoUpdateUsuario usuario) {
        this.apellido = usuario.apellido();
        this.nombre = usuario.nombre();
        this.email = usuario.email();
        this.telefono = usuario.telefono();
        this.fechaNacimiento = usuario.fechaNacimiento();
        this.sexo = usuario.sexo();
    }

    @PrePersist
    protected void onCreate(){
        this.activo = true;
        if(this.rol == null) this.rol = Rol.USER;
        this.ingresoActivo = false;
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
