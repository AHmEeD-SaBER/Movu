plugins {
    id("movu.base.ui")
    id("kotlin-parcelize")
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "com.example.navigation"
}

dependencies {
    implementation(projects.coreUi)
    implementation(projects.coreDomain)
    implementation(projects.authentication.ui)
    implementation(projects.splash.ui)
    implementation(projects.home.ui)
    implementation(projects.details.ui)
    implementation(projects.watchlist.ui)
    implementation(projects.search.ui)
    implementation(projects.profile.ui)

    

    // Navigation dependencies
    implementation(libs.androidx.navigation.compose)
    implementation(libs.kotlinx.serialization.json)

    // Coroutines
    implementation(libs.kotlinx.coroutines.android)
}