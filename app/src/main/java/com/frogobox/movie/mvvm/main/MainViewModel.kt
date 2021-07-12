package com.frogobox.movie.mvvm.main

import android.app.Application
import com.frogobox.base.BaseViewModel
import com.frogobox.base.source.model.Movie
import com.frogobox.base.source.model.TvShow
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
class MainViewModel(private val context: Application, private val repository: Repository) :
    BaseViewModel(context) {

    var movieListLive = SingleLiveEvent<List<Movie>>()
    var tvShowListLive = SingleLiveEvent<List<TvShow>>()

    fun getMovie() {
        repository.getMovies(object : DataSource.GetRemoteCallback<List<Movie>> {
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

    fun getTvShow() {
        repository.getTvShow(object : DataSource.GetRemoteCallback<List<TvShow>> {
            override fun onEmpty() {

            }

            override fun onShowProgressDialog() {
                eventShowProgress.postValue(true)
            }

            override fun onHideProgressDialog() {
                eventShowProgress.postValue(false)
            }

            override fun onSuccess(data: List<TvShow>) {
                tvShowListLive.postValue(data)
            }

            override fun onFinish() {

            }

            override fun onFailed(statusCode: Int, errorMessage: String?) {

            }
        })

    }


}