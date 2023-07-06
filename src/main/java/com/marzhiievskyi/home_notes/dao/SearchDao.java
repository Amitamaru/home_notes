package com.marzhiievskyi.home_notes.dao;

import com.marzhiievskyi.home_notes.domain.api.common.TagResponseDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface SearchDao {
    List<TagResponseDto> getTagsByNoteId(Long id);

}
