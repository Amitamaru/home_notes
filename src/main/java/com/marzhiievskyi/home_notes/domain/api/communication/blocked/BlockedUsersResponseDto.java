package com.marzhiievskyi.home_notes.domain.api.communication.blocked;

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
public class BlockedUsersResponseDto {

    private List<UserResponseDto> blockedUsers;
}
