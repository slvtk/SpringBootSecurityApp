package ru.kpfu.security.security;

public enum Permission {
    STUDENTS_READ("students:read"),
    STUDENTS_WRITE("students:write"),
    COURSES_READ("courses:read"),
    COURSES_WRITE("courses:write");
    private final String permission;

    Permission(String permission) {
        this.permission = permission;
    }

    public String getPermission() {
        return permission;
    }
}
