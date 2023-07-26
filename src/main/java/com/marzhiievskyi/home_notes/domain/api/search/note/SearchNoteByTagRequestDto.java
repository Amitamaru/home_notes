package com.marzhiievskyi.home_notes.domain.api.search.note;

import com.marzhiievskyi.home_notes.domain.constants.Sort;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SearchNoteByTagRequestDto {

    @DecimalMin(value = "1", message = "values of tagId must be > 0" )
    private Long tagId;

    @NotNull(message = "sort must be filled")
    private Sort sort;
}
