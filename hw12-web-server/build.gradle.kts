val freemarker: String = "2.3.33"

dependencies {
    implementation ("org.projectlombok:lombok")
    annotationProcessor ("org.projectlombok:lombok")

    implementation("ch.qos.logback:logback-classic")
    implementation("org.hibernate.orm:hibernate-core")
    implementation("org.flywaydb:flyway-core")
    runtimeOnly("org.flywaydb:flyway-database-postgresql")

    implementation("org.eclipse.jetty.ee10:jetty-ee10-servlet")
    implementation("org.eclipse.jetty:jetty-server")
    implementation("org.eclipse.jetty:jetty-http")
    implementation("org.eclipse.jetty:jetty-io")
    implementation("org.eclipse.jetty:jetty-util")
    implementation("org.freemarker:freemarker:$freemarker")

    implementation("com.google.code.gson:gson")

    implementation("org.postgresql:postgresql")
    testImplementation("com.h2database:h2")

    testImplementation("org.junit.jupiter:junit-jupiter-engine")
    testImplementation("org.assertj:assertj-core")
    testImplementation("org.mockito:mockito-junit-jupiter")

    testImplementation("org.testcontainers:junit-jupiter")
    testImplementation("org.testcontainers:postgresql")
}