package com.marzhiievskyi.home_notes.domain.api.user.common;

import com.marzhiievskyi.home_notes.domain.constants.ValidationRegExp;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Authorization {

    @NotBlank(message = "nickname must be filled")
    @Pattern(regexp = ValidationRegExp.nickname, message = "incorrect nickname")
    private String nickname;

    @NotBlank(message = "password must be filled")
    @Pattern(regexp = ValidationRegExp.password, message = "incorrect password")
    private String password;
}
