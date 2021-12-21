package com.jumia.services.customers.controllers.api;

import com.jumia.services.customers.model.PaginatedPhoneNumberResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;

public interface CustomerPhoneApi {
    ResponseEntity<PaginatedPhoneNumberResponse> getPhoneNumbers (@RequestParam(required = false) String country, @RequestParam(required = false) String state, Pageable pageable);
}
