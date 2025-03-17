plugins {
	java
	kotlin("jvm") version "1.8.21" // Adjust Kotlin version as necessary
	id("org.springframework.boot") version "3.4.1"
	id("io.spring.dependency-management") version "1.1.7"

}

group = "com.itrsa"
version = "0.0.1-SNAPSHOT"

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(17)
	}
}

repositories {
	mavenCentral()
}

dependencies {
	//implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.springframework.boot:spring-boot-starter-thymeleaf")
	implementation("org.springframework.boot:spring-boot-starter-web:3.4.1")// replace with the latest version
	developmentOnly("org.springframework.boot:spring-boot-devtools")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")
	implementation("org.mapstruct:mapstruct:1.5.3.Final")  // Or the latest version
	annotationProcessor("org.mapstruct:mapstruct-processor:1.5.3.Final")  // For the annotation processor
	implementation("org.projectlombok:lombok:1.18.28") // Use the latest version
	annotationProcessor("org.projectlombok:lombok:1.18.28")
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	runtimeOnly("mysql:mysql-connector-java:8.0.33")

	testImplementation("org.junit.jupiter:junit-jupiter-api:5.9.3")
	testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.9.3")

	testImplementation("org.mockito:mockito-core:5.5.0")
	testImplementation("org.mockito:mockito-junit-jupiter:5.5.0")

	testImplementation("org.springframework.boot:spring-boot-starter-test") {
		exclude(group = "org.junit.vintage", module = "junit-vintage-engine")
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}
tasks.withType<JavaCompile> {
	options.annotationProcessorPath = files(configurations.annotationProcessor)
}
