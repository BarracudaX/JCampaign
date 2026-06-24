package com.barracuda.jcampaign.dto;

import com.barracuda.jcampaign.domain.Email;
import com.barracuda.jcampaign.domain.Phone;

public record CreateCustomerForm(String firstName, String lastName, Email email, Phone phone) {
}
