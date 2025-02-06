package com.i2i.userrole.mapper;

import com.i2i.userrole.dto.UserDTO;
import com.i2i.userrole.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring") // Tells MapStruct to generate a Spring Bean
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    // Convert User entity to UserDTO
    @Mapping(source = "role.roleName", target="role")
    UserDTO toDto(User user);
}
