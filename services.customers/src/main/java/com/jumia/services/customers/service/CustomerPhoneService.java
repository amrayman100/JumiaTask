package com.jumia.services.customers.service;

import com.jumia.services.customers.model.PaginatedPhoneNumberResponse;
import org.springframework.data.domain.Pageable;

public interface CustomerPhoneService {
    PaginatedPhoneNumberResponse getCustomerPhoneNumbers(Pageable pageable, String country, String state);
}
