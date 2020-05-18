package org.fouda.administration.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import net.minidev.json.annotate.JsonIgnore;

import java.util.Map;
import java.util.Set;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Data
@Builder
@RequiredArgsConstructor
@JsonInclude(NON_NULL)
public class UserRepositoriesDto {
    private final UserDto userDto;
    private final Map<String, Set<String>> repositoryToProgrammingLanguage;
    private final Boolean problemsWhileFetchingRepositories;

    @JsonIgnore
    public Long getUserId() {
        return userDto.getId();
    }
}
