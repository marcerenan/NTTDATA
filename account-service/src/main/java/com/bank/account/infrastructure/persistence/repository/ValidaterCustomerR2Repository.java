package com.bank.account.infrastructure.persistence.repository;

import com.bank.account.infrastructure.persistence.entity.ValidatedCustomerEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ValidaterCustomerR2Repository extends ReactiveCrudRepository<ValidatedCustomerEntity, Integer> {
}
