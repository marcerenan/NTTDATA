package com.bank.customer.mapper;

import com.bank.customer.domain.model.Customer;
import com.bank.customer.infrastructure.persistence.entity.CustomerEntity;
import org.springframework.stereotype.Component;

@Component
public class CustomerMapper {
    public CustomerEntity toEntity(

            Customer c){

        CustomerEntity e=new CustomerEntity();

        e.setId(c.getId());

        e.setName(c.getName());

        e.setIdentification(
                c.getIdentification());

        e.setPhone(c.getPhone());

        e.setStatus(c.isStatus());

        return e;

    }

    public Customer toDomain(

            CustomerEntity e){

        return Customer.builder()

                .id(e.getId())

                .name(e.getName())

                .identification(
                        e.getIdentification())

                .phone(e.getPhone())

                .status(e.isStatus())

                .build();

    }

    public com.bank.customer.domain.model.dto.Customer toDto(

            Customer e){

        return new com.bank.customer.domain.model.dto.Customer()

                .id( e.getId().intValue())

                .name(e.getName())

                .identification(
                        e.getIdentification())

                .phone(e.getPhone())

                .status(e.isStatus());

    }
}
