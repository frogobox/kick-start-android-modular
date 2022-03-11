plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("kotlin-parcelize")
}

android {

    compileSdk = ProjectSetting.PROJECT_COMPILE_SDK

    defaultConfig {
        applicationId = ProjectSetting.PROJECT_APP_FAV_ID
        minSdk = ProjectSetting.PROJECT_MIN_SDK
        targetSdk = ProjectSetting.PROJECT_TARGET_SDK
        versionCode = ProjectSetting.PROJECT_VERSION_CODE
        versionName = ProjectSetting.PROJECT_VERSION_NAME

        multiDexEnabled = true
        vectorDrawables.useSupportLibrary = true

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        // Naming APK // AAB
        setProperty("archivesBaseName", "${ProjectSetting.NAME_APK}(${versionName})")

        // Declaration apps name debug mode
        val debugAttribute = "Development"
        val nameAppDebug = "${ProjectSetting.NAME_APP} $debugAttribute"
        resourceConfigurations += setOf("en", "id")

        // Inject app name for debug
        resValue("string", "app_name", nameAppDebug)


    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false

            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    buildFeatures {
        viewBinding = true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
        kotlinOptions {
            jvmTarget = JavaVersion.VERSION_11.toString()
        }
    }

}

dependencies {

    implementation(project(":base"))

    implementation(Androidx.appCompat)
    implementation(Androidx.Core.ktx)
    implementation(Androidx.constraintLayout)
    implementation(Androidx.preferenceKtx)

    implementation(Androidx.Lifecycle.viewmodelKtx)
    implementation(Androidx.Work.runtimeKtx)

    implementation(Androidx.Room.runtime)
    implementation(Androidx.Room.ktx)
    implementation(Androidx.Room.rxJava3)

    implementation(Google.gson)
    implementation(Google.material)

    implementation(Square.okhttp)
    implementation(Square.okhttpLogging)
    implementation(Square.Retrofit2.retrofit)
    implementation(Square.Retrofit2.converterGson)
    implementation(Square.Retrofit2.adapterRxJava3)

    implementation(Reactivex.rxJava3)
    implementation(Reactivex.rxAndroid3)

    implementation(Util.glide)

    implementation(Frogo.ui)
    implementation(Frogo.recyclerView)

    implementation(Koin.core)
    implementation(Koin.android)
    implementation(Koin.androidCompat)
    implementation(Koin.androidxWorkManager)
    implementation(Koin.androidxCompose)

    api("com.google.dagger:dagger:2.38.1")
    api(JetBrains.coroutinesCore)
    api(JetBrains.coroutinesAndroid)

    kapt(Androidx.Lifecycle.compiler)
    kapt(Androidx.Room.compiler)
    kapt(Util.glideCompiler)
    kapt("com.google.dagger:dagger-compiler:2.37")

    testImplementation("junit:junit:4.13.2")
    testImplementation("androidx.room:room-testing:2.4.2")

    androidTestImplementation("androidx.room:room-testing:2.4.2")
    androidTestImplementation("androidx.arch.core:core-testing:2.1.0")
    androidTestImplementation("androidx.test:runner:1.4.0")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.4.0")

}