package com.bank.account.service;

import com.bank.account.domain.model.CustomerEvent;
import com.bank.account.infrastructure.persistence.entity.ValidatedCustomerEntity;
import com.bank.account.infrastructure.persistence.repository.ValidatedCustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class SyncCustomerUseCase {

    private final ValidatedCustomerRepository repository;

    public Mono<Void> sync(CustomerEvent event) {
        if ("CREATE".equals(event.getAction())) {
            // Guardamos en la tabla local de R2DBC
            ValidatedCustomerEntity entity = new ValidatedCustomerEntity(event.getCustomerId());
            return repository.save(entity).then();
        } else if ("DELETE".equals(event.getAction())) {
            return repository.deleteById(event.getCustomerId());
        }
        return Mono.empty();
    }
}
