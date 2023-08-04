package com.marzhiievskyi.home_notes.domain.api.communication.comment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WhoseCommentDto {

    private Long commentUserId;
    private Long noteUserId;
}
