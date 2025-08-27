package com.covielloDevs.SistemaDeVerificacion.controllers;

import com.covielloDevs.SistemaDeVerificacion.models.dto.DtoAuthUser;
import com.covielloDevs.SistemaDeVerificacion.models.dto.DtoTokenUser;
import com.covielloDevs.SistemaDeVerificacion.services.security.JwtService;
import com.covielloDevs.SistemaDeVerificacion.services.security.UserDetailsService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final JwtService jwtService;

    public AuthController(AuthenticationManager authenticationManager, UserDetailsService userDetailsService, JwtService jwtService) {
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.jwtService = jwtService;
    }

    @PostMapping("/login")
    public ResponseEntity<DtoTokenUser> authenticate(@RequestBody DtoAuthUser request){
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.username(),
                request.password()));
        var user = userDetailsService.loadUserByUsername(request.username());
        var token = jwtService.generateToken(user);
        return ResponseEntity.ok(new DtoTokenUser(token));
    }


}
