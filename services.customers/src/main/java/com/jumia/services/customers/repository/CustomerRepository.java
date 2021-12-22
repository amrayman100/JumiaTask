package com.jumia.services.customers.repository;

import com.jumia.services.customers.entity.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

@Repository
public interface CustomerRepository extends PagingAndSortingRepository<Customer, Long> {
    Page<Customer> findAllByPhoneIgnoreCaseStartsWith(String code, Pageable pageable);
}
