package com.marzhiievskyi.home_notes.domain.api.user.registration;


import com.marzhiievskyi.home_notes.domain.api.user.common.Authorization;
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
public class RegistrationRequestUserDto {

    @Valid
    @NotNull(message = "authorization must be filled")
    private Authorization authorization;
}
