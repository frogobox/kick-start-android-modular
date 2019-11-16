package com.frogobox.mvvm.ui.fragment


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.frogobox.base.modular.model.TvShow
import com.frogobox.base.modular.rvadapter.TvShowAdapter
import com.frogobox.base.ui.BaseFragment
import com.frogobox.base.ui.adapter.BaseListener

import com.frogobox.mvvm.R
import com.frogobox.mvvm.ui.activity.DetailTvShowActivity
import com.frogobox.mvvm.ui.activity.MainActivity
import com.frogobox.mvvm.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.fragment_tv_movie_list.*

/**
 * A simple [Fragment] subclass.
 */
class TvShowFragment : BaseFragment(),
    BaseListener<TvShow> {

    private lateinit var mViewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        setupViewModel()
        return inflater.inflate(R.layout.fragment_tv_movie_grid, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getTvShow()
    }

    private fun getTvShow() {
        mActivity.setTitle(getString(R.string.title_tv))
        mViewModel.getTvShow()
    }

    private fun setupViewModel() {
        mViewModel = (activity as MainActivity).obtainMainViewModel().apply {

            tvShowListLive.observe(this@TvShowFragment, Observer {
                setupRecyclerView(it)
            })

            eventShowProgress.observe(this@TvShowFragment, Observer {
                setupEventProgressView(progressBar, it)
            })

        }
    }

    private fun setupRecyclerView(data: List<TvShow>) {
        val adapter = TvShowAdapter()
        context?.let { adapter.setRecyclerViewLayout(it, R.layout.item_grid_tv_movie) }
        adapter.setRecyclerViewListener(this)
        adapter.setRecyclerViewData(data)
        recyclerView.adapter = adapter
        recyclerView.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        recyclerView.layoutManager =
            StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
    }


    override fun onItemClicked(data: TvShow) {
        context?.let {
            baseStartActivity<DetailTvShowActivity, TvShow>(
                it,
                DetailTvShowActivity.EXTRA_TV,
                data
            )
        }
    }

    override fun onItemLongClicked(data: TvShow) {

    }


}
