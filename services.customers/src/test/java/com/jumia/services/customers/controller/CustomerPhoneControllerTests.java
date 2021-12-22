package com.jumia.services.customers.controller;

import com.jumia.services.customers.controllers.CustomerPhoneController;
import com.jumia.services.customers.model.PaginatedPhoneNumberResponse;
import com.jumia.services.customers.model.PhoneNumberDTO;
import com.jumia.services.customers.service.CustomerPhoneService;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import static org.mockito.Mockito.when;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = CustomerPhoneController.class)
@SpringBootTest()
public class CustomerPhoneControllerTests {

    @MockBean
    private CustomerPhoneService customerService;

    @Autowired
    private CustomerPhoneController customerPhoneController;

    @Test
    public void testGetPhoneNumbersIfStateFilterIsValid() {
        when(customerService.getCustomerPhoneNumbers((Mockito.any(Pageable.class)), Mockito.any(), Mockito.any())).thenReturn(CustomerPhoneControllerTestUtils.createPaginatedValidNumberResponse());
        Pageable pageable = PageRequest.of(0, 1);
        ResponseEntity<PaginatedPhoneNumberResponse> responseEntity = customerPhoneController.getPhoneNumbers(CustomerPhoneControllerTestUtils.VALID,null,pageable);
        assertNotNull(responseEntity.getBody());
        assertEquals(HttpStatus.OK,responseEntity.getStatusCode());
        for( PhoneNumberDTO phoneNumberDTO :  responseEntity.getBody().getPhoneNumberList()){
            assertEquals(CustomerPhoneControllerTestUtils.VALID,phoneNumberDTO.getState());
        }
        Mockito.verify(customerService).getCustomerPhoneNumbers((Mockito.any(Pageable.class)), Mockito.any(), Mockito.any());
    }

    @Test
    public void testGetPhoneNumbersIfStateFilterIsInValid() {
        when(customerService.getCustomerPhoneNumbers((Mockito.any(Pageable.class)), Mockito.any(), Mockito.any())).thenReturn(CustomerPhoneControllerTestUtils.createPaginatedInValidNumberResponse());
        Pageable pageable = PageRequest.of(0, 1);
        ResponseEntity<PaginatedPhoneNumberResponse> responseEntity = customerPhoneController.getPhoneNumbers(CustomerPhoneControllerTestUtils.VALID,null,pageable);
        assertNotNull(responseEntity.getBody());
        assertEquals(HttpStatus.OK,responseEntity.getStatusCode());
        for( PhoneNumberDTO phoneNumberDTO :  responseEntity.getBody().getPhoneNumberList()){
            assertEquals(CustomerPhoneControllerTestUtils.INVALID,phoneNumberDTO.getState());
        }
        Mockito.verify(customerService).getCustomerPhoneNumbers((Mockito.any(Pageable.class)), Mockito.any(), Mockito.any());
    }

    @Test
    public void testGetPhoneNumbersIfNoResultsAreFound() {
        when(customerService.getCustomerPhoneNumbers((Mockito.any(Pageable.class)), Mockito.any(), Mockito.any())).thenReturn(CustomerPhoneControllerTestUtils.createEmptyResponse());
        Pageable pageable = PageRequest.of(0, 1);
        ResponseEntity<PaginatedPhoneNumberResponse> responseEntity = customerPhoneController.getPhoneNumbers(CustomerPhoneControllerTestUtils.VALID,null,pageable);
        assertNotNull(responseEntity.getBody());
        assertEquals(HttpStatus.OK,responseEntity.getStatusCode());
        assertEquals(0,responseEntity.getBody().getPhoneNumberList().size());
        Mockito.verify(customerService).getCustomerPhoneNumbers((Mockito.any(Pageable.class)), Mockito.any(), Mockito.any());
    }


    @Test(expected = Exception.class)
    public void testGetPhoneNumbersIfServiceThrowsException() {
        when(customerService.getCustomerPhoneNumbers((Mockito.any(Pageable.class)), Mockito.any(), Mockito.any())).thenThrow(Exception.class);
        Pageable pageable = PageRequest.of(0, 1);
        customerPhoneController.getPhoneNumbers(CustomerPhoneControllerTestUtils.VALID,null,pageable);
        Mockito.verify(customerService).getCustomerPhoneNumbers((Mockito.any(Pageable.class)), Mockito.any(), Mockito.any());
    }
}
