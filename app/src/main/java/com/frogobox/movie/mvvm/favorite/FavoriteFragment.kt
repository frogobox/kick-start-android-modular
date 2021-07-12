package com.frogobox.movie.mvvm.favorite


import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import com.frogobox.base.BaseFragment
import com.frogobox.base.util.PagerHelper

import com.frogobox.movie.R
import com.frogobox.movie.databinding.FragmentFavoriteBinding
import kotlinx.android.synthetic.main.fragment_favorite.*

class FavoriteFragment : BaseFragment<FragmentFavoriteBinding>() {

    private fun setupViewPager(){
        val pagerAdapter = PagerHelper(childFragmentManager)
        pagerAdapter.setupPagerFragment(FavoriteMovieFragment(), resources.getString(R.string.title_favorite_movie))
        pagerAdapter.setupPagerFragment(FavoriteTvShowFragment(), resources.getString(R.string.title_favorite_tv_show))
        viewpager.adapter = pagerAdapter
        tablayout.setupWithViewPager(viewpager)
    }

    override fun setupViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup
    ): FragmentFavoriteBinding {
        return FragmentFavoriteBinding.inflate(inflater, container, false)
    }

    override fun setupViewModel() {
    }

    override fun setupUI(savedInstanceState: Bundle?) {
        mActivity.title = getString(R.string.title_favorite)
        setupViewPager()
    }

}
