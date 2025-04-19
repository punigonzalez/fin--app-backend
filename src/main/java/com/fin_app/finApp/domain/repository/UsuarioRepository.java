package com.fin_app.finApp.domain.repository;

import com.fin_app.finApp.domain.model.Usuario;

import java.util.Optional;

public interface UsuarioRepository {
    
    Optional<Usuario> findByUsername(String username);
    
    Optional<Usuario> findByEmail(String email);
    
    boolean existsByUsername(String username);
    
    boolean existsByEmail(String email);
    
    Usuario save(Usuario usuario);
}