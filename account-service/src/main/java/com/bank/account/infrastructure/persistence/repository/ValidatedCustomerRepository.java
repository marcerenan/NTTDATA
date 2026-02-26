package com.bank.account.infrastructure.persistence.repository;

import com.bank.account.domain.exception.AccountNotFoundException;
import com.bank.account.domain.model.Account;
import com.bank.account.infrastructure.persistence.entity.AccountEntity;
import com.bank.account.infrastructure.persistence.entity.ValidatedCustomerEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
@RequiredArgsConstructor
public class ValidatedCustomerRepository {

    public Mono<Void> save(ValidatedCustomerEntity validatedCustomerEntity) {
        System.out.println("Se agrego un customer:" + validatedCustomerEntity.getId() );
        return null;
    }

    public Mono<Void> deleteById(Long id) {
        System.out.println("Se borro un customer:" + id );
        return null;
    }
}
