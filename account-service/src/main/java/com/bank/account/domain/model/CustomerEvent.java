package com.bank.account.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerEvent {
    private String action; // "CREATE", "DELETE"
    private Long customerId;
    private String name;
}
