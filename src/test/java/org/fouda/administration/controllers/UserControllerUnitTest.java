package org.fouda.administration.controllers;

import org.fouda.administration.dto.UserDto;
import org.fouda.administration.dto.UserRepositoriesDto;
import org.fouda.administration.services.UserServiceFacade;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class UserControllerUnitTest {
    @Mock
    private UserServiceFacade userServiceFacade;

    @InjectMocks
    private UserController userController;


    @Test
    public void testThatVerifiesCallingServiceFacadeWhenCreatingUser() {
        UserDto userDto = mock(UserDto.class);
        when(userServiceFacade.createUser(userDto)).thenReturn(UserDto.builder().build());

        userController.createUser(userDto);

        verify(userServiceFacade).createUser(userDto);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testThatThrowsExceptionWhenCallingServiceFacadeWhenNullNewUser() {
        userController.createUser(null);
    }


    @Test
    public void testThatVerifiesCallingServiceFacadeWhenGettingUser() {
        Long userId = 1000L;
        when(userServiceFacade.getUserById(userId)).thenReturn(UserDto.builder().build());

        userController.getUser(userId);

        verify(userServiceFacade).getUserById(userId);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testThatThrowsExceptionWhenCallingGettingUserWithNullInput() {
        userController.getUser(null);
    }

    @Test
    public void testThatVerifiesCallingServiceFacadeWhenGettingUsers() {
        int pageNumber = 0;

        userController.getUsers(pageNumber);

        verify(userServiceFacade).getUsers(pageNumber);
    }

    @Test
    public void testThatVerifiesCallingServiceFacadeWhenDeletingUser() {
        Long userId = 1000L;

        userController.deleteUser(userId);

        verify(userServiceFacade).deleteUser(userId);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testThatThrowsExceptionWhenCallingDeletingUserWithNullInput() {
        userController.deleteUser(null);
    }

    @Test
    public void testThatVerifiesCallingServiceFacadeWhenUpdatingOrSavingUser() {
        UserDto userDto = mock(UserDto.class);
        when(userServiceFacade.updateOrSaveUser(userDto)).thenReturn(UserDto.builder().build());

        userController.updateOrSaveUser(userDto);

        verify(userServiceFacade).updateOrSaveUser(userDto);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testThatThrowsExceptionWhenCallingServiceFacadeWhenUpdatingOrSavingUserWithNullInput() {
        userController.updateOrSaveUser(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testThatThrowsExceptionWhenCallingServiceFacadeWhenGettingUserRepositoriesWithNullInput() {
        userController.getUserRepositories(null);
    }

    @Test
    public void testThatVerifiesCallingServiceFacadeWhenGettingUserRepositories() {
        Long userId = 1000L;
        when(userServiceFacade.getUserRepositories(userId)).thenReturn(UserRepositoriesDto.builder().userDto(UserDto.builder().build()).build());

        userController.getUserRepositories(userId);

        verify(userServiceFacade).getUserRepositories(userId);
    }

}
