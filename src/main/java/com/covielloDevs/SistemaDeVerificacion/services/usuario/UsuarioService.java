package com.covielloDevs.SistemaDeVerificacion.services.usuario;

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
public class UsuarioService implements IUsuarioService {
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final ISaveFiles saveFiles;
    public UsuarioService(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder,
                          ISaveFiles saveFiles) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
        this.saveFiles = saveFiles;
    }

    @Override
    public Usuario getUser(Long id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new UsuarioNotFoundException(String.format("El usuario con id %s no exite", id)));
    }

    @Override
    public Page<Usuario> getAllUsers(Pageable pageable) {
        return usuarioRepository.findAll(pageable);
    }

    @Override
    public Usuario createUser(Usuario usuario) {

        if(usuarioRepository.findByDni(usuario.getDni()).isPresent())
            throw new UsuarioDniDuplicateException("El DNI ingresado ya existe");

        if(usuarioRepository.findByEmail(usuario.getEmail()).isPresent())
            throw new UsuarioEmailDuplicateException("El email ingresado ya existe");

        usuario.setUsername(usuario.getDni());
        usuario.setPassword(passwordEncoder.encode(usuario.getDni()));
        return usuarioRepository.save(usuario);
    }

    @Override
    public Usuario updateUserAdmin(Usuario usuario, long id) {
        var userToUpdate = usuarioRepository.findById(id);

        if(userToUpdate.isEmpty())
            throw new UsuarioNotFoundException(String.format("Usuario con id %s no encontrado", usuario.getId()));

        if(usuario.getApellido() != null) userToUpdate.get()
                .setApellido(usuario.getApellido());
        if(usuario.getNombre() != null) userToUpdate.get()
                .setNombre(usuario.getNombre());
        if(usuario.getDni() != null) userToUpdate.get()
                .setDni(usuario.getDni());
        if(usuario.getSexo() != null) userToUpdate.get()
                .setSexo(usuario.getSexo());
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
    @Override
    public Usuario updateUser(Usuario usuario, long id){
        var userToUpdate = usuarioRepository.findById(id);

        if(userToUpdate.isEmpty())
            throw new UsuarioNotFoundException(String.format("Usuario con id %s no encontrado", usuario.getId()));

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
        if(usuario.getSexo() != null) userToUpdate.get()
                .setSexo(usuario.getSexo());

        return usuarioRepository.save(userToUpdate.get());
    }

    @Override
    public Boolean changePassword(Usuario usuario, String password) {
        return null;
    }

    @Override
    public Usuario addFoto(Long id, MultipartFile foto) throws IOException {
        if(foto.isEmpty())
            throw new RuntimeException("La imagen es requerida");
        var usuario = usuarioRepository.findById(id);
        if(usuario.isEmpty())
            throw new UsuarioNotFoundException(String.format("Usuario con id %s no encontrado", id));


        usuario.get().setFoto(saveFiles.save(foto));

        return usuarioRepository.save(usuario.get());
    }

    @Override
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

    @Override
    public void deleteUser(long id) {
        if(usuarioRepository.findById(id).isEmpty())
            throw new UsuarioNotFoundException(String.format("Usuario con id %s no encontrado", id));

        usuarioRepository.deleteById(id);
    }
}
