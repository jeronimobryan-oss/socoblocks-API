package com.socoblocks.socoblocks.service;

import com.socoblocks.socoblocks.dto.RegisterRequest;
import com.socoblocks.socoblocks.dto.UserResponse;
import com.socoblocks.socoblocks.exception.UsernameAlreadyExistsException;
import com.socoblocks.socoblocks.mapper.BaseMapper;
import com.socoblocks.socoblocks.model.AppUser;
import com.socoblocks.socoblocks.model.Role;
import com.socoblocks.socoblocks.repository.AppUserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthService {

    private final AppUserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final BaseMapper<AppUser, UserResponse> mapper;

    public UserResponse register(RegisterRequest request) {
        Role assignedRole = (request.role() != null) ? request.role() : Role.COLECCIONISTA;

        if (assignedRole == Role.ADMIN) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Rol no permitido");
        }
        if (repository.existsByUsername(request.username())) {
            throw new UsernameAlreadyExistsException(request.username());
        }

        try {
            AppUser user = new AppUser(request.username(),
                    passwordEncoder.encode(request.password()),
                    assignedRole);
            return mapper.mapEntityToDTO(repository.saveAndFlush(user));
        } catch (DataIntegrityViolationException e) {
            log.error("Error al registrar usuario", e.getMostSpecificCause());
            throw new UsernameAlreadyExistsException(request.username());
        }
    }
}
