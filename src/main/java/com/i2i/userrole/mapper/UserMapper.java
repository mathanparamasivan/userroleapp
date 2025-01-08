package com.i2i.userrole.mapper;

import com.i2i.userrole.dto.UserDTO;
import com.i2i.userrole.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring") // Tells MapStruct to generate a Spring Bean
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    // Convert UserDTO to User entity
    User toEntity(UserDTO userDTO);

    // Convert User entity to UserDTO
    UserDTO toDto(User user);
}
