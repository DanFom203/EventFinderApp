plugins {
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    alias(libs.plugins.jetbrainsKotlinKapt)
}

android {
    namespace = "com.itis.feature.kudago.api"
    compileSdk = 34

    defaultConfig {
        minSdk = 24

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        buildConfigField("String", "KUDAGO_API_EVENTS_BASE_URL", "\"https://kudago.com/public-api/v1.4/\"")
        
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
        buildConfig = true
    }
}

dependencies {
    api(project(":common"))
    api(project(":feature:events:api"))
    //dagger2
    implementation(libs.dagger)
    "kapt"(libs.dagger.compiler)
    //loggingInterceptor
    implementation(libs.logging.interceptor)
    //retrofit2
    implementation(libs.retrofit2)
    //retrofit2-converter-gson
    implementation(libs.retrofit2.converter.gson)
    //coroutineAdapter
    implementation(libs.retrofit2.kotlin.coroutines.adapter)

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}