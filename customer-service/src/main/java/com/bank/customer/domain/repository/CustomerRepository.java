package com.bank.customer.domain.repository;

import com.bank.customer.domain.model.Customer;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


public interface CustomerRepository {
    Flux<Customer> findAll();

    Mono<Customer> save(Customer customer);

    Mono<Void> deleteById(Long id);

    Mono<Customer> findById(Long id);

    Mono<Customer> update(Long id, Customer customer);
}
