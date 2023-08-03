package com.marzhiievskyi.home_notes.service.common;

import com.marzhiievskyi.home_notes.dao.CommonDao;
import com.marzhiievskyi.home_notes.domain.api.common.NoteResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
        }
    }
}
