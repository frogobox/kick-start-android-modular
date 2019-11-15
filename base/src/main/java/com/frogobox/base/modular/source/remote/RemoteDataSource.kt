package com.frogobox.base.modular.source.remote

import android.content.Context
import android.database.Cursor
import com.frogobox.base.BaseApiModel
import com.frogobox.base.BuildConfig
import com.frogobox.base.modular.model.FavoriteMovie
import com.frogobox.base.modular.model.FavoriteTvShow
import com.frogobox.base.modular.model.Movie
import com.frogobox.base.modular.model.TvShow
import com.frogobox.base.modular.source.DataSource
import com.frogobox.base.util.NullAdapter
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

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
 * com.frogobox.base.modular.source.remote
 *
 */
object RemoteDataSource : DataSource {

    private val movieListAdapter: Gson by lazy {
        GsonBuilder()
            .registerTypeAdapter(Movie::class.java, NullAdapter())
            .create()
    }

    private val tvShowListAdapter: Gson by lazy {
        GsonBuilder()
            .registerTypeAdapter(TvShow::class.java, NullAdapter())
            .create()
    }

    override fun getTvShow(callback: DataSource.GetRemoteCallback<List<TvShow>>) {
        ApiService.getApiService
            .getTvShow()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { callback.onShowProgressDialog() }
            .doOnTerminate { callback.onHideProgressDialog() }
            .subscribe(object : ApiCallback<BaseApiModel<List<TvShow>>>() {
                override fun onSuccess(model: BaseApiModel<List<TvShow>>) {
                    val oldData = model.results
                    val newData = ArrayList<TvShow>()

                    for (i in 0 until oldData!!.size) {
                        newData.add(
                            Gson().fromJson(
                                tvShowListAdapter.toJson(oldData[i]),
                                TvShow::class.java
                            )
                        )
                    }
                    callback.onSuccess(newData)
                }

                override fun onFailure(code: Int, errorMessage: String) {
                    callback.onFailed(code, errorMessage)
                }

                override fun onFinish() {
                    callback.onFinish()
                }
            })

    }

    override fun getMovies(callback: DataSource.GetRemoteCallback<List<Movie>>) {
        ApiService.getApiService
            .getMovies()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { callback.onShowProgressDialog() }
            .doOnTerminate { callback.onHideProgressDialog() }
            .subscribe(object : ApiCallback<BaseApiModel<List<Movie>>>() {
                override fun onSuccess(model: BaseApiModel<List<Movie>>) {
                    val oldData = model.results
                    val newData = ArrayList<Movie>()

                    for (i in 0 until oldData!!.size) {
                        newData.add(
                            Gson().fromJson(
                                movieListAdapter.toJson(oldData[i]),
                                Movie::class.java
                            )
                        )
                    }
                    callback.onSuccess(newData)
                }

                override fun onFailure(code: Int, errorMessage: String) {
                    callback.onFailed(code, errorMessage)
                }

                override fun onFinish() {
                    callback.onFinish()
                }
            })
    }

    override fun searchMovies(query: String, callback: DataSource.GetRemoteCallback<List<Movie>>) {
        ApiService.getApiService
            .searchMovie(BuildConfig.TMDB_API_KEY, BuildConfig.TMDB_BASE_LANG, query)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { callback.onShowProgressDialog() }
            .doOnTerminate { callback.onHideProgressDialog() }
            .subscribe(object : ApiCallback<BaseApiModel<List<Movie>>>() {
                override fun onSuccess(model: BaseApiModel<List<Movie>>) {
                    val oldData = model.results
                    val newData = ArrayList<Movie>()

                    for (i in 0 until oldData!!.size) {
                        newData.add(
                            Gson().fromJson(
                                movieListAdapter.toJson(oldData[i]),
                                Movie::class.java
                            )
                        )
                    }
                    callback.onSuccess(newData)
                }

                override fun onFailure(code: Int, errorMessage: String) {
                    callback.onFailed(code, errorMessage)
                }

                override fun onFinish() {
                    callback.onFinish()
                }
            })
    }

    override fun searchTvShow(query: String, callback: DataSource.GetRemoteCallback<List<TvShow>>) {

        ApiService.getApiService
            .searchTvShow(BuildConfig.TMDB_API_KEY, BuildConfig.TMDB_BASE_LANG, query)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { callback.onShowProgressDialog() }
            .doOnTerminate { callback.onHideProgressDialog() }
            .subscribe(object : ApiCallback<BaseApiModel<List<TvShow>>>() {
                override fun onSuccess(model: BaseApiModel<List<TvShow>>) {
                    val oldData = model.results
                    val newData = ArrayList<TvShow>()

                    for (i in 0 until oldData!!.size) {
                        newData.add(
                            Gson().fromJson(
                                tvShowListAdapter.toJson(oldData[i]),
                                TvShow::class.java
                            )
                        )
                    }
                    callback.onSuccess(newData)
                }

                override fun onFailure(code: Int, errorMessage: String) {
                    callback.onFailed(code, errorMessage)
                }

                override fun onFinish() {
                    callback.onFinish()
                }
            })


    }

    override fun reminderReleaseMovie(
        dateGte: String,
        dateLte: String,
        callback: DataSource.GetRemoteCallback<List<Movie>>
    ) {
        ApiService.getApiService
            .getReminderReleaseMovie(BuildConfig.TMDB_API_KEY, dateGte, dateLte)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { callback.onShowProgressDialog() }
            .doOnTerminate { callback.onHideProgressDialog() }
            .subscribe(object : ApiCallback<BaseApiModel<List<Movie>>>() {
                override fun onSuccess(model: BaseApiModel<List<Movie>>) {
                    val oldData = model.results
                    val newData = ArrayList<Movie>()

                    for (i in 0 until oldData!!.size) {
                        newData.add(
                            Gson().fromJson(
                                movieListAdapter.toJson(oldData[i]),
                                Movie::class.java
                            )
                        )
                    }
                    callback.onSuccess(newData)
                }

                override fun onFailure(code: Int, errorMessage: String) {
                    callback.onFailed(code, errorMessage)
                }

                override fun onFinish() {
                    callback.onFinish()
                }
            })
    }


    override fun saveFavoriteMovie(context: Context, data: FavoriteMovie): Boolean {
        return false
    }

    override fun saveFavoriteTvShow(context: Context, data: FavoriteTvShow): Boolean {
        return false
    }

    override fun getFavoriteMovieCursor(
        context: Context,
        callback: DataSource.GetLocalCallBack<Cursor>
    ) {
    }

    override fun getFavoriteTvShowCursor(
        context: Context,
        callback: DataSource.GetLocalCallBack<Cursor>
    ) {
    }

    override fun getFavoriteMovies(callback: DataSource.GetLocalCallBack<List<FavoriteMovie>>) {
    }

    override fun getFavoriteTvShows(callback: DataSource.GetLocalCallBack<List<FavoriteTvShow>>) {
    }

    override fun getFavoriteMoviewCursorById(
        context: Context,
        id: Int,
        callback: DataSource.GetLocalCallBack<Cursor>
    ) {}

    override fun getFavoriteTvShowCursorById(
        context: Context,
        id: Int,
        callback: DataSource.GetLocalCallBack<Cursor>
    ) {}

    override fun deleteFavoriteMovie(tableId: Int): Boolean {
        return false
    }

    override fun deleteFavoriteTvShow(tableId: Int): Boolean {
        return false
    }

    override fun deleteFavoriteMovieProvider(context: Context, tableId: Int): Boolean {
        return false
    }

    override fun deleteFavoriteTvShowProvider(context: Context, tableId: Int): Boolean {
        return false
    }

    override fun nukeFavoriteMovies(): Boolean {
        return false
    }

    override fun nukeFavoriteTvShows(): Boolean {
        return false
    }

    override fun savePrefReleaseReminder(state: Boolean) {}

    override fun savePrefDailyReminder(state: Boolean) {}

    override fun getPrefReleaseReminder(): Boolean {
        return true
    }

    override fun getPrefDailyReminder(): Boolean {
        return true
    }

    override fun deletePrefReleaseReminder() {}

    override fun deletePrefDailyReminder() {}
}