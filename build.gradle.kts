plugins {
    kotlin("jvm") version "1.9.0"
    application
}

group = "me.fun"
version = "1.0"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.fusesource.jansi:jansi:2.4.1")
    implementation("com.github.ajalt.mordant:mordant:2.2.0")
    implementation("org.apache.commons:commons-lang3:3.12.0")
}

kotlin {
    jvmToolchain(17)
}

application {
    mainClass.set("me.blackjack.BootKt")
}

tasks.jar {
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE

    manifest {
        attributes["Main-Class"] = "me.blackjack.BootKt"
    }
    from(configurations.runtimeClasspath.get().map { if (it.isDirectory) it else zipTree(it) })
}