plugins {
    id("java")
    id("io.freefair.lombok") version "8.7.1"
    application
}

application{
    mainClass="ru.otus.Demo"
}

group = "ru.otus"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    implementation("ch.qos.logback:logback-classic:1.5.6")
}

tasks.test {
    useJUnitPlatform()
}
sonarLint{
    ignoredPaths.add("Demo.java")
}

