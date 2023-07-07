package com.marzhiievskyi.home_notes.domain.api.search.tag;

import com.marzhiievskyi.home_notes.domain.constants.RegExp;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SearchTagsRequestDto {

    @NotBlank(message = "partOfTag must be filled ")
    @Pattern(regexp = RegExp.tag, message = "incorrect part of tag")
    private String partOfTag;
}
