plugins {
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    alias(libs.plugins.jetbrainsKotlinKapt)
    id("com.google.gms.google-services")
}

android {
    namespace = "com.itis.feature.events.impl"
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
    kotlinOptions {
        jvmTarget = "18"
    }
    buildFeatures {
        viewBinding = true
    }
}

dependencies {
    implementation(project(":common"))
    implementation(project(":feature:events:api"))
    implementation(project(":feature:kudago:api"))
    //dagger2
    implementation(libs.dagger)
    "kapt"(libs.dagger.compiler)
    //firebase-firestore-database
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.firestore)
    //viewBindingDelegateByKirich1409
    implementation(libs.viewbindingpropertydelegate.noreflection)
    //retrofit2
    implementation(libs.retrofit2)
    //glide
    implementation(libs.glide)

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)

    testImplementation(libs.junit)
    testImplementation(libs.mockk)
    testImplementation(libs.kotlin.test.junit)
    testImplementation(libs.kotlinx.coroutines.test)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}