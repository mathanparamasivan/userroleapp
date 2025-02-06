package com.i2i.userrole.mapper;

import com.i2i.userrole.dto.UserDTO;
import com.i2i.userrole.entity.User;
import com.i2i.userrole.entity.UserRole;
import com.i2i.userrole.repository.UserRoleRepository;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.modelmapper.spi.MappingContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static reactor.core.publisher.Mono.when;

@Component
public class UserModelMapper {

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    UserRoleRepository userRoleRepository;

    public User getUser(UserDTO userDTO, User user){
        Converter<String, UserRole> roleConverter = new Converter<>() {
            @Override
            public UserRole convert(MappingContext<String, UserRole> mappingContext) {
                String role = userDTO.getRole();
                return userRoleRepository.findByRoleName(role);
            }
        };

        modelMapper.addConverter(roleConverter);
        modelMapper.map(userDTO, user);

        return user;
    }

}
