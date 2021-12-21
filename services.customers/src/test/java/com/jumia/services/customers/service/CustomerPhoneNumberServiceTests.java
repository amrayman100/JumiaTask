package com.jumia.services.customers.service;

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

    @Autowired
    CustomerPhoneService customerPhoneService;
    @Test
    public void testGetCustomerPhoneNumbers(){
        when(customerRepository.findAll(Mockito.any(Pageable.class))).thenReturn(CustomerServiceTestUtils.createCustomersPage());
        Pageable pageable = PageRequest.of(0, 2);
        PaginatedPhoneNumberResponse expectedResult = CustomerServiceTestUtils.createPaginatedPhoneNumberResponse();
        PaginatedPhoneNumberResponse paginatedPhoneNumberResponse = customerPhoneService.getCustomerPhoneNumbers(pageable,null,null);
        assertEquals(expectedResult.getNumberOfItems(),paginatedPhoneNumberResponse.getNumberOfItems());
        int index = 0;
        for(PhoneNumberDTO phoneNumberDTO :  paginatedPhoneNumberResponse.getPhoneNumberList()){
            PhoneNumberDTO expectedPhoneDTO = expectedResult.getPhoneNumberList().get(index);
            assertEquals(expectedPhoneDTO.getName(),phoneNumberDTO.getName());
            assertEquals(expectedPhoneDTO.getCode(),phoneNumberDTO.getCode());
            assertEquals(expectedPhoneDTO.getNumber(),phoneNumberDTO.getNumber());
            assertEquals(expectedPhoneDTO.getState(),phoneNumberDTO.getState());
            index++;
        }
        Mockito.verify(customerRepository).findAll(Mockito.any(Pageable.class));
    }

    @Test
    public void testGetCustomerPhoneNumbersIfStateFilterIsValid(){
        when(customerRepository.findAll(Mockito.any(Pageable.class))).thenReturn(CustomerServiceTestUtils.createCustomersPage());
        Pageable pageable = PageRequest.of(0, 2);
        PaginatedPhoneNumberResponse paginatedPhoneNumberResponse = customerPhoneService.getCustomerPhoneNumbers(pageable,null,CustomerServiceTestUtils.VALID);
        long resultCount = paginatedPhoneNumberResponse.getNumberOfItems();
        assertEquals(1L,resultCount);
        for( PhoneNumberDTO phoneNumberDTO :  paginatedPhoneNumberResponse.getPhoneNumberList()){
            assertEquals(CustomerServiceTestUtils.VALID,phoneNumberDTO.getState());
        }
        Mockito.verify(customerRepository).findAll(Mockito.any(Pageable.class));
    }

    @Test
    public void testGetCustomerPhoneNumbersIfStateFilterIsInValid(){
        when(customerRepository.findAll(Mockito.any(Pageable.class))).thenReturn(CustomerServiceTestUtils.createCustomersPage());
        Pageable pageable = PageRequest.of(0, 2);
        PaginatedPhoneNumberResponse paginatedPhoneNumberResponse = customerPhoneService.getCustomerPhoneNumbers(pageable,null,CustomerServiceTestUtils.INVALID);
        long resultCount = paginatedPhoneNumberResponse.getNumberOfItems();
        assertEquals(1L,resultCount);
        for( PhoneNumberDTO phoneNumberDTO :  paginatedPhoneNumberResponse.getPhoneNumberList()){
            assertEquals(CustomerServiceTestUtils.INVALID,phoneNumberDTO.getState());
        }
        Mockito.verify(customerRepository).findAll(Mockito.any(Pageable.class));
    }

    @Test
    public void testGetCustomerPhoneNumbersIfCountryFilterIsInValid(){
        when(customerRepository.findAllByPhoneIgnoreCaseStartsWith(Mockito.any(String.class),Mockito.any(Pageable.class))).thenReturn(CustomerServiceTestUtils.createCustomersPage());
        Pageable pageable = PageRequest.of(0, 2);
        PaginatedPhoneNumberResponse paginatedPhoneNumberResponse = customerPhoneService.getCustomerPhoneNumbers(pageable,CustomerServiceTestUtils.CAMEROON,null);
        long resultCount = paginatedPhoneNumberResponse.getNumberOfItems();
        assertEquals(2L,resultCount);
        assertEquals(CustomerServiceTestUtils.MOROCCO,paginatedPhoneNumberResponse.getPhoneNumberList().get(0).getCountry());
        assertEquals(CustomerServiceTestUtils.CAMEROON,paginatedPhoneNumberResponse.getPhoneNumberList().get(1).getCountry());
        Mockito.verify(customerRepository).findAllByPhoneIgnoreCaseStartsWith(Mockito.any(String.class),Mockito.any(Pageable.class));
    }

    @Test
    public void testGetCustomerPhoneNumbersIfNumberIsTooSmall(){
        when(customerRepository.findAllByPhoneIgnoreCaseStartsWith(Mockito.any(String.class),Mockito.any(Pageable.class))).thenReturn(CustomerServiceTestUtils.createCustomerWithSmallNumberPage());
        Pageable pageable = PageRequest.of(0, 2);
        PaginatedPhoneNumberResponse paginatedPhoneNumberResponse = customerPhoneService.getCustomerPhoneNumbers(pageable,CustomerServiceTestUtils.CAMEROON,null);
        long resultCount = paginatedPhoneNumberResponse.getNumberOfItems();
        assertEquals(0L,resultCount);
        Mockito.verify(customerRepository).findAllByPhoneIgnoreCaseStartsWith(Mockito.any(String.class),Mockito.any(Pageable.class));
    }

    @Test
    public void testGetCustomerPhoneNumbersIfNumberIsMalformed(){
        when(customerRepository.findAllByPhoneIgnoreCaseStartsWith(Mockito.any(String.class),Mockito.any(Pageable.class))).thenReturn(CustomerServiceTestUtils.createCustomerWithMalformedNumberPage());
        Pageable pageable = PageRequest.of(0, 2);
        PaginatedPhoneNumberResponse paginatedPhoneNumberResponse = customerPhoneService.getCustomerPhoneNumbers(pageable,CustomerServiceTestUtils.CAMEROON,null);
        long resultCount = paginatedPhoneNumberResponse.getNumberOfItems();
        assertEquals(0L,resultCount);
        Mockito.verify(customerRepository).findAllByPhoneIgnoreCaseStartsWith(Mockito.any(String.class),Mockito.any(Pageable.class));
    }
}
