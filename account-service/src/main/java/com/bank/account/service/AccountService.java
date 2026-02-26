package com.bank.account.service;

import com.bank.account.domain.exception.ObjectNotFoundException;
import com.bank.account.domain.model.Account;
import com.bank.account.domain.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository repository;

    public Flux<Account> getAll(){
        return repository.findAll();
    }

    public Mono<Account> save(Account account){
        return repository.save(account);
    }

    public Mono<Void> delete(Integer id){
        return repository.deleteById(id);
    }

    public Mono<Account> getById(Integer id) {
        return repository.findById(id)
                .switchIfEmpty(Mono.error(new ObjectNotFoundException("Account not found with ID: " + id)));
    }

    public Mono<Account> update(Integer id, Account accountDetails) {
        return repository.findById(id)
                .switchIfEmpty(Mono.error(new ObjectNotFoundException("No se puede actualizar: Account no encontrado con ID: " + id)))
                .flatMap(existingAccount -> {
                    accountDetails.setId( existingAccount.getId() );
                    return repository.save(accountDetails);
                });
    }
}
