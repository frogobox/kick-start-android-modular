package com.frogobox.movie.util

import android.annotation.SuppressLint
import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.frogobox.base.source.Repository
import com.frogobox.base.util.Injection
import com.frogobox.movie.mvvm.favorite.FavoriteMovieViewModel
import com.frogobox.movie.mvvm.favorite.FavoriteTvShowViewModel
import com.frogobox.movie.mvvm.main.MainActivityViewModel
import com.frogobox.movie.mvvm.main.MainViewModel
import com.frogobox.movie.mvvm.main.SettingViewModel
import com.frogobox.movie.mvvm.movie.DetailMovieViewModel
import com.frogobox.movie.mvvm.movie.SearchMovieViewModel
import com.frogobox.movie.mvvm.tv.DetailTvShowViewModel
import com.frogobox.movie.mvvm.tv.SearchTvShowViewModel

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
 * com.frogobox.movie.util
 *
 */
class ViewModelFactory private constructor(
    private val mApplication: Application,
    private val repository: Repository
) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        with(modelClass) {
            when {

                isAssignableFrom(MainActivityViewModel::class.java) ->
                    MainActivityViewModel(mApplication, repository)

                isAssignableFrom(MainViewModel::class.java) ->
                    MainViewModel(mApplication, repository)

                isAssignableFrom(DetailMovieViewModel::class.java) ->
                    DetailMovieViewModel(mApplication, repository)

                isAssignableFrom(DetailTvShowViewModel::class.java) ->
                    DetailTvShowViewModel(mApplication, repository)

                isAssignableFrom(FavoriteMovieViewModel::class.java) ->
                    FavoriteMovieViewModel(mApplication, repository)

                isAssignableFrom(FavoriteTvShowViewModel::class.java) ->
                    FavoriteTvShowViewModel(mApplication, repository)

                isAssignableFrom(SearchMovieViewModel::class.java) ->
                    SearchMovieViewModel(mApplication, repository)

                isAssignableFrom(SearchTvShowViewModel::class.java) ->
                    SearchTvShowViewModel(mApplication, repository)

                isAssignableFrom(SettingViewModel::class.java) ->
                    SettingViewModel(mApplication, repository)

                else ->
                    throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
            }
        } as T

    companion object {

        @SuppressLint("StaticFieldLeak")
        @Volatile private var INSTANCE: ViewModelFactory? = null

        fun getInstance(mApplication: Application) =
            INSTANCE
                ?: synchronized(ViewModelFactory::class.java) {
                    INSTANCE
                        ?: ViewModelFactory(
                            mApplication,
                            Injection.provideRepository(mApplication.applicationContext)
                        )
                            .also { INSTANCE = it }
                }
    }
}
