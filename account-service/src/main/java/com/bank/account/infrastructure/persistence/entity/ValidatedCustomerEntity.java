package com.bank.account.infrastructure.persistence.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.Table;

@Table("validated_customers")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ValidatedCustomerEntity implements Persistable<Integer> {
    @Id
    private Long id;

    @Transient
    private boolean isNew = false;

    public ValidatedCustomerEntity(Long id) {
        this.id = id;
    }
    @Override
    public Integer getId() {
        return this.id.intValue();
    }
    @Override
    public boolean isNew() {
        return this.isNew || id == null;
    }
    public void setAsNew() {
        this.isNew = true;
    }

}
