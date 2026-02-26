package com.bank.account.infrastructure.persistence.repository;

import com.bank.account.domain.exception.ObjectNotFoundException;
import com.bank.account.domain.model.Account;
import com.bank.account.domain.repository.AccountRepository;
import com.bank.account.infrastructure.persistence.entity.AccountEntity;
import com.bank.account.mapper.AccountMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
@RequiredArgsConstructor
public class AccountRepositoryImpl implements AccountRepository {

    private final AccountR2Repository repository;
    private final AccountMapper mapper;

    @Override
    public Flux<Account> findAll() {
        return repository.findAll().map(mapper::toDomain);
    }

    @Override
    public Mono<Account> save(Account account) {
        AccountEntity accountEntity = mapper.toEntity(account);
        return repository.save( accountEntity ).map(mapper::toDomain);
    }

    @Override
    public Mono<Void> deleteById(Integer id) {
        return repository.existsById(id)
                .flatMap(exists -> {
                    if (!exists) {
                        return Mono.error(new ObjectNotFoundException("No se puede eliminar: ID " + id + " no encontrado"));
                    }
                    return repository.deleteById(id);
                });
    }

    @Override
    public Mono<Account> findById(Integer id) {
        return repository.findById(id).map(mapper::toDomain);
    }

    @Override
    public Mono<Account> update(Integer id, Account account) {
        return repository.existsById(id)
                .flatMap(exists -> {
                    if (!exists) {
                        return Mono.error(new ObjectNotFoundException("No se puede eliminar: ID " + id + " no encontrado"));
                    }
                    AccountEntity accountEntity = mapper.toEntity(account);
                    accountEntity.setId( id );
                    return repository.save(accountEntity).map(mapper::toDomain);
                });
    }

    @Override
    public Flux<Account> findByCustomerId(Integer customerId) {

        return repository
                .findByCustomerId(customerId)
                .map(mapper::toDomain);

    }
}
