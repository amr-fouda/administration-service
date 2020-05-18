package org.fouda.administration.services;

import org.fouda.administration.dto.UserDto;
import org.fouda.administration.dto.UserRepositoriesDto;
import org.fouda.administration.dto.feign.GithubRepositoryDto;
import org.fouda.administration.feign.clients.GithubFeignClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;
import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class GitHubRestServiceUnitTest {
    @Mock
    private GithubFeignClient githubFeignClient;

    @InjectMocks
    private GitHubRestService gitHubRestService;

    @Test
    public void testThatGetUserRepositoriesWithNullGithubProfileValuePassed() {
        UserDto userDto = Mockito.mock(UserDto.class);

        UserRepositoriesDto userRepositoriesDto = gitHubRestService.getUserRepositories(userDto);

        assertNotNull(userRepositoriesDto);
        assertNotNull(userRepositoriesDto.getProblemsWhileFetchingRepositories());
        assertTrue(userRepositoriesDto.getProblemsWhileFetchingRepositories());
    }

    @Test
    public void testThatGetUserRepositoriesWithValidInput() {
        UserDto userDto = UserDto.builder().id(1000L).githubProfileUrl("https://github.com/amr-fouda").position("dev").firstName("f").surName("s").build();
        when(githubFeignClient.getRepositories("amr-fouda")).thenReturn(Stream.of(GithubRepositoryDto.builder().name("repo1").build()).collect(toList()));
        Map<String, String> repoToProgrammingLang = new HashMap<>();
        repoToProgrammingLang.put("repo1", "java");
        when(githubFeignClient.getRepositoryProgrammingLanguages("amr-fouda", "repo1")).thenReturn(repoToProgrammingLang);

        UserRepositoriesDto userRepositoriesDto = gitHubRestService.getUserRepositories(userDto);

        assertNotNull(userRepositoriesDto);
        assertNull(userRepositoriesDto.getProblemsWhileFetchingRepositories());
        assertNotNull(userRepositoriesDto.getRepositoryToProgrammingLanguage());
        assertEquals(1, userRepositoriesDto.getRepositoryToProgrammingLanguage().size());
        assertEquals(Stream.of("repo1").collect(toSet()), userRepositoriesDto.getRepositoryToProgrammingLanguage().keySet());
    }
}
