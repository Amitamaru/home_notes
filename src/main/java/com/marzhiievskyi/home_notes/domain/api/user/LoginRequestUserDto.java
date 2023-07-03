package com.marzhiievskyi.home_notes.domain.api.user;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequestUserDto {

    @Valid
    @NotNull(message = "authorization must be filled")
    private Authorization authorization;

}
