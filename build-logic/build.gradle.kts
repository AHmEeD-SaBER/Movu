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