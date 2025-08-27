plugins {
	java
	id("org.springframework.boot") version "3.5.4"
	id("io.spring.dependency-management") version "1.1.7"
    // 컴파일 단에서 Null 체킹
    id("com.github.spotbugs") version "6.2.4"
}

group = "com.april2nd"
version = "0.0.1-SNAPSHOT"

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(21)
	}
}

configurations {
	compileOnly {
		extendsFrom(configurations.annotationProcessor.get())
	}
}

repositories {
	mavenCentral()
}

dependencies {
    // --- Application / Spring Core ---
    implementation("org.springframework.boot:spring-boot-starter-validation")

    // --- Web Layer ---
    implementation("org.springframework.boot:spring-boot-starter-web")

    // --- Security Layer ---
    implementation("org.springframework.security:spring-security-core")

    // --- Data / Persistence ---
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")

    // --- Database Drivers (Runtime Only) ---
    runtimeOnly("com.h2database:h2")
    runtimeOnly("com.mysql:mysql-connector-j")

    // --- Dev Tools ---
    developmentOnly("org.springframework.boot:spring-boot-docker-compose")

    // --- Lombok (Build-time Only) ---
    compileOnly("org.projectlombok:lombok")
    annotationProcessor("org.projectlombok:lombok")
    testCompileOnly("org.projectlombok:lombok")
    testAnnotationProcessor("org.projectlombok:lombok")

    // --- Testing ---
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.junit-pioneer:junit-pioneer:2.3.0")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.withType<Test> {
	useJUnitPlatform()
}

spotbugs {
    excludeFilter.set(file("${projectDir}/spotbugs-exclude-filter.xml"))
}
