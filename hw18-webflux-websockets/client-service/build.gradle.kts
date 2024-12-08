plugins {
    id("io.freefair.lombok")
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-websocket")
    implementation("org.springframework.boot:spring-boot-starter-webflux")
    implementation ("com.google.code.findbugs:jsr305")
    implementation("ch.qos.logback:logback-classic")

    implementation("org.webjars:webjars-locator-core")
    implementation("org.webjars:sockjs-client")
    implementation("org.webjars:stomp-websocket")
    implementation("org.webjars:bootstrap")
}


