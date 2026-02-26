package com.bank.account.service;

import com.bank.account.domain.exception.ObjectNotFoundException;
import com.bank.account.domain.model.Account;
import com.bank.account.domain.repository.AccountRepository;
import com.bank.account.infrastructure.persistence.entity.ValidatedCustomerEntity;
import com.bank.account.infrastructure.persistence.repository.ValidatedCustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AccountServiceTest {

    @Mock
    private AccountRepository repository;

    @Mock
    private ValidatedCustomerRepository valRepository;

    @InjectMocks
    private AccountService service;

    private Account account;

    @BeforeEach
    void setup() {
        account = new Account();
        account.setId(1l);
        account.setCustomerId(100l);
        account.setAccountNumber("12345");
    }

    // ======================
    // getAll()
    // ======================

    @Test
    void shouldReturnAllAccounts() {

        when(repository.findAll())
                .thenReturn(Flux.just(account));

        StepVerifier.create(service.getAll())
                .expectNext(account)
                .verifyComplete();

        verify(repository).findAll();
    }

    // ======================
    // save() SUCCESS
    // ======================

    @Test
    void shouldSaveAccountWhenCustomerExists() {

        ValidatedCustomerEntity customer = new ValidatedCustomerEntity();

        when(valRepository.findById(100L))
                .thenReturn(Mono.just(customer));

        when(repository.save(account))
                .thenReturn(Mono.just(account));

        StepVerifier.create(service.save(account))
                .expectNext(account)
                .verifyComplete();

        verify(repository).save(account);
    }

    // ======================
    // save() ERROR
    // ======================

    @Test
    void shouldFailSaveWhenCustomerNotExists() {

        when(valRepository.findById(100l))
                .thenReturn(Mono.empty());

        StepVerifier.create(service.save(account))
                .expectError(RuntimeException.class)
                .verify();
    }

    // ======================
    // delete()
    // ======================

    @Test
    void shouldDeleteAccount() {

        when(repository.deleteById(1))
                .thenReturn(Mono.empty());

        StepVerifier.create(service.delete(1))
                .verifyComplete();

        verify(repository).deleteById(1);
    }

    // ======================
    // getById SUCCESS
    // ======================

    @Test
    void shouldReturnAccountById() {

        when(repository.findById(1))
                .thenReturn(Mono.just(account));

        StepVerifier.create(service.getById(1))
                .expectNext(account)
                .verifyComplete();
    }

    // ======================
    // getById ERROR
    // ======================

    @Test
    void shouldFailWhenAccountNotFound() {

        when(repository.findById(1))
                .thenReturn(Mono.empty());

        StepVerifier.create(service.getById(1))
                .expectError(ObjectNotFoundException.class)
                .verify();
    }

    // ======================
    // update SUCCESS
    // ======================

    @Test
    void shouldUpdateAccount() {

        Account updated = new Account();
        updated.setCustomerId(100l);

        when(repository.findById(1))
                .thenReturn(Mono.just(account));

        when(repository.save(updated))
                .thenReturn(Mono.just(updated));

        StepVerifier.create(service.update(1, updated))
                .expectNext(updated)
                .verifyComplete();
    }

    // ======================
    // update ERROR
    // ======================

    @Test
    void shouldFailUpdateWhenNotFound() {

        when(repository.findById(1))
                .thenReturn(Mono.empty());

        StepVerifier.create(service.update(1, account))
                .expectError(ObjectNotFoundException.class)
                .verify();
    }

}