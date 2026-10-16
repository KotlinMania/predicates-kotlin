pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}

plugins { id("org.gradle.toolchains.foojay-resolver-convention") version "1.0.0" }

dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "predicates-kotlin"

val regexKotlinLocal = file("../regex-kotlin")
if (regexKotlinLocal.exists()) {
    includeBuild(regexKotlinLocal) {
        dependencySubstitution {
            substitute(module("io.github.kotlinmania:regex-kotlin"))
                .using(project(":"))
        }
    }
}
