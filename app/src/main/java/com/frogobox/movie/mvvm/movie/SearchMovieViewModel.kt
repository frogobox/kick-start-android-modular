package com.frogobox.movie.mvvm.movie

import android.app.Application
import com.frogobox.base.BaseViewModel
import com.frogobox.base.source.model.Movie
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
class SearchMovieViewModel(private val context: Application, private val repository: Repository) :
    BaseViewModel(context) {

    var movieListLive = SingleLiveEvent<List<Movie>>()

    fun searchMovies(query: String) {
        repository.searchMovies(query, object : DataSource.GetRemoteCallback<List<Movie>> {
            override fun onEmpty() {

            }

            override fun onShowProgressDialog() {
                eventShowProgress.postValue(true)
            }

            override fun onHideProgressDialog() {
                eventShowProgress.postValue(false)
            }

            override fun onSuccess(data: List<Movie>) {
                movieListLive.postValue(data)
            }

            override fun onFinish() {

            }

            override fun onFailed(statusCode: Int, errorMessage: String?) {

            }
        })
    }

}