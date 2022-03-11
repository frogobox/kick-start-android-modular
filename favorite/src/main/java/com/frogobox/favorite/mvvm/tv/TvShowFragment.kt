package com.frogobox.favorite.mvvm.tv

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.frogobox.base.source.model.FavoriteTvShow
import com.frogobox.base.adapter.FavoriteTvShowAdapter
import com.frogobox.base.BaseFragment
import com.frogobox.base.BaseListener
import com.frogobox.favorite.mvvm.main.MainActivity
import com.frogobox.favorite.R
import com.frogobox.favorite.databinding.FragmentTvMovieListBinding

/**
 * A simple [Fragment] subclass.
 */
class TvShowFragment : BaseFragment<FragmentTvMovieListBinding>(),
    BaseListener<FavoriteTvShow> {

    private lateinit var mViewModel: TvShowViewModel

    override fun setupViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup
    ): FragmentTvMovieListBinding {
        return FragmentTvMovieListBinding.inflate(inflater, container, false)
    }

    override fun setupViewModel() {
        mViewModel = (activity as MainActivity).obtainTvShowViewModel().apply {

            favTvShowListLive.observe(viewLifecycleOwner) {
                setupRecyclerView(it)
            }

            eventShowProgress.observe(viewLifecycleOwner) {
                setupEventProgressView(binding?.progressBar!!, it)
            }

            eventIsEmpty.observe(viewLifecycleOwner) {
//                setupEventEmptyView(empty_view, it)
            }

        }
    }


    override fun setupUI(savedInstanceState: Bundle?) {
        getTvShow()
    }

    private fun getTvShow() {
        mViewModel.getFavoriteTvShow()
    }

    override fun onResume() {
        super.onResume()
        getTvShow()
    }

    private fun setupRecyclerView(data: List<FavoriteTvShow>) {
        val adapter = FavoriteTvShowAdapter()
        context?.let { adapter.setRecyclerViewLayout(it, R.layout.item_list_tv_movie) }
        adapter.setRecyclerViewListener(this)
        adapter.setRecyclerViewData(data)
        binding?.apply {
            recyclerView.adapter = adapter
            recyclerView.layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        }
    }

    override fun onItemClicked(data: FavoriteTvShow) {
        context?.let {
            baseStartActivity<DetailTvShowActivity, FavoriteTvShow>(
                it,
                DetailTvShowActivity.EXTRA_FAV_TV,
                data
            )
        }
    }

    override fun onItemLongClicked(data: FavoriteTvShow) {}

}