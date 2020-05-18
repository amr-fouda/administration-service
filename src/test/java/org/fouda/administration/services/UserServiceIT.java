package org.fouda.administration.services;

import org.fouda.administration.dto.UserDto;
import org.fouda.administration.entities.UserEntity;
import org.fouda.administration.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

import static org.fouda.administration.utils.UserCompareUtil.IS_SAME_USER;
import static org.junit.Assert.*;

@SpringBootTest
class UserServiceIT {
    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void testThatSavesNewUser() {
        UserDto userDto = createDummyUser();

        Long userId = userService.createUser(userDto).getId();

        UserEntity userEntity = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("User was not saved successfully"));
        assertNotNull(userEntity);
        assertTrue(IS_SAME_USER.test(userEntity, userDto));
    }

    @Test
    public void testThatRetrievesJustSavedUserByUserId() {
        UserDto userDto = createDummyUser();

        Long userId = userService.createUser(userDto).getId();
        UserDto retrievedUser = userService.retrieveUserByUserId(userId).orElseThrow(() -> new IllegalArgumentException("User was not saved successfully"));

        assertNotNull(retrievedUser);
        assertEquals(retrievedUser.getFirstName(), userDto.getFirstName());
        assertEquals(retrievedUser.getSurName(), userDto.getSurName());
        assertEquals(retrievedUser.getGithubProfileUrl(), userDto.getGithubProfileUrl());
        assertEquals(retrievedUser.getPosition(), userDto.getPosition());
    }

    @Test
    public void testThatRetrievesUsersByFirstName() {
        IntStream.rangeClosed(1, 20).forEach(i -> userService.createUser(createDummyUser(new StringBuilder("http://test").append(i).toString())));

        List<UserDto> usersList = userService.retrieveAllUsers(0);

        assertNotNull(usersList);
        assertEquals(10, usersList.size());
    }

    @Test
    public void testThatDeletesUserById() {
        Long userId = userService.createUser(createDummyUser()).getId();

        userService.deleteUser(userId);

        Optional<UserEntity> user = userRepository.findById(userId);
        assertNotNull(user);
        assertFalse(user.isPresent());
    }

    @Test
    public void testThatUpdateJustSavedUserById() {
        Long userId = userService.createUser(createDummyUser()).getId();
        UserDto userDto = UserDto.builder().id(userId).firstName("Mohamed").surName("Sayed").position("Manager").githubProfileUrl("http://test123").build();

        userService.updateUser(userDto);

        UserEntity user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("User was not updated"));
        assertTrue(IS_SAME_USER.test(user, userDto));
    }


    @Test
    public void testThatUpdateExistingUserById() {
        Long userId = userService.createUser(createDummyUser()).getId();
        UserDto userDto = UserDto.builder().id(userId).firstName("Mohamed").surName("Sayed").position("Manager").githubProfileUrl("http://test123").build();

        userService.updateOrSaveUser(userDto);

        UserEntity user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("User was not updated"));
        assertTrue(IS_SAME_USER.test(user, userDto));
    }

    @Test
    public void testThatCreateNewUserByUsingUpdateOrSaveMethod() {
        Long userId = 1000L;
        UserDto userDto = UserDto.builder().id(userId).firstName("Mohamed").surName("Sayed").position("Manager").githubProfileUrl("http://test123").build();

        userId = userService.updateOrSaveUser(userDto).getId();

        UserEntity user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("User was not updated"));
        assertTrue(IS_SAME_USER.test(user, userDto));
    }

    @BeforeEach
    public void cleanDatabase() {
        userRepository.deleteAll();
    }

    private UserDto createDummyUser() {
        return UserDto.builder().firstName("John").surName("Tom").githubProfileUrl("http://github.com/test1").position("Backend developer").build();
    }

    private UserDto createDummyUser(String gitHubUrl) {
        return UserDto.builder().firstName("John").surName("Tom").githubProfileUrl(gitHubUrl).position("Backend developer").build();
    }

}
