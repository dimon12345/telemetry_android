plugins {
    id ("com.android.application")
    id ("com.google.dagger.hilt.android")
    id ("com.google.devtools.ksp")
    id ("org.jetbrains.kotlin.android")
}

android {
    namespace = "com.lexx.telemetry"
    compileSdk = 37

    defaultConfig {
        applicationId = "com.lexx.telemetry"
        minSdk = 26
        targetSdkVersion(37)
        versionCode = 1
        versionName = "0.1"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
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
    packagingOptions {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    implementation(project(path = ":domain"))
    implementation(project(path = ":data"))
    implementation(project(path = ":presentation"))

    implementation(libs.androidx.ktx)

    // Logger
    implementation(libs.timber)

    // Hilt
    implementation(libs.hilt.android.dagger)
    ksp(libs.hilt.compiler)

    // tests
    testImplementation(libs.junit.junit)
    androidTestImplementation(libs.junit.ext)
    androidTestImplementation(libs.junit.espresso)
}
