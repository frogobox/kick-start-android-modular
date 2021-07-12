package com.frogobox.movie.mvvm.tv

import android.app.Application
import com.frogobox.base.BaseViewModel
import com.frogobox.base.callback.DeleteViewCallback
import com.frogobox.base.callback.SaveViewCallback
import com.frogobox.base.source.model.FavoriteTvShow
import com.frogobox.base.source.DataSource
import com.frogobox.base.source.Repository
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
 * com.frogobox.movie.viewmodel
 *
 */
class DetailTvShowViewModel(private val context: Application, private val repository: Repository) :
    BaseViewModel(context) {

    var favoriteTvShow = SingleLiveEvent<FavoriteTvShow>()
    var eventIsFavorite = SingleLiveEvent<Boolean>()

    fun saveFavoriteTvShow(data: FavoriteTvShow, callback: com.frogobox.base.callback.SaveViewCallback){
        callback.onShowProgress()
        if (repository.saveFavoriteTvShow(context, data)){
            callback.onHideProgress()
            callback.onSuccesInsert()
            eventIsFavorite.postValue(true)
        } else {
            callback.onHideProgress()
            callback.onFailed("Failed")
        }
    }

    fun deleteFavoriteTvShow(tableId: Int, callback: com.frogobox.base.callback.DeleteViewCallback){
        callback.onShowProgress()
        if (repository.deleteFavoriteTvShow(tableId)){
            callback.onHideProgress()
            callback.onSuccesDelete()
            eventIsFavorite.postValue(false)
        } else {
            callback.onHideProgress()
            callback.onFailed("Failed")
        }
    }

    fun getFavoriteTvShow(id: Int){
        repository.getFavoriteTvShows(object : DataSource.GetLocalCallBack<List<FavoriteTvShow>>{
            override fun onShowProgressDialog() {
                eventShowProgress.postValue(true)
            }

            override fun onHideProgressDialog() {
                eventShowProgress.postValue(false)
            }

            override fun onSuccess(data: List<FavoriteTvShow>) {

                val tempFavoriteList = mutableListOf<FavoriteTvShow>()
                tempFavoriteList.clear()
                tempFavoriteList.addAll(data)

                for (i in tempFavoriteList.indices) {
                    if (tempFavoriteList[i].id!! == id) {
                        eventIsEmpty.postValue(false)
                        eventIsFavorite.postValue(true)
                        favoriteTvShow.postValue(tempFavoriteList[i])
                        break
                    } else {
                        eventIsEmpty.postValue(true)
                        eventIsFavorite.postValue(false)
                    }
                }
            }

            override fun onFinish() {}

            override fun onEmpty() {
                eventIsEmpty.postValue(true)
                eventIsFavorite.postValue(false)
            }

            override fun onFailed(statusCode: Int, errorMessage: String?) {}
        })
    }

}