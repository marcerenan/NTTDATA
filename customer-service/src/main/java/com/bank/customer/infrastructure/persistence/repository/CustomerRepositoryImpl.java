package com.bank.customer.infrastructure.persistence.repository;

import com.bank.customer.domain.exception.CustomerNotFoundException;
import com.bank.customer.domain.model.Customer;
import com.bank.customer.domain.repository.CustomerRepository;
import com.bank.customer.infrastructure.persistence.entity.CustomerEntity;
import com.bank.customer.mapper.CustomerMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;


@Repository
@RequiredArgsConstructor
public class CustomerRepositoryImpl implements CustomerRepository {

    private final CustomerJpaRepository jpaRepository;
    private final CustomerMapper mapper;

    @Override
    public Flux<Customer> findAll() {
        return Flux.fromIterable(jpaRepository.findAll())
                .map(mapper::toDomain)
                .subscribeOn(Schedulers.boundedElastic());
    }

    @Override
    public Mono<Customer> findById(Long id) {
        return Mono.fromCallable(() -> jpaRepository.findById(id))
                .subscribeOn(Schedulers.boundedElastic())
                .flatMap(optional -> optional.map(entity -> Mono.just(mapper.toDomain(entity)))
                        .orElseGet(Mono::empty));
    }

    @Override
    public Mono<Customer> update(Long id, Customer customer) {
        return Mono.fromCallable(() -> {
                    CustomerEntity entity = mapper.toEntity(customer);
                    return jpaRepository.save(entity);
                })
                .subscribeOn(Schedulers.boundedElastic())
                .map(mapper::toDomain);
    }

    @Override
    public Mono<Customer> save(Customer customer) {
        return Mono.fromCallable(() -> {
                    CustomerEntity entity = mapper.toEntity(customer);
                    return jpaRepository.save(entity);
                })
                .subscribeOn(Schedulers.boundedElastic())
                .map(mapper::toDomain);
    }

    @Override
    public Mono<Void> deleteById(Long id) {
        return Mono.fromRunnable(() -> {
                    if (!jpaRepository.existsById(id)) {
                        throw new CustomerNotFoundException("No se puede eliminar: Cliente no encontrado con ID: " + id);
                    }
                    jpaRepository.deleteById(id);
                })
                .subscribeOn(Schedulers.boundedElastic())
                .then();
    }
}
