package com.bank.account.service;

import com.bank.account.domain.exception.ObjectNotFoundException;
import com.bank.account.domain.model.Movement;
import com.bank.account.domain.repository.AccountRepository;
import com.bank.account.domain.repository.MovementRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class MovementService {

    private final MovementRepository repository;
    private final AccountRepository accountRepository;

    public Flux<Movement> getAll(){
        return repository.findAll();
    }

    @Transactional
    public Mono<Movement> save(Movement movement){
        return accountRepository.findById( movement.getAccountId() )
                .switchIfEmpty(Mono.error(new RuntimeException("La cuenta no existe")))
                .flatMap(account -> {
                    double amount = movement.getAmount();
                    boolean isDeposit = "DEPOSIT".equalsIgnoreCase(movement.getMovementType());
                    double newBalance = isDeposit
                            ? account.getInitialBalance() + amount
                            : account.getInitialBalance() - amount;
                    if (newBalance < 0) {
                        return Mono.error(new RuntimeException("Saldo insuficiente: " + account.getInitialBalance()));
                    }

                    account.setInitialBalance(newBalance);
                    movement.setBalance(newBalance);
                    return accountRepository.save(account)
                            .then(repository.save(movement));
                });

    }

    public Mono<Void> delete(Integer id){
        return repository.deleteById(id);
    }

    public Mono<Movement> getById(Integer id) {
        return repository.findById(id)
                .switchIfEmpty(Mono.error(new ObjectNotFoundException("Movement not found with ID: " + id)));
    }

    public Mono<Movement> update(Integer id, Movement movementDetails) {
        return repository.findById(id)
                .switchIfEmpty(Mono.error(new ObjectNotFoundException("No se puede actualizar: Movement no encontrado con ID: " + id)))
                .flatMap(existingMovement -> {
                    movementDetails.setId( existingMovement.getId() );
                    return repository.save(movementDetails);
                });
    }
}
