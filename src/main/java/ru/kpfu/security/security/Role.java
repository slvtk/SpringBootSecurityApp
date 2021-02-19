package ru.kpfu.security.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public enum Role{
    STUDENT(new HashSet<>()),
    ADMIN(new HashSet<>(Arrays.asList(
            Permission.COURSES_READ,
            Permission.COURSES_WRITE,
            Permission.STUDENTS_READ,
            Permission.STUDENTS_WRITE))),

    ADMIN_TRAINEE(new HashSet<>(Arrays.asList(
            Permission.COURSES_READ,
            Permission.STUDENTS_READ)));

    private final Set<Permission> permissions;

    Role(Set<Permission> permissions) {
        this.permissions = permissions;
    }

    public Set<Permission> getPermissions() {
        return permissions;
    }

    public Set<GrantedAuthority> getAuthorities() {
        Set<GrantedAuthority> authorities = getPermissions().stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toSet());
        authorities.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
        return authorities;
    }
}
