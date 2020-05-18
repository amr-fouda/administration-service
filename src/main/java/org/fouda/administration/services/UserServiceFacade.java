package org.fouda.administration.services;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.fouda.administration.dto.UserDto;
import org.fouda.administration.dto.UserRepositoriesDto;
import org.fouda.administration.exceptions.UserNotFoundException;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceFacade {
    private final UserService userService;
    private final UserValidatorService userValidatorService;
    private final GitHubRestService gitHubRestService;

    @Cacheable(value = "users")
    public UserDto getUserById(@NonNull Long userId) {
        log.info("Retrieve user by user id :{}", userId);
        return userService.retrieveUserByUserId(userId).orElseThrow(() -> new UserNotFoundException("No user found with the provided user id."));
    }

    public List<UserDto> getUsers(@NonNull Integer pageNumber) {
        log.info("Get users with page number :{}", pageNumber);
        return userService.retrieveAllUsers(pageNumber);
    }

    @Caching(evict = {
            @CacheEvict(value = "users", key = "#root.args[0].id"),
            @CacheEvict(value = "users-repositories", key = "#root.args[0].id")
    })
    public UserDto updateOrSaveUser(@NonNull UserDto userDto) {
        log.info("Update or save user :{}", userDto);
        userValidatorService.validateUserDto(userDto);
        return userService.updateOrSaveUser(userDto);
    }

    @Caching(evict = {
            @CacheEvict("users"),
            @CacheEvict(value = "users-repositories")
    })
    public void deleteUser(@NonNull Long userId) {
        log.info("Delete user :{}", userId);
        userService.deleteUser(userId);
    }

    @CachePut(value = "users", key = "#result.id")
    public UserDto createUser(@NonNull UserDto userDto) {
        log.info("Create user :{}", userDto);
        return userService.createUser(userDto);
    }

    @Cacheable(value = "users-repositories")
    public UserRepositoriesDto getUserRepositories(@NonNull Long userId) {
        log.info("Get user repositories :{}", userId);
        return gitHubRestService.getUserRepositories(getUserById(userId));
    }
}
