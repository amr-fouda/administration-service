package org.fouda.administration.services;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.fouda.administration.dto.UserDto;
import org.fouda.administration.dto.UserRepositoriesDto;
import org.fouda.administration.dto.feign.GithubRepositoryDto;
import org.fouda.administration.feign.clients.GithubFeignClient;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static org.springframework.data.util.Pair.of;


@Service
@RequiredArgsConstructor
public class GitHubRestService {
    private final GithubFeignClient githubClient;

    //TODO add hystrix (timeout & fallback) instead of manual exception catching
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
                .map(repo -> repo.getName())
                .map(repo -> of(repo, githubClient.getRepositoryProgrammingLanguages(userName, repo).keySet()))
                .collect(Collectors.toMap(pair -> pair.getFirst(), pair -> pair.getSecond()));
    }

    private String extractGithubUserNameFromProfile(@NonNull String githubProfileUrl) {
        return githubProfileUrl.substring(githubProfileUrl.lastIndexOf("/") + 1);
    }
}
