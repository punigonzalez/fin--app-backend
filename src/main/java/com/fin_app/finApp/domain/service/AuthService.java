package com.fin_app.finApp.domain.service;

import com.fin_app.finApp.application.dto.JwtResponse;
import com.fin_app.finApp.application.dto.LoginRequest;
import com.fin_app.finApp.application.dto.MensajeResponse;
import com.fin_app.finApp.application.dto.RegistroRequest;
import com.fin_app.finApp.domain.model.Usuario;
import com.fin_app.finApp.domain.repository.UsuarioRepository;
import com.fin_app.finApp.infrastructure.security.JwtUtils;
import com.fin_app.finApp.infrastructure.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder encoder;
    private final JwtUtils jwtUtils;

    public JwtResponse autenticarUsuario(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);
        
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        Set<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority().replace("ROLE_", ""))
                .collect(Collectors.toSet());

        return new JwtResponse(
                jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getEmail(),
                roles);
    }

    @Transactional
    public MensajeResponse registrarUsuario(RegistroRequest registroRequest) {
        if (usuarioRepository.existsByUsername(registroRequest.getUsername())) {
            throw new RuntimeException("Error: El nombre de usuario ya está en uso");
        }

        if (usuarioRepository.existsByEmail(registroRequest.getEmail())) {
            throw new RuntimeException("Error: El email ya está en uso");
        }

        // Crear nuevo usuario
        Usuario usuario = Usuario.builder()
                .username(registroRequest.getUsername())
                .password(encoder.encode(registroRequest.getPassword()))
                .email(registroRequest.getEmail())
                .roles(new HashSet<>())
                .activo(true)
                .build();

        // Por defecto, asignar rol de usuario
        usuario.addRol("USER");
        
        usuarioRepository.save(usuario);

        return new MensajeResponse("Usuario registrado exitosamente");
    }
}