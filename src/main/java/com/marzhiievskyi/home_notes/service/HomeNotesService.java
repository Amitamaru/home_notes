package com.marzhiievskyi.home_notes.service;

import com.marzhiievskyi.home_notes.domain.constants.Code;
import com.marzhiievskyi.home_notes.domain.response.Response;
import com.marzhiievskyi.home_notes.domain.response.SuccessResponse;
import com.marzhiievskyi.home_notes.domain.response.error.exception.CommonException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class HomeNotesService {

    public ResponseEntity<Response> testMethod() {
        throw CommonException.builder()
                .code(Code.TEST)
                .message("Test exception")
                .httpStatus(HttpStatus.INTERNAL_SERVER_ERROR)
                .build();
    }
}
