plugins {
    id("java")
    id("org.graalvm.buildtools.native") version "0.11.0"
}

group = "sap.sustainability"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("info.picocli:picocli:4.7.5")
    annotationProcessor("info.picocli:picocli-codegen:4.7.5")
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

graalvmNative {
    binaries {
        named("main") {
            imageName.set("co2-calculator")
            mainClass.set("sap.sustainability.Main")
        }
    }
}


tasks.test {
    useJUnitPlatform()
}