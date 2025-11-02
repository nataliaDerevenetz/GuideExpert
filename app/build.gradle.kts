plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.compose.compiler)

    kotlin("plugin.serialization") version "2.0.21"

    id("com.google.dagger.hilt.android") version "2.56.1" apply false
    id ("dagger.hilt.android.plugin")
    id("kotlin-parcelize")
    id("com.google.devtools.ksp")
    id("com.google.gms.google-services")
}

android {
    namespace = "com.example.GuideExpert"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.GuideExpert"
        minSdk = 26
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }

        manifestPlaceholders["YANDEX_CLIENT_ID"] = "a2f57012385840da950e5f51e761ece9"
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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.3"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildFeatures {
        buildConfig = true
    }

    packaging {
        jniLibs.keepDebugSymbols.add("**/libandroidx.graphics.path.so")
        jniLibs.keepDebugSymbols.add("**/libdatastore_shared_counter.so")
    }
}

dependencies {
    implementation("androidx.compose.material3:material3-adaptive-navigation-suite")
    implementation("io.coil-kt:coil-compose:2.7.0")
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.media3.common.ktx)
    implementation(libs.firebase.messaging.ktx)
    implementation(libs.androidx.work.runtime.ktx)

    implementation("androidx.compose.material3:material3:1.3.2")


    val nav_version = "2.8.3"
    implementation("androidx.navigation:navigation-compose:$nav_version")

    implementation("com.google.dagger:hilt-android:2.56.1")
    ksp("com.google.dagger:hilt-compiler:2.56.1")

    implementation("androidx.hilt:hilt-navigation-compose:1.2.0")

    implementation ("androidx.appcompat:appcompat:1.3.1")
    implementation("androidx.compose.material:material:1.8.0")

    implementation(libs.kotlinx.serialization.json)
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.8.7")
    implementation("androidx.savedstate:savedstate-ktx:1.2.1")

    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.lifecycle.livedata.ktx)
    implementation(libs.androidx.runtime.livedata)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    implementation(project(":feature:home"))
    implementation(project(":feature:favorites"))
    implementation(project(":feature:profile"))
    implementation(project(":core:domain"))
    implementation(project(":core:models"))
    implementation(project(":core:utils"))
    implementation(project(":core:design"))
    implementation(project(":core:data"))
    implementation(project(":core:notifications"))

}