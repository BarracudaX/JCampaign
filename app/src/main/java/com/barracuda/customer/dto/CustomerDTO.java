package com.barracuda.customer.dto;

import com.barracuda.customer.domain.Email;
import com.barracuda.customer.domain.Phone;

public record CustomerDTO(long id, String firstName, String lastName, Email email, Phone phone) {
}
