package com.marzhiievskyi.home_notes.domain.api;


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
public class RegistrationRequestUserDto {

    @NotBlank(message = "nickname must be filled")
    @Pattern(regexp = "^[a-zA-Z0-9. _-]{4,15}", message = "incorrect nickname")
    private String nickname;

    @NotBlank(message = "password must be filled")
    @Pattern(regexp = "^[a-zA-Zа-яА-Я0-9., _!@#$%^&*()\"\\\\{}\\[\\]\\-]{8,100}", message = "incorrect password")
    private String password;
}
