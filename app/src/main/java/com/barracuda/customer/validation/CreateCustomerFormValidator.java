package com.barracuda.customer.validation;

import com.barracuda.customer.dto.CreateCustomerForm;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class CreateCustomerFormValidator implements Validator {

    private final PhoneValidator phoneValidator;
    private final SpringEmailValidator emailValidator;

    public CreateCustomerFormValidator(PhoneValidator phoneValidator, SpringEmailValidator emailValidator) {
        this.phoneValidator = phoneValidator;
        this.emailValidator = emailValidator;
    }


    @Override
    public boolean supports(Class<?> clazz) {
        return CreateCustomerForm.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
    }
}
