package com.marzhiievskyi.home_notes.service;

import com.marzhiievskyi.home_notes.dao.SearchDao;
import com.marzhiievskyi.home_notes.dao.UserDao;
import com.marzhiievskyi.home_notes.domain.api.common.NoteListResponse;
import com.marzhiievskyi.home_notes.domain.api.common.NoteResponseDto;
import com.marzhiievskyi.home_notes.domain.api.common.TagResponseDto;
import com.marzhiievskyi.home_notes.domain.api.common.UserResponseDto;
import com.marzhiievskyi.home_notes.domain.api.search.note.SearchNoteByTagRequestDto;
import com.marzhiievskyi.home_notes.domain.api.search.note.SearchNotesByWordRequestDto;
import com.marzhiievskyi.home_notes.domain.api.search.tag.SearchTagResponseDto;
import com.marzhiievskyi.home_notes.domain.api.search.tag.SearchTagsRequestDto;
import com.marzhiievskyi.home_notes.domain.api.search.user.SearchUserByNicknameRequestDto;
import com.marzhiievskyi.home_notes.domain.api.search.user.SearchUserByNicknameResponseDto;
import com.marzhiievskyi.home_notes.domain.response.Response;
import com.marzhiievskyi.home_notes.domain.response.SuccessResponse;
import com.marzhiievskyi.home_notes.service.common.CommonService;
import com.marzhiievskyi.home_notes.util.ValidationProcessor;
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
    private final ValidationProcessor validationProcessor;
    private final CommonService commonService;

    public ResponseEntity<Response> findTagsByPart(SearchTagsRequestDto searchTagRequest, String accessToken) {

        validationProcessor.validationRequest(searchTagRequest);
        userDao.findUserIdIByTokenOrThrowException(accessToken);

        List<TagResponseDto> tagsByTagPart = searchDao.getTagsByTagPart(searchTagRequest.getPartOfTag());

        return new ResponseEntity<>(SuccessResponse.builder()
                .data(SearchTagResponseDto.builder()
                        .tags(tagsByTagPart)
                        .build())
                .build(), HttpStatus.OK);
    }

    public ResponseEntity<Response> findNotesByTag(SearchNoteByTagRequestDto searchNotesRequest, String accessToken) {

        validationProcessor.validationRequest(searchNotesRequest);
        userDao.findUserIdIByTokenOrThrowException(accessToken);

        List<NoteResponseDto> notesByTag = searchDao.getNotesByTag(searchNotesRequest);
        commonService.insertDataIntoNotes(notesByTag);

        return new ResponseEntity<>(SuccessResponse.builder()
                .data(NoteListResponse.builder()
                        .notes(notesByTag)
                        .build())
                .build(), HttpStatus.OK);
    }

    public ResponseEntity<Response> findNotesByPartWord(SearchNotesByWordRequestDto searchNotesRequest, String accessToken) {

        validationProcessor.validationRequest(searchNotesRequest);
        userDao.findUserIdIByTokenOrThrowException(accessToken);

        List<NoteResponseDto> notesByPartWord = searchDao.findNotesByPartWord(searchNotesRequest);
        commonService.insertDataIntoNotes(notesByPartWord);

        return new ResponseEntity<>(SuccessResponse.builder()
                .data(NoteListResponse.builder()
                        .notes(notesByPartWord)
                        .build())
                .build(), HttpStatus.OK);
    }

    public ResponseEntity<Response> findUserByPartNickname(SearchUserByNicknameRequestDto searchUserRequest, String accessToken) {
        validationProcessor.validationRequest(searchUserRequest);
        userDao.findUserIdIByTokenOrThrowException(accessToken);

        List<UserResponseDto> users = searchDao.getUsersByNicknamePart(searchUserRequest);

        return new ResponseEntity<>(SuccessResponse.builder()
                .data(SearchUserByNicknameResponseDto.builder()
                        .users(users)
                        .build())
                .build(), HttpStatus.OK);
    }
}
