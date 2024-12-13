plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
}

android {
    namespace = "com.codingwithnobody.myandroidapp"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.codingwithnobody.myandroidapp"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        externalNativeBuild {
            cmake {
                cppFlags += ""
            }
        }
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
    externalNativeBuild {
        cmake {
            path = file("src/main/cpp/CMakeLists.txt")
            version = "3.22.1"
        }
    }

    sourceSets {
        getByName("main") {
            jniLibs.srcDirs("src/main/jniLibs")
        }
    }

    packaging {
        jniLibs.pickFirsts.add("lib/arm64-v8a/libavcodec.so")
        jniLibs.pickFirsts.add("lib/arm64-v8a/libavformat.so")
        jniLibs.pickFirsts.add("lib/arm64-v8a/libavutil.so")
        jniLibs.pickFirsts.add("lib/arm64-v8a/libswscale.so")
        jniLibs.pickFirsts.add("lib/armeabi-v7a/libavcodec.so")
        jniLibs.pickFirsts.add("lib/armeabi-v7a/libavformat.so")
        jniLibs.pickFirsts.add("lib/armeabi-v7a/libavutil.so")
        jniLibs.pickFirsts.add("lib/armeabi-v7a/libswscale.so")
        jniLibs.pickFirsts.add("lib/x86/libavcodec.so")
        jniLibs.pickFirsts.add("lib/x86/libavformat.so")
        jniLibs.pickFirsts.add("lib/x86/libavutil.so")
        jniLibs.pickFirsts.add("lib/x86/libswscale.so")
        jniLibs.pickFirsts.add("lib/x86_64/libavcodec.so")
        jniLibs.pickFirsts.add("lib/x86_64/libavformat.so")
        jniLibs.pickFirsts.add("lib/x86_64/libavutil.so")
        jniLibs.pickFirsts.add("lib/x86_64/libswscale.so")
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}