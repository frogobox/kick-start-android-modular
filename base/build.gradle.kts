plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("kotlin-parcelize")
}


android {

    compileSdk = ProjectSetting.PROJECT_COMPILE_SDK

    defaultConfig {

        minSdk = ProjectSetting.PROJECT_MIN_SDK
        targetSdk = ProjectSetting.PROJECT_TARGET_SDK

        testInstrumentationRunner  = "androidx.test.runner.AndroidJUnitRunner"

        buildConfigField("String", "TMDB_API_KEY", BuildConstant.TMDB_API_KEY)
        buildConfigField("String", "TMDB_BASE_URL", BuildConstant.TMDB_BASE_URL)
        buildConfigField("String", "TMDB_BASE_LANG", BuildConstant.TMDB_BASE_LANG)

        buildConfigField("String", "TMDB_PATH_URL_IMAGE", BuildConstant.TMDB_PATH_URL_IMAGE)

        buildConfigField("String", "TMDB_URL_MOVIE", BuildConstant.TMDB_URL_MOVIE)
        buildConfigField("String", "TMDB_URL_TV", BuildConstant.TMDB_URL_TV)

        buildConfigField("String", "TMDB_URL_SEARCH_MOVIE", BuildConstant.TMDB_URL_SEARCH_MOVIE)
        buildConfigField("String", "TMDB_URL_SEARCH_TV_SHOW", BuildConstant.TMDB_URL_SEARCH_TV_SHOW)

        buildConfigField("String", "TMDB_URL_RELEASE_BY_DATE", BuildConstant.TMDB_URL_RELEASE_BY_DATE)

        // Inject admob id for debug
        resValue("string", "admob_app_id", AdmobValue.debugAdmobAppId)
        resValue("string", "admob_banner", AdmobValue.debugAdmobBanner)
        resValue("string", "admob_interstitial", AdmobValue.debugAdmobInterstitial)
        resValue("string", "admob_interstitial_video", AdmobValue.debugAdmobInterstitialVideo)
        resValue("string", "admob_rewarded", AdmobValue.debugAdmobRewarded)
        resValue("string", "admob_rewarded_interstitial", AdmobValue.debugAdmobRewardedInterstitial)
        resValue("string", "admob_native_advanced", AdmobValue.debugAdmobNativeAdvanced)
        resValue("string", "admob_native_advanced_video", AdmobValue.debugAdmobNativeAdvancedVideo)

    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false

            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )

            // Inject admob id for release
            resValue("string", "admob_app_id", AdmobValue.releaseAdmobAppId)
            resValue("string", "admob_banner", AdmobValue.releaseAdmobBanner)
            resValue("string", "admob_interstitial", AdmobValue.releaseAdmobInterstitial)
            resValue("string", "admob_interstitial_video", AdmobValue.releaseAdmobInterstitialVideo)
            resValue("string", "admob_rewarded", AdmobValue.releaseAdmobRewarded)
            resValue("string", "admob_rewarded_interstitial", AdmobValue.releaseAdmobRewardedInterstitial)
            resValue("string", "admob_native_advanced", AdmobValue.releaseAdmobNativeAdvanced)
            resValue("string", "admob_native_advanced_video", AdmobValue.releaseAdmobNativeAdvancedVideo)

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