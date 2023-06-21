package com.marzhiievskyi.home_notes.service;

import com.marzhiievskyi.home_notes.domain.response.Error;
import com.marzhiievskyi.home_notes.domain.response.ErrorResponse;
import com.marzhiievskyi.home_notes.domain.response.Response;
import com.marzhiievskyi.home_notes.domain.response.SuccessResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class HomeNotesService {

    public ResponseEntity<Response> testMethod() {
//        return new ResponseEntity<>(SuccessResponse.builder()
//                .data("Success response Object")
//                .build(), HttpStatus.OK);

        return new ResponseEntity<>(ErrorResponse.builder()
                .error(Error.builder()
                        .code("VALIDATION_ERROR")
                        .message("error of validation")
                        .build())
                .build(), HttpStatus.BAD_REQUEST);
    }
}
