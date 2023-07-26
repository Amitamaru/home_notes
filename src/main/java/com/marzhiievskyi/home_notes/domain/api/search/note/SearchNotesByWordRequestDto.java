package com.marzhiievskyi.home_notes.domain.api.search.note;

import com.marzhiievskyi.home_notes.domain.constants.ValidationRegExp;
import com.marzhiievskyi.home_notes.domain.constants.Sort;
import jakarta.validation.constraints.NotBlank;
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
public class SearchNotesByWordRequestDto {

    @NotBlank(message = "partWord must be filled")
    @Pattern(regexp = ValidationRegExp.partWord, message = "incorrect partWord")
    private String partWord;

    @NotNull(message = "sort must be filled")
    private Sort sort;
}
