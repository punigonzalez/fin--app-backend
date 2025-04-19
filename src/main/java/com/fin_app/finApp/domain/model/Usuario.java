package com.fin_app.finApp.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Usuario {
    private Long id;
    private String username;
    private String password;
    private String email;
    private Set<String> roles = new HashSet<>();
    private boolean activo;
    
    public void addRol(String rol) {
        if (this.roles == null) {
            this.roles = new HashSet<>();
        }
        this.roles.add(rol);
    }
}

//comentario prueba para dev-test