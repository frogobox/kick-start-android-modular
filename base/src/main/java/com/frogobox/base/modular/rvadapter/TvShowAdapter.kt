package com.frogobox.base.modular.rvadapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.frogobox.base.BuildConfig
import com.frogobox.base.modular.model.TvShow
import com.frogobox.base.ui.adapter.BaseViewAdapter
import com.frogobox.base.ui.adapter.BaseViewHolder
import com.frogobox.base.util.Helper
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_grid_tv_movie.view.*

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
 * com.frogobox.base.modular.rvadapter
 *
 */
class TvShowAdapter : BaseViewAdapter<TvShow, TvShowAdapter.TvShowViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        TvShowViewHolder(
            LayoutInflater.from(mContext).inflate(
                mRecyclerViewLayout,
                parent,
                false
            )
        )

    inner class TvShowViewHolder(view: View) : BaseViewHolder<TvShow>(view) {

        private val ivPoster = view.iv_poster
        private val tvTitle = view.tv_title
        private val tvOverview = view.tv_overview

        override fun initComponent(data: TvShow) {
            super.initComponent(data)
            val poster = data.poster_path?.let { Helper.Func.removeBackSlash(it) }
            Glide.with(mContext).load(BuildConfig.TMDB_PATH_URL_IMAGE + poster).into(ivPoster)
            tvTitle.text = data.name
            tvOverview.text = data.overview
        }

    }

}