plugins {
    id("java")
    id("maven-publish")
}

group = findProperty("group") ?: "llvm4j"
version = findProperty("version") ?: "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    // your deps here
}

tasks.test {
    failOnNoDiscoveredTests = false
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(24))
    }
}

publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            from(components["java"])

            groupId = project.group.toString()
            artifactId = "llvm4j"
            version = project.version.toString()
        }
    }
}
