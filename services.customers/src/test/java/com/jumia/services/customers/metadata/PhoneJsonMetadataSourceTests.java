package com.jumia.services.customers.metadata;

import com.jumia.services.customers.metadata.impl.PhoneJsonMetadataSource;
import com.jumia.services.customers.validator.PhoneNumberValidator;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.List;

import static org.junit.Assert.assertNotNull;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@ExtendWith(SpringExtension.class)
@SpringBootTest
public class PhoneJsonMetadataSourceTests {

    private PhoneJsonMetadataSource jsonMetadataSource;

    @Before
    public void init() {
        jsonMetadataSource = new PhoneJsonMetadataSource();
    }

    @Test
    public void testGetValidatorsCodeMapFromJsonFileIfDataIsGiven(){
        HashMap<String, PhoneNumberValidator> validatorHashMap = jsonMetadataSource.getValidatorCodeMap();
        assertNotNull(validatorHashMap);
        List<PhoneNumberValidator> expectedValidators = PhoneJsonMetadataSourceTestUtils.createPhoneNumberValidatorList();
        expectedValidators.forEach(val -> {
            assertNotNull(validatorHashMap.get(val.getPhoneMetaData().getCode()));
        });
    }

    @Test
    public void testGetValidatorsCountryMapFromJsonFileIfDataIsGiven(){
        HashMap<String, PhoneNumberValidator> validatorHashMap = jsonMetadataSource.getValidatorCountryMap();
        assertNotNull(validatorHashMap);
        List<PhoneNumberValidator> expectedValidators = PhoneJsonMetadataSourceTestUtils.createPhoneNumberValidatorList();
        expectedValidators.forEach(val -> {
            assertNotNull(validatorHashMap.get(val.getPhoneMetaData().getCountryName()));
        });
    }
}
