package com.jwtinternalserviceapi.service;

import com.jwtinternalserviceapi.dto.UserResponse;
import com.jwtinternalserviceapi.exception.UserNotFoundException;
import com.jwtinternalserviceapi.mapper.BaseMapper;
import com.jwtinternalserviceapi.model.AppUser;
import com.jwtinternalserviceapi.model.Role;
import com.jwtinternalserviceapi.repository.AppUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final AppUserRepository repository;
    private final BaseMapper<AppUser, UserResponse> mapper;

    public List<UserResponse> findAll() {
        return repository.findAll().stream().map(mapper::mapEntityToDTO).toList();
    }

}
