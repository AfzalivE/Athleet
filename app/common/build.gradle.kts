import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget

plugins {
    kotlin("multiplatform")
    id("com.android.library")
}

group = "com.afzaln"
version = "1.0"

kotlin {
    android()
    jvm("desktop") {
        compilations.all {
            kotlinOptions.jvmTarget = "11"
        }
        testRuns["test"].executionTask.configure {
            useJUnit()
        }
    }
    iosArm64 {
        binaries {
            framework {
                baseName = "common"
            }
        }
    }
    iosSimulatorArm64 {
        binaries {
            framework {
                baseName = "common"
            }
        }
    }
    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation("io.ktor:ktor-client-core:2.0.0-beta-1")
                implementation("io.ktor:ktor-client-serialization:2.0.0-beta-1")
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
            }
        }
        val androidMain by getting {
            dependencies {
                api("androidx.appcompat:appcompat:1.4.1")
                api("androidx.core:core-ktx:1.7.0")
                implementation("io.ktor:ktor-client-android:2.0.0-beta-1")
            }
        }
        val androidTest by getting {
            dependencies {
                implementation("junit:junit:4.13.2")
            }
        }
        val desktopMain by getting {
            dependencies {
                implementation("io.ktor:ktor-client-java:2.0.0-beta-1")
            }
        }
        val desktopTest by getting
        val iosArm64Main by getting {
            dependencies {
                implementation("io.ktor:ktor-client-ios:2.0.0-beta-1")
            }
        }
        val iosArm64Test by getting
        val iosSimulatorArm64Main by getting {
            dependsOn(iosArm64Main)
        }
        val iosSimulatorArm64Test by getting {
            dependsOn(iosArm64Test)
        }
    }
}

android {
    compileSdkVersion(31)
    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    defaultConfig {
        minSdk = 24
        targetSdk = 31
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

val packForXcode by tasks.creating(Sync::class) {
    group = "build"
    val mode = System.getenv("CONFIGURATION") ?: "DEBUG"
    val sdkName = System.getenv("SDK_NAME") ?: "iphonesimulator"
    val targetName = if (sdkName.contains("simulator")) "iosSimulatorArm64" else "iosArm64"
    val framework = kotlin.targets.getByName<KotlinNativeTarget>(targetName).binaries.getFramework(mode)
    inputs.property("mode", mode)
    dependsOn(framework.linkTask)
    val targetDir = File(buildDir, "xcode-frameworks")
    from({ framework.outputDirectory })
    into(targetDir)
}

tasks.getByName("build").dependsOn(packForXcode)
