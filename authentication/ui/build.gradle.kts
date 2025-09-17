plugins {
    id("movu.base.ui")
}

android {
    namespace = "com.movu.authentication.ui"
}

dependencies {
    implementation(projects.authentication.domain)
    implementation(projects.coreUi)
    implementation(libs.androidx.compose.ui.lint)
    implementation(libs.androidx.material.icons.extended)
}