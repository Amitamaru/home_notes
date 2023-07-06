package com.marzhiievskyi.home_notes.domain.api.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NoteResponseDto {

    private Long noteId;
    private Long userid;
    private String nickname;
    private String text;
    private List<TagResponseDto> tags;
    private String timeInsert;
}
