plugins {
    id("movu.base.ui")
}

android {
    namespace = "com.example.ui"
}

dependencies {
    implementation(projects.splash.domain)
    implementation(projects.coreUi)
    implementation(libs.androidx.splash.screen)
}