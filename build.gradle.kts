import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
val ktor_version: String by project
val kotlin_version: String by project
val logback_version: String by project
val kmongo_version: String by project
val commons_codec_version: String by project

plugins {
    application
    kotlin("jvm") version "2.1.10"
    id("io.ktor.plugin") version "2.2.3"
    id("org.jetbrains.kotlin.plugin.serialization") version "1.8.10"
    id("com.github.johnrengelman.shadow") version "8.1.1"

}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(21))  // Specify Java 21
    }
}

group = "app.netlify.cazterk"
version = "0.0.1"
application {
    mainClass.set("io.ktor.server.netty.EngineMain")

    val isDevelopment: Boolean = project.ext.has("development")
    applicationDefaultJvmArgs = listOf("-Dio.ktor.development=$isDevelopment")
}

repositories {

    mavenCentral()
}

val sshAntTask = configurations.create("sshAntTask")

dependencies {
    implementation("io.ktor:ktor-server-content-negotiation-jvm:$ktor_version")
    implementation("io.ktor:ktor-server-core-jvm:$ktor_version")
    implementation("io.ktor:ktor-serialization-kotlinx-json-jvm:$ktor_version")
    implementation("io.ktor:ktor-server-call-logging-jvm:$ktor_version")
    implementation("io.ktor:ktor-server-auth-jvm:$ktor_version")
    implementation("io.ktor:ktor-server-auth-jwt-jvm:$ktor_version")
    implementation("io.ktor:ktor-server-netty-jvm:$ktor_version")
    implementation("ch.qos.logback:logback-classic:$logback_version")
    implementation("io.ktor:ktor-server-config-yaml:$ktor_version")
    implementation("io.ktor:ktor-server-auth:$ktor_version")
    implementation("io.ktor:ktor-server-auth-jwt:$ktor_version")
// mongo dependencies
    implementation("org.litote.kmongo:kmongo:$kmongo_version")
    implementation("org.litote.kmongo:kmongo-coroutine:$kmongo_version")

    implementation("commons-codec:commons-codec:$commons_codec_version")
    implementation("org.litote.kmongo:kmongo-reactor:$kmongo_version")


    testImplementation("io.ktor:ktor-server-tests-jvm:$ktor_version")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit:$kotlin_version")

    sshAntTask("org.apache.ant:ant-jsch:1.10.12")

}

//tasks.register<Jar>("buildFatJar") {
//    archiveBaseName.set("meetApp-ktor-backend")
//    archiveVersion.set("")
//    archiveClassifier.set("")
//
//    manifest {
//        attributes["Main-Class"] = "io.ktor.server.netty.EngineMain"
//    }
//
//    from(sourceSets.main.get().output)
//
//    dependsOn("shadowJar")
//}

