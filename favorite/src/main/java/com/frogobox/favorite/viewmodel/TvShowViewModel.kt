package com.frogobox.favorite.viewmodel

import android.app.Application
import android.database.Cursor
import com.frogobox.base.BaseViewModel
import com.frogobox.base.modular.model.FavoriteTvShow
import com.frogobox.base.modular.source.DataSource
import com.frogobox.base.modular.source.Repository
import com.frogobox.base.util.Constant.RoomDatabase.TvShow.setupCursor
import com.frogobox.base.util.SingleLiveEvent

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
class TvShowViewModel(private val context: Application, private val repository: Repository) :
    BaseViewModel(context) {

    var favTvShowListLive = SingleLiveEvent<List<FavoriteTvShow>>()

    fun getFavoriteTvShow() {
        repository.getFavoriteTvShowCursor(context, object : DataSource.GetLocalCallBack<Cursor> {
            override fun onShowProgressDialog() {
                eventShowProgress.postValue(true)
            }

            override fun onHideProgressDialog() {
                eventShowProgress.postValue(false)
            }

            override fun onSuccess(data: Cursor) {
                eventIsEmpty.postValue(false)
                favTvShowListLive.postValue(setupCursor(data))
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