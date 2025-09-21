plugins {
    id("movu.base.ui")
}

android {
    namespace = "com.example.ui"
}

dependencies {
    implementation(projects.details.domain)
    implementation(projects.coreUi)
    implementation(libs.androidx.compose.ui.lint)
    implementation(libs.androidx.material.icons.extended)
    // Coil
    implementation(libs.coil)
}