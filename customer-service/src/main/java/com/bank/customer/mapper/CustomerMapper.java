package com.bank.customer.mapper;

import com.bank.customer.domain.model.Customer;
import com.bank.customer.domain.model.dto.CustomerRequest;
import com.bank.customer.infrastructure.persistence.entity.CustomerEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CustomerMapper {

    public CustomerEntity toEntity (Customer c);

    public Customer toDomain(CustomerEntity e);

    public com.bank.customer.domain.model.dto.Customer toDto(Customer e);

    public Customer toDomainRequest(CustomerRequest e);
}
