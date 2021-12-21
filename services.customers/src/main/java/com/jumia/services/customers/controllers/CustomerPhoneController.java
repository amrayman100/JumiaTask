package com.jumia.services.customers.controllers;
import com.jumia.services.customers.controllers.api.CustomerPhoneApi;
import com.jumia.services.customers.logging.Loggable;
import com.jumia.services.customers.model.PaginatedPhoneNumberResponse;
import org.springframework.data.domain.Pageable;
import com.jumia.services.customers.service.CustomerPhoneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/v1")
@Loggable
public class CustomerPhoneController implements CustomerPhoneApi {
    private CustomerPhoneService customerService;

    @Autowired
    public CustomerPhoneController(CustomerPhoneService customerService){
        this.customerService = customerService;
    }

    @GetMapping("/numbers")
    public ResponseEntity<PaginatedPhoneNumberResponse> getPhoneNumbers (@RequestParam(required = false) String country, @RequestParam(required = false) String state, Pageable pageable) {
        return ResponseEntity.ok(customerService.getCustomerPhoneNumbers(pageable,country,state));
    }
}
