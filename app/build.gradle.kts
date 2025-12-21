plugins {
    id("kotlin-kapt")
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
}

android {
    namespace = "com.tayari365.class10sciencenotes"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.tayari365.class10sciencenotes"
        minSdk = 24
        targetSdk = 36
        versionCode = 3
        versionName = "1.1"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            // Include native debug symbols in release build
            ndk {
                debugSymbolLevel = "FULL"
            }
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    kotlinOptions {
        jvmTarget = "11"
    }

    buildFeatures {
        viewBinding = true
    }

    // Fix for META-INF conflict issue
    packaging {
        resources {
            excludes += setOf(
                "META-INF/INDEX.LIST",
                "META-INF/io.netty.versions.properties",
                "META-INF/DEPENDENCIES"
            )
        }
    }
    ndkVersion = "29.0.13599879 rc2"
}

dependencies {
    implementation(libs.kotlinx.coroutines.android)

    // Retrofit for API calls
    implementation(libs.retrofit)
    implementation(libs.okhttp)
    implementation("com.squareup.okhttp3:logging-interceptor:5.1.0")
    implementation("com.squareup.retrofit2:converter-gson:3.0.0")

    // ViewModel & LiveData
    implementation("androidx.activity:activity-ktx:1.10.1")
    implementation("androidx.fragment:fragment-ktx:1.8.8")
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation("androidx.lifecycle:lifecycle-livedata-core-ktx:2.9.2")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.9.2")

//    // Dialogflow (Google Cloud)
//    implementation("com.google.cloud:google-cloud-dialogflow:4.74.0")
//    implementation(libs.grpc.okhttp)
//    implementation("io.grpc:grpc-protobuf:1.73.0")
//    implementation("io.grpc:grpc-stub:1.73.0")

    implementation(libs.lottie)
    // Other dependencies
    implementation("com.github.marain87:AndroidPdfViewer:3.2.7")
    implementation(libs.androidx.viewpager2)
    implementation(libs.material.v190)
    implementation(libs.glide)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.generativeai)
    implementation(libs.androidx.ui.text.android)


    // Testing dependencies
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}
