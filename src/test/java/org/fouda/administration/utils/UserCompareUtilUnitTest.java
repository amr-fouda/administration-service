package org.fouda.administration.utils;

import org.fouda.administration.dto.UserDto;
import org.fouda.administration.entities.UserEntity;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;

import static org.fouda.administration.utils.UserCompareUtil.IS_SAME_USER;
import static org.junit.Assert.assertTrue;

@RunWith(BlockJUnit4ClassRunner.class)
public class UserCompareUtilUnitTest {
    @Test
    public void testThatSameUserForTwoModels() {
        Long id = 1000L;
        String github = "github", position = "developer", surName = "Tom", firstName = "Amr";
        UserEntity userEntity = UserEntity.builder().id(id).githubProfileUrl(github).position(position).surName(surName).firstName(firstName).build();
        UserDto userDto = UserDto.builder().id(id).githubProfileUrl(github).position(position).surName(surName).firstName(firstName).build();

        assertTrue(IS_SAME_USER.test(userEntity, userDto));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testThatThrowsExceptionIfInputIsNull() {
        assertTrue(IS_SAME_USER.test(null, null));
    }
}
