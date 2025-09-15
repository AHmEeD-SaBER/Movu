import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.getByType
import org.gradle.kotlin.dsl.dependencies
import org.jetbrains.kotlin.gradle.dsl.KotlinJvmProjectExtension

class BaseDomainPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply("org.jetbrains.kotlin.jvm")

            val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")

            // Kotlin config
            extensions.getByType<KotlinJvmProjectExtension>().apply {
                jvmToolchain(libs.findVersion("javaVersion").get().requiredVersion.toInt())
            }

            // Common Domain dependencies
            dependencies {
                // DI (pure Kotlin only)
                add("implementation", libs.findLibrary("koin-core").get())

                // Testing
                add("testImplementation", libs.findLibrary("junit").get())
            }
        }
    }
}
