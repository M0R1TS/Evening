plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    id ("kotlin-parcelize")
    id ("kotlin-android")
    id ("kotlin-kapt")
}

android {
    namespace = "ru.devsokovix.evening"
    compileSdk = 34

    defaultConfig {
        applicationId = "ru.devsokovix.evening"
        minSdk = 24
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }

    buildFeatures {
        viewBinding = true
        buildConfig = true
    }
}

dependencies {
    //Room
    implementation ("androidx.room:room-runtime:2.6.1")
    kapt ("androidx.room:room-compiler:2.6.1")

    //Dagger
    implementation ("com.google.dagger:dagger:2.51")
    implementation(libs.androidx.swiperefreshlayout)
    kapt ("com.google.dagger:dagger-compiler:2.51")

    //Retrofit
    implementation ("com.squareup.retrofit2:retrofit:2.9.0")
    implementation ("com.squareup.retrofit2:converter-gson:2.6.0")
    implementation ("com.squareup.okhttp3:logging-interceptor:3.12.6")
    implementation ("com.github.bumptech.glide:glide:4.11.0")
    annotationProcessor ("com.github.bumptech.glide:compiler:4.11.0")

    //Lifecycle
    implementation ("android.arch.lifecycle:extensions:1.1.1")
    annotationProcessor ("android.arch.lifecycle:compiler:1.1.1")

    implementation("com.squareup.retrofit2:adapter-rxjava2:2.9.0")
    implementation ("com.google.code.gson:gson:2.11.0")
    implementation("androidx.core:core-splashscreen:1.0.0-alpha01")
    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.10.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("androidx.compose.material3:material3:1.1.2")
    implementation(libs.androidx.activity)
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    implementation("androidx.cardview:cardview:1.0.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.2")
    implementation("androidx.activity:activity-compose:1.8.1")
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("com.google.android.material:material:1.12.0-alpha01")
    implementation ("androidx.viewpager2:viewpager2:1.1.0-beta02")
    implementation ("androidx.recyclerview:recyclerview:1.3.2")
    implementation ("jp.wasabeef:recyclerview-animators:4.0.2")
    implementation ("androidx.coordinatorlayout:coordinatorlayout:1.2.0")
    androidTestImplementation(platform("androidx.compose:compose-bom:2023.03.00"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    implementation ("androidx.navigation:navigation-fragment-ktx:2.7.5")
    implementation ("androidx.navigation:navigation-ui-ktx:2.7.5")
    implementation ("com.github.bumptech.glide:glide:4.16.0")
    annotationProcessor ("com.github.bumptech.glide:compiler:4.11.0")
}