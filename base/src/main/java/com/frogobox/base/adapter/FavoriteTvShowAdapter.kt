package com.frogobox.base.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.frogobox.base.BuildConfig
import com.frogobox.base.source.model.FavoriteTvShow
import com.frogobox.base.BaseViewAdapter
import com.frogobox.base.BaseViewHolder
import com.frogobox.base.R
import com.frogobox.base.util.Helper

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
 * com.frogobox.base.adapter
 *
 */
class FavoriteTvShowAdapter :
    BaseViewAdapter<FavoriteTvShow, FavoriteTvShowAdapter.FavoriteTvShowViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteTvShowViewHolder =

        FavoriteTvShowViewHolder(
            LayoutInflater.from(mContext).inflate(
                mRecyclerViewLayout,
                parent,
                false
            )
        )

    inner class FavoriteTvShowViewHolder(view: View) : BaseViewHolder<FavoriteTvShow>(view) {

        private val ivPoster = view.findViewById<ImageView>(R.id.iv_poster)
        private val tvTitle = view.findViewById<TextView>(R.id.tv_title)
        private val tvOverview = view.findViewById<TextView>(R.id.tv_overview)

        override fun initComponent(data: FavoriteTvShow) {
            super.initComponent(data)
            val poster = data.poster_path?.let { Helper.Func.removeBackSlash(it) }
            Glide.with(mContext).load(BuildConfig.TMDB_PATH_URL_IMAGE + poster).into(ivPoster)
            tvTitle.text = data.name
            tvOverview.text = data.overview
        }

    }

}