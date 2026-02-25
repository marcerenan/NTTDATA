package com.bank.customer.api.controller;

import com.bank.customer.api.CustomerApi;
import com.bank.customer.domain.model.dto.CustomerRequest;
import com.bank.customer.mapper.CustomerMapper;
import com.bank.customer.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import com.bank.customer.domain.model.dto.Customer;

@RestController
@RequiredArgsConstructor
public class CustomerController implements CustomerApi {

    private final CustomerService service;
    private final CustomerMapper mapper;


    @Override
    public Mono<ResponseEntity<Customer>> createCustomer(Mono<CustomerRequest> customerRequest, ServerWebExchange exchange) throws Exception {
        return customerRequest
                 .map(mapper::toDomainRequest)
                .flatMap(service::save)
                .map(mapper::toDto)
                .map(responseDto -> ResponseEntity
                        .status(HttpStatus.CREATED)
                        .body(responseDto))
                .onErrorResume(e -> Mono.error(e));
    }

    @Override
    public Mono<ResponseEntity<Void>> deleteCustomer(Integer id, ServerWebExchange exchange) throws Exception {
        return service.delete(Long.valueOf(id))
                .then(Mono.just(ResponseEntity.noContent().<Void>build()));
    }

    @Override
    public Mono<ResponseEntity<Flux<Customer>>> getAllCustomers(ServerWebExchange exchange) throws Exception {
        Flux<Customer> flux = service.getAll()
                .map( mapper::toDto);
        return Mono.just(ResponseEntity.ok(flux));
    }

    @Override
    public Mono<ResponseEntity<Customer>> getCustomerById(Integer id, ServerWebExchange exchange) throws Exception {
        return service.getById(Long.valueOf(id))
                .map(mapper::toDto)
                .map(ResponseEntity::ok);
    }

    @Override
    public Mono<ResponseEntity<Customer>> updateCustomer(Integer id, Mono<CustomerRequest> customerRequest, ServerWebExchange exchange) throws Exception {
        return customerRequest
                .map(mapper::toDomainRequest)
                .flatMap(customerDomain -> service.update(Long.valueOf(id), customerDomain))
                .map(mapper::toDto)
                .map(ResponseEntity::ok);
    }
}
