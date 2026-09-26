// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    //extra.apply{ set("junit_version", "4.13.2") }
    //extra.apply{ set("junit_test_ext_version", "1.3.0") }
    //extra.apply{ set("expresso_core_version", "3.7.0") }

    dependencies {
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:2.3.10")
        classpath("com.google.dagger:hilt-android-gradle-plugin:2.60.1")
    }
    repositories {
        mavenCentral()
    }
}

plugins {
    id ("com.android.application") version "9.4.1" apply false
    id ("com.android.library") version "9.4.1" apply false
    id ("org.jetbrains.kotlin.plugin.compose") version "2.4.20" apply false
    id ("com.google.devtools.ksp") version "2.3.3" apply false
    id ("com.google.dagger.hilt.android") version "2.60.1" apply false
}
