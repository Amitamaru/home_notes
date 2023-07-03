package com.marzhiievskyi.home_notes.domain.api.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegistrationResponseUserDto {

    private String accessToken;
}
