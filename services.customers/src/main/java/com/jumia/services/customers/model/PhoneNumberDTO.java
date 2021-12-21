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

//    public PhoneNumberDTO(){
//
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public String getNumber() {
//        return number;
//    }
//
//    public void setNumber(String number) throws MetadataSourceNotSetException, NumberTooSmallException, CodeNotFoundException {
//        this.number = number;
//        PhoneNumberMatcher phoneNumberMatcher = new PhoneNumberMatcher(new PhoneJsonMetadataSource());
//        NumberValidationResponse numberValidationResponse = phoneNumberMatcher.getPhoneType(number);
//        this.state = numberValidationResponse.isValid() ? "Valid" : "Invalid";
//        this.country = numberValidationResponse.getOrigin();
//        this.code = numberValidationResponse.getCode();
//    }
//
//    public String getCode() {
//        return code;
//    }
//
//    public String getState() {
//        return state;
//    }
//
//    public String getCountry() {
//        return country;
//    }
}
