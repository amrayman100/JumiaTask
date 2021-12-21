package com.jumia.services.customers.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PhoneMetaData{
    private String countryName;
    private String code;
    private String regex;
}
