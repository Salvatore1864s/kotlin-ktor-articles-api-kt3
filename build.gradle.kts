
plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(ktorLibs.plugins.ktor)
    alias(libs.plugins.kotlin.serialization)
}

group = "com.example"
version = "1.0.0-SNAPSHOT"

application {
    mainClass = "com.example.MainKt"
}

kotlin {
    jvmToolchain(21)
}

dependencies {
    // Ktor
    implementation(ktorLibs.serialization.kotlinx.json)
    implementation(ktorLibs.server.config.yaml)
    implementation(ktorLibs.server.contentNegotiation)
    implementation(ktorLibs.server.core)
    implementation(ktorLibs.server.netty)
    implementation(ktorLibs.server.resources)
    implementation(ktorLibs.server.auth)
    implementation(ktorLibs.server.auth.jwt)

    // КТ3: документация, обработка ошибок, логирование
    implementation("io.ktor:ktor-server-swagger:3.6.0")
    implementation("io.ktor:ktor-server-status-pages:3.6.0")
    implementation("io.ktor:ktor-server-call-logging:3.6.0")

    // PostgreSQL и Exposed
    implementation(libs.exposed.core)
    implementation(libs.exposed.jdbc)
    implementation(libs.postgresql)
    implementation(libs.hikari)

    // Пароли и JWT
    implementation(libs.jbcrypt)
    implementation(libs.java.jwt)

    // Логирование
    implementation(libs.logback.classic)

    // Тестирование
    testImplementation(kotlin("test"))
    testImplementation(ktorLibs.server.testHost)
}
