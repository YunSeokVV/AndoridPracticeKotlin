plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
}

android {
    namespace = "com.example.samplekotlin"
    compileSdk = 33

    defaultConfig {
        applicationId = "com.example.samplekotlin"
        minSdk = 24
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        buildConfigField("String", "UNSPLASH_KEY",
            (project.properties["UNSPLASH_KEY"] ?: "\"NOT_FOUND\"").toString()
        )
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
        sourceCompatibility = org.gradle.api.JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
}

dependencies {
    implementation("com.github.bumptech.glide:glide:4.11.0")
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    // retrofit 통신을 사용해서 데이터를 받아올때 json 형태가 아닌 문자열이나 숫자로 받아올때 scalars 라이브러리가 필요하다
    implementation("com.squareup.retrofit2:converter-scalars:2.9.0")
    // Object 타입을 JSON 또는 JSON을 Object형태로 바꿔주는 역할을 해주는 라이브러리
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("androidx.core:core-ktx:1.9.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.8.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

    // 로그를 보기 편하게 해주는 라이브러리
    implementation("com.orhanobut:logger:2.2.0")


    implementation ("androidx.room:room-runtime:2.5.2")
    kapt ("androidx.room:room-compiler:2.5.2")

    // registerForActivityResult를 쓰기위한 라이브러리 추가
    implementation ("androidx.activity:activity-ktx:1.4.0")
}