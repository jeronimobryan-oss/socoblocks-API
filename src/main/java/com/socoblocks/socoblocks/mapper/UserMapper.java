package com.jwtinternalserviceapi.mapper;

import com.jwtinternalserviceapi.dto.UserResponse;
import com.jwtinternalserviceapi.model.AppUser;
import org.springframework.stereotype.Service;

@Service
public class UserMapper implements BaseMapper<AppUser, UserResponse>{

    @Override
    public AppUser mapDtoToEntity(UserResponse entity) {
        return null;
    }

    @Override
    public UserResponse mapEntityToDTO(AppUser entity) {
        return new UserResponse(entity.getId(), entity.getUsername(), entity.getRole());
    }
}
