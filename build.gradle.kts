plugins {
    java
    application
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(26))
    }
}

application {
    mainClass.set("eu.veldsoft.aura.Main")
}

tasks.test {
    useJUnitPlatform()
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("io.jenetics:jenetics:9.0.0")
    implementation("com.google.googlejavaformat:google-java-format:1.17.0")
}