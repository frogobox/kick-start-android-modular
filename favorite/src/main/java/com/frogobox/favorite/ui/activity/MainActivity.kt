package com.frogobox.favorite.ui.activity

import android.os.Bundle
import com.frogobox.base.util.PagerHelper
import com.frogobox.favorite.R
import com.frogobox.favorite.ui.fragment.MovieFragment
import com.frogobox.favorite.ui.fragment.TvShowFragment
import com.frogobox.favorite.util.BaseFavoriteActivity
import com.frogobox.favorite.viewmodel.MovieViewModel
import com.frogobox.favorite.viewmodel.TvShowViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseFavoriteActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mActivity.setTitle(getString(R.string.title_favorite))
        setupViewPager()
    }

    fun obtainMovieViewModel(): MovieViewModel =
        obtainViewModel(MovieViewModel::class.java)

    fun obtainTvShowViewModel(): TvShowViewModel =
        obtainViewModel(TvShowViewModel::class.java)

    private fun setupViewPager() {
        val pagerAdapter = PagerHelper(supportFragmentManager)
        pagerAdapter.setupPagerFragment(
            MovieFragment(),
            resources.getString(R.string.title_favorite_movie)
        )
        pagerAdapter.setupPagerFragment(
            TvShowFragment(),
            resources.getString(R.string.title_favorite_tv_show)
        )
        viewpager.adapter = pagerAdapter
        tablayout.setupWithViewPager(viewpager)
    }


}
