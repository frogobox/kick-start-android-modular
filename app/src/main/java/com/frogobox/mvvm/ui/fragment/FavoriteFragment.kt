package com.frogobox.mvvm.ui.fragment


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.frogobox.base.BaseFragment
import com.frogobox.base.util.PagerHelper

import com.frogobox.mvvm.R
import kotlinx.android.synthetic.main.fragment_favorite.*

/**
 * A simple [Fragment] subclass.
 */
class FavoriteFragment : BaseFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favorite, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mActivity.setTitle(getString(R.string.title_favorite))
        setupViewPager()
    }

    private fun setupViewPager(){
        val pagerAdapter = PagerHelper(childFragmentManager)
        pagerAdapter.setupPagerFragment(FavoriteMovieFragment(), resources.getString(R.string.title_favorite_movie))
        pagerAdapter.setupPagerFragment(FavoriteTvShowFragment(), resources.getString(R.string.title_favorite_tv_show))
        viewpager.adapter = pagerAdapter
        tablayout.setupWithViewPager(viewpager)
    }

}
