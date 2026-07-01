package com.barracuda.jcampaign.customer;

import com.barracuda.jcampaign.TestcontainersConfiguration;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.Import;
import org.springframework.modulith.test.ApplicationModuleTest;
import org.springframework.test.context.ActiveProfiles;

@Import(TestcontainersConfiguration.class)
@ActiveProfiles("test")
@ApplicationModuleTest
public class CustomerModuleTest {

    @Test
    void contextLoad() {

    }
}
