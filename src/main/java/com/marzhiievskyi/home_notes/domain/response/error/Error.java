package com.marzhiievskyi.home_notes.domain.response.error;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.marzhiievskyi.home_notes.domain.constants.Code;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Error {

    private Code code;

    private String userMessage;

    private String techMessage;
}
