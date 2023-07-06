package com.marzhiievskyi.home_notes.domain.api.search.tag;

import com.marzhiievskyi.home_notes.domain.api.common.TagResponseDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SearchTagResponseDto {

    private List<TagResponseDto> tags;
}
