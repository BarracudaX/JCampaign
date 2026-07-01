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
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.boot:spring-boot-starter-data-jdbc")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework.boot:spring-boot-starter-webmvc")
    implementation("org.springframework.boot:spring-boot-starter-kafka")
    implementation("org.springframework.kafka:spring-kafka")
    implementation("org.springframework.boot:spring-boot-docker-compose")
    implementation("org.springframework.boot:spring-boot-starter-liquibase")
    implementation ("io.temporal:temporal-spring-boot-starter:1.36.0")

    implementation ("org.springframework.modulith:spring-modulith-starter-core")
    implementation ("org.springframework.modulith:spring-modulith-starter-jdbc")
    implementation ("org.springframework.modulith:spring-modulith-events-api")


    implementation("org.mapstruct:mapstruct:1.6.3")
    implementation("commons-validator:commons-validator:1.10.1")
    implementation("com.googlecode.libphonenumber:libphonenumber:9.0.31")

    annotationProcessor("org.mapstruct:mapstruct-processor:1.6.3")


    runtimeOnly("org.postgresql:postgresql")
    testImplementation ("org.springframework.modulith:spring-modulith-starter-test")
    testImplementation("org.springframework.kafka:spring-kafka-test")
    testImplementation("org.springframework.boot:spring-boot-starter-data-jdbc-test")
    testImplementation("org.springframework.boot:spring-boot-starter-security-test")
    testImplementation("org.springframework.boot:spring-boot-starter-validation-test")
    testImplementation("org.springframework.boot:spring-boot-starter-webmvc-test")
    testImplementation("org.springframework.boot:spring-boot-testcontainers")
    testImplementation("org.testcontainers:testcontainers-junit-jupiter")
    testImplementation("org.testcontainers:testcontainers-postgresql")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.withType<Test> {
    useJUnitPlatform()
}
