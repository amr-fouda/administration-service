package org.fouda.administration.services;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.fouda.administration.dto.UserDto;
import org.fouda.administration.entities.UserEntity;
import org.fouda.administration.exceptions.UserNotFoundException;
import org.fouda.administration.repositories.UserRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static java.util.Optional.*;
import static java.util.stream.Collectors.toList;
import static org.fouda.administration.utils.UserBuilderUtil.*;


@Service
@Transactional
@RequiredArgsConstructor
public class UserService {
    private final static int DEFAULT_PAGE = 0;
    private final static int MAX_PAGE_SIZE = 10;

    private final UserRepository repository;

    public UserDto createUser(@NonNull UserDto dto) {
        return ENTITY_TO_DTO.apply(repository.save(DTO_TO_ENTITY.apply(dto)));
    }

    @Transactional(readOnly = true)
    public Optional<UserDto> retrieveUserByUserId(@NonNull Long userId) {
        return repository.findById(userId)
                .map(entity -> of(ENTITY_TO_DTO.apply(entity)))
                .orElse(empty());
    }

    @Transactional(readOnly = true)
    public List<UserDto> retrieveAllUsers(@NonNull Integer pageNumber) {
        return repository.findAll(PageRequest.of(ofNullable(pageNumber).orElse(DEFAULT_PAGE), MAX_PAGE_SIZE))
                .stream()
                .map(ENTITY_TO_DTO::apply).collect(toList());
    }

    public void updateUser(@NonNull UserDto dto) {
        UserEntity user = repository.findById(dto.getId()).orElseThrow(() -> new UserNotFoundException("User not found by the provided id"));
        COPY_DTO_TO_ENTITY.accept(user, dto);
        repository.save(user);
    }

    public void deleteUser(@NonNull Long userId) {
        repository.deleteById(userId);
    }

    public UserDto updateOrSaveUser(@NonNull UserDto userDto) {
        UserEntity user = repository.findById(userDto.getId()).orElse(UserEntity.builder().id(userDto.getId()).build());
        COPY_DTO_TO_ENTITY.accept(user, userDto);
        repository.save(user);
        return ENTITY_TO_DTO.apply(user);
    }
}
