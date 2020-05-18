package org.fouda.administration.utils;

import org.fouda.administration.dto.UserDto;
import org.fouda.administration.entities.UserEntity;

import java.util.function.BiConsumer;
import java.util.function.Function;


public class UserBuilderUtil {
    public final static Function<UserEntity, UserDto> ENTITY_TO_DTO = UserBuilderUtil::buildUserDto;
    public final static Function<UserDto, UserEntity> DTO_TO_ENTITY = UserBuilderUtil::buildUserEntity;
    public final static BiConsumer<UserEntity, UserDto> COPY_DTO_TO_ENTITY = UserBuilderUtil::copyUserData;

    private UserBuilderUtil() {
    }

    private static UserEntity buildUserEntity(UserDto userDto) {
        return UserEntity.builder()
                .githubProfileUrl(userDto.getGithubProfileUrl())
                .position(userDto.getPosition())
                .surName(userDto.getSurName())
                .firstName(userDto.getFirstName())
                .build();
    }

    private static UserDto buildUserDto(UserEntity userEntity) {
        return UserDto.builder()
                .id(userEntity.getId())
                .firstName(userEntity.getFirstName())
                .githubProfileUrl(userEntity.getGithubProfileUrl())
                .position(userEntity.getPosition())
                .surName(userEntity.getSurName())
                .build();
    }

    private static void copyUserData(UserEntity user, UserDto dto) {
        user.setGithubProfileUrl(dto.getGithubProfileUrl());
        user.setFirstName(dto.getFirstName());
        user.setSurName(dto.getSurName());
        user.setPosition(dto.getPosition());
    }
}
