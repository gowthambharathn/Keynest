plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.compose)

    // Apply KSP
    id("com.google.devtools.ksp")
}

android {
    namespace = "skynetbee.gowtham.keynest"
    compileSdk {
        version = release(37)
    }

    defaultConfig {
        applicationId = "skynetbee.gowtham.keynest"
        minSdk = 26
        targetSdk = 37
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            optimization {
                enable = false
            }
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    buildFeatures {
        compose = true
    }
}

dependencies {
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    testImplementation(libs.junit)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(libs.androidx.junit)
    debugImplementation(libs.androidx.compose.ui.test.manifest)
    debugImplementation(libs.androidx.compose.ui.tooling)

    //Lib
    implementation("com.github.gowthambharathn:Orbit:1.0.2")

    // ViewModel
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.10.0")

    // Material Icons
    implementation("androidx.compose.material:material-icons-extended")

    // Navigation Compose
    implementation("androidx.navigation:navigation-compose:2.9.7")

    //Security
    implementation("androidx.security:security-crypto:1.1.0-alpha06")

    val roomVersion = "2.7.0"

    implementation("androidx.room:room-runtime:$roomVersion")
    implementation("androidx.room:room-ktx:$roomVersion")
    ksp("androidx.room:room-compiler:$roomVersion")

    //sql
    implementation("net.zetetic:sqlcipher-android:4.14.1@aar")
    implementation("androidx.sqlite:sqlite:2.6.2")

    //lifecycles
    implementation("androidx.lifecycle:lifecycle-process:2.8.0")
}