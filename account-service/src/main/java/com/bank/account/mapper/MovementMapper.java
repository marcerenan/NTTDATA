package com.bank.account.mapper;

import com.bank.account.domain.model.Movement;
import com.bank.account.infrastructure.persistence.entity.MovementEntity;
import org.mapstruct.Mapper;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

@Mapper(componentModel = "spring")
public interface MovementMapper {

    public MovementEntity toEntity (Movement c);

    public Movement toDomain(MovementEntity e);

    public com.bank.account.domain.model.dto.Movement toDto(Movement e);

    public Movement toDomainRequest(com.bank.account.domain.model.dto.MovementRequest e);

    default OffsetDateTime map(LocalDateTime value) {
        return value == null ? null : value.atOffset(ZoneOffset.UTC);
    }

    default LocalDateTime map(OffsetDateTime value) {
        return value == null ? null : value.toLocalDateTime();
    }
}
