plugins {
    id("com.android.library")
    id("com.google.dagger.hilt.android")
    id("com.google.devtools.ksp")

    id("org.jetbrains.kotlin.plugin.compose")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "com.lexx.presentation"
    compileSdk = 37

    defaultConfig {
        minSdk = 26

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
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
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.0"
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

    // Color Picker
    implementation(libs.compose.colorpicker)

    // Logger
    implementation(libs.timber)

    // Tests
    testImplementation(libs.junit.junit)
    androidTestImplementation(libs.junit.ext)
    androidTestImplementation(libs.junit.espresso)

    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.junit4)
    debugImplementation(libs.androidx.compose.tooling)
    debugImplementation(libs.androidx.compose.manifest)

    // Hilt
    implementation(libs.hilt.android.dagger)
    ksp(libs.hilt.compiler)

    // ViewModel
    implementation(libs.androidx.compose.viewmodel)

    // Compose
    implementation(libs.androidx.compose.activity)
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.compose.material3.window.size.class1)
}
