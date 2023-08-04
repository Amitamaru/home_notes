package com.marzhiievskyi.home_notes.domain.api.communication.comment;

import com.marzhiievskyi.home_notes.domain.constants.ValidationRegExp;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentNoteRequestDto {

    @DecimalMin(value = "1", message = "value pubUserId must be higher than 0")
    private Long noteId;

    @NotNull(message = "sort must be filled")
    @Pattern(regexp = ValidationRegExp.note, message = "incorrect text")
    private String text;
}
