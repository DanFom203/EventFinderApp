plugins {
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    alias(libs.plugins.jetbrainsKotlinKapt)
    id("com.google.gms.google-services")
}

android {
    namespace = "com.itis.common"
    compileSdk = 34

    defaultConfig {
        minSdk = 21

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
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
        sourceCompatibility = JavaVersion.VERSION_18
        targetCompatibility = JavaVersion.VERSION_18
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.3"
    }
    kotlinOptions {
        jvmTarget = "18"
    }
}

dependencies {
    //compose-bom
    implementation(platform(libs.compose.bom))
    //material3
    implementation(libs.androidx.material3)
    // Android Studio Preview support
    implementation("androidx.compose.ui:ui-tooling-preview")
    debugImplementation("androidx.compose.ui:ui-tooling")
    //dagger2
    implementation(libs.dagger)
    implementation(libs.androidx.lifecycle.runtime.android)
    "kapt"(libs.dagger.compiler)
    //retrofit2
    implementation(libs.retrofit2)
    //room
    implementation(libs.room.runtime)
    implementation(libs.room.ktx)
    "kapt"(libs.room.compiler)
    //firebase-auth
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.auth)
    //firebase-firestore-database
    implementation(libs.firebase.firestore)
    //firebase-storage
    implementation(libs.firebase.storage)

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}