package com.marzhiievskyi.home_notes.domain.api.note;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResponseNoteDto {

    private Long id;
    private String text;
    private List<String> tags;
    private String timeInsert;
}
