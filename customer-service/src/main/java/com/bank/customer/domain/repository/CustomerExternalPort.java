package com.bank.customer.domain.repository;

import com.bank.customer.domain.model.CustomerEvent;
import reactor.core.publisher.Mono;

public interface CustomerExternalPort {
    Mono<Void> notifyCustomerChange(CustomerEvent event);
    Mono<Void> notifyCustomerChange(String action, Long customerId);
}
