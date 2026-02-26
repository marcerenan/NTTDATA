package com.bank.account.service;

import com.bank.account.domain.model.Account;
import com.bank.account.domain.model.AccountReport;
import com.bank.account.domain.repository.AccountRepository;
import com.bank.account.domain.repository.MovementRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ReportService {

    private final AccountRepository accountRepository;
    private final MovementRepository movementRepository;

    public Flux<AccountReport> getReport(Integer customerId,
                                         LocalDateTime startDate,
                                         LocalDateTime endDate){
        return accountRepository.findByCustomerId(customerId)
                .flatMap(account ->
                        movementRepository.findByAccountIdAndDates(
                                account.getId().intValue(),
                                startDate,
                                endDate
                        ).collectList().map(movements ->
                                new AccountReport(
                                        account.getAccountNumber(),
                                        account.getAccountType(),
                                        account.getInitialBalance(),
                                        movements
                                )
                        )
                );
    }
}
