package com.user.model;

import com.user.utils.RoleUser;
import io.quarkus.hibernate.orm.panache.PanacheEntity;
import io.quarkus.security.IdentityAttribute;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "tb-users")
public class UserEntity extends PanacheEntity {
    @NotNull @NotBlank(message = "email empty")
    private String email;
    @NotNull @NotBlank(message = "username empty")
    private String username;
    @NotNull @NotBlank(message = "password empty")
    private String password;
    @Enumerated(EnumType.STRING)
    private RoleUser rol;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public RoleUser getRol() {
        return rol;
    }

    public void setRol(RoleUser rol) {
        this.rol = rol;
    }
}
