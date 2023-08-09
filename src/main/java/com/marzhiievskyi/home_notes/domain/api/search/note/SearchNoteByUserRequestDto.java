package com.marzhiievskyi.home_notes.domain.api.search.note;

import jakarta.validation.constraints.DecimalMin;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SearchNoteByUserRequestDto {

    @DecimalMin(value = "1", message = "values of userId must be > 0" )
    private Long userId;
}
