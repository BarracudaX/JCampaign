package com.barracuda.jcampaign.dto;

import com.barracuda.jcampaign.domain.Email;
import com.barracuda.jcampaign.domain.Phone;

public record CustomerDTO(long id, String firstName, String lastName, Email email, Phone phone) {
}
