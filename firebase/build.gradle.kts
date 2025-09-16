plugins {
    id("movu.base.data")
}

android {
    namespace = "com.example.core_data"
}

dependencies {
    implementation(libs.firebase.auth)
}
