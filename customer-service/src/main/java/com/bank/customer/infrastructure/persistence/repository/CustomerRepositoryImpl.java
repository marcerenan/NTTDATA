package com.bank.customer.infrastructure.persistence.repository;

import com.bank.customer.domain.model.Customer;
import com.bank.customer.domain.repository.CustomerRepository;
import com.bank.customer.mapper.CustomerMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class CustomerRepositoryImpl implements CustomerRepository {

    private final CustomerJpaRepository jpaRepository;
    private final CustomerMapper mapper;

    @Override
    public List<Customer> findAll() {
        return jpaRepository.findAll().stream()

                .map(mapper::toDomain)

                .toList();
    }
}
