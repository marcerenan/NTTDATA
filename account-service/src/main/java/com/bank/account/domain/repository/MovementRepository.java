package com.bank.account.domain.repository;

import com.bank.account.domain.model.Movement;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

public interface MovementRepository {
    Flux<Movement> findAll();

    Mono<Movement> save(Movement account);

    Mono<Void> deleteById(Integer id);

    Mono<Movement> findById(Integer id);

    Mono<Movement> update(Integer id, Movement account);

    Flux<Movement> findByAccountIdAndDates(
            Integer accountId,
            LocalDateTime startDate,
            LocalDateTime endDate);
}
