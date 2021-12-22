package com.jumia.services.customers.metadata;


import com.jumia.services.customers.model.PhoneMetaData;
import com.jumia.services.customers.validator.PhoneNumberValidator;
import java.util.ArrayList;
import java.util.List;

public class PhoneJsonMetadataSourceTestUtils {

    public static List<PhoneNumberValidator> createPhoneNumberValidatorList(){
        List<PhoneNumberValidator> phoneNumberValidators = new ArrayList<>();
        phoneNumberValidators.add(PhoneNumberValidator.builder().phoneMetaData(PhoneMetaData.builder().code("(256)").countryName("Uganda").regex("\\(256\\)\\ ?\\d{9}$").build()).build());
        phoneNumberValidators.add(PhoneNumberValidator.builder().phoneMetaData(PhoneMetaData.builder().code("(237)").countryName("Cameroon").regex("\\(237\\)\\ ?[2368]\\d{7,8}$").build()).build());
        phoneNumberValidators.add(PhoneNumberValidator.builder().phoneMetaData(PhoneMetaData.builder().code("(251)").countryName("Ethiopia").regex("\\(251\\)\\ ?[1-59]\\d{8}$").build()).build());
        phoneNumberValidators.add(PhoneNumberValidator.builder().phoneMetaData(PhoneMetaData.builder().code("(212)").countryName("Morocco").regex("\\(212\\)\\ ?[5-9]\\d{8}$").build()).build());
        phoneNumberValidators.add(PhoneNumberValidator.builder().phoneMetaData(PhoneMetaData.builder().code("(258)").countryName("Mozambique").regex("\\(258\\)\\ ?[28]\\d{7,8}$").build()).build());
        return phoneNumberValidators;
    }
}
