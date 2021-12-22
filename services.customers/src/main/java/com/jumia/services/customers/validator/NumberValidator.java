package com.jumia.services.customers.validator;


import com.jumia.services.customers.model.NumberValidationResponse;

public interface NumberValidator {
   NumberValidationResponse validate(String number);
}
