package com.barracuda.jcampaign.domain;

import jakarta.persistence.Embeddable;

@Embeddable
public class Phone {

    String phoneNumber;

    public Phone(String number) {
        this.phoneNumber = number;
    }

    public Phone() {

    }

    public String getPhoneNumber() {
        return phoneNumber;
    }
}
