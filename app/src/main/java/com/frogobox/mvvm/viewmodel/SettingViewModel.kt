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
class SettingViewModel(private val context: Application, private val repository: Repository) :
    BaseViewModel(context) {

    var eventStateReleaseReminder = SingleLiveEvent<Boolean>()
    var eventStateDailyReminder = SingleLiveEvent<Boolean>()

    fun savePrefReleaseReminder(state: Boolean) {
        repository.savePrefReleaseReminder(state)
    }

    fun savePrefDailyReminder(state: Boolean) {
        repository.savePrefDailyReminder(state)
    }

    fun deletePrefReleaseReminder() {
        repository.deletePrefReleaseReminder()
    }

    fun deletePrefDailyReminder() {
        repository.deletePrefDailyReminder()
    }

    fun getPref() {
        eventStateReleaseReminder.postValue(repository.getPrefReleaseReminder())
        eventStateDailyReminder.postValue(repository.getPrefDailyReminder())
    }

}