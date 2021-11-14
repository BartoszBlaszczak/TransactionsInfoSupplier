import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	id("org.springframework.boot") version "2.5.6"
	id("io.spring.dependency-management") version "1.0.11.RELEASE"
	kotlin("jvm") version "1.5.31"
	kotlin("plugin.spring") version "1.5.31"
}

group = "hex"
version = "1.0.0"
java.sourceCompatibility = JavaVersion.VERSION_11

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.springframework.boot:spring-boot-starter-security")
	implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
	
	implementation("org.litote.kmongo:kmongo-coroutine:4.3.0")
	implementation("com.github.doyaaaaaken:kotlin-csv-jvm:1.2.0")
	
	testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("io.kotest:kotest-runner-junit5-jvm:4.6.3")
	testImplementation("io.kotest.extensions:kotest-extensions-spring:1.0.0")
	testImplementation ("io.mockk:mockk:1.12.0")
}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs = listOf("-Xjsr305=strict")
		jvmTarget = "11"
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}

tasks.jar {
	manifest { attributes["Main-Class"] = "hex.transactionsinfosupplier.application.TransactionsInfoSupplierApplicationKt" }
	duplicatesStrategy = DuplicatesStrategy.INCLUDE
	from(configurations.runtimeClasspath.get().map{ if (it.isDirectory) it else zipTree(it) })
}