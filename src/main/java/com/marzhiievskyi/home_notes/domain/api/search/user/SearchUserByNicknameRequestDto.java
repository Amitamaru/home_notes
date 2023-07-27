package com.marzhiievskyi.home_notes.domain.api.search.user;

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
public class SearchUserByNicknameRequestDto {

    @NotBlank(message = "partNickname must be filled")
    @Pattern(regexp = ValidationRegExp.partNickname, message = "incorrect partNickname")
    private String partNickname;
}
