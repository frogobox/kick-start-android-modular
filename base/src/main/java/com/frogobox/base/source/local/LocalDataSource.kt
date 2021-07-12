package com.frogobox.base.source.local

import android.content.ContentValues
import android.content.Context
import android.content.SharedPreferences
import android.database.Cursor
import androidx.annotation.VisibleForTesting
import com.frogobox.base.BaseCallback
import com.frogobox.base.source.model.FavoriteMovie
import com.frogobox.base.source.model.FavoriteTvShow
import com.frogobox.base.source.model.Movie
import com.frogobox.base.source.model.TvShow
import com.frogobox.base.source.DataSource
import com.frogobox.base.source.local.dao.FavoriteMovieDao
import com.frogobox.base.source.local.dao.FavoriteTvShowDao
import com.frogobox.base.util.AppExecutors
import com.frogobox.base.util.Constant
import com.frogobox.base.util.Constant.Pref.PREF_DAILY_REMINDER
import com.frogobox.base.util.Constant.Pref.PREF_RELEASE_REMINDER
import com.frogobox.base.util.Helper.Preference.Delete.deletePref
import com.frogobox.base.util.Helper.Preference.Load.loadPrefBoolean
import com.frogobox.base.util.Helper.Preference.Save.savePrefBoolean
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

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
 * com.frogobox.base.source.local
 *
 */
class LocalDataSource(
    private val appExecutors: AppExecutors,
    private val sharedPreferences: SharedPreferences,
    private val favoriteMovieDao: FavoriteMovieDao,
    private val favoriteTvShowDao: FavoriteTvShowDao
) : DataSource {

    override fun getMovies(callback: DataSource.GetRemoteCallback<List<Movie>>) {}

    override fun getTvShow(callback: DataSource.GetRemoteCallback<List<TvShow>>) {}

    override fun searchMovies(query: String, callback: DataSource.GetRemoteCallback<List<Movie>>) {}

    override fun searchTvShow(
        query: String,
        callback: DataSource.GetRemoteCallback<List<TvShow>>
    ) {
    }

    override fun reminderReleaseMovie(
        dateGte: String,
        dateLte: String,
        callback: DataSource.GetRemoteCallback<List<Movie>>
    ) {
    }

    override fun saveFavoriteMovie(context: Context, data: FavoriteMovie): Boolean {
        appExecutors.diskIO.execute {
            val contentValues = ContentValues()
            contentValues.put(Constant.RoomDatabase.Movie._ID, data.table_id)
            contentValues.put(Constant.RoomDatabase.Movie.COLUMN_ID, data.id)
            contentValues.put(Constant.RoomDatabase.Movie.COLUMN_TITLE, data.title)
            contentValues.put(Constant.RoomDatabase.Movie.COLUMN_POSTER_PATH, data.poster_path)
            contentValues.put(Constant.RoomDatabase.Movie.COLUMN_BACKDROP_PATH, data.backdrop_path)
            contentValues.put(Constant.RoomDatabase.Movie.COLUMN_OVERVIEW, data.overview)

            val uri = context.contentResolver.insert(
                Constant.RoomDatabase.Movie.CONTENT_URI,
                contentValues
            )!!

        }
        return true
    }

    override fun saveFavoriteTvShow(context: Context, data: FavoriteTvShow): Boolean {
        appExecutors.diskIO.execute {
            val contentValues = ContentValues()
            contentValues.put(Constant.RoomDatabase.TvShow._ID, data.table_id)
            contentValues.put(Constant.RoomDatabase.TvShow.COLUMN_ID, data.id)
            contentValues.put(Constant.RoomDatabase.TvShow.COLUMN_NAME, data.name)
            contentValues.put(Constant.RoomDatabase.TvShow.COLUMN_POSTER_PATH, data.poster_path)
            contentValues.put(Constant.RoomDatabase.TvShow.COLUMN_BACKDROP_PATH, data.backdrop_path)
            contentValues.put(Constant.RoomDatabase.TvShow.COLUMN_OVERVIEW, data.overview)

            val uri = context.contentResolver.insert(
                Constant.RoomDatabase.TvShow.CONTENT_URI,
                contentValues
            )!!
        }
        return true
    }

    override fun getFavoriteMovies(callback: DataSource.GetLocalCallBack<List<FavoriteMovie>>) {
        appExecutors.diskIO.execute {
            favoriteMovieDao.getAllData()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : BaseCallback<List<FavoriteMovie>>() {
                    override fun onCallbackSucces(data: List<FavoriteMovie>) {
                        callback.onShowProgressDialog()
                        callback.onSuccess(data)
                        if (data.size == 0) {
                            callback.onEmpty()
                        }
                        callback.onHideProgressDialog()
                    }

                    override fun onCallbackError(code: Int, errorMessage: String) {
                        callback.onFailed(code, errorMessage)
                    }

                    override fun onAddSubscribe(disposable: Disposable) {
                        addSubscribe(disposable = disposable)
                    }

                    override fun onCompleted() {
                        callback.onHideProgressDialog()
                    }
                })
        }
    }

    override fun getFavoriteTvShows(callback: DataSource.GetLocalCallBack<List<FavoriteTvShow>>) {
        appExecutors.diskIO.execute {
            favoriteTvShowDao.getAllData()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : BaseCallback<List<FavoriteTvShow>>() {
                    override fun onCallbackSucces(data: List<FavoriteTvShow>) {
                        callback.onShowProgressDialog()
                        callback.onSuccess(data)
                        if (data.size == 0) {
                            callback.onEmpty()
                        }
                        callback.onHideProgressDialog()
                    }

                    override fun onCallbackError(code: Int, errorMessage: String) {
                        callback.onFailed(code, errorMessage)
                    }

                    override fun onAddSubscribe(disposable: Disposable) {
                        addSubscribe(disposable = disposable)
                    }

                    override fun onCompleted() {
                        callback.onHideProgressDialog()
                    }
                })
        }
    }

    override fun getFavoriteMovieCursor(
        context: Context,
        callback: DataSource.GetLocalCallBack<Cursor>
    ) {
        appExecutors.diskIO.execute {
            callback.onShowProgressDialog()
            val cursor = context.contentResolver.query(
                Constant.RoomDatabase.Movie.CONTENT_URI,
                Constant.RoomDatabase.Movie.PROJECTION,
                null, null, null
            )

            if (cursor != null) {
                if (cursor.count == 0) {
                    callback.onHideProgressDialog()
                    callback.onEmpty()
                } else {
                    callback.onHideProgressDialog()
                    cursor.let { callback.onSuccess(it) }
                }
            }

            cursor?.close()
        }
    }

    override fun getFavoriteTvShowCursor(
        context: Context,
        callback: DataSource.GetLocalCallBack<Cursor>
    ) {

        appExecutors.diskIO.execute {
            callback.onShowProgressDialog()
            val cursor = context.contentResolver.query(
                Constant.RoomDatabase.TvShow.CONTENT_URI,
                Constant.RoomDatabase.TvShow.PROJECTION,
                null, null, null
            )

            if (cursor != null) {
                if (cursor.count == 0) {
                    callback.onHideProgressDialog()
                    callback.onEmpty()
                } else {
                    callback.onHideProgressDialog()
                    cursor.let { callback.onSuccess(it) }
                }
            }
            cursor?.close()
        }
    }

    override fun getFavoriteMoviewCursorById(
        context: Context,
        id: Int,
        callback: DataSource.GetLocalCallBack<Cursor>
    ) {

        appExecutors.diskIO.execute {
            callback.onShowProgressDialog()
            val cursor = context.contentResolver.query(
                Constant.RoomDatabase.Movie.CONTENT_URI,
                Constant.RoomDatabase.Movie.PROJECTION,
                Constant.RoomDatabase.Movie.SELECTION(id), null, null
            )

            if (cursor != null) {
                if (cursor.count == 0) {
                    callback.onHideProgressDialog()
                    callback.onEmpty()
                } else {
                    callback.onHideProgressDialog()
                    cursor.let { callback.onSuccess(it) }
                }
            }
            cursor?.close()
        }
    }

    override fun getFavoriteTvShowCursorById(
        context: Context,
        id: Int,
        callback: DataSource.GetLocalCallBack<Cursor>
    ) {
        appExecutors.diskIO.execute {
            callback.onShowProgressDialog()
            val cursor = context.contentResolver.query(
                Constant.RoomDatabase.TvShow.CONTENT_URI,
                Constant.RoomDatabase.TvShow.PROJECTION,
                Constant.RoomDatabase.TvShow.SELECTION(id), null, null
            )

            if (cursor != null) {
                if (cursor.count == 0) {
                    callback.onHideProgressDialog()
                    callback.onEmpty()
                } else {
                    callback.onHideProgressDialog()
                    cursor.let { callback.onSuccess(it) }
                }
            }
            cursor?.close()
        }
    }

    override fun deleteFavoriteMovie(tableId: Int): Boolean {
        appExecutors.diskIO.execute {
            favoriteMovieDao.deleteDataFromTableId(tableId)
        }
        return true
    }

    override fun deleteFavoriteTvShow(tableId: Int): Boolean {
        appExecutors.diskIO.execute {
            favoriteTvShowDao.deleteDataFromTableId(tableId)
        }
        return true
    }

    override fun deleteFavoriteMovieProvider(context: Context, tableId: Int): Boolean {
        appExecutors.diskIO.execute {
            val resultUri = context.contentResolver.delete(
                Constant.RoomDatabase.Movie.CONTENT_URI,
                Constant.RoomDatabase.Movie.SELECTION(tableId),
                null
            )
        }

        return true
    }

    override fun deleteFavoriteTvShowProvider(context: Context, tableId: Int): Boolean {
        appExecutors.diskIO.execute {
            val resultUri = context.contentResolver.delete(
                Constant.RoomDatabase.TvShow.CONTENT_URI,
                Constant.RoomDatabase.TvShow.SELECTION(tableId),
                null
            )
        }
        return true
    }

    override fun nukeFavoriteMovies(): Boolean {
        appExecutors.diskIO.execute {
            favoriteMovieDao.nukeData()
        }
        return true
    }


    override fun nukeFavoriteTvShows(): Boolean {
        appExecutors.diskIO.execute {
            favoriteTvShowDao.nukeData()
        }
        return true
    }


    override fun savePrefReleaseReminder(state: Boolean) {
        savePrefBoolean(sharedPreferences, PREF_RELEASE_REMINDER, state)
    }

    override fun savePrefDailyReminder(state: Boolean) {
        savePrefBoolean(sharedPreferences, PREF_DAILY_REMINDER, state)
    }

    override fun getPrefReleaseReminder(): Boolean {
        return loadPrefBoolean(sharedPreferences, PREF_RELEASE_REMINDER)
    }

    override fun getPrefDailyReminder(): Boolean {
        return loadPrefBoolean(sharedPreferences, PREF_DAILY_REMINDER)
    }

    override fun deletePrefReleaseReminder() {
        deletePref(sharedPreferences, PREF_RELEASE_REMINDER)
    }

    override fun deletePrefDailyReminder() {
        deletePref(sharedPreferences, PREF_DAILY_REMINDER)
    }

    private var compositeDisposable: CompositeDisposable? = null

    fun addSubscribe(disposable: Disposable) {
        if (compositeDisposable == null) {
            compositeDisposable = CompositeDisposable()

            compositeDisposable?.add(disposable)
        }
    }

    /**
     * Clear all subscribers active in app
     */
    private fun clearSubscribe() {
        if (compositeDisposable != null) {
            compositeDisposable?.clear()
        }
    }

    companion object {

        private var INSTANCE: LocalDataSource? = null

        @JvmStatic
        fun getInstance(
            appExecutors: AppExecutors,
            sharedPreferences: SharedPreferences,
            favoriteMovieDao: FavoriteMovieDao,
            favoriteTvShowDao: FavoriteTvShowDao

        ): LocalDataSource {
            if (INSTANCE == null) {
                synchronized(LocalDataSource::javaClass) {
                    INSTANCE =
                        LocalDataSource(
                            appExecutors,
                            sharedPreferences,
                            favoriteMovieDao,
                            favoriteTvShowDao
                        )
                }
            }
            return INSTANCE!!
        }

        @VisibleForTesting
        fun clearInstance() {
            INSTANCE = null
        }
    }

}