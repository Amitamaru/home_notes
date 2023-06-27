package com.marzhiievskyi.home_notes.domain.response.error.exception;

import com.marzhiievskyi.home_notes.domain.constants.Code;
import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;


@Data
@Builder
public class CommonException extends RuntimeException{

    private Code code;

    private String message;

    private HttpStatus httpStatus;
}
