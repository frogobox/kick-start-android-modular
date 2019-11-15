package com.frogobox.base.util

import android.content.Context
import android.preference.PreferenceManager

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
 * com.frogobox.base.util
 *
 */
object Injection {

    fun provideRepository(context: Context): Repository {

        val favoriteMovieDao: FavoriteMovieDao by lazy {
            AppDatabase.getInstance(context).favoriteMovieDao()
        }

        val favoriteTvShowDao: FavoriteTvShowDao by lazy {
            AppDatabase.getInstance(context).favoriteTvShowDao()
        }

        val appExecutors = AppExecutors()

        return Repository.getInstance(
            RemoteDataSource, LocalDataSource.getInstance(
                appExecutors,
                PreferenceManager.getDefaultSharedPreferences(context),
                favoriteMovieDao, favoriteTvShowDao))
    }
}