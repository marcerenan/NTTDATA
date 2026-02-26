package com.bank.account.service;

import com.bank.account.domain.exception.ObjectNotFoundException;
import com.bank.account.domain.model.Movement;
import com.bank.account.domain.repository.MovementRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class MovementService {

    private final MovementRepository repository;

    public Flux<Movement> getAll(){
        return repository.findAll();
    }

    public Mono<Movement> save(Movement movement){
        return repository.save(movement);
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
