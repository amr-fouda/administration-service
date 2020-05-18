package org.fouda.administration.services;

import lombok.NonNull;
import org.fouda.administration.dto.UserDto;
import org.springframework.stereotype.Service;

@Service
public class UserValidatorService {
    public void validateUserDto(@NonNull UserDto userDto) {
        if (userDto.getId() == null || userDto.getPosition() == null || userDto.getGithubProfileUrl() == null || userDto.getSurName() == null || userDto.getFirstName() == null) {
            throw new IllegalArgumentException("Missing mandatory user data");
        }
    }
}
