package com.bank.customer.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Customer {

    private Long id;

    private String name;

    private String gender;

    private String identification;

    private String address;

    private String phone;

    private String password;

    private boolean status;
}
