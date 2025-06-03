plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    id("com.google.gms.google-services")
}

android {
    namespace = "com.example.sidera_app"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.sidera_app"
        minSdk = 26
        targetSdk = 35
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
        isCoreLibraryDesugaringEnabled = true
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
}
dependencies {
    // Filament core + utils + gltfio
    implementation(libs.filament.android)
    implementation(libs.filament.utils)
    implementation(libs.gltfio)
    implementation(libs.gson)
    implementation(libs.gson.converter)
    implementation(libs.retrofit)
    implementation(libs.coil)

    coreLibraryDesugaring("com.android.tools:desugar_jdk_libs:2.0.4")
    coreLibraryDesugaring(libs.desugar.jdk.libs)

    // ML Kit Translation
    implementation ("com.google.mlkit:translate:17.0.1")
    implementation ("com.google.android.gms:play-services-base:18.3.0")
    implementation ("io.coil-kt:coil-compose:2.4.0")




    implementation(platform("com.google.firebase:firebase-bom:33.14.0"))
    implementation("com.google.firebase:firebase-analytics")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-play-services:1.7.3")




    // Material 3
    implementation("androidx.compose.material3:material3:1.2.1")

    implementation("com.google.firebase:firebase-firestore-ktx")
    // Material Design Components (no Compose)
    implementation("com.google.android.material:material:1.12.0")

    // Material Icons para Compose
    implementation("androidx.compose.material:material-icons-core:1.6.7")
    implementation("androidx.compose.material:material-icons-extended:1.6.7")

    // Navigation Compose
    implementation("androidx.navigation:navigation-compose:2.7.7")

    // Core KTX
    implementation("androidx.core:core-ktx:1.12.0")

    // Compose (usando BOM)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)

    // Testing
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}
