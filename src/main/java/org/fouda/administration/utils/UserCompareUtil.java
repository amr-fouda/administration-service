package org.fouda.administration.utils;

import lombok.NonNull;
import org.fouda.administration.dto.UserDto;
import org.fouda.administration.entities.UserEntity;

import java.util.function.BiPredicate;

public class UserCompareUtil {
    public static final BiPredicate<UserEntity, UserDto> IS_SAME_USER = UserCompareUtil::isSameUser;

    private UserCompareUtil() {

    }

    private static boolean isSameUser(@NonNull UserEntity userEntity, @NonNull UserDto userDto) {
        return userDto.getFirstName().equalsIgnoreCase(userEntity.getFirstName()) && userDto.getSurName().equalsIgnoreCase(userEntity.getSurName()) && userDto.getGithubProfileUrl().equalsIgnoreCase(userEntity.getGithubProfileUrl()) && userDto.getPosition().equalsIgnoreCase(userEntity.getPosition());
    }
}
