plugins {
    id("movu.base.data")
}

android {
    namespace = "com.example.core_data"
}

dependencies {
    implementation(libs.firebase.auth)
    implementation(libs.firebase.firestore.ktx)

    // Add dependency on details domain module to access Movie and Tv models
    implementation(project(":details:domain"))

    // Add dependency injection
    implementation(libs.koin.android)

    // Add coroutines
    implementation(libs.kotlinx.coroutines.android)
}
