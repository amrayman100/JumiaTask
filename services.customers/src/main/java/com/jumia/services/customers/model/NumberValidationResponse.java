package com.jumia.services.customers.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NumberValidationResponse {
    private boolean isValid;
    private String origin;
    private String code;
}
