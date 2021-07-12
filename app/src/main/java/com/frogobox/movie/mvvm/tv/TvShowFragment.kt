package com.frogobox.movie.mvvm.tv


import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.frogobox.base.source.model.TvShow
import com.frogobox.base.adapter.TvShowAdapter
import com.frogobox.base.BaseFragment
import com.frogobox.base.BaseListener
import com.frogobox.movie.R
import com.frogobox.movie.databinding.FragmentTvMovieGridBinding
import com.frogobox.movie.mvvm.main.MainActivity
import com.frogobox.movie.mvvm.main.MainViewModel

/**
 * A simple [Fragment] subclass.
 */
class TvShowFragment : BaseFragment<FragmentTvMovieGridBinding>(),
    BaseListener<TvShow> {

    private lateinit var mViewModel: MainViewModel

    override fun setupViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup
    ): FragmentTvMovieGridBinding {
        return FragmentTvMovieGridBinding.inflate(inflater, container, false)
    }

    override fun setupViewModel() {
        mViewModel = (activity as MainActivity).obtainMainViewModel().apply {

            tvShowListLive.observe(viewLifecycleOwner, Observer {
                setupRecyclerView(it)
            })

            eventShowProgress.observe(viewLifecycleOwner, Observer {
                setupEventProgressView(binding?.progressBar!!, it)
            })

        }
    }

    override fun setupUI(savedInstanceState: Bundle?) {
        getTvShow()
    }

    private fun getTvShow() {
        mActivity.title = getString(R.string.title_tv)
        mViewModel.getTvShow()
    }

    private fun setupRecyclerView(data: List<TvShow>) {
        val adapter = TvShowAdapter()
        context?.let { adapter.setRecyclerViewLayout(it, R.layout.item_grid_tv_movie) }
        adapter.setRecyclerViewListener(this)
        adapter.setRecyclerViewData(data)
        binding?.apply {
            recyclerView.adapter = adapter
            recyclerView.layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            recyclerView.layoutManager =
                StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        }
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

    override fun onItemLongClicked(data: TvShow) {}

}