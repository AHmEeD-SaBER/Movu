plugins {
    id("movu.base.ui")
}

android {
    namespace = "com.example.core_ui"
}

dependencies {
    implementation(projects.coreDomain)
    implementation(libs.androidx.compose.ui.lint)
    implementation(libs.androidx.material.icons.extended)
    implementation(libs.kotlinx.serialization.json)
}