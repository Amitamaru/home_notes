package com.marzhiievskyi.home_notes.domain.response.error.exception;

import com.marzhiievskyi.home_notes.domain.constants.Code;
import com.marzhiievskyi.home_notes.domain.response.error.Error;
import com.marzhiievskyi.home_notes.domain.response.error.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class HomeNotesErrorHandler {

    @ExceptionHandler(CommonException.class)
    public ResponseEntity<ErrorResponse> handleCommonException(CommonException e) {
        return new ResponseEntity<>(ErrorResponse.builder()
                .error(Error.builder()
                        .code(e.getCode())
                        .message(e.getMessage())
                        .build())
                .build(), e.getHttpStatus());
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleUnexpectedErrorException(Exception e) {
        log.error("internal server error: {}", e.toString());
        e.printStackTrace();
        return new ResponseEntity<>(ErrorResponse.builder()
                .error(Error.builder()
                        .code(Code.INTERNAL_SERVER_ERROR)
                        .message("Internal server error.")
                        .build())
                .build(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
