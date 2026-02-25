package com.bank.customer.service;

import com.bank.customer.domain.model.Customer;
import com.bank.customer.domain.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository repository;

    public List<Customer> getAll(){

        return repository.findAll();
    }
}
