package com.marzhiievskyi.home_notes.domain.response.error.exception;

import com.marzhiievskyi.home_notes.domain.constants.Code;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;


@Getter
@Setter
@Builder
public class CommonException extends RuntimeException{

    private Code code;
    private String userMessage;
    private String techMessage;
    private HttpStatus httpStatus;
}
