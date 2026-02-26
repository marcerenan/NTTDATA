package com.bank.account.infrastructure.persistence.repository;

import com.bank.account.infrastructure.persistence.entity.AccountEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountR2Repository extends ReactiveCrudRepository<AccountEntity, Integer> {
}
