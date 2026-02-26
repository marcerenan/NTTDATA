package com.bank.account.domain.model;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class Account {
    private Long id;

    private String accountNumber;

    private String accountType;

    private Double initialBalance;

    private Boolean status;

    private Long customerId;
}
