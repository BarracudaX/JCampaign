package com.barracuda.jcampaign;

import org.springframework.boot.SpringApplication;

public class TestJCampaignApplication {

    public static void main(String[] args) {
        SpringApplication.from(JCampaignApplication::main).with(TestcontainersConfiguration.class).run(args);
    }

}
