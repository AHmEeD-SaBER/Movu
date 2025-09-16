plugins {
    id("movu.base.ui")
}

android {
    namespace = "com.movu.authentication.ui"
}

dependencies {
    implementation(projects.authentication.domain)
    implementation(projects.coreUi)
}