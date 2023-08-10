package com.marzhiievskyi.home_notes.common;

import com.marzhiievskyi.home_notes.dao.CommonDao;
import com.marzhiievskyi.home_notes.domain.api.common.NoteResponseDto;
import com.marzhiievskyi.home_notes.domain.constants.Code;
import com.marzhiievskyi.home_notes.domain.response.error.exception.CommonException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CommonService {

    private final CommonDao commonDao;

    public void insertDataIntoNotes(List<NoteResponseDto> notes) {
        for (NoteResponseDto note : notes) {
            note.setTags(commonDao.getTagsByNoteId(note.getNoteId()));
            note.setLikesCount(commonDao.getLikesCountByNoteId(note.getNoteId()));
            note.setComments(commonDao.getCommentsByNoteId(note.getNoteId()));
        }
    }

    public void checkBlockUserByUserId(Long userId, Long checkBlockPublisher) {
        checkBlock(userId, checkBlockPublisher);
    }

    private void checkBlock(Long userId, Long checkBlockPublisher) {
        if (commonDao.isBlocked(userId, checkBlockPublisher)) {
            throw CommonException.builder()
                    .code(Code.BLOCKED)
                    .userMessage("you have been blocked by this user")
                    .httpStatus(HttpStatus.BAD_REQUEST)
                    .build();
        }
    }

    public void checkBlockUserByNoteId(Long userId, long noteId) {
        Long checkBlockPublisher = commonDao.getUserIdByNoteId(noteId);
        checkBlock(userId, checkBlockPublisher);
    }
}
