package com.jumia.services.customers.metadata.impl;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jumia.services.customers.metadata.PhoneMetadataSource;
import com.jumia.services.customers.model.PhoneMetaData;
import com.jumia.services.customers.validator.PhoneNumberValidator;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public class PhoneJsonMetadataSource implements PhoneMetadataSource {

    @Override
    public HashMap<String, PhoneNumberValidator> getValidatorCodeMap() {
        HashMap<String, PhoneNumberValidator> metaDataMap = new HashMap<>();
        try {
            List<PhoneMetaData> phoneMetaDataList = new ObjectMapper().readerForListOf(PhoneMetaData.class).readValue(this.getClass().getResource("/phone_metadata.json"));
            phoneMetaDataList.forEach((meta) ->{
                    metaDataMap.put(meta.getCode(), PhoneNumberValidator.builder().phoneMetaData(meta).build());
            });
            return metaDataMap;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public HashMap<String, PhoneNumberValidator> getValidatorCountryMap() {
        HashMap<String, PhoneNumberValidator> metaDataMap = new HashMap<>();
        try {
            List<PhoneMetaData> phoneMetaDataList = new ObjectMapper().readerForListOf(PhoneMetaData.class).readValue(this.getClass().getResource("/phone_metadata.json"));
            phoneMetaDataList.forEach((meta) ->{
                metaDataMap.put(meta.getCountryName(), PhoneNumberValidator.builder().phoneMetaData(meta).build());
            });
            return metaDataMap;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
