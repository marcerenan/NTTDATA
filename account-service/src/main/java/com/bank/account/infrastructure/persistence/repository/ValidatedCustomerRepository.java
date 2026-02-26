package com.bank.account.infrastructure.persistence.repository;

import com.bank.account.infrastructure.persistence.entity.ValidatedCustomerEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
@RequiredArgsConstructor
@Slf4j
public class ValidatedCustomerRepository {

    private final ValidaterCustomerR2Repository repository;

    public Mono<Void> save(ValidatedCustomerEntity validatedCustomerEntity) {
        log.info("Se agrego un customer:" + validatedCustomerEntity.getId() );
        return repository.save( validatedCustomerEntity ).then();
    }

    public Mono<Void> deleteById(Long id) {
        log.info("Se borro un customer:" + id );
        return repository.deleteById( id.intValue() ).then();
    }
}
