package com.marzhiievskyi.home_notes.dao;

import com.marzhiievskyi.home_notes.domain.api.common.TagResponseDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CommonDao {
    List<TagResponseDto> getTagsByNoteId(Long noteId);

    Long getLikesCountByNoteId(Long noteId);
}
