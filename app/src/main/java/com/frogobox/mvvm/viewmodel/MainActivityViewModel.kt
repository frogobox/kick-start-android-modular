package com.frogobox.mvvm.viewmodel

import android.app.Application
import com.frogobox.base.BaseViewModel
import com.frogobox.base.modular.model.Movie
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
class MainActivityViewModel(private val context: Application, private val repository: Repository) :
    BaseViewModel(context) {

    var eventStateReleaseReminder = SingleLiveEvent<Boolean>()
    var eventStateDailyReminder = SingleLiveEvent<Boolean>()
    var reminderLive = SingleLiveEvent<List<Movie>>()

    fun getReleaseReminder(dateGte: String, dateLte: String){
        repository.reminderReleaseMovie(dateGte, dateLte, object : DataSource.GetRemoteCallback<List<Movie>>{
            override fun onEmpty() {}

            override fun onShowProgressDialog() {
                eventShowProgress.postValue(true)
            }

            override fun onHideProgressDialog() {
                eventShowProgress.postValue(false)
            }

            override fun onSuccess(data: List<Movie>) {
                reminderLive.postValue(data)
            }

            override fun onFinish() {}

            override fun onFailed(statusCode: Int, errorMessage: String?) {}
        })
    }

    fun getOprionReminder() {
        eventStateReleaseReminder.postValue(repository.getPrefReleaseReminder())
        eventStateDailyReminder.postValue(repository.getPrefDailyReminder())
    }

}