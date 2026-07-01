package com.barracuda.jcampaign.customer.domain;

import lombok.Getter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Embedded;
import org.springframework.data.relational.core.mapping.Table;

@Table(name = "customers")
public record Customer(
        @Id
        Long id,

        String firstName,

        String lastName,

        @Embedded.Nullable Email email,

        @Embedded.Nullable Phone phone
) {
}
