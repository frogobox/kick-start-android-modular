package com.frogobox.movie.mvvm.movie

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.frogobox.base.source.model.Movie
import com.frogobox.base.adapter.MovieAdapter
import com.frogobox.base.BaseListener
import com.frogobox.movie.R
import com.frogobox.movie.databinding.ActivitySearchBinding
import com.frogobox.movie.util.BaseAppActivity

class SearchMovieActivity : BaseAppActivity<ActivitySearchBinding>(),
    BaseListener<Movie> {

    private lateinit var mViewModel: SearchMovieViewModel
    private lateinit var adapter: MovieAdapter

    override fun setupViewBinding(): ActivitySearchBinding {
        return ActivitySearchBinding.inflate(layoutInflater)
    }

    override fun setupViewModel() {
        adapter = MovieAdapter()

        mViewModel = obtainSearchMovieViewModel().apply {

            movieListLive.observe(this@SearchMovieActivity, Observer {
                setupRecyclerView(it)
            })

            eventShowProgress.observe(this@SearchMovieActivity, Observer {
                setupEventProgressView(binding.progressBar, it)
            })

        }
    }

    override fun setupUI(savedInstanceState: Bundle?) {
        setupViewElement()
    }

    private fun obtainSearchMovieViewModel(): SearchMovieViewModel =
        obtainViewModel(SearchMovieViewModel::class.java)

    private fun searchMovie(query: String) {
        mViewModel.searchMovies(query)
    }

    private fun setupViewElement() {

        binding.toolbar.apply {
            etSearch.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(p0: Editable?) {}
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    val query = etSearch.text.toString()
                    if (!query.equals("")) {
                        searchMovie(query)
                    } else {
                        adapter.nukeRecyclerViewData()
                    }
                }
            })

            ivBack.setOnClickListener {
                finish()
            }

            ivClose.setOnClickListener {
                etSearch.text.clear()
            }
        }
    }

    private fun setupRecyclerView(data: List<Movie>) {
        adapter.setRecyclerViewLayout(this, R.layout.item_list_tv_movie)
        adapter.setRecyclerViewListener(this)
        adapter.setRecyclerViewData(data)
        binding.apply {
            recyclerView.adapter = adapter
            recyclerView.layoutManager =
                LinearLayoutManager(this@SearchMovieActivity, LinearLayoutManager.VERTICAL, false)
        }
    }

    override fun onItemClicked(data: Movie) {
        baseStartActivity<DetailMovieActivity, Movie>(
            this,
            DetailMovieActivity.EXTRA_MOVIE, data
        )
    }

    override fun onItemLongClicked(data: Movie) {}

}