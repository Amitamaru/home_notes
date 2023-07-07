package com.marzhiievskyi.home_notes.service;

import com.marzhiievskyi.home_notes.dao.SearchDao;
import com.marzhiievskyi.home_notes.dao.UserDao;
import com.marzhiievskyi.home_notes.domain.api.common.TagResponseDto;
import com.marzhiievskyi.home_notes.domain.api.search.tag.SearchTagResponseDto;
import com.marzhiievskyi.home_notes.domain.api.search.tag.SearchTagsRequestDto;
import com.marzhiievskyi.home_notes.domain.response.Response;
import com.marzhiievskyi.home_notes.domain.response.SuccessResponse;
import com.marzhiievskyi.home_notes.util.ValidationUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class SearchService {

    private final SearchDao searchDao;
    private final UserDao userDao;
    private final ValidationUtils validationUtils;

    public List<TagResponseDto> getTagsByNoteId(Long noteId) {
       return searchDao.getTagsByNoteId(noteId);
    }

    public ResponseEntity<Response> findTagsByPart(SearchTagsRequestDto searchTagRequest, String accessToken) {

        validationUtils.validationRequest(searchTagRequest);
        userDao.getUserIdByAccessToken(accessToken);

        List<TagResponseDto> tagsByTagPart = searchDao.getTagsByTagPart(searchTagRequest.getPartOfTag());

        return new ResponseEntity<>(SuccessResponse.builder()
                .data(SearchTagResponseDto.builder()
                        .tags(tagsByTagPart)
                        .build())
                .build(), HttpStatus.OK);
    }
}
