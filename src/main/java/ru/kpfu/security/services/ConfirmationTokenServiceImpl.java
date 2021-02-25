package ru.kpfu.security.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.kpfu.security.models.ConfirmationToken;
import ru.kpfu.security.models.Student;
import ru.kpfu.security.repositories.ConfirmationTokenRepository;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class ConfirmationTokenServiceImpl implements ConfirmationTokenService {
    private final ConfirmationTokenRepository confirmationTokenRepository;

    @Autowired
    public ConfirmationTokenServiceImpl(ConfirmationTokenRepository confirmationTokenRepository) {
        this.confirmationTokenRepository = confirmationTokenRepository;
    }

    @Override
    public void saveToken(ConfirmationToken confirmationToken) {
        confirmationTokenRepository.save(confirmationToken);
    }

    @Override
    public ConfirmationToken createToken(Student student) {
        String token = UUID.randomUUID().toString();

        return new ConfirmationToken(
                token,
                LocalDateTime.now(),
                LocalDateTime.now().plusMinutes(15),
                student
        );
    }


}
