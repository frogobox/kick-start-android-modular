package com.frogobox.base.source.remote

import com.frogobox.base.BaseApiModel
import com.frogobox.base.BaseApplication
import com.frogobox.base.BuildConfig
import com.frogobox.base.source.model.Movie
import com.frogobox.base.source.model.TvShow
import com.frogobox.base.util.Helper
import io.reactivex.Observable
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import java.util.concurrent.TimeUnit

/**
 * Created by Faisal Amir
 * FrogoBox Inc License
 * =========================================
 * movie
 * Copyright (C) 16/11/2019.
 * All rights reserved
 * -----------------------------------------
 * Name     : Muhammad Faisal Amir
 * E-mail   : faisalamircs@gmail.com
 * Github   : github.com/amirisback
 * LinkedIn : linkedin.com/in/faisalamircs
 * -----------------------------------------
 * FrogoBox Software Industries
 * com.frogobox.base.source.remote
 *
 */
interface ApiService {

    @GET(BuildConfig.TMDB_URL_MOVIE)
    fun getMovies(): Observable<BaseApiModel<List<Movie>>>

    @GET(BuildConfig.TMDB_URL_TV)
    fun getTvShow(): Observable<BaseApiModel<List<TvShow>>>

    @GET(BuildConfig.TMDB_URL_SEARCH_MOVIE)
    fun searchMovie(
        @Query("api_key") apiKey: String,
        @Query("language") language: String,
        @Query("query") query: String
    ): Observable<BaseApiModel<List<Movie>>>

    @GET(BuildConfig.TMDB_URL_SEARCH_TV_SHOW)
    fun searchTvShow(
        @Query("api_key") apiKey: String,
        @Query("language") language: String,
        @Query("query") query: String
    ): Observable<BaseApiModel<List<TvShow>>>

    @GET(BuildConfig.TMDB_URL_MOVIE)
    fun getReminderReleaseMovie(
        @Query("api_key") apiKey: String,
        @Query("primary_release_date.gte") dateGte: String,
        @Query("primary_release_date.lte") dateLte: String
    ): Observable<BaseApiModel<List<Movie>>>

    @GET(BuildConfig.TMDB_URL_MOVIE)
    fun getReminderReleaseOrdinary(
        @Query("api_key") apiKey: String,
        @Query("primary_release_date.gte") dateGte: String,
        @Query("primary_release_date.lte") dateLte: String
    ): Call<BaseApiModel<List<Movie>>>



    companion object Factory {

        val getApiService: ApiService by lazy {
            val mLoggingInterceptor = HttpLoggingInterceptor()
            mLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

            val cacheSize = (5 * 1024 * 1024).toLong()
            val appCache = Cache(BaseApplication.getContext().cacheDir, cacheSize)
            val mClient = if (BuildConfig.DEBUG) {
                OkHttpClient.Builder()
                    .cache(appCache)
//                    .addInterceptor(ChuckInterceptor(BaseApplication.getContext()))
                    .addInterceptor { chain ->
                        // Get the request from the chain.
                        var request = chain.request()

                        /*
                    *  Leveraging the advantage of using Kotlin,
                    *  we initialize the request and change its header depending on whether
                    *  the device is connected to Internet or not.
                    */
                        request =
                            if (Helper.Func.isNetworkAvailable(BaseApplication.getContext())!!)
                            /*
                        *  If there is Internet, get the cache that was stored 5 seconds ago.
                        *  If the cache is older than 5 seconds, then discard it,
                        *  and indicate an error in fetching the response.
                        *  The 'max-age' attribute is responsible for this behavior.
                        */
                                request.newBuilder().header(
                                    "Cache-Control",
                                    "public, max-age=" + 5
                                ).build()
                            else
                            /*
                        *  If there is no Internet, get the cache that was stored 7 days ago.
                        *  If the cache is older than 7 days, then discard it,
                        *  and indicate an error in fetching the response.
                        *  The 'max-stale' attribute is responsible for this behavior.
                        *  The 'only-if-cached' attribute indicates to not retrieve new data; fetch the cache only instead.
                        */
                                request.newBuilder().header(
                                    "Cache-Control",
                                    "public, only-if-cached, max-stale=" + 60 * 60 * 24 * 7
                                ).build()
                        // End of if-else statement

                        // Add the modified request to the chain.
                        chain.proceed(request)
                    }
                    .addInterceptor(mLoggingInterceptor)
//                    .addInterceptor(ChuckInterceptor(BaseApplication.getContext()))
                    .readTimeout(30, TimeUnit.SECONDS)
                    .connectTimeout(30, TimeUnit.SECONDS)
                    .build()
            } else {
                OkHttpClient.Builder()
                    .cache(appCache)
                    .addInterceptor { chain ->
                        var request = chain.request()
                        request =
                            if (Helper.Func.isNetworkAvailable(BaseApplication.getContext())!!)
                                request.newBuilder().header(
                                    "Cache-Control",
                                    "public, max-age=" + 5
                                ).build()
                            else
                                request.newBuilder().header(
                                    "Cache-Control",
                                    "public, only-if-cached, max-stale=" + 60 * 60 * 24 * 7
                                ).build()
                        chain.proceed(request)
                    }
                    .readTimeout(30, TimeUnit.SECONDS)
                    .connectTimeout(30, TimeUnit.SECONDS)
                    .build()
            }

            val mRetrofit = Retrofit.Builder()
                .baseUrl(BuildConfig.TMDB_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(mClient)
                .build()

            mRetrofit.create(ApiService::class.java)
        }
    }
}