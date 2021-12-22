package com.jumia.services.customers.model;

import com.jumia.services.customers.exception.CodeNotFoundException;
import com.jumia.services.customers.exception.MetadataSourceNotSetException;
import com.jumia.services.customers.exception.NumberTooSmallException;
import com.jumia.services.customers.matcher.impl.PhoneNumberMatcher;
import com.jumia.services.customers.metadata.impl.PhoneJsonMetadataSource;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class PhoneNumberDTO {
    private String name;
    private String number;
    private String code;
    private String state;
    private String country;
}
