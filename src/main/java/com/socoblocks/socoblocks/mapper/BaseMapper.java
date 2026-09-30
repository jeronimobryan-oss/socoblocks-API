package com.jwtinternalserviceapi.mapper;

public interface BaseMapper <T,D>{

    T mapDtoToEntity(D entity);

    D mapEntityToDTO(T entity);

}