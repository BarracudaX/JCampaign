package com.barracuda.jcampaign.customer.service;

import com.barracuda.jcampaign.TestcontainersConfiguration;
import com.barracuda.jcampaign.customer.domain.Email;
import com.barracuda.jcampaign.customer.domain.Phone;
import com.barracuda.jcampaign.customer.dto.CreateCustomerForm;
import com.barracuda.jcampaign.customer.repository.CustomerRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.modulith.test.ApplicationModuleTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@Import(TestcontainersConfiguration.class)
@ActiveProfiles("test")
@ApplicationModuleTest
@Transactional
public class CustomerServiceTest {

    @Autowired
    private CustomerService customerService;
    @Autowired
    private CustomerRepository customerRepository;

    private final CreateCustomerForm createCustomerForm = new CreateCustomerForm("SOME_FIRST_NAME", "SOME_LAST_NAME", new Email("some@email.com"), new Phone("SOME_PHONE"));


    @Test
    void shouldCreateNewCustomer() {
        var customer = customerService.createCustomer(createCustomerForm);

        assertThat(customer).usingRecursiveComparison().ignoringFields("id").isEqualTo(createCustomerForm);

        assertThat(customerRepository.findById(customer.id())).get().usingRecursiveComparison().isEqualTo(customer);
    }

}
