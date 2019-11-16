package com.frogobox.favorite.viewmodel

import android.app.Application
import android.database.Cursor
import com.frogobox.base.BaseViewModel
import com.frogobox.base.modular.callback.DeleteViewCallback
import com.frogobox.base.modular.callback.SaveViewCallback
import com.frogobox.base.modular.model.FavoriteMovie
import com.frogobox.base.modular.source.DataSource
import com.frogobox.base.modular.source.Repository
import com.frogobox.base.util.Constant.RoomDatabase.Movie.setupCursor
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
class DetailMovieViewModel (private val context: Application, private val repository: Repository) :
    BaseViewModel(context) {

    var favoriteMovie = SingleLiveEvent<FavoriteMovie>()
    var eventIsFavorite = SingleLiveEvent<Boolean>()

    fun saveFavoriteMovie(
        data: FavoriteMovie,
        callback: SaveViewCallback
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

    fun deleteFavoriteMovie(tableId: Int, callback: DeleteViewCallback) {
        callback.onShowProgress()
        if (repository.deleteFavoriteMovieProvider(context, tableId)) {
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
        repository.getFavoriteMovieCursor(context, object :
            DataSource.GetLocalCallBack<Cursor> {
            override fun onShowProgressDialog() {
                eventShowProgress.postValue(true)
            }

            override fun onHideProgressDialog() {
                eventShowProgress.postValue(false)
            }

            override fun onSuccess(data: Cursor) {

                val tempFavoriteList = mutableListOf<FavoriteMovie>()
                tempFavoriteList.clear()
                tempFavoriteList.addAll(setupCursor(data))

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