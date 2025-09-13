import org.gradle.api.Plugin
import org.gradle.api.Project
import com.android.build.gradle.LibraryExtension
import org.gradle.api.JavaVersion
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.getByType
import org.gradle.kotlin.dsl.dependencies
import org.jetbrains.kotlin.gradle.dsl.KotlinAndroidProjectExtension

class BaseUiPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply("com.android.library")
            pluginManager.apply("org.jetbrains.kotlin.android")
            pluginManager.apply("org.jetbrains.kotlin.plugin.compose")

            val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")

            extensions.getByType<LibraryExtension>().apply {
                compileSdk = libs.findVersion("compileSdk").get().requiredVersion.toInt()

                defaultConfig {
                    minSdk = libs.findVersion("minSdk").get().requiredVersion.toInt()
                }

                compileOptions {
                    sourceCompatibility = JavaVersion.VERSION_11
                    targetCompatibility = JavaVersion.VERSION_11
                }

                buildFeatures {
                    compose = true
                }

                composeOptions {
                    kotlinCompilerExtensionVersion = libs.findVersion("composeCompiler").get().requiredVersion
                }
            }

            // Kotlin config
            extensions.getByType<KotlinAndroidProjectExtension>().apply {
                jvmToolchain(libs.findVersion("javaVersion").get().requiredVersion.toInt())
            }

            // Common UI dependencies
            dependencies {
                add("implementation", platform(libs.findLibrary("androidx-compose-bom").get()))
                add("implementation", libs.findLibrary("androidx-core-ktx").get())
                add("implementation", libs.findLibrary("androidx-activity-compose").get())
                add("implementation", libs.findLibrary("androidx-ui").get())
                add("implementation", libs.findLibrary("androidx-ui-tooling-preview").get())
                add("implementation", libs.findLibrary("androidx-material3").get())
                add("debugImplementation", libs.findLibrary("androidx-ui-tooling").get())
                add("debugImplementation", libs.findLibrary("androidx-ui-test-manifest").get())
            }
        }
    }
}
