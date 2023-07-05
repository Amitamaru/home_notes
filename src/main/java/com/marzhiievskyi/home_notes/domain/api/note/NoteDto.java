package com.marzhiievskyi.home_notes.domain.api.note;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NoteDto {

    private Long id;

    private Long userId;

    private String text;

    private String timeInsert;
}
