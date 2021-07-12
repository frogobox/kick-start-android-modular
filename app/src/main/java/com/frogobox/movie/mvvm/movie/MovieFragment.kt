package com.frogobox.movie.mvvm.movie


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.frogobox.base.source.model.Movie
import com.frogobox.base.adapter.MovieAdapter
import com.frogobox.base.BaseFragment
import com.frogobox.base.BaseListener
import com.frogobox.movie.R
import com.frogobox.movie.mvvm.main.MainActivity
import com.frogobox.movie.mvvm.main.MainViewModel
import kotlinx.android.synthetic.main.fragment_tv_movie_grid.*

/**
 * A simple [Fragment] subclass.
 */
class MovieFragment : BaseFragment(),
    BaseListener<Movie> {

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
        getMovies()
    }

    private fun getMovies() {
        mActivity.setTitle(getString(R.string.title_movie))
        mViewModel.getMovie()
    }

    private fun setupViewModel() {
        mViewModel = (activity as MainActivity).obtainMainViewModel().apply {

            movieListLive.observe(viewLifecycleOwner, Observer {
                setupRecyclerView(it)
            })

            eventShowProgress.observe(viewLifecycleOwner, Observer {
                setupEventProgressView(progressBar, it)
            })

        }
    }

    private fun setupRecyclerView(data: List<Movie>) {
        val adapter = MovieAdapter()
        context?.let { adapter.setRecyclerViewLayout(it, R.layout.item_grid_tv_movie) }
        adapter.setRecyclerViewListener(this)
        adapter.setRecyclerViewData(data)
        recyclerView.adapter = adapter
//        recyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        recyclerView.layoutManager =
            StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
    }


    override fun onItemClicked(data: Movie) {
        context?.let {
            baseStartActivity<DetailMovieActivity, Movie>(
                it,
                DetailMovieActivity.EXTRA_MOVIE,
                data
            )
        }
    }

    override fun onItemLongClicked(data: Movie) {

    }

}