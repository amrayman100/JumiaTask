package com.jumia.services.customers.validator;


import com.jumia.services.customers.model.NumberValidationResponse;
import lombok.Data;

public interface NumberValidator {
   NumberValidationResponse validate(String number);
}
