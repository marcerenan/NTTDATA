package com.bank.customer.domain.model;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Customer extends Person{

    private Long id;

    private String password;

    private boolean status;
}
