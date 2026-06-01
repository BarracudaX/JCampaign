package com.barracuda.jcampaign.domain;

import jakarta.persistence.Embeddable;

@Embeddable
public record Email(String email) {

}
