package com.marzhiievskyi.home_notes.domain.api.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

    private String nickname;

    private String encryptedPassword;

    private String accessToken;
}
