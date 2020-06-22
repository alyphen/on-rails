import java.io.FileInputStream
import java.util.*

plugins {
    kotlin("js") version "1.3.72"
}

group = "uk.co.renbinden"
version = "1.0.0-SNAPSHOT"

var githubUsername = ""
var githubToken = ""
val githubPropertiesFile = File(project.projectDir, "github.properties")
if (githubPropertiesFile.exists()) {
    val githubProperties = Properties()
    githubProperties.load(FileInputStream(githubPropertiesFile))
    githubUsername = githubProperties["github-username"] as String
    githubToken = githubProperties["github-token"] as String
}

repositories {
    mavenCentral()
    maven {
        url = uri("https://maven.pkg.github.com/alyphen/ilse")
        credentials {
            username = githubUsername
            password = githubToken
        }
    }
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib-js")
    implementation(group = "uk.co.renbinden", name = "ilse", version = "1.4.6")
    testCompile(group = "junit", name = "junit", version = "4.12")
    testImplementation("org.jetbrains.kotlin:kotlin-test-js")
}

kotlin.target.browser { }