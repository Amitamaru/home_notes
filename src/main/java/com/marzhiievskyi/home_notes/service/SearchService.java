package com.marzhiievskyi.home_notes.service;

import com.marzhiievskyi.home_notes.dao.SearchDao;
import com.marzhiievskyi.home_notes.domain.api.common.TagResponseDto;
import com.marzhiievskyi.home_notes.domain.api.search.tag.SearchTagsRequestDto;
import com.marzhiievskyi.home_notes.domain.response.Response;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class SearchService {

    private final SearchDao searchDao;

    public List<TagResponseDto> getTagsByNoteId(Long noteId) {
       return searchDao.getTagsByNoteId(noteId);
    }


    public ResponseEntity<Response> findTagsByPart(SearchTagsRequestDto searchTagRequest, String accessToken) {
        //TODO finish method search tags by part of them
        return null;
    }
}
