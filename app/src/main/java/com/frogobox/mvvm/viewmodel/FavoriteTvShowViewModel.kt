package com.frogobox.mvvm.viewmodel

import android.app.Application
import com.frogobox.base.BaseViewModel
import com.frogobox.base.modular.model.FavoriteTvShow
import com.frogobox.base.modular.source.DataSource
import com.frogobox.base.modular.source.Repository
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
 * com.frogobox.mvvm.viewmodel
 *
 */
class FavoriteTvShowViewModel(private val context: Application, private val repository: Repository) :
    BaseViewModel(context){

    var favTvShowListLive = SingleLiveEvent<List<FavoriteTvShow>>()

    fun getFavoriteTvShow(){
        repository.getFavoriteTvShows(object : DataSource.GetLocalCallBack<List<FavoriteTvShow>>{
            override fun onShowProgressDialog() {
                eventShowProgress.postValue(true)
            }

            override fun onHideProgressDialog() {
                eventShowProgress.postValue(false)
            }

            override fun onSuccess(data: List<FavoriteTvShow>) {
                eventIsEmpty.postValue(false)
                favTvShowListLive.postValue(data)
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