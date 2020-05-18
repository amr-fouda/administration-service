package org.fouda.administration.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    @NotNull(message = "{id.not.null.value}")
    @Positive(message = "{id.positive.value}")
    @Min(value = 1, message = "{id.min.value}")
    private Long id;

    @NotEmpty(message = "{first.name.not.empty}")
    private String firstName;

    @NotEmpty(message = "{sur.name.not.empty}")
    private String surName;

    @NotEmpty(message = "{position.not.empty}")
    private String position;

    @NotEmpty(message = "{github.profile.url.not.empty}")
    private String githubProfileUrl;
}
