plugins {
    id("movu.base.data")
}

android {
    namespace = "com.movu.authentication.data"
}

dependencies {
    // Domain module dependency
    implementation(project(":authentication:domain"))

    // Coroutines
    implementation(libs.kotlinx.coroutines.android)

    // Firebase dependencies (explicit override)
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.auth)
    implementation(libs.firebase.firestore.ktx)
}
