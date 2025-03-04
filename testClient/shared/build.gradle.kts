plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)

    //id("io.github.thearchitect123.atlasGraphGenerator") version "0.3.3"
    id("io.github.thearchitect123.atlasGraphGenerator")
}

kotlin {
    androidTarget {
        compilations.all {
            kotlinOptions {
                jvmTarget = "1.8"
            }
        }
    }
    
    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach {
        it.binaries.framework {
            baseName = "shared"
            isStatic = true
        }
    }

    sourceSets {
        commonMain.dependencies {
            //put your multiplatform dependencies here
            implementation("io.github.thearchitect123:atlas-core:0.1.3")


//            implementation("io.github.thearchitect123:atlas-core:0.0.")

            //implementation(projects.atlasGraphGenerator)
        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }

        commonMain {
            kotlin.srcDir("build/generated/commonMain/kotlin")
        }
        androidMain {
            kotlin.srcDir("build/generated/androidMain/kotlin")
            dependencies{
                implementation("androidx.core:core:1.15.0")
                implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.8.7")
                implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.8.7")
                implementation("androidx.fragment:fragment:1.8.5")
                implementation("androidx.activity:activity:1.10.0")
                implementation("androidx.appcompat:appcompat:1.7.0")
            }
        }
    }
}


tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().configureEach {
    dependsOn("generateDependencyGraph")
}

android {
    namespace = "com.architect.atlastestclient"
    compileSdk = 35
    defaultConfig {
        minSdk = 24
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

