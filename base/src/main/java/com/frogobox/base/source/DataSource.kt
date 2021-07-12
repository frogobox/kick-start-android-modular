package com.frogobox.base.source

import android.content.Context
import android.database.Cursor
import com.frogobox.base.BaseDataSource
import com.frogobox.base.source.model.FavoriteMovie
import com.frogobox.base.source.model.FavoriteTvShow
import com.frogobox.base.source.model.Movie
import com.frogobox.base.source.model.TvShow

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
 * com.frogobox.base.source
 *
 */
interface DataSource : BaseDataSource {

    // API TMDB ------------------------------------------------------------------------------------
    // Get
    fun getMovies(callback: GetRemoteCallback<List<Movie>>)
    fun getTvShow(callback: GetRemoteCallback<List<TvShow>>)

    // Search
    fun searchMovies(query: String, callback: GetRemoteCallback<List<Movie>>)
    fun searchTvShow(query: String, callback: GetRemoteCallback<List<TvShow>>)

    // Reminder
    fun reminderReleaseMovie(
        dateGte: String,
        dateLte: String,
        callback: GetRemoteCallback<List<Movie>>
    )

    // Room Database -------------------------------------------------------------------------------
    // Create
    fun saveFavoriteMovie(context: Context, data: FavoriteMovie): Boolean
    fun saveFavoriteTvShow(context: Context, data: FavoriteTvShow): Boolean

    // Read
    fun getFavoriteMovies(callback: GetLocalCallBack<List<FavoriteMovie>>)
    fun getFavoriteTvShows(callback: GetLocalCallBack<List<FavoriteTvShow>>)

    fun getFavoriteMovieCursor(context: Context, callback: GetLocalCallBack<Cursor>)
    fun getFavoriteTvShowCursor(context: Context, callback: GetLocalCallBack<Cursor>)

    fun getFavoriteMoviewCursorById(context: Context, id: Int, callback: GetLocalCallBack<Cursor>)
    fun getFavoriteTvShowCursorById(context: Context, id: Int, callback: GetLocalCallBack<Cursor>)

    // Delete
    fun deleteFavoriteMovie(tableId: Int): Boolean
    fun deleteFavoriteTvShow(tableId: Int): Boolean

    fun deleteFavoriteMovieProvider(context: Context, tableId: Int): Boolean
    fun deleteFavoriteTvShowProvider(context: Context, tableId: Int): Boolean

    // Clear
    fun nukeFavoriteMovies(): Boolean
    fun nukeFavoriteTvShows(): Boolean

    // Shared Preference ---------------------------------------------------------------------------

    // Create
    fun savePrefReleaseReminder(state: Boolean)
    fun savePrefDailyReminder(state: Boolean)

    // Read
    fun getPrefReleaseReminder(): Boolean
    fun getPrefDailyReminder(): Boolean

    // Delete
    fun deletePrefReleaseReminder()
    fun deletePrefDailyReminder()

    // Callback ------------------------------------------------------------------------------------
    interface GetRemoteCallback<T> : BaseDataSource.ResponseCallback<T>
    interface GetLocalCallBack<T> : BaseDataSource.ResponseCallback<T>

}