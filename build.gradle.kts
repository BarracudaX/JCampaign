plugins {
    java
    id("org.springframework.boot") version "4.0.6"
    id("io.spring.dependency-management") version "1.1.7"
    id("io.freefair.lombok") version "9.5.0"
}

dependencyManagement{
    imports {
        mavenBom ("org.springframework.modulith:spring-modulith-bom:2.1.0")
    }
}

group = "com.barracuda"
version = "0.0.1-SNAPSHOT"
description = "JCampaign"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(26)
    }
}

repositories {
    mavenCentral()
}

dependencies {

}

tasks.withType<Test> {
    useJUnitPlatform()
}
