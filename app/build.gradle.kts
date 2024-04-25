plugins {
    id("com.android.application")
}

android {
    namespace = "msh.myonlineshop"
    compileSdk = 34

    defaultConfig {
        applicationId = "msh.myonlineshop"
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
    buildFeatures {
        viewBinding = true
    }
}

dependencies {

    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")

    //start my imports
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("androidx.annotation:annotation:1.7.1")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.7.0")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.7.0")
    //
    //implementation("com.github.smarteist:autoimageslider:1.3.9-appcompat")
    ////https://github.com/denzcoskun/ImageSlideshow
    implementation ("com.github.denzcoskun:ImageSlideshow:0.1.2")
    //
    implementation("com.squareup.picasso:picasso:2.71828")
    //implementation ("com.android.support:exifinterface:27.1.1")
//    implementation ("androidx.exifinterface:exifinterface:1.0.0-alpha01")
    //end my imports

    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}