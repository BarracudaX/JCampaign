package com.barracuda.customer.dto;

import com.barracuda.customer.domain.Email;
import com.barracuda.customer.domain.Phone;

public record CreateCustomerForm(String firstName, String lastName, Email email, Phone phone) {
}
