package net.weg.otavio.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

@AllArgsConstructor
public enum Authorization implements GrantedAuthority {
    GET("Get"),
    POST("Post"),
    PUT("Put"),
    DELETE("Delete");

    private final String name;

    @Override
    public String getAuthority() {
        return name();
    }
}
