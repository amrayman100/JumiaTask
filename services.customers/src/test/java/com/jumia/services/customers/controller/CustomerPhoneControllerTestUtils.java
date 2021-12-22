package com.jumia.services.customers.controller;

import com.jumia.services.customers.model.PaginatedPhoneNumberResponse;
import com.jumia.services.customers.model.PhoneNumberDTO;
import java.util.ArrayList;
import java.util.List;

public class CustomerPhoneControllerTestUtils {

    public static final String CUSTOMER_NAME_1 = "Walid Hammadi";
    public static final String VALID_PHONE = "(212) 698054317";
    public static final String INVALID_PHONE = "(212) 6007989253";
    public static final String CODE = "(212)";
    public static final String VALID = "Valid";
    public static final String INVALID = "Invalid";


    public static PhoneNumberDTO createPhoneDTO(String name, String number, String code, String state){
        PhoneNumberDTO phoneNumberDTO = new PhoneNumberDTO();
        phoneNumberDTO.setName(name);
        phoneNumberDTO.setNumber(number);
        phoneNumberDTO.setCode(code);
        phoneNumberDTO.setState(state);
        return phoneNumberDTO;
    }

    public static PaginatedPhoneNumberResponse createPaginatedValidNumberResponse() {
        PaginatedPhoneNumberResponse.PaginatedPhoneNumberResponseBuilder paginatedPhoneNumberResponseBuilder = PaginatedPhoneNumberResponse.builder().numberOfItems(2L);
        List<PhoneNumberDTO> phoneNumbers = new ArrayList<>();
        phoneNumbers.add(createPhoneDTO(CUSTOMER_NAME_1, VALID_PHONE,CODE,VALID));
        paginatedPhoneNumberResponseBuilder.phoneNumberList(phoneNumbers);
        return paginatedPhoneNumberResponseBuilder.build();
    }

    public static PaginatedPhoneNumberResponse createPaginatedInValidNumberResponse() {
        PaginatedPhoneNumberResponse.PaginatedPhoneNumberResponseBuilder paginatedPhoneNumberResponseBuilder = PaginatedPhoneNumberResponse.builder().numberOfItems(2L);
        List<PhoneNumberDTO> phoneNumbers = new ArrayList<>();
        phoneNumbers.add(createPhoneDTO(CUSTOMER_NAME_1, INVALID_PHONE,CODE,INVALID));
        paginatedPhoneNumberResponseBuilder.phoneNumberList(phoneNumbers);
        return paginatedPhoneNumberResponseBuilder.build();
    }

    public static PaginatedPhoneNumberResponse createEmptyResponse() {
        PaginatedPhoneNumberResponse.PaginatedPhoneNumberResponseBuilder paginatedPhoneNumberResponseBuilder = PaginatedPhoneNumberResponse.builder().numberOfItems(0L);
        List<PhoneNumberDTO> phoneNumbers = new ArrayList<>();
        paginatedPhoneNumberResponseBuilder.phoneNumberList(phoneNumbers);
        return paginatedPhoneNumberResponseBuilder.build();
    }
}
