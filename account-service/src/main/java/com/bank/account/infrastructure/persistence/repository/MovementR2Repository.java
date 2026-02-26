package com.bank.account.infrastructure.persistence.repository;

import com.bank.account.infrastructure.persistence.entity.MovementEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import java.time.LocalDateTime;

@Repository
public interface MovementR2Repository extends ReactiveCrudRepository<MovementEntity, Integer> {
    Flux<MovementEntity> findByAccountIdAndMovementDateBetween(
            Integer accountId,
            LocalDateTime startDate,
            LocalDateTime endDate
    );
}
