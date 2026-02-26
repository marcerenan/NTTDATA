package com.bank.account.api.controller;

import com.bank.account.api.ReportesApi;
import com.bank.account.domain.model.dto.AccountStatementReport;
import com.bank.account.mapper.AccountReportMapper;
import com.bank.account.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.time.LocalDate;

@RestController
@RequiredArgsConstructor
public class ReportController implements ReportesApi {

    private final ReportService service;
    private final AccountReportMapper mapper;

    @Override
    public Mono<ResponseEntity<AccountStatementReport>> getAccountStatementReport(Integer clientId, LocalDate startDate, LocalDate endDate, ServerWebExchange exchange) throws Exception {
        return service
                .getReport(
                        clientId,
                        startDate.atStartOfDay(),
                        endDate.atTime(23,59,59)
                )
                .collectList()
                .map(reports -> mapper.toDto(
                        reports, startDate, endDate
                ))
                .map(ResponseEntity::ok);
    }
}
