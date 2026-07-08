package com.barracuda;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.modulith.Modulithic;

@Modulithic(systemName = "JCampaing")
@SpringBootApplication
public class JCampaignApplication {

    static void main(String[] args) {
        SpringApplication.run(JCampaignApplication.class, args);
    }

}
