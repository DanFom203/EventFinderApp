plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    alias(libs.plugins.jetbrainsKotlinKapt)
    id("com.google.gms.google-services")
    alias(libs.plugins.googleFirebaseCrashlytics)
}

android {
    namespace = "com.itis.eventfinderapp"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.itis.eventfinderapp"
        minSdk = 21
        targetSdk = 34
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
    implementation(project(":feature:auth:api"))
    implementation(project(":feature:auth:impl"))
    implementation(project(":feature:biometrics:api"))
    implementation(project(":feature:biometrics:impl"))
    implementation(project(":feature:events:api"))
    implementation(project(":feature:events:impl"))
    implementation(project(":feature:kudago:api"))
    implementation(project(":feature:notes:api"))
    implementation(project(":feature:notes:impl"))
    implementation(project(":feature:profile:api"))
    implementation(project(":feature:profile:impl"))
    //dagger2
    implementation(libs.dagger)
    "kapt"(libs.dagger.compiler)
    //navigation
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)
    //viewBindingDelegateByKirich1409
    implementation(libs.viewbindingpropertydelegate.noreflection)
    //lifecycle
    implementation(libs.androidx.lifecycle.extensions)
    "kapt"(libs.androidx.lifecycle.compiler)
    //firebase-auth
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.auth)
    //firebase-crashlytics
    implementation(libs.firebase.crashlytics)
    //firebase-analytics
    implementation(libs.firebase.analytics)
    //firebase-performance
    implementation(libs.firebase.perf)
    //firebase-firestore-database
    implementation(libs.firebase.firestore)
    //firebase-storage
    implementation(libs.firebase.storage)
    //loggingInterceptor
    implementation(libs.logging.interceptor)

    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}