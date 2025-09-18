plugins {
    id("movu.base.ui")
}

android {
    namespace = "com.example.ui"
}

dependencies {
    implementation(projects.home.domain)
    implementation(projects.coreUi)
    implementation(libs.androidx.compose.ui.lint)
    implementation(libs.androidx.material.icons.extended)
}