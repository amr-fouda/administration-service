package org.fouda.administration.controllers;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.fouda.administration.dto.UserDto;
import org.fouda.administration.dto.UserRepositoriesDto;
import org.fouda.administration.services.UserServiceFacade;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class UserController {
    private final UserServiceFacade facade;

    @PostMapping(value = "/users", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public EntityModel<UserDto> createUser(@NonNull @RequestBody @Valid final UserDto userDto) {
        log.info("Received create user request :{}", userDto);
        return buildUserEntityModel(facade.createUser(userDto));
    }

    @GetMapping(value = "/users/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public EntityModel<UserDto> getUser(@NonNull @PathVariable final Long userId) {
        log.info("Received get user request for user id :{}", userId);
        return buildUserEntityModel(facade.getUserById(userId));
    }

    @GetMapping(value = "/users/", produces = MediaType.APPLICATION_JSON_VALUE)
    public CollectionModel<EntityModel<UserDto>> getUsers(@RequestParam(required = false, defaultValue = "0") final Integer pageNumber) {
        log.info("Received get users request with page Number : {}", pageNumber);
        return CollectionModel.of(facade.getUsers(pageNumber).stream().map(this::buildUserEntityModel).collect(Collectors.toList()));
    }

    @DeleteMapping(value = "/users/{userId}")
    public void deleteUser(@NonNull @PathVariable final Long userId) {
        log.info("Received delete user request for user id : {}", userId);
        facade.deleteUser(userId);
    }

    @PutMapping(value = "/users", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public EntityModel<UserDto> updateOrSaveUser(@NonNull @RequestBody @Valid final UserDto userDto) {
        log.info("Received save or update user request : {}", userDto);
        return buildUserEntityModel(facade.updateOrSaveUser(userDto));
    }

    @GetMapping(value = "/users/{userId}/repositories")
    public EntityModel<UserRepositoriesDto> getUserRepositories(@NonNull @PathVariable final Long userId) {
        log.info("Received get user repositories request for user id : {}", userId);
        return buildUserEntityModel(facade.getUserRepositories(userId));
    }

    private EntityModel<UserDto> buildUserEntityModel(UserDto userDto) {
        return EntityModel.of(userDto, linkTo(methodOn(UserController.class)
                .getUser(userDto.getId())).withSelfRel());
    }

    private EntityModel<UserRepositoriesDto> buildUserEntityModel(UserRepositoriesDto userRepositoriesDto) {
        return EntityModel.of(userRepositoriesDto, linkTo(methodOn(UserController.class)
                .getUser(userRepositoriesDto.getUserId())).withSelfRel());
    }


}
