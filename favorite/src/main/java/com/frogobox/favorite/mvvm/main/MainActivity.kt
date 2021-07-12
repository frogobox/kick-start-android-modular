package com.frogobox.favorite.mvvm.main

import android.os.Bundle
import com.frogobox.base.util.PagerHelper
import com.frogobox.favorite.R
import com.frogobox.favorite.databinding.ActivityMainBinding
import com.frogobox.favorite.mvvm.movie.MovieFragment
import com.frogobox.favorite.mvvm.tv.TvShowFragment
import com.frogobox.favorite.util.BaseFavoriteActivity
import com.frogobox.favorite.mvvm.movie.MovieViewModel
import com.frogobox.favorite.mvvm.tv.TvShowViewModel

class MainActivity : BaseFavoriteActivity<ActivityMainBinding>() {

    override fun setupViewBinding(): ActivityMainBinding {
        return ActivityMainBinding.inflate(layoutInflater)
    }

    override fun setupViewModel() {}

    override fun setupUI(savedInstanceState: Bundle?) {
        mActivity.title = getString(R.string.title_favorite)
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
        binding.apply {
            viewpager.adapter = pagerAdapter
            tablayout.setupWithViewPager(viewpager)
        }
    }

}