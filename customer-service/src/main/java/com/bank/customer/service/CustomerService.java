package com.bank.customer.service;

import com.bank.customer.domain.exception.CustomerNotFoundException;
import com.bank.customer.domain.model.Customer;
import com.bank.customer.domain.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository repository;

    public Flux<Customer> getAll(){
        return repository.findAll();
    }

    public Mono<Customer> save(Customer customer){
        return repository.save(customer);
    }

    public Mono<Void> delete(Long id){
        return repository.deleteById(id);
    }

    public Mono<Customer> getById(Long id) {
        return repository.findById(id)
                .switchIfEmpty(Mono.error(new CustomerNotFoundException("Customer not found with ID: " + id)));
    }

    public Mono<Customer> update(Long id, Customer customerDetails) {
        return repository.findById(id)
                .switchIfEmpty(Mono.error(new CustomerNotFoundException("No se puede actualizar: Cliente no encontrado con ID: " + id)))
                .flatMap(existingCustomer -> {
                    customerDetails.setId( existingCustomer.getId() );
                    return repository.save(customerDetails);
                });
    }
}
