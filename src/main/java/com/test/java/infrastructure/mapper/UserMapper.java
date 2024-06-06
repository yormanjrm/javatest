package com.test.java.infrastructure.mapper;

import com.test.java.domain.model.User;
import com.test.java.infrastructure.entity.UserEntity;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mappings({
            @Mapping(source = "id", target = "id"),
            @Mapping(source = "name", target = "name"),
            @Mapping(source = "email", target = "email"),
            @Mapping(source = "password", target = "password")
    })

    User toUser(UserEntity userEntity);

    @InheritInverseConfiguration
    UserEntity toUserEntity(User user);

}