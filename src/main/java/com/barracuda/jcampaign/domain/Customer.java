package com.barracuda.jcampaign.domain;

import jakarta.persistence.*;

@Entity
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private String firstName;

    private String lastName;

    @Embedded
    private Email email;

    @Embedded
    private Phone phone;
}
