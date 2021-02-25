package ru.kpfu.security.models;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import java.util.Collection;
import java.util.Collections;

@Data
@Entity
@NoArgsConstructor
public class Student implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String email;
    private String password;
    private String name;
    private String city;
    private String about;
    @Enumerated(value = EnumType.STRING)
    private Role role;
    private boolean locked;
    private boolean enabled;

    public Student(String email,
                   String password,
                   String name,
                   String city,
                   String about,
                   Role role) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.city = city;
        this.about = about;
        this.role = role;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(role);
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !locked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }
}