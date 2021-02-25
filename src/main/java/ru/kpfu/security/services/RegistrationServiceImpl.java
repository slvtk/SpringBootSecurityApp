package ru.kpfu.security.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.kpfu.security.models.ConfirmationToken;
import ru.kpfu.security.models.RegistrationForm;
import ru.kpfu.security.models.Student;
import ru.kpfu.security.repositories.ConfirmationTokenRepository;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class RegistrationServiceImpl implements RegistrationService {

    private final UserDetailsServiceImpl userDetailsService;
    private final ConfirmationTokenRepository confirmationTokenRepository;

    @Autowired
    public RegistrationServiceImpl(UserDetailsServiceImpl userDetailsService,
                                   ConfirmationTokenRepository confirmationTokenRepository) {
        this.userDetailsService = userDetailsService;
        this.confirmationTokenRepository = confirmationTokenRepository;
    }

    @Override
    public void register(RegistrationForm form) {
        userDetailsService.signUp(form);
    }

    @Transactional
    @Override
    public void confirmToken(String token) {
        Optional<ConfirmationToken> confirmationTokenOptional = confirmationTokenRepository.findByToken(token);
        if (confirmationTokenOptional.isPresent()) {
            ConfirmationToken confirmationToken = confirmationTokenOptional.get();
            Student studentForConfirmation = confirmationToken.getStudent();
            if (confirmationToken.getExpiredAt().isBefore(LocalDateTime.now())) {
                throw new IllegalStateException();
            }
            if (studentForConfirmation.isEnabled()) {
                throw new IllegalStateException();
            }
            userDetailsService.enableStudent(confirmationToken.getStudent());
            confirmationToken.setActivatedAt(LocalDateTime.now());
        }
    }
}
