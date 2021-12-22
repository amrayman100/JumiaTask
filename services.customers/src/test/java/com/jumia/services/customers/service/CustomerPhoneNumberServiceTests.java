package com.jumia.services.customers.service;

import com.jumia.services.customers.exception.CodeNotFoundException;
import com.jumia.services.customers.exception.MetadataSourceNotSetException;
import com.jumia.services.customers.exception.NumberTooSmallException;
import com.jumia.services.customers.matcher.NumberMatcher;
import com.jumia.services.customers.matcher.PhoneNumberMatcherTestsUtils;
import com.jumia.services.customers.model.NumberValidationResponse;
import com.jumia.services.customers.model.PaginatedPhoneNumberResponse;
import com.jumia.services.customers.model.PhoneNumberDTO;
import com.jumia.services.customers.repository.CustomerRepository;
import com.jumia.services.customers.service.impl.CustomerPhoneServiceImpl;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;
import static org.junit.Assert.assertEquals;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = CustomerPhoneServiceImpl.class)
@SpringBootTest()
public class CustomerPhoneNumberServiceTests {
    @MockBean
    CustomerRepository customerRepository;

    @MockBean
    NumberMatcher numberMatcher;

    @Autowired
    CustomerPhoneService customerPhoneService;

    @Test
    public void testGetCustomerPhoneNumbersIfAllAreValid() throws NumberTooSmallException, MetadataSourceNotSetException, CodeNotFoundException {
        when(customerRepository.findAll(Mockito.any(Pageable.class))).thenReturn(CustomerServiceTestUtils.createCustomersPage());
        NumberValidationResponse validationResponse = CustomerServiceTestUtils.getResponse(true);
        when(numberMatcher.getPhoneType(Mockito.anyString())).thenReturn(validationResponse);
        Pageable pageable = PageRequest.of(0, 2);
        PaginatedPhoneNumberResponse expectedResult = CustomerServiceTestUtils.createPaginatedPhoneNumberResponse();
        PaginatedPhoneNumberResponse paginatedPhoneNumberResponse = customerPhoneService.getCustomerPhoneNumbers(pageable,null,null);
        assertEquals(expectedResult.getNumberOfItems(),paginatedPhoneNumberResponse.getNumberOfItems());
        String state = validationResponse.isValid() ? "Valid" : "Invalid";
        int index = 0;
        for(PhoneNumberDTO phoneNumberDTO :  paginatedPhoneNumberResponse.getPhoneNumberList()){
            PhoneNumberDTO expectedPhoneDTO = expectedResult.getPhoneNumberList().get(index);
            assertEquals(expectedPhoneDTO.getName(),phoneNumberDTO.getName());
            assertEquals(expectedPhoneDTO.getNumber(),phoneNumberDTO.getNumber());
            assertEquals(validationResponse.getCode(),phoneNumberDTO.getCode());
            assertEquals(state,phoneNumberDTO.getState());
            index++;
        }
        Mockito.verify(customerRepository).findAll(Mockito.any(Pageable.class));
        Mockito.verify(numberMatcher,times(2)).getPhoneType(Mockito.anyString());
    }

    @Test
    public void testGetCustomerPhoneNumbersIfAllAreInValid() throws NumberTooSmallException, MetadataSourceNotSetException, CodeNotFoundException {
        when(customerRepository.findAll(Mockito.any(Pageable.class))).thenReturn(CustomerServiceTestUtils.createCustomersPage());
        NumberValidationResponse validationResponse = CustomerServiceTestUtils.getResponse(false);
        when(numberMatcher.getPhoneType(Mockito.anyString())).thenReturn(validationResponse);
        Pageable pageable = PageRequest.of(0, 2);
        PaginatedPhoneNumberResponse expectedResult = CustomerServiceTestUtils.createPaginatedPhoneNumberResponse();
        PaginatedPhoneNumberResponse paginatedPhoneNumberResponse = customerPhoneService.getCustomerPhoneNumbers(pageable,null,null);
        assertEquals(expectedResult.getNumberOfItems(),paginatedPhoneNumberResponse.getNumberOfItems());
        String state = validationResponse.isValid() ? "Valid" : "Invalid";
        int index = 0;
        for(PhoneNumberDTO phoneNumberDTO :  paginatedPhoneNumberResponse.getPhoneNumberList()){
            PhoneNumberDTO expectedPhoneDTO = expectedResult.getPhoneNumberList().get(index);
            assertEquals(expectedPhoneDTO.getName(),phoneNumberDTO.getName());
            assertEquals(expectedPhoneDTO.getNumber(),phoneNumberDTO.getNumber());
            assertEquals(validationResponse.getCode(),phoneNumberDTO.getCode());
            assertEquals(state,phoneNumberDTO.getState());
            index++;
        }
        Mockito.verify(customerRepository).findAll(Mockito.any(Pageable.class));
        Mockito.verify(numberMatcher,times(2)).getPhoneType(Mockito.anyString());
    }

    @Test
    public void testGetCustomerPhoneNumbersIfStateFilterIsValid() throws NumberTooSmallException, MetadataSourceNotSetException, CodeNotFoundException {
        when(customerRepository.findAll(Mockito.any(Pageable.class))).thenReturn(CustomerServiceTestUtils.createCustomersPage());
        NumberValidationResponse validationResponse = CustomerServiceTestUtils.getResponse(true);
        when(numberMatcher.getPhoneType(Mockito.anyString())).thenReturn(validationResponse);
        Pageable pageable = PageRequest.of(0, 2);
        PaginatedPhoneNumberResponse paginatedPhoneNumberResponse = customerPhoneService.getCustomerPhoneNumbers(pageable,null,CustomerServiceTestUtils.VALID);
        long resultCount = paginatedPhoneNumberResponse.getNumberOfItems();
        assertEquals(2L,resultCount);
        for( PhoneNumberDTO phoneNumberDTO :  paginatedPhoneNumberResponse.getPhoneNumberList()){
            assertEquals(CustomerServiceTestUtils.VALID,phoneNumberDTO.getState());
        }
        Mockito.verify(customerRepository,times(1)).findAll(Mockito.any(Pageable.class));
        Mockito.verify(numberMatcher,times(2)).getPhoneType(Mockito.anyString());
    }

    @Test
    public void testGetCustomerPhoneNumbersIfStateFilterIsInValid() throws NumberTooSmallException, MetadataSourceNotSetException, CodeNotFoundException {
        when(customerRepository.findAll(Mockito.any(Pageable.class))).thenReturn(CustomerServiceTestUtils.createCustomersPage());
        NumberValidationResponse validationResponse = CustomerServiceTestUtils.getResponse(false);
        when(numberMatcher.getPhoneType(Mockito.anyString())).thenReturn(validationResponse);
        Pageable pageable = PageRequest.of(0, 2);
        PaginatedPhoneNumberResponse paginatedPhoneNumberResponse = customerPhoneService.getCustomerPhoneNumbers(pageable,null,CustomerServiceTestUtils.INVALID);
        long resultCount = paginatedPhoneNumberResponse.getNumberOfItems();
        assertEquals(2L,resultCount);
        for( PhoneNumberDTO phoneNumberDTO :  paginatedPhoneNumberResponse.getPhoneNumberList()){
            assertEquals(CustomerServiceTestUtils.INVALID,phoneNumberDTO.getState());
        }
        Mockito.verify(customerRepository).findAll(Mockito.any(Pageable.class));
        Mockito.verify(numberMatcher,times(2)).getPhoneType(Mockito.anyString());
    }

    @Test
    public void testGetCustomerPhoneNumbersIfCountryFilterIsInValid() throws NumberTooSmallException, MetadataSourceNotSetException, CodeNotFoundException {
        when(customerRepository.findAllByPhoneIgnoreCaseStartsWith(Mockito.any(String.class),Mockito.any(Pageable.class))).thenReturn(CustomerServiceTestUtils.createCustomersPage());
        NumberValidationResponse validationResponse = CustomerServiceTestUtils.getResponse(false);
        when(numberMatcher.getPhoneMetaData(Mockito.any())).thenReturn(CustomerServiceTestUtils.getPhoneMetaData());
        when(numberMatcher.getPhoneType(Mockito.anyString())).thenReturn(validationResponse);
        Pageable pageable = PageRequest.of(0, 1);
        PaginatedPhoneNumberResponse paginatedPhoneNumberResponse = customerPhoneService.getCustomerPhoneNumbers(pageable,CustomerServiceTestUtils.CAMEROON,null);
        long resultCount = paginatedPhoneNumberResponse.getNumberOfItems();
        assertEquals(2L,resultCount);
        assertEquals(CustomerServiceTestUtils.CAMEROON,paginatedPhoneNumberResponse.getPhoneNumberList().get(1).getCountry());
        Mockito.verify(customerRepository).findAllByPhoneIgnoreCaseStartsWith(Mockito.any(String.class),Mockito.any(Pageable.class));
        Mockito.verify(numberMatcher,times(2)).getPhoneType(Mockito.anyString());
        Mockito.verify(numberMatcher,times(1)).getPhoneMetaData(Mockito.any());
    }

    @Test
    public void testGetCustomerPhoneNumbersIfNumberIsTooSmall() throws NumberTooSmallException, MetadataSourceNotSetException, CodeNotFoundException {
        when(customerRepository.findAllByPhoneIgnoreCaseStartsWith(Mockito.any(String.class),Mockito.any(Pageable.class))).thenReturn(CustomerServiceTestUtils.createCustomerWithSmallNumberPage());
        Pageable pageable = PageRequest.of(0, 2);
        when(numberMatcher.getPhoneType(Mockito.anyString())).thenThrow(NumberTooSmallException.class);
        when(numberMatcher.getPhoneMetaData(Mockito.any())).thenReturn(CustomerServiceTestUtils.getPhoneMetaData());
        PaginatedPhoneNumberResponse paginatedPhoneNumberResponse = customerPhoneService.getCustomerPhoneNumbers(pageable,CustomerServiceTestUtils.CAMEROON,null);
        long resultCount = paginatedPhoneNumberResponse.getNumberOfItems();
        assertEquals(0L,resultCount);
        Mockito.verify(customerRepository).findAllByPhoneIgnoreCaseStartsWith(Mockito.any(String.class),Mockito.any(Pageable.class));
        Mockito.verify(numberMatcher,times(1)).getPhoneType(Mockito.anyString());
        Mockito.verify(numberMatcher,times(1)).getPhoneMetaData(Mockito.any());
    }

    @Test
    public void testGetCustomerPhoneNumbersIfNumberIsMalformed() throws NumberTooSmallException, MetadataSourceNotSetException, CodeNotFoundException {
        when(customerRepository.findAllByPhoneIgnoreCaseStartsWith(Mockito.any(String.class),Mockito.any(Pageable.class))).thenReturn(CustomerServiceTestUtils.createCustomerWithMalformedNumberPage());
        when(numberMatcher.getPhoneType(Mockito.anyString())).thenThrow(CodeNotFoundException.class);
        when(numberMatcher.getPhoneMetaData(Mockito.any())).thenReturn(CustomerServiceTestUtils.getPhoneMetaData());
        Pageable pageable = PageRequest.of(0, 2);
        PaginatedPhoneNumberResponse paginatedPhoneNumberResponse = customerPhoneService.getCustomerPhoneNumbers(pageable,CustomerServiceTestUtils.CAMEROON,null);
        long resultCount = paginatedPhoneNumberResponse.getNumberOfItems();
        assertEquals(0L,resultCount);
        Mockito.verify(customerRepository).findAllByPhoneIgnoreCaseStartsWith(Mockito.any(String.class),Mockito.any(Pageable.class));
        Mockito.verify(numberMatcher,times(1)).getPhoneType(Mockito.anyString());
        Mockito.verify(numberMatcher,times(1)).getPhoneMetaData(Mockito.any());
    }
}
