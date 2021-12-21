package com.jumia.services.customers.validator;

import com.jumia.services.customers.model.NumberValidationResponse;
import com.jumia.services.customers.model.PhoneMetaData;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@ExtendWith(SpringExtension.class)
@SpringBootTest
public class PhoneNumberValidatorTests {

    @Test
    public void testPhoneNumberRegexValidatorIfPhoneNumberIsValid(){
        PhoneNumberValidator phoneNumberValidator = PhoneNumberValidatorTestsUtils.getValidator();
        NumberValidationResponse numberValidationResponse = phoneNumberValidator.validate(PhoneNumberValidatorTestsUtils.VALID_NUMBER);
        assertNotNull(numberValidationResponse);
        assertEquals(PhoneNumberValidatorTestsUtils.CODE,numberValidationResponse.getCode());
        assertEquals(PhoneNumberValidatorTestsUtils.COUNTRY_NAME,numberValidationResponse.getOrigin());
        assertTrue(numberValidationResponse.isValid());
    }

    @Test
    public void testPhoneNumberRegexValidatorIfPhoneNumberIsInValid(){
        PhoneNumberValidator phoneNumberValidator = PhoneNumberValidatorTestsUtils.getValidator();
        NumberValidationResponse numberValidationResponse = phoneNumberValidator.validate(PhoneNumberValidatorTestsUtils.INVALID_NUMBER);
        assertNotNull(numberValidationResponse);
        assertEquals(PhoneNumberValidatorTestsUtils.CODE,numberValidationResponse.getCode());
        assertEquals(PhoneNumberValidatorTestsUtils.COUNTRY_NAME,numberValidationResponse.getOrigin());
        assertFalse(numberValidationResponse.isValid());
    }


}
