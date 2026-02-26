package com.bank.account.infrastructure.persistence.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Table("movements")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MovementEntity {
    @Id
    private Integer id;

    @Column("account_id")
    private Integer accountId;

    @Column("movement_date")
    private LocalDateTime movementDate;

    @Column("movement_type")
    private String movementType;

    @Column("amount")
    private Double amount;

    @Column("balance")
    private Double balance;
}
