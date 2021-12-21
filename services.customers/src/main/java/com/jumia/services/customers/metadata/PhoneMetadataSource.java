package com.jumia.services.customers.metadata;

import com.jumia.services.customers.validator.PhoneNumberValidator;
import java.util.HashMap;

public interface PhoneMetadataSource {
    HashMap<String, PhoneNumberValidator> getValidatorCodeMap();
    HashMap<String, PhoneNumberValidator> getValidatorCountryMap();
}
