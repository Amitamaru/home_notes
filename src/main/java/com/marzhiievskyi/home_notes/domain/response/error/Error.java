package com.marzhiievskyi.home_notes.domain.response.error;

import com.marzhiievskyi.home_notes.domain.constants.Code;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Error {

    private Code code;

    private String message;
}
