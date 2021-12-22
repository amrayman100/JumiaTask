package com.jumia.services.customers.matcher.impl;
import com.jumia.services.customers.exception.CodeNotFoundException;
import com.jumia.services.customers.exception.MetadataSourceNotSetException;
import com.jumia.services.customers.exception.NumberTooSmallException;
import com.jumia.services.customers.logging.Loggable;
import com.jumia.services.customers.matcher.NumberMatcher;
import com.jumia.services.customers.metadata.PhoneMetadataSource;
import com.jumia.services.customers.model.NumberValidationResponse;
import com.jumia.services.customers.model.PhoneMetaData;
import com.jumia.services.customers.validator.PhoneNumberValidator;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@AllArgsConstructor
@Loggable
@Service
public class PhoneNumberMatcher implements NumberMatcher {

    private PhoneMetadataSource metadataSource;

    @Autowired
    public void setMetadataSource(PhoneMetadataSource metadataSource) {
        this.metadataSource = metadataSource;
    }

    public String extractCode(String number) throws NumberTooSmallException {
        if(number.length() < 6){
            throw new NumberTooSmallException("Phone Number given is too small");
        }
        return number.substring(0,5);
    }

    public NumberValidationResponse getPhoneType(String number) throws MetadataSourceNotSetException, NumberTooSmallException, CodeNotFoundException {
        if(metadataSource == null){
            throw new MetadataSourceNotSetException("You must set a source for the metadata loader");
        }
        HashMap<String, PhoneNumberValidator> metaDataMap = metadataSource.getValidatorCodeMap();
        PhoneNumberValidator phoneNumberValidator = metaDataMap.get(extractCode(number));
        if(phoneNumberValidator == null){
            throw new CodeNotFoundException("The code supplied is not valid");
        }
        return phoneNumberValidator.validate(number);
    }

    public PhoneMetaData getPhoneMetaData(String country){
        HashMap<String, PhoneNumberValidator> countryCodeMap = metadataSource.getValidatorCountryMap();
        PhoneNumberValidator phoneNumberValidator = countryCodeMap.get(country);
        if(phoneNumberValidator == null){
            return null;
        }
        return phoneNumberValidator.getPhoneMetaData();
    }
}
