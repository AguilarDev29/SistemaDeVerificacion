package com.covielloDevs.SistemaDeVerificacion.services;

import com.covielloDevs.SistemaDeVerificacion.models.usuario.Usuario;
import com.covielloDevs.SistemaDeVerificacion.repositories.UsuarioRepository;
import com.covielloDevs.SistemaDeVerificacion.utils.saveFiles.ISaveFiles;
import com.covielloDevs.SistemaDeVerificacion.utils.exceptions.usuario.UsuarioDniDuplicateException;
import com.covielloDevs.SistemaDeVerificacion.utils.exceptions.usuario.UsuarioEmailDuplicateException;
import com.covielloDevs.SistemaDeVerificacion.utils.exceptions.usuario.UsuarioNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class UsuarioService {
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final ISaveFiles saveFiles;
    public UsuarioService(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder,
                          ISaveFiles saveFiles) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
        this.saveFiles = saveFiles;
    }

    public Usuario getUser(Long id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new UsuarioNotFoundException(String.format("El usuario con id %s no exite", id)));
    }

    public Page<Usuario> getAllUsers(Pageable pageable) {
        return usuarioRepository.findAll(pageable);
    }

    public Usuario createUser(Usuario usuario) {

        if(usuarioRepository.existsByDni(usuario.getDni()))
            throw new UsuarioDniDuplicateException("El DNI ingresado ya existe");

        if(usuarioRepository.existsByEmail(usuario.getEmail()))
            throw new UsuarioEmailDuplicateException("El email ingresado ya existe");

        usuario.setUsername(usuario.getDni());
        usuario.setPassword(passwordEncoder.encode(usuario.getDni()));
        return usuarioRepository.save(usuario);
    }

    public Usuario updateUserAdmin(Usuario usuario, long id) {
        var userToUpdate = usuarioRepository.findById(id);

        if(userToUpdate.isEmpty())
            throw new UsuarioNotFoundException(String.format("Usuario con id %s no encontrado", id));

        if(usuarioRepository.existsByDni(usuario.getDni()))
            throw new UsuarioDniDuplicateException("El DNI ingresado ya existe");

        if(usuarioRepository.existsByEmail(usuario.getEmail()))
            throw new UsuarioEmailDuplicateException("El email ingresado ya existe");

        if(usuario.getApellido() != null) userToUpdate.get()
                .setApellido(usuario.getApellido());
        if(usuario.getNombre() != null) userToUpdate.get()
                .setNombre(usuario.getNombre());
        if(usuario.getDni() != null) userToUpdate.get()
                .setDni(usuario.getDni());
        if(usuario.getFechaNacimiento() != null) userToUpdate.get()
                .setFechaNacimiento(usuario.getFechaNacimiento());
        if(usuario.getEmail() != null) userToUpdate.get()
                .setEmail(usuario.getEmail());
        if(usuario.getTelefono() != null) userToUpdate.get()
                .setTelefono(usuario.getTelefono());
        if(usuario.getRol() != null) userToUpdate.get()
                .setRol(usuario.getRol());

        return usuarioRepository.save(userToUpdate.get());
    }

    public Usuario updateUser(Usuario usuario, long id){
        var userToUpdate = usuarioRepository.findById(id);

        if(userToUpdate.isEmpty())
            throw new UsuarioNotFoundException(String.format("Usuario con id %s no encontrado", id));

        if(usuario.getApellido() != null) userToUpdate.get()
                .setApellido(usuario.getApellido());
        if(usuario.getNombre() != null) userToUpdate.get()
                .setNombre(usuario.getNombre());
        if(usuario.getEmail() != null) userToUpdate.get()
                .setEmail(usuario.getEmail());
        if(usuario.getTelefono() != null) userToUpdate.get()
                .setTelefono(usuario.getTelefono());
        if(usuario.getFechaNacimiento() != null) userToUpdate.get()
                .setFechaNacimiento(usuario.getFechaNacimiento());


        return usuarioRepository.save(userToUpdate.get());
    }

    public Boolean changePassword(Usuario usuario, String password) {
        return null;
    }

    public Usuario addFoto(Long id, MultipartFile foto) throws IOException {
        if(foto.isEmpty())
            throw new RuntimeException("La imagen es requerida");
        var usuario = usuarioRepository.findById(id);
        if(usuario.isEmpty())
            throw new UsuarioNotFoundException(String.format("Usuario con id %s no encontrado", id));


        usuario.get().setFoto(saveFiles.save(foto));

        return usuarioRepository.save(usuario.get());
    }

    public void enableDisableUser(long id, boolean enable) {
        var user = usuarioRepository.findById(id);
        if(user.isEmpty())
            throw new UsuarioNotFoundException(String.format("Usuario con id %s no encontrado", id));

        if (enable)
            user.get().setActivo(true);
        if (!enable)
            user.get().setActivo(false);

        usuarioRepository.save(user.get());
    }

    public void deleteUser(long id) {
        if(usuarioRepository.findById(id).isEmpty())
            throw new UsuarioNotFoundException(String.format("Usuario con id %s no encontrado", id));

        usuarioRepository.deleteById(id);
    }
}
