package com.jumia.services.customers.model;

import lombok.Builder;
import lombok.Data;
import java.util.List;

@Data
@Builder
public class PaginatedPhoneNumberResponse {
    private List<PhoneNumberDTO> phoneNumberList;
    private Long numberOfItems;
}
