package com.bank.customer.api.controller;

import com.bank.customer.api.DefaultApi;
import com.bank.customer.domain.model.Customer;
import com.bank.customer.domain.model.dto.CustomerRequest;
import com.bank.customer.mapper.CustomerMapper;
import com.bank.customer.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@RestController
@RequestMapping("/customers")
@RequiredArgsConstructor
public class CustomerController implements DefaultApi {

    private final CustomerService service;
    private final CustomerMapper mapper;

    @GetMapping
    public Flux<Customer> getAll(){

        return Flux.fromIterable(
                service.getAll());

    }

    @Override
    public Mono<ResponseEntity<com.bank.customer.domain.model.dto.Customer>> createCustomer(Mono<CustomerRequest> customerRequest, ServerWebExchange exchange) throws Exception {
        return null;
    }

    @Override
    public Mono<ResponseEntity<Void>> deleteCustomer(Integer id, ServerWebExchange exchange) throws Exception {
        return null;
    }

    @Override
    public Mono<ResponseEntity<Flux<com.bank.customer.domain.model.dto.Customer>>> getAllCustomers(ServerWebExchange exchange) throws Exception {
        return Mono.fromCallable(service::getAll)
                .subscribeOn(Schedulers.boundedElastic())
                .map(Flux::fromIterable)
                .map(flux -> flux.map(mapper::toDto))
                .map(ResponseEntity::ok);
    }

    @Override
    public Mono<ResponseEntity<com.bank.customer.domain.model.dto.Customer>> getCustomerById(Integer id, ServerWebExchange exchange) throws Exception {
        return null;
    }

    @Override
    public Mono<ResponseEntity<com.bank.customer.domain.model.dto.Customer>> updateCustomer(Integer id, Mono<CustomerRequest> customerRequest, ServerWebExchange exchange) throws Exception {
        return null;
    }
}
