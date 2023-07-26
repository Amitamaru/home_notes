package com.marzhiievskyi.home_notes.dao;

import com.marzhiievskyi.home_notes.domain.api.common.NoteResponseDto;
import com.marzhiievskyi.home_notes.domain.api.common.TagResponseDto;
import com.marzhiievskyi.home_notes.domain.api.search.note.SearchNoteByTagRequestDto;
import com.marzhiievskyi.home_notes.domain.api.search.note.SearchNotesByWordRequestDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface SearchDao {
    List<TagResponseDto> getTagsByNoteId(Long id);

    List<TagResponseDto> getTagsByTagPart(String partTag);

    List<NoteResponseDto> getNotesByTag(SearchNoteByTagRequestDto searchNoteByTagRequestDto);

    List<NoteResponseDto> findNotesByPartWord(SearchNotesByWordRequestDto searchNotesByWordRequestDto);
}
