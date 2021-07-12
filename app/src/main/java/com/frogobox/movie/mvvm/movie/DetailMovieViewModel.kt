package com.frogobox.movie.mvvm.movie

import android.app.Application
import com.frogobox.base.BaseViewModel
import com.frogobox.base.callback.DeleteViewCallback
import com.frogobox.base.callback.SaveViewCallback
import com.frogobox.base.source.model.FavoriteMovie
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
class DetailMovieViewModel(private val context: Application, private val repository: Repository) :
    BaseViewModel(context) {

    var favoriteMovie = SingleLiveEvent<FavoriteMovie>()
    var eventIsFavorite = SingleLiveEvent<Boolean>()

    fun saveFavoriteMovie(
        data: FavoriteMovie,
        callback: com.frogobox.base.callback.SaveViewCallback
    ) {
        callback.onShowProgress()
        if (repository.saveFavoriteMovie(context, data)) {
            callback.onHideProgress()
            callback.onSuccesInsert()
            eventIsEmpty.postValue(false)
            eventIsFavorite.postValue(true)
        } else {
            callback.onHideProgress()
            callback.onFailed("Failed")
        }
    }

    fun deleteFavoriteMovie(tableId: Int, callback: com.frogobox.base.callback.DeleteViewCallback) {
        callback.onShowProgress()
        if (repository.deleteFavoriteMovie(tableId)) {
            callback.onHideProgress()
            callback.onSuccesDelete()
            eventIsEmpty.postValue(true)
            eventIsFavorite.postValue(false)
        } else {
            callback.onHideProgress()
            callback.onFailed("Failed")
        }
    }

    fun getFavoriteMovie(id: Int) {
        repository.getFavoriteMovies(object :
            DataSource.GetLocalCallBack<List<FavoriteMovie>> {
            override fun onShowProgressDialog() {
                eventShowProgress.postValue(true)
            }

            override fun onHideProgressDialog() {
                eventShowProgress.postValue(false)
            }

            override fun onSuccess(data: List<FavoriteMovie>) {

                val tempFavoriteList = mutableListOf<FavoriteMovie>()
                tempFavoriteList.clear()
                tempFavoriteList.addAll(data)

                for (i in tempFavoriteList.indices) {
                    if (tempFavoriteList[i].id!! == id) {
                        eventIsEmpty.postValue(false)
                        eventIsFavorite.postValue(true)
                        favoriteMovie.postValue(tempFavoriteList[i])
                        break
                    } else {
                        eventIsFavorite.postValue(false)
                        eventIsEmpty.postValue(true)
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