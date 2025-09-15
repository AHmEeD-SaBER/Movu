plugins {
    id("movu.base.data")
}

android {
    namespace = "com.movu.authentication.data"
}

dependencies {
    // Firebase Firestore for storing user data
    implementation(platform(libs.firebase.bom))
    implementation(libs.google.firebase.firestore.ktx)

    // Domain module dependency
    implementation(project(":authentication:domain"))

    // User preferences module for auth state
    implementation(project(":user-preferences"))

    // Coroutines
    implementation(libs.kotlinx.coroutines.android)
    implementation(libs.firebase.database.ktx)
    implementation(libs.firebase.firestore.ktx)
}