package com.jumia.services.customers.matcher;

import com.jumia.services.customers.exception.CodeNotFoundException;
import com.jumia.services.customers.exception.MetadataSourceNotSetException;
import com.jumia.services.customers.exception.NumberTooSmallException;
import com.jumia.services.customers.matcher.impl.PhoneNumberMatcher;
import com.jumia.services.customers.metadata.PhoneMetadataSource;
import com.jumia.services.customers.metadata.impl.PhoneJsonMetadataSource;
import com.jumia.services.customers.model.NumberValidationResponse;
import com.jumia.services.customers.model.PhoneMetaData;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

@SpringBootTest
public class PhoneNumberMatcherTests {
    PhoneNumberMatcher phoneNumberMatcher;

    @Test
    public void testExtractCodeIfNumberGivenIsCorrectLength() throws NumberTooSmallException {
        phoneNumberMatcher = new PhoneNumberMatcher(new PhoneJsonMetadataSource());
        String code = phoneNumberMatcher.extractCode(PhoneNumberMatcherTestsUtils.NUMBER);
        assertEquals(PhoneNumberMatcherTestsUtils.CODE,code);
    }

    @Test(expected = NumberTooSmallException.class)
    public void testExtractCodeIfNumberGivenIsTooShortLength() throws NumberTooSmallException {
        phoneNumberMatcher = new PhoneNumberMatcher(new PhoneJsonMetadataSource());
        phoneNumberMatcher.extractCode(PhoneNumberMatcherTestsUtils.SHORT_NUMBER);
    }

    @Test
    public void testMatcherIfEthiopiaNumberIsGivenValid() throws MetadataSourceNotSetException, NumberTooSmallException, CodeNotFoundException {
        PhoneMetadataSource metadataSource = Mockito.mock(PhoneMetadataSource.class);
        phoneNumberMatcher = new PhoneNumberMatcher(metadataSource);
        when(metadataSource.getValidatorCodeMap()).thenReturn(PhoneNumberMatcherTestsUtils.createMetaDataMap());
        NumberValidationResponse numberValidationResponse = phoneNumberMatcher.getPhoneType(PhoneNumberMatcherTestsUtils.VALID_ETHIOPIA_NUMBER);
        assertTrue(numberValidationResponse.isValid());
        assertEquals(PhoneNumberMatcherTestsUtils.ETHIOPIA_CODE,numberValidationResponse.getCode());
        assertEquals(PhoneNumberMatcherTestsUtils.ETHIOPIA,numberValidationResponse.getOrigin());
    }

    @Test
    public void testMatcherIfEthiopiaNumberIsGivenInValid() throws MetadataSourceNotSetException, NumberTooSmallException, CodeNotFoundException {
        PhoneMetadataSource metadataSource = Mockito.mock(PhoneMetadataSource.class);
        phoneNumberMatcher = new PhoneNumberMatcher(metadataSource);
        when(metadataSource.getValidatorCodeMap()).thenReturn(PhoneNumberMatcherTestsUtils.createMetaDataMap());
        NumberValidationResponse numberValidationResponse = phoneNumberMatcher.getPhoneType(PhoneNumberMatcherTestsUtils.INVALID_ETHIOPIA_NUMBER);
        assertFalse(numberValidationResponse.isValid());
        assertEquals(PhoneNumberMatcherTestsUtils.ETHIOPIA_CODE,numberValidationResponse.getCode());
        assertEquals(PhoneNumberMatcherTestsUtils.ETHIOPIA,numberValidationResponse.getOrigin());
    }

    @Test(expected = MetadataSourceNotSetException.class)
    public void testMatcherIfMetaDataSourceIsNull() throws MetadataSourceNotSetException, NumberTooSmallException, CodeNotFoundException {
        phoneNumberMatcher = new PhoneNumberMatcher(new PhoneJsonMetadataSource());
        phoneNumberMatcher.setMetadataSource(null);
        phoneNumberMatcher.getPhoneType(PhoneNumberMatcherTestsUtils.INVALID_ETHIOPIA_NUMBER);
    }

    @Test
    public void testMatcherGetPhoneMetaDataIfValueIsInMap() {
        PhoneMetadataSource metadataSource = Mockito.mock(PhoneMetadataSource.class);
        phoneNumberMatcher = new PhoneNumberMatcher(metadataSource);
        when(metadataSource.getValidatorCountryMap()).thenReturn(PhoneNumberMatcherTestsUtils.createCountryMetaDataMap());
        PhoneMetaData phoneMetaData = phoneNumberMatcher.getPhoneMetaData(PhoneNumberMatcherTestsUtils.ETHIOPIA);
        assertNotNull(phoneMetaData);
        assertEquals(PhoneNumberMatcherTestsUtils.ETHIOPIA,phoneMetaData.getCountryName());
    }

    @Test
    public void testMatcherGetPhoneMetaDataIfValueIsNotInMap() {
        PhoneMetadataSource metadataSource = Mockito.mock(PhoneMetadataSource.class);
        phoneNumberMatcher = new PhoneNumberMatcher(metadataSource);
        when(metadataSource.getValidatorCountryMap()).thenReturn(PhoneNumberMatcherTestsUtils.createCountryMetaDataMap());
        PhoneMetaData phoneMetaData = phoneNumberMatcher.getPhoneMetaData(PhoneNumberMatcherTestsUtils.LONDON);
        assertNull(phoneMetaData);
    }

}
