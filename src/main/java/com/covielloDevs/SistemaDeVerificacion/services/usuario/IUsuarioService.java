package com.covielloDevs.SistemaDeVerificacion.services.usuario;

import com.covielloDevs.SistemaDeVerificacion.models.usuario.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface IUsuarioService {

    Usuario getUser(Long id);

    Page<Usuario> getAllUsers(Pageable pageable);

    Usuario createUser(Usuario usuario);

    Usuario updateUserAdmin(Usuario usuario, long id);

    Usuario updateUser(Usuario usuario, long id);

    Boolean changePassword(Usuario usuario, String password);

    Usuario addFoto(Long id, MultipartFile foto) throws IOException;

    void enableDisableUser(long id, boolean enable);

    void deleteUser(long id);
}
