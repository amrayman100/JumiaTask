package com.jumia.services.customers.validator;

import com.jumia.services.customers.model.NumberValidationResponse;
import com.jumia.services.customers.model.PhoneMetaData;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PhoneNumberValidator implements NumberValidator{
    PhoneMetaData phoneMetaData;

    public NumberValidationResponse validate(String number) {
        boolean isValid = number.matches(this.phoneMetaData.getRegex());
        return NumberValidationResponse.builder()
                .isValid(isValid)
                .code(this.phoneMetaData.getCode()).origin(this.phoneMetaData.getCountryName())
                .build();
    }
}
