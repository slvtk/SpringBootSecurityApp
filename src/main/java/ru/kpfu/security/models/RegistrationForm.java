package ru.kpfu.security.models;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.Max;

@Data
@NoArgsConstructor
public class RegistrationForm {
    @Email
    private String email;
    @Max(value = 10)
    private String password;
    @Max(value = 10)
    private String name;
    @Max(value = 10)
    private String city;
    @Max(value = 32)
    private String about;
}
