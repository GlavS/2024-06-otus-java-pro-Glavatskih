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
    implementation("ch.qos.logback:logback-classic:1.5.6")
}

sonarLint{
    ignoredPaths.add("Demo.java")
}

