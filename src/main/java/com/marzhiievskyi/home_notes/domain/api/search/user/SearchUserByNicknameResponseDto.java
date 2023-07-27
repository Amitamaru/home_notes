package com.marzhiievskyi.home_notes.domain.api.search.user;

import com.marzhiievskyi.home_notes.domain.api.common.UserResponseDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SearchUserByNicknameResponseDto {

    private List<UserResponseDto> users;
}
