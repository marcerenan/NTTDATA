package com.bank.account.infrastructure.persistence.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table("accounts")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AccountEntity {
    @Id
    private Integer id;

    @Column("account_number")
    private String accountNumber;

    @Column("account_type")
    private String accountType;

    @Column("initial_balance")
    private Double initialBalance;

    @Column("status")
    private Boolean status;

    @Column("customer_id")
    private Integer customerId;
}
