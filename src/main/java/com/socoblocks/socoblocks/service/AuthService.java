package com.socoblocks.socoblocks.service;

import com.socoblocks.socoblocks.dto.RegisterRequest;
import com.socoblocks.socoblocks.dto.UserResponse;
import com.socoblocks.socoblocks.exception.UsernameAlreadyExistsException;
import com.socoblocks.socoblocks.mapper.BaseMapper;
import com.socoblocks.socoblocks.model.AppUser;
import com.socoblocks.socoblocks.model.Role;
import com.socoblocks.socoblocks.repository.AppUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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
        //Evalua el rol enviado del dt, si viene null asigna coleccionista
        Role assignedRole = (request.role()!=null)? request.role() : Role.COLECCIONISTA;
        try {
            AppUser user = new AppUser(request.username(),
                    passwordEncoder.encode(request.password()),
                    Role.COLECCIONISTA);
            return mapper.mapEntityToDTO(repository.saveAndFlush(user));
        } catch (DataIntegrityViolationException e) {
            throw new UsernameAlreadyExistsException(request.username());
        }
    }
}
