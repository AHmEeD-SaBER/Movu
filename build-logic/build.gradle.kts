plugins {
    `kotlin-dsl`
}

repositories {
    google()
    mavenCentral()
    gradlePluginPortal()
}

dependencies {
    implementation(libs.android.gradlePlugin)
    implementation(libs.kotlin.gradlePlugin)
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:${libs.versions.kotlin.get()}")
    // Use the correct KSP dependency with version from catalog
    implementation("com.google.devtools.ksp:symbol-processing-gradle-plugin:${libs.versions.ksp.get()}")
}

gradlePlugin {
    plugins {
        register("baseUi") {
            id = "movu.base.ui"
            implementationClass = "BaseUiPlugin"
        }
        register("baseData") {
            id = "movu.base.data"
            implementationClass = "BaseDataPlugin"
        }
        register("baseDomain") {
            id = "movu.base.domain"
            implementationClass = "BaseDomainPlugin"
        }
    }
}