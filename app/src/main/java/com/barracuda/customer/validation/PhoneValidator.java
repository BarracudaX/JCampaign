package com.barracuda.customer.validation;

import com.barracuda.customer.domain.Phone;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class PhoneValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return Phone.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {

    }
}
