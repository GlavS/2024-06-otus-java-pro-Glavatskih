plugins {
    id("io.freefair.lombok")
}


dependencies {
    implementation("org.springframework.boot:spring-boot-starter-data-jdbc")
    implementation("org.springframework.boot:spring-boot-starter-thymeleaf")
    implementation("org.springframework.boot:spring-boot-starter-web")
    runtimeOnly("org.flywaydb:flyway-core")
    runtimeOnly("org.postgresql:postgresql")

    implementation("org.webjars:bootstrap")
    // https://mvnrepository.com/artifact/org.webjars/jquery
    implementation("org.webjars:jquery")

    // https://mvnrepository.com/artifact/org.webjars/webjars-locator
    implementation("org.webjars:webjars-locator")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.testcontainers:junit-jupiter")
    testImplementation("org.springframework.boot:spring-boot-testcontainers")
    testImplementation("org.testcontainers:postgresql")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}
