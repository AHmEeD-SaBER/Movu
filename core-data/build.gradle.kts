plugins {
    id("movu.base.ui")
    alias(libs.plugins.google.gms.google.services)
}

android {
    namespace = "com.example.core_data"
}

dependencies {

    implementation(libs.firebase.auth)
}