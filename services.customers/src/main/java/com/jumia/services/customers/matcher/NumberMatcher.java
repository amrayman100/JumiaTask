package com.jumia.services.customers.matcher;

import com.jumia.services.customers.exception.CodeNotFoundException;
import com.jumia.services.customers.exception.MetadataSourceNotSetException;
import com.jumia.services.customers.exception.NumberTooSmallException;
import com.jumia.services.customers.model.NumberValidationResponse;

public interface NumberMatcher {
    NumberValidationResponse getPhoneType(String number) throws MetadataSourceNotSetException, NumberTooSmallException, CodeNotFoundException;
}
