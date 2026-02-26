package com.bank.account.domain.repository;

import com.bank.account.domain.model.Account;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface AccountRepository {
    Flux<Account> findAll();

    Mono<Account> save(Account account);

    Mono<Void> deleteById(Integer id);

    Mono<Account> findById(Integer id);

    Mono<Account> update(Integer id, Account account);

    Flux<Account> findByCustomerId(Integer customerId);
}
