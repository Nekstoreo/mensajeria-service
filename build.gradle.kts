plugins {
    java
    id("org.springframework.boot") version "4.0.0"
    id("io.spring.dependency-management") version "1.1.7"
}

group = "com.pragma"
version = "0.0.1-SNAPSHOT"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

repositories {
    mavenCentral()
}

extra.apply {
    set("springdocVersion", "3.0.0")
    set("mapstructVersion", "1.6.2")
    set("jjwtVersion", "0.12.5")
    set("lombokVersion", "1.18.34")
    set("lombokMapstructBindingVersion", "0.2.0")
    set("dotenvVersion", "4.0.0")
    set("twilioVersion", "10.6.4")
}

dependencies {
    // Spring Boot Starters
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework.boot:spring-boot-starter-security")
    
    // Dotenv for environment variables
    implementation("me.paulschwarz:spring-dotenv:${property("dotenvVersion")}")
    
    // Twilio SDK
    implementation("com.twilio.sdk:twilio:${property("twilioVersion")}")
    
    // OpenAPI / Swagger
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:${property("springdocVersion")}")
    
    // MapStruct
    implementation("org.mapstruct:mapstruct:${property("mapstructVersion")}")
    
    // JWT
    implementation("io.jsonwebtoken:jjwt-api:${property("jjwtVersion")}")
    
    // Lombok
    compileOnly("org.projectlombok:lombok:${property("lombokVersion")}")
    
    // Annotation Processors
    annotationProcessor("org.mapstruct:mapstruct-processor:${property("mapstructVersion")}")
    annotationProcessor("org.projectlombok:lombok:${property("lombokVersion")}")
    annotationProcessor("org.projectlombok:lombok-mapstruct-binding:${property("lombokMapstructBindingVersion")}")
    
    // Runtime Only
    runtimeOnly("io.jsonwebtoken:jjwt-impl:${property("jjwtVersion")}")
    runtimeOnly("io.jsonwebtoken:jjwt-jackson:${property("jjwtVersion")}")
    
    // Testing
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.springframework.security:spring-security-test")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.withType<Test> {
    useJUnitPlatform()
}
