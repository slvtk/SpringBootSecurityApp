package ru.kpfu.security.services;

import ru.kpfu.security.models.RegistrationForm;

public interface RegistrationService {
    void register(RegistrationForm email);
    void confirmToken(String token);
}
