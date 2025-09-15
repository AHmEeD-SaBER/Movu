import org.gradle.api.Plugin
import org.gradle.api.Project
import com.android.build.gradle.LibraryExtension
import org.gradle.api.JavaVersion
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.getByType
import org.gradle.kotlin.dsl.dependencies
import org.jetbrains.kotlin.gradle.dsl.KotlinAndroidProjectExtension

class BaseDataPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply("com.android.library")
            pluginManager.apply("org.jetbrains.kotlin.android")
            // pluginManager.apply("org.jetbrains.kotlin.plugin.serialization") // optional
            pluginManager.apply("com.google.devtools.ksp")

            val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")

            // Android config
            extensions.getByType<LibraryExtension>().apply {
                compileSdk = libs.findVersion("compileSdk").get().requiredVersion.toInt()

                defaultConfig {
                    minSdk = libs.findVersion("minSdk").get().requiredVersion.toInt()
                }

                compileOptions {
                    sourceCompatibility = JavaVersion.VERSION_11
                    targetCompatibility = JavaVersion.VERSION_11
                }
            }

            // Kotlin config
            extensions.getByType<KotlinAndroidProjectExtension>().apply {
                jvmToolchain(libs.findVersion("javaVersion").get().requiredVersion.toInt())
            }

            // Common Data dependencies
            dependencies {
                // Core
                add("implementation", libs.findLibrary("androidx-core-ktx").get())

                // Networking
                add("implementation", libs.findLibrary("retrofit").get())
                add("implementation", libs.findLibrary("retrofit-converter-gson").get())
                add("implementation", libs.findLibrary("okhttp").get())
                add("implementation", libs.findLibrary("okhttp-logging").get())

                // Database
                add("implementation", libs.findLibrary("room-runtime").get())
                add("implementation", libs.findLibrary("room-paging").get())
                add("ksp", libs.findLibrary("room-compiler").get())

                // Serialization
                add("implementation", libs.findLibrary("kotlinx-serialization-json").get())

                // DI
                add("implementation", libs.findLibrary("koin-core").get())
                add("implementation", libs.findLibrary("koin-android").get())

                // Firebase
                add("implementation", platform(libs.findLibrary("firebase-bom").get()))
                add("implementation", libs.findLibrary("firebase-auth").get())
                add("implementation", libs.findLibrary("firebase-firestore-ktx").get())
            }
        }
    }
}
