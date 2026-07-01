package com.barracuda.jcampaign;

import io.temporal.client.WorkflowClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.modulith.ApplicationModule;
import org.springframework.modulith.Modulithic;

@Modulithic(systemName = "JCampaing")
@SpringBootApplication
public class JCampaignApplication {

    static void main(String[] args) {
        SpringApplication.run(JCampaignApplication.class, args);
    }

}
