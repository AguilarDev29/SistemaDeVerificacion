package com.covielloDevs.SistemaDeVerificacion.controllers;

import com.covielloDevs.SistemaDeVerificacion.models.usuario.Usuario;
import com.covielloDevs.SistemaDeVerificacion.models.usuario.dto.DtoAltaUsuarioAdmin;
import com.covielloDevs.SistemaDeVerificacion.models.usuario.dto.DtoDatosUsuario;
import com.covielloDevs.SistemaDeVerificacion.models.usuario.dto.DtoUpdateUsuario;
import com.covielloDevs.SistemaDeVerificacion.models.usuario.dto.DtoUpdateUsuarioAdmin;
import com.covielloDevs.SistemaDeVerificacion.services.qr.QRCodeService;
import com.covielloDevs.SistemaDeVerificacion.services.usuario.IUsuarioService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.net.URI;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/v1/users")
public class UsuarioController {
    private final IUsuarioService usuarioService;
    private final QRCodeService qrCodeService;
    public UsuarioController(IUsuarioService usuarioService, QRCodeService qrCodeService) {
        this.usuarioService = usuarioService;
        this.qrCodeService = qrCodeService;
    }

    @GetMapping("/admin/all")
    public ResponseEntity<List<DtoDatosUsuario>> getAllUsers(Pageable pageable){
        return ResponseEntity.ok(usuarioService.getAllUsers(pageable).map(DtoDatosUsuario::new).toList());
    }

    @GetMapping("/admin/{id}")
    public ResponseEntity<DtoDatosUsuario> getUserById(@PathVariable Long id){
        Usuario usuario = usuarioService.getUser(id);
        return ResponseEntity.ok(new DtoDatosUsuario(usuario));
    }

    @GetMapping("/data")
    public ResponseEntity<DtoDatosUsuario> getUserData(@AuthenticationPrincipal Usuario usuario){
        Usuario userData = usuarioService.getUser(usuario.getId());
        return ResponseEntity.ok(new DtoDatosUsuario(userData));
    }


    @PostMapping("/admin/create")
    public ResponseEntity<DtoDatosUsuario> createUser(@RequestBody @Valid DtoAltaUsuarioAdmin usuario){
        Usuario createdUser = usuarioService.createUser(new Usuario(usuario));

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(createdUser.getId())
                .toUri();

        return ResponseEntity.created(location).body(new DtoDatosUsuario(createdUser));
    }

    @PutMapping("/admin/update/{id}")
    public ResponseEntity<DtoDatosUsuario> updateUserAdmin(@PathVariable Long id,
                                                      @RequestBody @Valid DtoUpdateUsuarioAdmin usuario){
        return ResponseEntity.ok(new DtoDatosUsuario(usuarioService.updateUserAdmin(new Usuario(usuario), id)));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<DtoDatosUsuario> updateUser(@PathVariable Long id,
                                                      @RequestBody @Valid DtoUpdateUsuario usuario){
        return ResponseEntity.ok(new DtoDatosUsuario(usuarioService.updateUser(new Usuario(usuario), id)));
    }

    @PatchMapping("/add/foto/{id}")
    public ResponseEntity<DtoDatosUsuario> addFoto(@PathVariable Long id,
                                                   @RequestParam MultipartFile foto) throws IOException {
        return ResponseEntity.ok(new DtoDatosUsuario(usuarioService.addFoto(id, foto)));
    }

    @PatchMapping("/admin/enable/{id}")
    public ResponseEntity<Map<String, String>> enableUser(@PathVariable Long id){
        var user = usuarioService.getUser(id);
        usuarioService.enableDisableUser(id, true);
        return ResponseEntity.ok(Map.of("Message", "Usuario activado exitosamente"));
    }

    @PatchMapping("/admin/disable/{id}")
    public ResponseEntity<Map<String, String>> disableUser(@PathVariable Long id){
        var user = usuarioService.getUser(id);
        usuarioService.enableDisableUser(id, false);
        return ResponseEntity.ok(Map.of("Message", "Usuario desactivado exitosamente"));
    }

    @DeleteMapping("/admin/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id){
        usuarioService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}
