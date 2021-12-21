package com.jumia.services.customers.matcher;


import com.jumia.services.customers.model.PhoneMetaData;
import com.jumia.services.customers.validator.PhoneNumberValidator;

import java.util.HashMap;

public class PhoneNumberMatcherTestsUtils {
    public final static  String CODE = "(212)";
    public final static String NUMBER = "(212) 6007989253";
    public final static String SHORT_NUMBER = "(21";
    public final static String VALID_ETHIOPIA_NUMBER = "(251) 914701723";
    public final static String INVALID_ETHIOPIA_NUMBER = "(251) 9773199405";
    public static final String ETHIOPIA = "Ethiopia";
    public static final String LONDON = "LONDON";
    public static final String CAMEROON = "Cameroon";
    public static final String ETHIOPIA_CODE = "(251)";

    public static HashMap<String, PhoneNumberValidator> createMetaDataMap(){
        HashMap<String, PhoneNumberValidator> metaDataValidatorMap = new HashMap<>();
        PhoneNumberValidator cameroonNumberValidator = PhoneNumberValidator.builder().phoneMetaData(PhoneMetaData.builder().code("(237)")
                .regex("\\(237\\)\\ ?[2368]\\d{7,8}$").countryName(CAMEROON ).build()).build();
        PhoneNumberValidator ethiopiaNumberValidator = PhoneNumberValidator.builder().phoneMetaData(PhoneMetaData.builder().code("(251)")
                .regex("\\(251\\)\\ ?[1-59]\\d{8}$").countryName(ETHIOPIA).build()).build();
        metaDataValidatorMap.put(cameroonNumberValidator.getPhoneMetaData().getCode(),cameroonNumberValidator);
        metaDataValidatorMap.put(ethiopiaNumberValidator.getPhoneMetaData().getCode(),ethiopiaNumberValidator);
        return metaDataValidatorMap;
    }

    public static HashMap<String, PhoneNumberValidator> createCountryMetaDataMap(){
        HashMap<String, PhoneNumberValidator> metaDataValidatorMap = new HashMap<>();
        PhoneNumberValidator cameroonNumberValidator = PhoneNumberValidator.builder().phoneMetaData(PhoneMetaData.builder().code("(237)")
                .regex("\\(237\\)\\ ?[2368]\\d{7,8}$").countryName(CAMEROON ).build()).build();
        PhoneNumberValidator ethiopiaNumberValidator = PhoneNumberValidator.builder().phoneMetaData(PhoneMetaData.builder().code("(251)")
                .regex("\\(251\\)\\ ?[1-59]\\d{8}$").countryName(ETHIOPIA).build()).build();
        metaDataValidatorMap.put(cameroonNumberValidator.getPhoneMetaData().getCountryName(),cameroonNumberValidator);
        metaDataValidatorMap.put(ethiopiaNumberValidator.getPhoneMetaData().getCountryName(),ethiopiaNumberValidator);
        return metaDataValidatorMap;
    }
}
