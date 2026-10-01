package com.socoblocks.socoblocks.service;

import com.socoblocks.socoblocks.dto.UserResponse;
import com.socoblocks.socoblocks.mapper.BaseMapper;
import com.socoblocks.socoblocks.model.AppUser;
import com.socoblocks.socoblocks.repository.AppUserRepository;
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
