plugins {
    id("movu.base.domain")
}
dependencies {
    // Coroutines
    implementation(libs.kotlinx.coroutines.android)
    api(projects.coreDomain)
}