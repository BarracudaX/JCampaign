package com.barracuda.jcampaign;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.modulith.core.ApplicationModules;
import org.springframework.test.context.ActiveProfiles;

@Import(TestcontainersConfiguration.class)
@ActiveProfiles("test")
@SpringBootTest
class JCampaignApplicationTest {

    @Test
    void contextLoads() {
    }

    @Test
    void verify() {
        ApplicationModules.of(JCampaignApplication.class).verify();
    }
}
