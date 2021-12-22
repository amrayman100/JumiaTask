package com.jumia.services.customers.service.impl;
import com.jumia.services.customers.entity.Customer;
import com.jumia.services.customers.exception.CodeNotFoundException;
import com.jumia.services.customers.exception.MetadataSourceNotSetException;
import com.jumia.services.customers.exception.NumberTooSmallException;
import com.jumia.services.customers.logging.Loggable;
import com.jumia.services.customers.matcher.NumberMatcher;
import com.jumia.services.customers.matcher.impl.PhoneNumberMatcher;
import com.jumia.services.customers.model.NumberValidationResponse;
import com.jumia.services.customers.model.PaginatedPhoneNumberResponse;
import com.jumia.services.customers.model.PhoneMetaData;
import com.jumia.services.customers.model.PhoneNumberDTO;
import com.jumia.services.customers.repository.CustomerRepository;
import com.jumia.services.customers.service.CustomerPhoneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Loggable
@Service
public class CustomerPhoneServiceImpl implements CustomerPhoneService {

    CustomerRepository customerRepository;

    NumberMatcher phoneNumberMatcher;

    @Autowired
    public CustomerPhoneServiceImpl(CustomerRepository customerRepository,NumberMatcher numberMatcher){
        this.customerRepository = customerRepository;
        this.phoneNumberMatcher = numberMatcher;
    }

    public PaginatedPhoneNumberResponse getCustomerPhoneNumbers(Pageable pageable, String queryCountry, String queryState)  {
        Page<Customer> customers;
        List<PhoneNumberDTO> filteredPhoneNumberModels = new ArrayList<>();
        PhoneMetaData phoneMetaData = getPhoneMetaData(queryCountry,phoneNumberMatcher);
        if(phoneMetaData == null){
            customers = customerRepository.findAll(pageable);
        } else {
            customers = customerRepository.findAllByPhoneIgnoreCaseStartsWith(phoneMetaData.getCode(),pageable);
        }
        customers.forEach(customer -> {
            PhoneNumberDTO phoneNumberDTO = getPhoneNumberDTO(customer.getPhone(),customer.getName(),phoneNumberMatcher);
            if(phoneNumberDTO != null && (queryState == null || phoneNumberDTO.getState().equalsIgnoreCase(queryState))){
                filteredPhoneNumberModels.add(phoneNumberDTO);
            }
        });

        return PaginatedPhoneNumberResponse.builder()
                .numberOfItems((long) filteredPhoneNumberModels.size())
                .phoneNumberList(filteredPhoneNumberModels)
                .build();
    }

    private PhoneMetaData getPhoneMetaData(String country, NumberMatcher phoneNumberMatcher){
        return phoneNumberMatcher.getPhoneMetaData(country);
    }

    private PhoneNumberDTO getPhoneNumberDTO(String number, String name, NumberMatcher phoneNumberMatcher)  {
        try {
            NumberValidationResponse numberValidationResponse = phoneNumberMatcher.getPhoneType(number);
            String state = numberValidationResponse.isValid() ? "Valid" : "Invalid";
            return  PhoneNumberDTO.builder().code(numberValidationResponse.getCode()).country(numberValidationResponse.getOrigin())
                    .name(name).number(number).state(state).build();
        } catch (MetadataSourceNotSetException | NumberTooSmallException | CodeNotFoundException ignored) {
            return null;
        }
    }
}
