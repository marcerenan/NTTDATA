package com.bank.account.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class AccountReport extends Account{

    private List<Movement> movements;

    public AccountReport( String accountNumber,
                          String accountType,
                          Double initialBalance,
                          List<Movement> movements){
        this.setAccountNumber(accountNumber);
        this.setAccountType(accountType);
        this.setInitialBalance(initialBalance);
        this.movements=movements;

    }
}
