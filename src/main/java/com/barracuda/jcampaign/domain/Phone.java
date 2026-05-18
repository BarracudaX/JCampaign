package com.barracuda.jcampaign.domain;

import jakarta.persistence.Embeddable;

@Embeddable
public class Phone {

    String number;

    public Phone(String number) {
        this.number = number;
    }

    public Phone() {

    }

    public String getNumber() {
        return number;
    }
}
