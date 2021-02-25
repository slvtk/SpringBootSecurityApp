package ru.kpfu.security.services;

import ru.kpfu.security.models.ConfirmationToken;
import ru.kpfu.security.models.Student;

public interface ConfirmationTokenService {

    void saveToken(ConfirmationToken confirmationToken);

    ConfirmationToken createToken(Student student);

}
