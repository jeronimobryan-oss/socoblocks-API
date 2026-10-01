package com.socoblocks.socoblocks.mapper;

public interface BaseMapper <T,D>{

    T mapDtoToEntity(D entity);

    D mapEntityToDTO(T entity);

}