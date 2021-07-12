package com.frogobox.movie.mvvm.favorite


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
import com.frogobox.movie.R
import com.frogobox.movie.databinding.FragmentTvMovieListBinding
import com.frogobox.movie.mvvm.tv.DetailTvShowActivity
import com.frogobox.movie.mvvm.main.MainActivity
import kotlinx.android.synthetic.main.empty_view.*
import kotlinx.android.synthetic.main.fragment_tv_movie_grid.*

/**
 * A simple [Fragment] subclass.
 */
class FavoriteTvShowFragment : BaseFragment<FragmentTvMovieListBinding>(),
    BaseListener<FavoriteTvShow> {

    private lateinit var mViewModel: FavoriteTvShowViewModel

    override fun setupViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup
    ): FragmentTvMovieListBinding {
        return FragmentTvMovieListBinding.inflate(inflater, container, false)
    }

    override fun setupViewModel() {
        mViewModel = (activity as MainActivity).obtainFavoriteTvShowViewModel().apply {

            favTvShowListLive.observe(viewLifecycleOwner, Observer {
                setupRecyclerView(it)
            })

            eventShowProgress.observe(viewLifecycleOwner, Observer {
                setupEventProgressView(progressBar, it)
            })

            eventIsEmpty.observe(viewLifecycleOwner, Observer {
                setupEventEmptyView(empty_view, it)
            })

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
        recyclerView.adapter = adapter
        recyclerView.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
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