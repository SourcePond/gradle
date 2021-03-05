plugins {
    java
}

repositories {
    mavenCentral()
}

// tag::jupiter-dependencies[]
dependencies {
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.7.1")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine")
}
// end::jupiter-dependencies[]

// tag::enabling-junit-platform[]
tasks.named<Test>("test") {
    useJUnitPlatform()
}
// end::enabling-junit-platform[]
