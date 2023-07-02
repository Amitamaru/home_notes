package com.marzhiievskyi.home_notes.domain.api;

import com.marzhiievskyi.home_notes.domain.constants.RegExp;
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
public class LoginRequestUserDto {


    @NotBlank(message = "nickname must be filled")
    @Pattern(regexp = RegExp.nickname, message = "incorrect nickname")
    private String nickname;

    @NotBlank(message = "password must be filled")
    @Pattern(regexp = RegExp.password, message = "incorrect password")
    private String password;
}
