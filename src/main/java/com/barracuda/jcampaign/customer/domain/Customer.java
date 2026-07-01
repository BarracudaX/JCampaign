package com.barracuda.jcampaign.customer.domain;

import lombok.Getter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Embedded;
import org.springframework.data.relational.core.mapping.Table;

@Table(name = "customers")
@Getter
@ToString
public class Customer {

    @Id
    private Long id;

    private String firstName;

    private String lastName;


    @Embedded.Nullable
    private Email email;

    @Embedded.Nullable
    private Phone phone;
}
