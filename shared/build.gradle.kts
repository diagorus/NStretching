plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.android.kotlin.multiplatform.library)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.kotlin.serialization)
}

kotlin {
    androidLibrary {
        namespace = "com.diagorus.nstretching.shared"
        compileSdk = 36
        minSdk = 24

        withDeviceTestBuilder {
            sourceSetTreeName = "test"
        }.configure {
            instrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        }
        androidResources.enable = true
    }

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64(),
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "sharedKit"
        }
    }

    targets.withType<org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget> {
        binaries.all {
            freeCompilerArgs += "-Xadd-light-debug=enable"
        }
    }

    sourceSets {
        commonMain {
            dependencies {
                implementation(libs.kotlin.stdlib)
                implementation(libs.kotlin.stdlib.common)


                implementation(compose.runtime)
                implementation(compose.foundation)
                implementation(compose.material3)
                implementation(compose.components.resources)


                implementation(libs.koin.core)
                implementation(libs.koin.compose.viewmodel.nav)

                implementation(libs.androidx.datastore.preferences)

                implementation(libs.lifecycle.viewmodel.compose)
                implementation(libs.kermit)

            }
        }

        commonTest {
            dependencies {
                implementation(libs.kotlin.test)
//                implementation(libs.kotlin.test.annotations.common)
//                implementation(libs.kotlinx.coroutines.test)
//                implementation(libs.koinTest)
            }
        }

        androidMain {
            dependencies {
                implementation(libs.kotlin.stdlib)

                implementation(libs.androidx.appcompat)
                implementation(libs.material)

                implementation(project.dependencies.platform("androidx.compose:compose-bom:2025.08.00"))

                implementation(libs.androidx.material3)
                implementation(libs.androidx.navigation.compose)

                implementation(libs.kotlinx.serialization.json)

                implementation(libs.androidx.ui.tooling.preview)
                implementation(libs.androidx.datastore.preferences)

                // Media3
                implementation(libs.androidx.media3.exoplayer)
                implementation(libs.androidx.media3.common.ktx)
                implementation(libs.androidx.media3.datasource)

                implementation(libs.kotlinx.serialization.json)
            }
        }

        getByName("androidDeviceTest") {
            dependencies {
                implementation(libs.androidx.runner)
                implementation(libs.androidx.core)
                implementation(libs.androidx.junit)
            }
        }

        iosMain {
            dependencies {
            }
        }
    }
}

compose.resources {
    packageOfResClass = "com.diagorus.nstretching.shared.generated.resources"
}