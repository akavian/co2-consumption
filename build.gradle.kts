plugins {
    id("java")
    id("application")
    id("com.gradleup.shadow") version "9.0.1"
    id("org.graalvm.buildtools.native") version "0.11.0"
    id("checkstyle")
}

group = "sap.sustainability"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

graalvmNative {
    binaries {
        named("main") {
            imageName.set("co2-calculator")
            mainClass.set("sap.sustainability.Main")
            buildArgs.addAll(
                listOf(
                    "--no-server",
                    "--no-fallback",
                    "--initialize-at-run-time=org.slf4j,org.apache.logging.log4j",
                    "-H:IncludeResources=.*(logback\\.xml)"
                )
            )
        }
    }
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(21))
    }
}

application {
    mainClass.set("sap.sustainability.Main")
}

dependencies {
    implementation("info.picocli:picocli:4.7.5")
    implementation("com.fasterxml.jackson.core:jackson-databind:2.16.2")
    implementation("com.squareup.okhttp3:okhttp:4.12.0")
    implementation("ch.qos.logback:logback-classic:1.5.18")
    implementation("com.google.dagger:dagger:2.57")
    annotationProcessor("info.picocli:picocli-codegen:4.7.5")
    annotationProcessor("com.google.dagger:dagger-compiler:2.57")
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testImplementation("org.mockito:mockito-core:2.1.0")
    testImplementation("org.mockito:mockito-junit-jupiter:2.18.3")
}

checkstyle {
    toolVersion = "11.0.0"
    configFile = file("config/checkstyle/checkstyle.xml")
}

tasks.register("outShadowJarAll") {
    dependsOn("shadowJar", "shadowDistTar", "shadowDistZip")
}

tasks {
    shadowJar {
        manifest {
            attributes["Main-Class"] = "sap.sustainability.Main"
        }
        archiveFileName.set("co2-calculator.jar")
        archiveVersion.set("")
    }
    shadowDistTar {
        archiveBaseName.set("co2-calculator")
        archiveVersion.set("")
        archiveClassifier.set("")
    }
    shadowDistZip {
        archiveBaseName.set("co2-calculator")
        archiveVersion.set("")
        archiveClassifier.set("")
    }

    test {
        useJUnitPlatform()
        testLogging {
            events("passed", "skipped", "failed")
        }
    }
}