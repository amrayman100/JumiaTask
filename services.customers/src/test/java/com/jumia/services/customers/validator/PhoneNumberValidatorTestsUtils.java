package com.jumia.services.customers.validator;

import com.jumia.services.customers.model.PhoneMetaData;

public class PhoneNumberValidatorTestsUtils {
    public static final String REGEX = "\\(256\\)\\ ?\\d{9}$";
    public static final String CODE = "+256";
    public static final String COUNTRY_NAME = "Arbitrary";

    public static final String INVALID_NUMBER = "(256) 77";
    public static final String VALID_NUMBER = "(256) 775069443";

    public static PhoneNumberValidator getValidator(){
        return PhoneNumberValidator.builder().phoneMetaData(PhoneMetaData.builder().code(PhoneNumberValidatorTestsUtils.CODE).
                countryName(PhoneNumberValidatorTestsUtils.COUNTRY_NAME).
                regex(PhoneNumberValidatorTestsUtils.REGEX).build()).build();
    }
}
