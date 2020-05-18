package org.fouda.administration.feign.clients;

import org.fouda.administration.dto.feign.GithubRepositoryDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Map;

@FeignClient(url = "https://api.github.com", name = "github")
public interface GithubFeignClient {

    @GetMapping(value = "/users/{userName}/repos")
    List<GithubRepositoryDto> getRepositories(@PathVariable String userName);

    @GetMapping(value = "repos/{userName}/{repoName}/languages")
    Map<String, String> getRepositoryProgrammingLanguages(@PathVariable String userName, @PathVariable String repoName);
}
