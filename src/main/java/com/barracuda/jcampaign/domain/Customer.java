package com.barracuda.jcampaign.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.ToString;

@Entity
@Table(name = "customers")
@Getter
@ToString
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private String firstName;

    private String lastName;

    @ManyToOne
    private LoyaltyCard loyaltyCard;

    @Embedded
    private Email email;

    @Embedded
    private Phone phone;
}
