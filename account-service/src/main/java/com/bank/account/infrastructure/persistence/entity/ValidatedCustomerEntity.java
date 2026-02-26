package com.bank.account.infrastructure.persistence.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("validated_customers")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ValidatedCustomerEntity {
    @Id
    private Long id;
}
