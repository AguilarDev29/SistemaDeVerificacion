package com.covielloDevs.SistemaDeVerificacion.controllers;

import com.covielloDevs.SistemaDeVerificacion.models.dto.DtoQRToken;
import com.covielloDevs.SistemaDeVerificacion.models.movimiento.Movimiento;
import com.covielloDevs.SistemaDeVerificacion.models.usuario.Usuario;
import com.covielloDevs.SistemaDeVerificacion.services.movimiento.IMovimientoService;
import com.covielloDevs.SistemaDeVerificacion.services.qr.QRCodeService;
import com.covielloDevs.SistemaDeVerificacion.services.qr.QRJwtService;
import com.covielloDevs.SistemaDeVerificacion.services.security.UserDetailsService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/v1/qr")
public class QRController {

    private final QRJwtService qrJwtService;
    private final UserDetailsService userDetailsService;
    private final QRCodeService qrCodeService;
    private final IMovimientoService movimientoService;
    public QRController(QRJwtService qrJwtService, UserDetailsService userDetailsService,
                        QRCodeService qrCodeService, IMovimientoService movimientoService) {
        this.qrJwtService = qrJwtService;
        this.userDetailsService = userDetailsService;
        this.qrCodeService = qrCodeService;
        this.movimientoService = movimientoService;
    }

    @GetMapping("/generate-qr-token")
    public ResponseEntity<Void> generateQrToken(HttpServletResponse response,
                                                               @AuthenticationPrincipal Usuario usuario)
                                                                throws Exception {
        String qrToken = qrJwtService.generateToken(usuario);
        qrCodeService.generateQRCode(response, qrToken);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/generate-token")
    public ResponseEntity<Map<String, String>> generateToken(HttpServletResponse response,
                                                @AuthenticationPrincipal Usuario usuario){
        String qrToken = qrJwtService.generateToken(usuario);
        return ResponseEntity.ok(Map.of("qrToken", qrToken));
    }

    @PostMapping("/validate-qr")
    public ResponseEntity<Map<String, String>> validateToken(@RequestBody DtoQRToken qrToken){
        String username = qrJwtService.validateToken(qrToken.qrToken());
        Usuario usuario = (Usuario) userDetailsService.loadUserByUsername(username);
        movimientoService.createMovimiento(usuario, new Movimiento());
        return ResponseEntity.ok(Map.of("message", "Registro creado"));
    }

}
