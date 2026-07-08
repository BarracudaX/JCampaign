package com.barracuda.customer.validation;

import com.barracuda.customer.domain.Email;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class SpringEmailValidator implements Validator {

    private final EmailValidator emailValidator;

    public SpringEmailValidator(EmailValidator emailValidator) {
        this.emailValidator = emailValidator;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Email.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {

    }
}
