package com.jwtinternalserviceapi.service;

import com.jwtinternalserviceapi.dto.LoginRequest;
import com.jwtinternalserviceapi.dto.RegisterRequest;
import com.jwtinternalserviceapi.dto.TokenResponse;
import com.jwtinternalserviceapi.dto.UserResponse;
import com.jwtinternalserviceapi.exception.UsernameAlreadyExistsException;
import com.jwtinternalserviceapi.mapper.BaseMapper;
import com.jwtinternalserviceapi.model.AppUser;
import com.jwtinternalserviceapi.model.Role;
import com.jwtinternalserviceapi.repository.AppUserRepository;
import com.jwtinternalserviceapi.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AppUserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final BaseMapper<AppUser, UserResponse> mapper;

    public UserResponse register(RegisterRequest request) {
        if (repository.existsByUsername(request.username())) {
            throw new UsernameAlreadyExistsException(request.username());
        }
        try {
            AppUser user = new AppUser(request.username(),
                    passwordEncoder.encode(request.password()),
                    Role.USER);
            return mapper.mapEntityToDTO(repository.saveAndFlush(user));
        } catch (DataIntegrityViolationException e) {
            throw new UsernameAlreadyExistsException(request.username());
        }
    }
}
