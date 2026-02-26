package com.bank.account.infrastructure.persistence.repository;

import com.bank.account.domain.exception.ObjectNotFoundException;
import com.bank.account.domain.model.Movement;
import com.bank.account.domain.repository.MovementRepository;
import com.bank.account.infrastructure.persistence.entity.MovementEntity;
import com.bank.account.mapper.MovementMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
@RequiredArgsConstructor
public class MovementRepositoryImpl implements MovementRepository {

    private final MovementR2Repository repository;
    private final MovementMapper mapper;

    @Override
    public Flux<Movement> findAll() {
        return repository.findAll().map(mapper::toDomain);
    }

    @Override
    public Mono<Movement> save(Movement movement) {
        MovementEntity movementEntity = mapper.toEntity(movement);
        return repository.save( movementEntity ).map(mapper::toDomain);
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
    public Mono<Movement> findById(Integer id) {
        return repository.findById(id).map(mapper::toDomain);
    }

    @Override
    public Mono<Movement> update(Integer id, Movement movement) {
        return repository.existsById(id)
                .flatMap(exists -> {
                    if (!exists) {
                        return Mono.error(new ObjectNotFoundException("No se puede eliminar: ID " + id + " no encontrado"));
                    }
                    MovementEntity movementEntity = mapper.toEntity(movement);
                    movementEntity.setId( id );
                    return repository.save(movementEntity).map(mapper::toDomain);
                });
    }
}
