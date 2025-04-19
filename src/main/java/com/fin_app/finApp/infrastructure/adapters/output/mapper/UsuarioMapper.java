package com.fin_app.finApp.infrastructure.persistence.mapper;

import com.fin_app.finApp.domain.model.Usuario;
import com.fin_app.finApp.infrastructure.persistence.entity.UsuarioEntity;
import org.springframework.stereotype.Component;

@Component
public class UsuarioMapper {

    public Usuario toDomain(UsuarioEntity entity) {
        if (entity == null) {
            return null;
        }
        
        return Usuario.builder()
                .id(entity.getId())
                .username(entity.getUsername())
                .password(entity.getPassword())
                .email(entity.getEmail())
                .roles(entity.getRoles())
                .activo(entity.isActivo())
                .build();
    }
    
    public UsuarioEntity toEntity(Usuario domain) {
        if (domain == null) {
            return null;
        }
        
        return UsuarioEntity.builder()
                .id(domain.getId())
                .username(domain.getUsername())
                .password(domain.getPassword())
                .email(domain.getEmail())
                .roles(domain.getRoles())
                .activo(domain.isActivo())
                .build();
    }
}