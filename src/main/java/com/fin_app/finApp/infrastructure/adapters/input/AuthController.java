package com.fin_app.finApp.infrastructure.adapters.in;

import com.fin_app.finApp.application.dto.JwtResponse;
import com.fin_app.finApp.application.dto.LoginRequest;
import com.fin_app.finApp.application.dto.MensajeResponse;
import com.fin_app.finApp.application.dto.RegistroRequest;
import com.fin_app.finApp.domain.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    
    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<?> autenticarUsuario(@Valid @RequestBody LoginRequest loginRequest) {
        try {
            JwtResponse jwtResponse = authService.autenticarUsuario(loginRequest);
            return ResponseEntity.ok(jwtResponse);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new MensajeResponse("Error: " + e.getMessage()));
        }
    }

    @PostMapping("/registro")
    public ResponseEntity<?> registrarUsuario(@Valid @RequestBody RegistroRequest registroRequest) {
        try {
            MensajeResponse response = authService.registrarUsuario(registroRequest);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new MensajeResponse("Error: " + e.getMessage()));
        }
    }
}