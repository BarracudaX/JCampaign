plugins {
    java
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
