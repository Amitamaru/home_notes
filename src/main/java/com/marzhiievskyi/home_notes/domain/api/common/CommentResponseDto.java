package com.marzhiievskyi.home_notes.domain.api.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentResponseDto {

    private Long userId;
    private String nickname;
    private Long commentId;
    private String text;
    private String timeInsert;
}
