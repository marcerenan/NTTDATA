package com.bank.account.api.controller;

import com.bank.account.api.AccountApi;
import com.bank.account.domain.model.dto.Account;
import com.bank.account.domain.model.dto.AccountRequest;
import com.bank.account.mapper.AccountMapper;
import com.bank.account.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
public class AccountController implements AccountApi {

    private final AccountService service;
    private final AccountMapper mapper;



    @Override
    public Mono<ResponseEntity<Account>> createAccount(Mono<AccountRequest> accountRequest, ServerWebExchange exchange) throws Exception {
        return accountRequest
                .map(mapper::toDomainRequest)
                .flatMap(service::save)
                .map(mapper::toDto)
                .map(responseDto -> ResponseEntity
                        .status(HttpStatus.CREATED)
                        .body(responseDto))
                .onErrorResume(e -> Mono.error(e));
    }

    @Override
    public Mono<ResponseEntity<Void>> deleteAccount(Integer id, ServerWebExchange exchange) throws Exception {
        return service.delete(id)
                .then(Mono.just(ResponseEntity.noContent().<Void>build()));
    }

    @Override
    public Mono<ResponseEntity<Account>> getAccountById(Integer id, ServerWebExchange exchange) throws Exception {
        return service.getById(id)
                .map(mapper::toDto)
                .map(ResponseEntity::ok);
    }

    @Override
    public Mono<ResponseEntity<Flux<Account>>> getAllAccounts(ServerWebExchange exchange) throws Exception {
        Flux<Account> flux = service.getAll().map(mapper::toDto);
        return Mono.just(ResponseEntity.ok(flux));
    }

    @Override
    public Mono<ResponseEntity<Account>> updateAccount(Integer id, Mono<AccountRequest> accountRequest, ServerWebExchange exchange) throws Exception {
        return accountRequest
                .map(mapper::toDomainRequest)
                .flatMap(accountDomain -> service.update(id, accountDomain))
                .map(mapper::toDto)
                .map(ResponseEntity::ok);
    }
}
