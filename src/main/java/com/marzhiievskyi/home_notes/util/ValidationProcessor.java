package com.marzhiievskyi.home_notes.util;

import com.marzhiievskyi.home_notes.domain.constants.Code;
import com.marzhiievskyi.home_notes.domain.response.error.exception.CommonException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
public class ValidationProcessor {

    private final Validator validator;

    public <T> void validationRequest(T request) {

        if (request != null) {

            Set<ConstraintViolation<T>> validated = validator.validate(request);

            if (!validated.isEmpty()) {

                String resultValidation = validated.stream()
                        .map(ConstraintViolation::getMessage)
                        .reduce((firstPart, secondPart) -> firstPart + ". " + secondPart).orElse("");

                log.error("The json passed in the request is not valid, validation errors: {}", resultValidation);

                throw CommonException.builder()
                        .code(Code.REQUEST_VALIDATION_ERROR)
                        .techMessage(resultValidation)
                        .httpStatus(HttpStatus.BAD_REQUEST)
                        .build();
            }
        }
    }

    public void validationDecimalMin(String fieldName, long fieldValue, long constraint) {
        if (fieldValue < constraint) {
            log.error("Request validation of '{}' error:  field value {} < constraint {}", fieldName, fieldValue, constraint);
            throw CommonException.builder()
                    .code(Code.REQUEST_VALIDATION_ERROR)
                    .techMessage(fieldName + " must be equal or more than " + constraint)
                    .httpStatus(HttpStatus.BAD_REQUEST)
                    .build();
        }

    }
}
