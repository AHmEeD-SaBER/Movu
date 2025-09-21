plugins {
    id("movu.base.data")
}

android {
    namespace = "com.example.data"
}

dependencies {
    // Domain module dependency
    implementation(projects.details.domain)
    implementation(projects.coreData)
    implementation(projects.firebase)

    // Retrofit networking libraries
    implementation(libs.retrofit)
    implementation(libs.retrofit.converter.gson)
    implementation(libs.okhttp.logging)
    implementation(libs.gson)

}
