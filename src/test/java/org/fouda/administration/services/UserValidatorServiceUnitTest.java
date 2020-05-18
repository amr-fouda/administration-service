package org.fouda.administration.services;

import org.fouda.administration.dto.UserDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;

@RunWith(BlockJUnit4ClassRunner.class)
public class UserValidatorServiceUnitTest {
    private final UserValidatorService userValidatorService = new UserValidatorService();

    @Test(expected = IllegalArgumentException.class)
    public void testThatThrowsExceptionWhenPassingNullInputForValidation() {
        userValidatorService.validateUserDto(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testThatThrowsExceptionWhenPassingSomeNullInputForValidation() {
        userValidatorService.validateUserDto(UserDto.builder().firstName("Amr").id(1000L).build());
    }

    @Test
    public void testThatAllMandatoryFieldsExists() {
        userValidatorService.validateUserDto(UserDto.builder().firstName("Amr").surName("Fouda").position("Developer").githubProfileUrl("Test").id(1000L).build());
    }

}
