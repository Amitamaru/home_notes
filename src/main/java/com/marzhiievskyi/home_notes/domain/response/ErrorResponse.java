package com.marzhiievskyi.home_notes.domain.response;


import lombok.Builder;
import lombok.Data;


@Data
@Builder
public class ErrorResponse implements Response{

    private Error error;
}
