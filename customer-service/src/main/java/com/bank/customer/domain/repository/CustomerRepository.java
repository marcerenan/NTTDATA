package com.bank.customer.domain.repository;

import com.bank.customer.domain.model.Customer;

import java.util.List;

public interface CustomerRepository {
    List<Customer> findAll();
}
