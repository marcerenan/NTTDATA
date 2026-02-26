package com.bank.account.mapper;

import com.bank.account.domain.model.Account;
import com.bank.account.infrastructure.persistence.entity.AccountEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AccountMapper {

    public AccountEntity toEntity (Account c);

    public Account toDomain(AccountEntity e);

    public com.bank.account.domain.model.dto.Account toDto(Account e);

    public Account toDomainRequest(com.bank.account.domain.model.dto.AccountRequest e);
}
