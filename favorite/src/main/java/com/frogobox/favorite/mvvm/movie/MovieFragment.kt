package com.frogobox.favorite.mvvm.movie


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.frogobox.base.source.model.FavoriteMovie
import com.frogobox.base.BaseFragment
import com.frogobox.base.BaseListener
import com.frogobox.favorite.mvvm.main.MainActivity
import com.frogobox.favorite.R
import com.frogobox.favorite.databinding.FragmentTvMovieListBinding

/**
 * A simple [Fragment] subclass.
 */
class MovieFragment : BaseFragment<FragmentTvMovieListBinding>(),
    BaseListener<FavoriteMovie> {

    private lateinit var mViewModel: MovieViewModel

    override fun setupViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup
    ): FragmentTvMovieListBinding {
        return FragmentTvMovieListBinding.inflate(inflater, container, false)
    }

    override fun setupViewModel() {
        mViewModel = (activity as MainActivity).obtainMovieViewModel().apply {

            favMovieListLive.observe(viewLifecycleOwner, Observer {
                setupRecyclerView(it)
            })

            eventShowProgress.observe(viewLifecycleOwner, Observer {
                setupEventProgressView(binding?.progressBar!!, it)
            })

            eventIsEmpty.observe(viewLifecycleOwner, Observer {
//                setupEventEmptyView(empty_view, it)
            })

        }
    }

    override fun setupUI(savedInstanceState: Bundle?) {
        getMovie()
    }

    override fun onResume() {
        super.onResume()
        getMovie()
    }

    private fun getMovie() {
        mViewModel.getFavoriteMovie()
    }

    private fun setupRecyclerView(data: List<FavoriteMovie>) {
        val adapter = _root_ide_package_.com.frogobox.base.adapter.FavoriteMovieAdapter()
        context?.let { adapter.setRecyclerViewLayout(it, R.layout.item_list_tv_movie) }
        adapter.setRecyclerViewListener(this)
        adapter.setRecyclerViewData(data)
        binding?.apply {
            recyclerView.adapter = adapter
            recyclerView.layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        }
    }

    override fun onItemClicked(data: FavoriteMovie) {
        context?.let {
            baseStartActivity<DetailMovieActivity, FavoriteMovie>(
                it,
                DetailMovieActivity.EXTRA_FAV_MOVIE,
                data
            )
        }
    }

    override fun onItemLongClicked(data: FavoriteMovie) {}

}
