plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.example.smartspot"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.example.smartspot"
        minSdk = 24
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    // Recommended: Add this if you use ViewBinding later to avoid findViewById
    buildFeatures {
        viewBinding = true
    }
}

dependencies {
    // Standard UI Libraries from Version Catalog
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)

    // Networking - Retrofit & GSON
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    // Useful for logging API requests in Logcat
    implementation("com.squareup.okhttp3:logging-interceptor:4.12.0")

    // QR Code Generation & Scanning
    // zxing-android-embedded is great for the UI
    implementation("com.journeyapps:zxing-android-embedded:4.3.0")
    // core is needed for the actual QR logic
    implementation("com.google.zxing:core:3.5.3")

    // Firebase (Keep if using Firestore for live data)
    implementation(libs.firebase.firestore)

    // Testing
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
}