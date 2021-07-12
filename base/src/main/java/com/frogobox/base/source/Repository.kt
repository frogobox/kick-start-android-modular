package com.frogobox.base.source

import android.content.Context
import android.database.Cursor
import com.frogobox.base.source.model.FavoriteMovie
import com.frogobox.base.source.model.FavoriteTvShow
import com.frogobox.base.source.model.Movie
import com.frogobox.base.source.model.TvShow
import com.frogobox.base.source.local.LocalDataSource
import com.frogobox.base.source.remote.RemoteDataSource

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
open class Repository(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource
) :
    DataSource {

    override fun getMovies(callback: DataSource.GetRemoteCallback<List<Movie>>) {
        remoteDataSource.getMovies(callback)
    }

    override fun getTvShow(callback: DataSource.GetRemoteCallback<List<TvShow>>) {
        remoteDataSource.getTvShow(callback)
    }

    override fun searchMovies(query: String, callback: DataSource.GetRemoteCallback<List<Movie>>) {
        remoteDataSource.searchMovies(query, callback)
    }

    override fun searchTvShow(query: String, callback: DataSource.GetRemoteCallback<List<TvShow>>) {
        remoteDataSource.searchTvShow(query, callback)
    }

    override fun reminderReleaseMovie(
        dateGte: String,
        dateLte: String,
        callback: DataSource.GetRemoteCallback<List<Movie>>
    ) {
        remoteDataSource.reminderReleaseMovie(dateGte, dateLte, callback)
    }

    override fun saveFavoriteMovie(context: Context, data: FavoriteMovie): Boolean {
        return localDataSource.saveFavoriteMovie(context, data)
    }

    override fun saveFavoriteTvShow(context: Context, data: FavoriteTvShow): Boolean {
        return localDataSource.saveFavoriteTvShow(context, data)
    }

    override fun getFavoriteMovies(callback: DataSource.GetLocalCallBack<List<FavoriteMovie>>) {
        localDataSource.getFavoriteMovies(callback)
    }

    override fun getFavoriteTvShows(callback: DataSource.GetLocalCallBack<List<FavoriteTvShow>>) {
        localDataSource.getFavoriteTvShows(callback)
    }

    override fun getFavoriteMovieCursor(
        context: Context,
        callback: DataSource.GetLocalCallBack<Cursor>
    ) {
        localDataSource.getFavoriteMovieCursor(context, callback)
    }

    override fun getFavoriteTvShowCursor(
        context: Context,
        callback: DataSource.GetLocalCallBack<Cursor>
    ) {
        localDataSource.getFavoriteTvShowCursor(context, callback)
    }

    override fun getFavoriteMoviewCursorById(
        context: Context,
        id: Int,
        callback: DataSource.GetLocalCallBack<Cursor>
    ) {
        localDataSource.getFavoriteMoviewCursorById(context, id, callback)
    }

    override fun getFavoriteTvShowCursorById(
        context: Context,
        id: Int,
        callback: DataSource.GetLocalCallBack<Cursor>
    ) {
        localDataSource.getFavoriteTvShowCursorById(context, id, callback)
    }

    override fun deleteFavoriteMovie(tableId: Int): Boolean {
        return localDataSource.deleteFavoriteMovie(tableId)
    }

    override fun deleteFavoriteTvShow(tableId: Int): Boolean {
        return localDataSource.deleteFavoriteTvShow(tableId)
    }

    override fun deleteFavoriteMovieProvider(context: Context, tableId: Int): Boolean {
        return localDataSource.deleteFavoriteMovieProvider(context, tableId)
    }

    override fun deleteFavoriteTvShowProvider(context: Context, tableId: Int): Boolean {
        return localDataSource.deleteFavoriteTvShowProvider(context, tableId)
    }

    override fun nukeFavoriteMovies(): Boolean {
        return localDataSource.nukeFavoriteMovies()
    }

    override fun nukeFavoriteTvShows(): Boolean {
        return localDataSource.nukeFavoriteTvShows()
    }

    override fun savePrefReleaseReminder(state: Boolean) {
        localDataSource.savePrefReleaseReminder(state)
    }

    override fun savePrefDailyReminder(state: Boolean) {
        localDataSource.savePrefDailyReminder(state)
    }

    override fun getPrefReleaseReminder(): Boolean {
        return localDataSource.getPrefReleaseReminder()
    }

    override fun getPrefDailyReminder(): Boolean {
        return localDataSource.getPrefDailyReminder()
    }

    override fun deletePrefReleaseReminder() {
        localDataSource.deletePrefReleaseReminder()
    }

    override fun deletePrefDailyReminder() {
        localDataSource.deletePrefDailyReminder()
    }

    companion object {

        private var INSTANCE: Repository? = null

        /**
         * Returns the single instance of this class, creating it if necessary.

         * @param RemoteDataSource backend data source
         * *
         * @return the [Repository] instance
         */
        @JvmStatic
        fun getInstance(RemoteDataSource: RemoteDataSource, localDataSource: LocalDataSource) =
            INSTANCE
                ?: synchronized(Repository::class.java) {
                    INSTANCE
                        ?: Repository(RemoteDataSource, localDataSource)
                            .also { INSTANCE = it }
                }

        /**
         * Used to force [getInstance] to create a new instance
         * next time it's called.
         */
        @JvmStatic
        fun destroyInstance() {
            INSTANCE = null
        }
    }

}