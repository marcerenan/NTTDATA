package com.bank.account.infrastructure.persistence.repository;

import com.bank.account.infrastructure.persistence.entity.AccountEntity;
import com.bank.account.infrastructure.persistence.entity.MovementEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MovementR2Repository extends ReactiveCrudRepository<MovementEntity, Integer> {
}
