package com.barracuda.jcampaign.domain;

import jakarta.persistence.Embeddable;

@Embeddable
public record Phone(String phoneNumber) {
}
