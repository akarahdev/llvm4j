plugins { id("java") }

group = "llvm4j"

version = "1.0-SNAPSHOT"

repositories { mavenCentral() }

dependencies {

}

tasks.test {
	failOnNoDiscoveredTests = false
}

java { toolchain { languageVersion = JavaLanguageVersion.of(24) } }
