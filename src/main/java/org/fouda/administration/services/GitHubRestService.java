package org.fouda.administration.services;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.fouda.administration.dto.UserDto;
import org.fouda.administration.dto.UserRepositoriesDto;
import org.fouda.administration.dto.feign.GithubRepositoryDto;
import org.fouda.administration.feign.clients.GithubFeignClient;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import static java.util.stream.Collectors.toMap;
import static org.springframework.data.util.Pair.of;


@Service
@RequiredArgsConstructor
public class GitHubRestService {
    private final GithubFeignClient githubClient;

    public UserRepositoriesDto getUserRepositories(@NonNull UserDto userDto) {
        Boolean problemsWhileFetchingRepositories = null;
        Map<String, Set<String>> repositoryToProgrammingLanguage = null;
        try {
            String userName = extractGithubUserNameFromProfile(userDto.getGithubProfileUrl());
            repositoryToProgrammingLanguage = fetchRepositoriesToProgrammingLanguages(userName);
        } catch (Exception e) {
            problemsWhileFetchingRepositories = true;
        }
        return UserRepositoriesDto.builder()
                .userDto(userDto)
                .repositoryToProgrammingLanguage(repositoryToProgrammingLanguage)
                .problemsWhileFetchingRepositories(problemsWhileFetchingRepositories)
                .build();
    }

    private Map<String, Set<String>> fetchRepositoriesToProgrammingLanguages(String userName) {
        List<GithubRepositoryDto> repositories = githubClient.getRepositories(userName);
        return repositories.stream()
                .map(GithubRepositoryDto::getName)
                .map(repo -> of(repo, githubClient.getRepositoryProgrammingLanguages(userName, repo).keySet()))
                .collect(toMap(Pair::getFirst, Pair::getSecond));
    }

    private String extractGithubUserNameFromProfile(@NonNull String githubProfileUrl) {
        return githubProfileUrl.substring(githubProfileUrl.lastIndexOf('/') + 1);
    }
}
