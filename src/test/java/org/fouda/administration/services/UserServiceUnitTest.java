package org.fouda.administration.services;

import org.fouda.administration.dto.UserDto;
import org.fouda.administration.entities.UserEntity;
import org.fouda.administration.exceptions.UserNotFoundException;
import org.fouda.administration.repositories.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.Optional;

import static org.fouda.administration.utils.UserBuilderUtil.DTO_TO_ENTITY;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceUnitTest {
    @Mock
    private UserRepository repository;

    @InjectMocks
    private UserService userService;

    @Test
    public void testThatVerifiesCallingRepositorySaveMethodWhenCreatingUser() {
        UserDto userDto = createDummyUser();
        UserEntity userEntity = DTO_TO_ENTITY.apply(userDto);
        when(repository.save(userEntity)).thenReturn(UserEntity.builder().build());

        userService.createUser(userDto);

        verify(repository).save(userEntity);
    }

    @Test(expected = UserNotFoundException.class)
    public void testThatThrowsExceptionWhenNoUserFoundToUpdate() {
        UserDto userDto = createDummyUser();
        when(repository.findById(userDto.getId())).thenReturn(Optional.empty());

        userService.updateUser(userDto);
    }

    @Test
    public void testThatVerifiesCallingRepositorySaveMethodWhenUpdatingExistingUser() {
        UserDto userDto = createDummyUser();
        UserEntity userEntity = DTO_TO_ENTITY.apply(userDto);
        when(repository.findById(userDto.getId())).thenReturn(Optional.empty());

        userService.updateOrSaveUser(userDto);

        verify(repository).save(userEntity);
    }

    @Test
    public void testThatVerifiesCallingRepositoryDeleteMethodWhenDeletingUser() {
        final long userId = 1000L;

        userService.deleteUser(userId);

        verify(repository).deleteById(userId);
    }


    @Test
    public void testThatVerifiesCallingRepositoryFindAllMethodWhenGettingAllUsers() {
        final int pageNumber = 0;
        PageRequest page = PageRequest.of(pageNumber, 10);
        when(repository.findAll(page)).thenReturn(mock(Page.class));

        userService.retrieveAllUsers(pageNumber);

        verify(repository).findAll(page);
    }


    private UserDto createDummyUser() {
        return UserDto.builder().firstName("John").surName("Tom").githubProfileUrl("http://github.com/test1").position("Backend developer").build();
    }
}
