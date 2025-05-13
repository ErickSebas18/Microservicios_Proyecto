plugins {
    id("java")
    id("org.springframework.boot") version "3.4.3"
    id("io.spring.dependency-management") version "1.1.7"
}

group = "com.proyecto"
version = "1.0-SNAPSHOT"

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


dependencies {
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-oauth2-resource-server:3.4.3")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.boot:spring-boot-starter-web-services")
    compileOnly("org.projectlombok:lombok")
    developmentOnly("org.springframework.boot:spring-boot-devtools")
    runtimeOnly("org.postgresql:postgresql")
    annotationProcessor("org.projectlombok:lombok")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.springframework.security:spring-security-test")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
    implementation("org.keycloak:keycloak-admin-client:26.0.4")
    //implementation("org.jboss.resteasy:resteasy-jaxrs:3.15.6.Final")}
    implementation("org.keycloak:keycloak-spring-boot-starter:22.0.5") {
        exclude(group = "org.jboss.resteasy")
    }
    implementation("org.springframework.cloud:spring-cloud-starter-consul-discovery:4.2.0")
    implementation("io.vertx:vertx-consul-client:4.5.13")
    implementation("org.springframework.boot:spring-boot-starter-actuator:3.4.3")
    implementation("org.springframework.cloud:spring-cloud-starter-openfeign:4.2.0")

}

tasks.test {
    useJUnitPlatform()
}