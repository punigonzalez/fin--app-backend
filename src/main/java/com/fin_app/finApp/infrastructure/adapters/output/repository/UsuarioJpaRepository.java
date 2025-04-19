package com.fin_app.finApp.infrastructure.persistence.repository;

import com.fin_app.finApp.infrastructure.persistence.entity.UsuarioEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioJpaRepository extends JpaRepository<UsuarioEntity, Long> {
    
    Optional<UsuarioEntity> findByUsername(String username);
    
    Optional<UsuarioEntity> findByEmail(String email);
    
    boolean existsByUsername(String username);
    
    boolean existsByEmail(String email);
}