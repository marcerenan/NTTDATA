package com.bank.account.service;

import com.bank.account.domain.exception.ObjectNotFoundException;
import com.bank.account.domain.model.Account; // Asumiendo este nombre de clase
import com.bank.account.domain.model.Movement;
import com.bank.account.domain.repository.AccountRepository;
import com.bank.account.domain.repository.MovementRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MovementServiceTest {

    @Mock
    private MovementRepository movementRepository;

    @Mock
    private AccountRepository accountRepository;

    @InjectMocks
    private MovementService movementService;

    private Movement movement;
    private Account account;

    @BeforeEach
    void setUp() {
        movement = new Movement();
        movement.setId(1l);
        movement.setAccountId(100);
        movement.setAmount(50.0);
        movement.setMovementType("DEPOSIT");

        // Simulación de la cuenta relacionada
        account = new Account();
        account.setId(100l);
        account.setInitialBalance(200.0);
    }

    @Test
    @DisplayName("Debe retornar todos los movimientos")
    void getAllMovements() {
        when(movementRepository.findAll()).thenReturn(Flux.just(movement));

        Flux<Movement> result = movementService.getAll();

        StepVerifier.create(result)
                .expectNext(movement)
                .verifyComplete();
    }

    @Test
    @DisplayName("Debe guardar un depósito exitosamente y actualizar el saldo")
    void saveMovementSuccess() {
        when(accountRepository.findById(100)).thenReturn(Mono.just(account));
        when(accountRepository.save(any())).thenReturn(Mono.just(account));
        when(movementRepository.save(any())).thenReturn(Mono.just(movement));

        Mono<Movement> result = movementService.save(movement);

        StepVerifier.create(result)
                .expectNextMatches(m -> m.getBalance() == 250.0) // 200 + 50
                .verifyComplete();

        verify(accountRepository).save(argThat(a -> a.getInitialBalance() == 250.0));
    }

    @Test
    @DisplayName("Debe fallar si el saldo es insuficiente en un retiro")
    void saveMovementInsufficientBalance() {
        movement.setMovementType("WITHDRAWAL");
        movement.setAmount(300.0); // Saldo actual es 200

        when(accountRepository.findById(100)).thenReturn(Mono.just(account));

        Mono<Movement> result = movementService.save(movement);

        StepVerifier.create(result)
                .expectErrorMatches(throwable -> throwable instanceof RuntimeException
                        && throwable.getMessage().contains("Saldo insuficiente"))
                .verify();
    }

    @Test
    @DisplayName("Debe lanzar ObjectNotFoundException cuando el ID no existe")
    void getByIdNotFound() {
        when(movementRepository.findById(1)).thenReturn(Mono.empty());

        Mono<Movement> result = movementService.getById(1);

        StepVerifier.create(result)
                .expectError(ObjectNotFoundException.class)
                .verify();
    }

    @Test
    @DisplayName("Debe eliminar un movimiento")
    void deleteMovement() {
        when(movementRepository.deleteById(1)).thenReturn(Mono.empty());

        Mono<Void> result = movementService.delete(1);

        StepVerifier.create(result)
                .verifyComplete();

        verify(movementRepository, times(1)).deleteById(1);
    }

    @Test
    @DisplayName("Debe actualizar un movimiento existente")
    void updateMovement() {
        Movement newDetails = new Movement();
        newDetails.setAmount(100.0);

        when(movementRepository.findById(1)).thenReturn(Mono.just(movement));
        when(movementRepository.save(any())).thenReturn(Mono.just(newDetails));

        Mono<Movement> result = movementService.update(1, newDetails);

        StepVerifier.create(result)
                .expectNext(newDetails)
                .verifyComplete();
    }
}