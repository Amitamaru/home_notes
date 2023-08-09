package com.marzhiievskyi.home_notes.domain.api.communication.subscription;

import jakarta.validation.constraints.DecimalMin;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UnsubscriptionRequestDto {

    @DecimalMin(value = "1", message = "value pubUserId must be higher than 0")
    private Long pubUserId;
}
