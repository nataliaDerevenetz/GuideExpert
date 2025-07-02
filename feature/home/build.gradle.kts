plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.compose.compiler)

    kotlin("plugin.serialization") version "2.0.21"
    id("com.google.dagger.hilt.android") version "2.56.1" apply false
    id("kotlin-parcelize")
    id("com.google.devtools.ksp")

}

android {
    namespace = "com.example.feature.home"
    compileSdk = 35

    defaultConfig {
        minSdk = 26

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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
        freeCompilerArgs = freeCompilerArgs + "-Xcontext-receivers"
    }
}

dependencies {

    implementation("io.coil-kt:coil-compose:2.7.0")

    implementation("androidx.compose.material3:material3:1.3.2")

    implementation("androidx.compose.material:material-icons-extended:1.7.8")

    implementation("androidx.compose.animation:animation:1.8.0")


    implementation("com.google.accompanist:accompanist-pager:0.28.0")
    implementation("com.google.accompanist:accompanist-pager-indicators:0.28.0")

    val paging_version = "3.3.2"
    implementation("androidx.paging:paging-runtime:$paging_version")
    implementation("androidx.paging:paging-compose:3.3.6")

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


    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    implementation(project(":core:domain"))
    implementation(project(":core:models"))
    implementation(project(":core:utils"))
    implementation(project(":core:design"))
    implementation(project(":core:notifications"))

}