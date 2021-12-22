package com.jumia.services.customers.service;

import com.jumia.services.customers.entity.Customer;
import com.jumia.services.customers.model.NumberValidationResponse;
import com.jumia.services.customers.model.PaginatedPhoneNumberResponse;
import com.jumia.services.customers.model.PhoneMetaData;
import com.jumia.services.customers.model.PhoneNumberDTO;
import org.springframework.data.domain.PageImpl;

import java.util.ArrayList;
import java.util.List;

public class CustomerServiceTestUtils {
    public static final String CUSTOMER_NAME_1 = "Walid Hammadi";
    public static final String CUSTOMER_NAME_2 = "Yosaf Karrouch";
    public static final String CUSTOMER_PHONE_1 = "(212) 6007989253";
    public static final String CUSTOMER_PHONE_2 = "(237) 698054317";
    public static final String CUSTOMER_CODE_1 = "(212)";
    public static final String CUSTOMER_CODE_2 = "(237)";
    public static final String CAMEROON = "Cameroon";
    public static final String MOROCCO = "Morocco";
    public static final String VALID = "Valid";
    public static final String INVALID = "Invalid";
    public static final String SMALL_NUMBER = "(1";
    public static final String MALFORMED_NUMBER = "(999) 6780009592";

    public static PhoneNumberDTO createPhoneDTO(String name, String number, String code, String state){
        PhoneNumberDTO phoneNumberDTO = new PhoneNumberDTO();
        phoneNumberDTO.setName(name);
        phoneNumberDTO.setNumber(number);
        phoneNumberDTO.setCode(code);
        phoneNumberDTO.setState(state);
        return phoneNumberDTO;
    }

    public static PaginatedPhoneNumberResponse createPaginatedPhoneNumberResponse() {
        PaginatedPhoneNumberResponse.PaginatedPhoneNumberResponseBuilder paginatedPhoneNumberResponseBuilder = PaginatedPhoneNumberResponse.builder().numberOfItems(2L);
        List<PhoneNumberDTO> phoneNumbers = new ArrayList<>();
        phoneNumbers.add(createPhoneDTO(CUSTOMER_NAME_1, CUSTOMER_PHONE_1,CUSTOMER_CODE_1, INVALID));
        phoneNumbers.add(createPhoneDTO(CUSTOMER_NAME_2, CUSTOMER_PHONE_2,CUSTOMER_CODE_2, VALID));
        paginatedPhoneNumberResponseBuilder.phoneNumberList(phoneNumbers);
        return paginatedPhoneNumberResponseBuilder.build();
    }

    public static PageImpl<Customer> createCustomersPage(){
        List<Customer> customers = new ArrayList<>();
        customers.add(new Customer(CUSTOMER_NAME_1, CUSTOMER_PHONE_1));
        customers.add(new Customer(CUSTOMER_NAME_2, CUSTOMER_PHONE_2));
        return new PageImpl<>(customers);
    }

    public static PageImpl<Customer> createCustomerWithSmallNumberPage(){
        List<Customer> customers = new ArrayList<>();
        customers.add(new Customer(CUSTOMER_NAME_1, SMALL_NUMBER));
        return new PageImpl<>(customers);
    }

    public static PageImpl<Customer> createCustomerWithMalformedNumberPage(){
        List<Customer> customers = new ArrayList<>();
        customers.add(new Customer(CUSTOMER_NAME_1, MALFORMED_NUMBER));
        return new PageImpl<>(customers);
    }

    public static NumberValidationResponse getResponse(boolean isValid){
        return NumberValidationResponse.builder().isValid(isValid).origin(CAMEROON)
                .code(CUSTOMER_CODE_1).build();
    }

    public static PhoneMetaData getPhoneMetaData(){
        return PhoneMetaData.builder().countryName(CAMEROON).code("(212)").build();
    }
}
