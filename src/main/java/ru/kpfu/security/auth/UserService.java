package ru.kpfu.security.auth;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

import static ru.kpfu.security.security.Role.*;

@Slf4j
@Service
public class UserService implements UserDetailsService {
    PasswordEncoder passwordEncoder;

    public UserService(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return findUserByUserName(username);
    }

    private User findUserByUserName(String username) {
        List<User> users = Arrays.asList(
                new User(ADMIN.getAuthorities(),
                        passwordEncoder.encode("1"),
                        "salavatAdmin"
                ),
                new User(STUDENT.getAuthorities(),
                        passwordEncoder.encode("1"),
                        "antonStudent"
                ),
                new User(
                        ADMIN_TRAINEE.getAuthorities(),
                        passwordEncoder.encode("1"),
                        "sergeyAdminTrainee"
                )
        );
        log.info(users.toString());
        log.info(username);
        log.info(users.stream().filter(user -> user.getUsername().equals(username)).findAny().orElse(null).toString());
        return users.stream().filter(user -> user.getUsername().equals(username)).findAny().orElse(null);
    }
}
