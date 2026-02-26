package com.bank.account.api.controller;

import com.bank.account.api.MovementApi;
import com.bank.account.domain.model.dto.Movement;
import com.bank.account.domain.model.dto.MovementRequest;
import com.bank.account.mapper.MovementMapper;
import com.bank.account.service.MovementService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
public class MovementController implements MovementApi {

    private final MovementService service;
    private final MovementMapper mapper;



    @Override
    public Mono<ResponseEntity<Movement>> createMovement(@Valid @RequestBody Mono<MovementRequest> movementRequest, ServerWebExchange exchange) throws Exception {
        return movementRequest
                .map(mapper::toDomainRequest)
                .flatMap(service::save)
                .map(mapper::toDto)
                .map(responseDto -> ResponseEntity
                        .status(HttpStatus.CREATED)
                        .body(responseDto))
                .onErrorResume(e -> Mono.error(e));
    }

    @Override
    public Mono<ResponseEntity<Void>> deleteMovement(Integer id, ServerWebExchange exchange) throws Exception {
        return service.delete(id)
                .then(Mono.just(ResponseEntity.noContent().<Void>build()));
    }

    @Override
    public Mono<ResponseEntity<Flux<Movement>>> getAllMovements(ServerWebExchange exchange) throws Exception {
        Flux<Movement> flux = service.getAll().map(mapper::toDto);
        return Mono.just(ResponseEntity.ok(flux));
    }

    @Override
    public Mono<ResponseEntity<Movement>> getMovementById(Integer id, ServerWebExchange exchange) throws Exception {
        return service.getById(id)
                .map(mapper::toDto)
                .map(ResponseEntity::ok);
    }

    @Override
    public Mono<ResponseEntity<Movement>> updateMovement(Integer id, Mono<MovementRequest> movementRequest, ServerWebExchange exchange) throws Exception {
        return movementRequest
                .map(mapper::toDomainRequest)
                .flatMap(movementDomain -> service.update(id, movementDomain))
                .map(mapper::toDto)
                .map(ResponseEntity::ok);
    }
}
