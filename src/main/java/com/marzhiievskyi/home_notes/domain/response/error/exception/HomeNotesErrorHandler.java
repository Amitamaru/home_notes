package com.marzhiievskyi.home_notes.domain.response.error.exception;

import com.marzhiievskyi.home_notes.domain.constants.Code;
import com.marzhiievskyi.home_notes.domain.response.error.Error;
import com.marzhiievskyi.home_notes.domain.response.error.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class HomeNotesErrorHandler {

    @ExceptionHandler(CommonException.class)
    public ResponseEntity<ErrorResponse> handleCommonException(CommonException e) {
        log.error("CommonException: {}", e.toString());
        return new ResponseEntity<>(ErrorResponse.builder()
                .error(Error.builder()
                        .code(e.getCode())
                        .userMessage(e.getUserMessage())
                        .techMessage(e.getTechMessage())
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
                        .userMessage("Internal server error.")
                        .build())
                .build(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleMissingRequestHeaderException(MissingRequestHeaderException e) {
        log.error("MissingRequestHeaderException: {}", e.toString());
        return new ResponseEntity<>(ErrorResponse.builder()
                .error(Error.builder()
                        .code(Code.MISSING_REQUEST_HEADER)
                        .techMessage(e.getMessage())
                        .build())
                .build(), HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        log.error("HttpMessageNotReadableException: {}", e.toString());
        return new ResponseEntity<>(ErrorResponse.builder()
                .error(Error.builder()
                        .code(Code.NOT_READABLE)
                        .techMessage(e.getMessage())
                        .build())
                .build(), HttpStatus.BAD_REQUEST);
    }
}
