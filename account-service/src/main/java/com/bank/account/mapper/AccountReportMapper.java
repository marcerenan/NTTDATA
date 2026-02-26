package com.bank.account.mapper;


import com.bank.account.domain.model.AccountReport;
import com.bank.account.domain.model.dto.AccountStatementReport;
import com.bank.account.domain.model.dto.Accounts;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Component
@RequiredArgsConstructor
public class AccountReportMapper {

    private final MovementMapper movementMapper;

    public AccountStatementReport toDto(
            List<AccountReport> accounts,
            LocalDate startDate,
            LocalDate endDate
    ){
        AccountStatementReport report =
                new AccountStatementReport();
        report.setStartDate(startDate);
        report.setEndDate(endDate);
        report.setAccounts(
                accounts.stream()
                        .map(account -> {
                            Accounts acc =
                                    new Accounts();
                            acc.setAccountNumber(
                                    account.getAccountNumber());
                            acc.setInitialBalance(
                                    BigDecimal.valueOf(account.getInitialBalance()));
                            acc.setAccountType(
                                    Accounts.AccountTypeEnum.valueOf(account.getAccountType()));
                            acc.setMovements(
                                    account.getMovements()
                                            .stream()
                                            .map(movementMapper::toDto)
                                            .toList()
                            );
                            return acc;
                        })
                        .toList()
        );
        return report;

    }
}
