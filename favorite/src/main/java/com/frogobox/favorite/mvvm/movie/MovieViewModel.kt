package com.frogobox.favorite.mvvm.movie

import android.app.Application
import android.database.Cursor
import com.frogobox.base.BaseViewModel
import com.frogobox.base.source.model.FavoriteMovie
import com.frogobox.base.source.DataSource
import com.frogobox.base.source.Repository
import com.frogobox.base.util.Constant.RoomDatabase.Movie.setupCursor
import com.frogobox.base.util.SingleLiveEvent

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
 * com.frogobox.favorite
 *
 */
class MovieViewModel(private val context: Application, private val repository: Repository) :
    BaseViewModel(context) {

    var favMovieListLive = SingleLiveEvent<List<FavoriteMovie>>()

    fun getFavoriteMovie() {
        repository.getFavoriteMovieCursor(context, object :
            DataSource.GetLocalCallBack<Cursor> {
            override fun onShowProgressDialog() {
                eventShowProgress.postValue(true)
            }

            override fun onHideProgressDialog() {
                eventShowProgress.postValue(false)
            }

            override fun onSuccess(data: Cursor) {
                favMovieListLive.postValue(setupCursor(data))
                eventIsEmpty.postValue(false)
            }

            override fun onFinish() {

            }

            override fun onEmpty() {
                eventIsEmpty.postValue(true)
            }

            override fun onFailed(statusCode: Int, errorMessage: String?) {

            }
        })
    }


}