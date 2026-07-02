package com.barracuda.app.customer.dto;

import com.barracuda.app.customer.domain.Email;
import com.barracuda.app.customer.domain.Phone;

public record CustomerDTO(long id, String firstName, String lastName, Email email, Phone phone) {
}
