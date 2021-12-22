package com.jumia.services.customers.matcher;

import com.jumia.services.customers.exception.CodeNotFoundException;
import com.jumia.services.customers.exception.MetadataSourceNotSetException;
import com.jumia.services.customers.exception.NumberTooSmallException;
import com.jumia.services.customers.matcher.impl.PhoneNumberMatcher;
import com.jumia.services.customers.metadata.PhoneMetadataSource;
import com.jumia.services.customers.metadata.impl.PhoneJsonMetadataSource;
import com.jumia.services.customers.model.NumberValidationResponse;
import com.jumia.services.customers.model.PhoneMetaData;
import com.jumia.services.customers.service.impl.CustomerPhoneServiceImpl;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = PhoneNumberMatcher.class)
public class PhoneNumberMatcherTests {
    @Autowired
    PhoneNumberMatcher phoneNumberMatcher;

    @MockBean
    PhoneMetadataSource metadataSource;

    @Test
    public void testExtractCodeIfNumberGivenIsCorrectLength() throws NumberTooSmallException {
        String code = phoneNumberMatcher.extractCode(PhoneNumberMatcherTestsUtils.NUMBER);
        assertEquals(PhoneNumberMatcherTestsUtils.CODE,code);
    }

    @Test(expected = NumberTooSmallException.class)
    public void testExtractCodeIfNumberGivenIsTooShortLength() throws NumberTooSmallException {
        phoneNumberMatcher.extractCode(PhoneNumberMatcherTestsUtils.SHORT_NUMBER);
    }

    @Test
    public void testMatcherIfEthiopiaNumberIsGivenValid() throws MetadataSourceNotSetException, NumberTooSmallException, CodeNotFoundException {
        when(metadataSource.getValidatorCodeMap()).thenReturn(PhoneNumberMatcherTestsUtils.createMetaDataMap());
        NumberValidationResponse numberValidationResponse = phoneNumberMatcher.getPhoneType(PhoneNumberMatcherTestsUtils.VALID_ETHIOPIA_NUMBER);
        assertTrue(numberValidationResponse.isValid());
        assertEquals(PhoneNumberMatcherTestsUtils.ETHIOPIA_CODE,numberValidationResponse.getCode());
        assertEquals(PhoneNumberMatcherTestsUtils.ETHIOPIA,numberValidationResponse.getOrigin());
        Mockito.verify(metadataSource).getValidatorCodeMap();
    }

    @Test
    public void testMatcherIfEthiopiaNumberIsGivenInValid() throws MetadataSourceNotSetException, NumberTooSmallException, CodeNotFoundException {
        when(metadataSource.getValidatorCodeMap()).thenReturn(PhoneNumberMatcherTestsUtils.createMetaDataMap());
        NumberValidationResponse numberValidationResponse = phoneNumberMatcher.getPhoneType(PhoneNumberMatcherTestsUtils.INVALID_ETHIOPIA_NUMBER);
        assertFalse(numberValidationResponse.isValid());
        assertEquals(PhoneNumberMatcherTestsUtils.ETHIOPIA_CODE,numberValidationResponse.getCode());
        assertEquals(PhoneNumberMatcherTestsUtils.ETHIOPIA,numberValidationResponse.getOrigin());
        Mockito.verify(metadataSource).getValidatorCodeMap();
    }

    @Test(expected = MetadataSourceNotSetException.class)
    public void testMatcherIfMetaDataSourceIsNull() throws MetadataSourceNotSetException, NumberTooSmallException, CodeNotFoundException {
        phoneNumberMatcher = new PhoneNumberMatcher(new PhoneJsonMetadataSource());
        phoneNumberMatcher.setMetadataSource(null);
        phoneNumberMatcher.getPhoneType(PhoneNumberMatcherTestsUtils.INVALID_ETHIOPIA_NUMBER);
    }

    @Test
    public void testMatcherGetPhoneMetaDataIfValueIsInCountryMap() {
        when(metadataSource.getValidatorCountryMap()).thenReturn(PhoneNumberMatcherTestsUtils.createCountryMetaDataMap());
        PhoneMetaData phoneMetaData = phoneNumberMatcher.getPhoneMetaData(PhoneNumberMatcherTestsUtils.ETHIOPIA);
        assertNotNull(phoneMetaData);
        assertEquals(PhoneNumberMatcherTestsUtils.ETHIOPIA,phoneMetaData.getCountryName());
        Mockito.verify(metadataSource).getValidatorCountryMap();
    }

    @Test
    public void testMatcherGetPhoneMetaDataIfValueIsInNotInCountryMap() {
        when(metadataSource.getValidatorCountryMap()).thenReturn(PhoneNumberMatcherTestsUtils.createCountryMetaDataMap());
        PhoneMetaData phoneMetaData = phoneNumberMatcher.getPhoneMetaData(PhoneNumberMatcherTestsUtils.LONDON);
        assertNull(phoneMetaData);
        Mockito.verify(metadataSource).getValidatorCountryMap();
    }

}
