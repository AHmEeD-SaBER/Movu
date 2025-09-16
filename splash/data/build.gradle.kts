plugins {
    id("movu.base.data")
}

android {
    namespace = "com.example.data"
}

dependencies {
    implementation(projects.splash.domain)
    implementation(projects.coreData)
    implementation(projects.firebase)
}