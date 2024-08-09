import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
    id ("com.github.johnrengelman.shadow")
}

dependencies {
    implementation ("ch.qos.logback:logback-classic")
}

tasks {
    named<ShadowJar>("shadowJar") {
        archiveBaseName.set("hw04gc")
        archiveVersion.set("0.1")
        archiveClassifier.set("")
        manifest {
            attributes(mapOf("Main-Class" to "ru.calculator.CalcDemo"))
        }
    }

    build {
        dependsOn(shadowJar)
    }
}


