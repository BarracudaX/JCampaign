package com.barracuda.jcampaign.customer.dto;

import com.barracuda.jcampaign.customer.domain.Email;
import com.barracuda.jcampaign.customer.domain.Phone;

public record CustomerDTO(long id, String firstName, String lastName, Email email, Phone phone) {
}
