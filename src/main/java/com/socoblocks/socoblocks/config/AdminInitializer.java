package com.jwtinternalserviceapi.config;

import com.jwtinternalserviceapi.model.AppUser;
import com.jwtinternalserviceapi.model.Role;
import com.jwtinternalserviceapi.repository.AppUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AdminInitializer implements ApplicationRunner {

    private final AppUserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final AdminProperties props;

    @Override
    public void run(ApplicationArguments args) {
        if (!repository.existsByUsername(props.username())) {
            repository.save(new AppUser(props.username(), passwordEncoder.encode(props.password()), Role.ADMIN));
        }
    }
}
