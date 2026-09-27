plugins {
    id("com.android.library")
    id("com.google.dagger.hilt.android")
    id("com.google.devtools.ksp")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "com.lexx.data"
    compileSdk = 37

    defaultConfig {
        minSdk = 26

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")

        buildConfigField("String", "DEFAULT_BASE_URL", "\"192.168.0.1:8080\"")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlin {
        compilerOptions {
            jvmTarget.set(org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_17)
        }
    }

    buildFeatures {
        buildConfig = true
    }
    lint {
        targetSdk = 33
    }
    testOptions {
        targetSdk = 33
    }
}

dependencies {
    implementation(project(path = ":domain"))

    implementation(libs.androidx.ktx)

    // Room
    implementation(libs.androidx.room.runtime)
    ksp(libs.androidx.room.compiler)
    implementation(libs.androidx.room.ktx)

    // Logger
    implementation(libs.timber)

    // Hilt
    implementation(libs.hilt.android.dagger)
    ksp(libs.hilt.compiler)

    // Tests
    testImplementation(libs.junit.junit)
    androidTestImplementation(libs.junit.ext)
    androidTestImplementation(libs.junit.espresso)

    // User settings
    implementation(libs.androidx.datastore.preferences)

    // REST API
    implementation(libs.squareup.retrofit)
    implementation(libs.squareup.converter.gson)
}
