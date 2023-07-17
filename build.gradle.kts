import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.openapitools.generator.gradle.plugin.tasks.GenerateTask


plugins {
    id("org.springframework.boot") version "3.1.0"
    id("io.spring.dependency-management") version "1.1.0"
    id("org.openapi.generator") version "6.6.0"
    id("org.jetbrains.gradle.plugin.idea-ext") version "1.1.7"
    kotlin("jvm") version "1.8.21"
    kotlin("plugin.spring") version "1.8.21"
    kotlin("plugin.jpa") version "1.8.21"

    id("org.barfuin.gradle.jacocolog") version "2.0.0"
    jacoco
}

group = "clipboardhealth.challenge"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_17

repositories {
    mavenCentral()
    mavenLocal()
}


extra["springCloudVersion"] = "2022.0.3"

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    testImplementation("org.projectlombok:lombok:1.18.26")
    developmentOnly("org.springframework.boot:spring-boot-devtools")
    runtimeOnly("org.postgresql:postgresql")
    implementation("org.springframework.boot:spring-boot-starter-actuator:3.1.0")
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.1.0")

    // Tests
    testImplementation("org.springframework.boot:spring-boot-starter-test:3.1.0")
    testImplementation("org.testcontainers:postgresql")
    testImplementation("org.testcontainers:junit-jupiter")
    implementation("org.springframework.cloud:spring-cloud-starter-openfeign")

    // Monitoring
    implementation("io.micrometer:micrometer-registry-prometheus:1.11.1")
    implementation("io.micrometer:micrometer-tracing-bridge-otel:1.1.2")
    implementation("io.opentelemetry:opentelemetry-exporter-zipkin:1.27.0")

    // Caching
    implementation("org.springframework.boot:spring-boot-starter-cache:3.1.0")

}
dependencyManagement {
    imports {
        mavenBom("org.springframework.cloud:spring-cloud-dependencies:${property("springCloudVersion")}")
    }
}
val generateApi by tasks.creating(GenerateTask::class) {
    inputs.files("$rootDir/specs/openapi.yaml", "$rootDir/.openapi-generator-ignore")
    outputs.dir("$rootDir/build/api")
    group = "api"
    generatorName.set("kotlin-spring")
    inputSpec.set("$rootDir/specs/openapi.yaml")

    outputDir.set("$rootDir/build/api")
    apiPackage.set("clipboardhealth.challenge.nursify.api")
    invokerPackage.set("clipboardhealth.challenge.nursify")
    modelPackage.set("clipboardhealth.challenge.nursify.api.model")
    generateApiTests.set(false)
    ignoreFileOverride.set("$rootDir/.openapi-generator-ignore")
    configOptions.set(
        mapOf(
            "serviceInterface" to "true",
            "packageName" to "clipboardhealth.challenge.nursify.api",
            "enumPropertyNaming" to "UPPERCASE",
            "useTags" to "true",
            "dateLibrary" to "java8",
            "useSpringBoot3" to "true"
        )
    )
    doFirst {
        delete("$rootDir/build/api")
    }
}

tasks.named("compileKotlin") {
    dependsOn(generateApi)
}

tasks.named("openApiGenerate") {
    enabled = false
}

kotlin {
    sourceSets {
        main {
            kotlin.srcDir("build/api/src/main/kotlin")
        }
    }
}



tasks {
    check {
        dependsOn(jacocoTestReport)
    }
    withType<KotlinCompile> {
        kotlinOptions {
            freeCompilerArgs = listOf("-Xjsr305=strict")
            jvmTarget = "17"
        }
    }

    withType<JacocoReport> {
        reports {
            xml.required.set(true)
            html.required.set(true)
            csv.required.set(false)
        }
        afterEvaluate {
            classDirectories.setFrom(
                files(
                    classDirectories.files.map {
                        fileTree(it).apply {
                            exclude("clipboardhealth/challenge/nursify")
                        }
                    }
                )
            )
        }
    }
    withType<Test> {
        useJUnitPlatform()

        testLogging {
            events = setOf(
                org.gradle.api.tasks.testing.logging.TestLogEvent.STARTED,
                org.gradle.api.tasks.testing.logging.TestLogEvent.PASSED,
                org.gradle.api.tasks.testing.logging.TestLogEvent.FAILED,
                org.gradle.api.tasks.testing.logging.TestLogEvent.SKIPPED,
                org.gradle.api.tasks.testing.logging.TestLogEvent.STANDARD_OUT,
                org.gradle.api.tasks.testing.logging.TestLogEvent.STANDARD_ERROR
            )
            showExceptions = true
            showCauses = true
            showStackTraces = true
            exceptionFormat = org.gradle.api.tasks.testing.logging.TestExceptionFormat.FULL
        }
    }
}


