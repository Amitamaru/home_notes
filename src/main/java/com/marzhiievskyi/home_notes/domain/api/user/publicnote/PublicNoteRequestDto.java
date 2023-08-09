package com.marzhiievskyi.home_notes.domain.api.user.publicnote;

import com.marzhiievskyi.home_notes.domain.constants.ValidationRegExp;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PublicNoteRequestDto {

    @NotBlank(message = "note text must be filled")
    @Pattern(regexp = ValidationRegExp.note, message = "incorrect note text")
    private String text;

    @Size(max = 5, message = "count of tags must be less than 5")
    private List<
            @NotBlank(message = "tag must be filled")
            @Pattern(regexp = ValidationRegExp.tag, message = "incorrect tag text")
                    String> tags;
}
