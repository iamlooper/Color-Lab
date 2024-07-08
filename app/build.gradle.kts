import java.text.SimpleDateFormat
import java.util.Date

fun getCurrentDate(): String {
    return SimpleDateFormat("yyyyMMdd").format(Date())
}

plugins {
    id("com.android.application")
    id("kotlin-android")
}

android {
    namespace = "com.looper.seeker"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.looper.seeker"
        minSdk = 31
        targetSdk = 34
        versionCode = 28
        versionName = "2.0.0-beta-" + getCurrentDate()

        vectorDrawables.useSupportLibrary = true
    }
    
    buildFeatures {
        buildConfig = true
        aidl = true
    }    
    
    buildTypes {
        getByName("release") {
            // Enables code shrinking, obfuscation, and optimization.
            isMinifyEnabled = true

            // Enables resource shrinking.
            isShrinkResources = true

            // Includes the default ProGuard rules files.
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = "17"
    }
}

dependencies {
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.core)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.fragment)
    implementation(libs.androidx.navigation.fragment)
    implementation(libs.androidx.navigation.ui)
    implementation(libs.androidx.preference)
    implementation(libs.androidx.work.runtime)
    
    implementation(libs.google.material)

    implementation(libs.topjohnwu.libsu.core)
    implementation(libs.topjohnwu.libsu.service)
    
    implementation(libs.lsposed.hiddenapibypass)

    implementation(libs.noties.markwon.core)

    implementation(libs.looper.android.support)
}