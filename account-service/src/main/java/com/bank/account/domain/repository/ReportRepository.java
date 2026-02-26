package com.bank.account.domain.repository;

import com.bank.account.domain.model.AccountReport;
import reactor.core.publisher.Flux;

public interface ReportRepository {
    Flux<AccountReport> findByClientId(Long clientId);
}
