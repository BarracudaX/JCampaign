package com.barracuda.jcampaign.customer.dto;

import com.barracuda.jcampaign.customer.domain.Email;
import com.barracuda.jcampaign.customer.domain.Phone;

public record CreateCustomerForm(String firstName, String lastName, Email email, Phone phone) {
}
