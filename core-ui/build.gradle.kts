plugins {
    id("movu.base.ui")
}

android {
    namespace = "com.example.core_ui"
}

dependencies {
    implementation(libs.androidx.compose.ui.lint)
    implementation(libs.androidx.material.icons.extended)
}