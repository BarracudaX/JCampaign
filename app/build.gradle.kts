plugins {
    id("java")
    id("org.springframework.boot") version "4.0.6"
    id("io.spring.dependency-management") version "1.1.7"
    id("io.freefair.lombok") version "9.5.0"
    id("com.github.spotbugs") version "6.5.9"
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(26)
    }
}

group = "com.barracuda"
version = "0.0.1-SNAPSHOT"

dependencyManagement{
    imports {
        mavenBom ("org.springframework.modulith:spring-modulith-bom:2.1.0")
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

    implementation ("org.springframework.modulith:spring-modulith-starter-core")
    implementation ("org.springframework.modulith:spring-modulith-starter-jdbc")
    implementation ("org.springframework.modulith:spring-modulith-events-api")


    implementation("org.mapstruct:mapstruct:1.6.3")
    implementation("commons-validator:commons-validator:1.10.1")
    implementation("com.googlecode.libphonenumber:libphonenumber:9.0.31")
    implementation("org.apache.fory:fory-core:1.3.0")

    annotationProcessor("org.mapstruct:mapstruct-processor:1.6.3")
    spotbugsPlugins("com.h3xstream.findsecbugs:findsecbugs-plugin:1.14.0")


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

spotbugs{
    ignoreFailures = true
}

tasks.spotbugsMain {
    reports.create("html") {
        required = true
        outputLocation = file("${project.layout.buildDirectory.get()}/reports/spotbugs.html")
        setStylesheet("fancy-hist.xsl")
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
    maxHeapSize = "4g"
    jvmArgs = listOf("--enable-preview","--add-opens=java.base/java.lang.invoke=ALL-UNNAMED")
}

tasks.withType<JavaCompile>(){
    this.options.compilerArgs.add("--enable-preview")
}
