package com.frogobox.favorite.util

import android.annotation.SuppressLint
import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.frogobox.base.modular.source.Repository
import com.frogobox.base.util.Injection
import com.frogobox.favorite.viewmodel.DetailMovieViewModel
import com.frogobox.favorite.viewmodel.DetailTvShowViewModel
import com.frogobox.favorite.viewmodel.MovieViewModel
import com.frogobox.favorite.viewmodel.TvShowViewModel

/**
 * Created by Faisal Amir
 * FrogoBox Inc License
 * =========================================
 * mvvm
 * Copyright (C) 16/11/2019.
 * All rights reserved
 * -----------------------------------------
 * Name     : Muhammad Faisal Amir
 * E-mail   : faisalamircs@gmail.com
 * Github   : github.com/amirisback
 * LinkedIn : linkedin.com/in/faisalamircs
 * -----------------------------------------
 * FrogoBox Software Industries
 * com.frogobox.favorite
 *
 */
class ViewModelFactory private constructor(
    private val mApplication: Application,
    private val repository: Repository
) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>) =
        with(modelClass) {
            when {

                isAssignableFrom(MovieViewModel::class.java) ->
                    MovieViewModel(mApplication, repository)

                isAssignableFrom(TvShowViewModel::class.java) ->
                    TvShowViewModel(mApplication, repository)

                isAssignableFrom(DetailMovieViewModel::class.java) ->
                    DetailMovieViewModel(mApplication, repository)

                isAssignableFrom(DetailTvShowViewModel::class.java) ->
                    DetailTvShowViewModel(mApplication, repository)


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