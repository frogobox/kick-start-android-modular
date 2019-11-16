package com.frogobox.mvvm.ui.activity

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.frogobox.base.modular.model.Movie
import com.frogobox.base.modular.rvadapter.MovieAdapter
import com.frogobox.base.ui.adapter.BaseListener
import com.frogobox.mvvm.R
import com.frogobox.mvvm.util.BaseAppActivity
import com.frogobox.mvvm.viewmodel.SearchMovieViewModel
import kotlinx.android.synthetic.main.fragment_tv_movie_list.*
import kotlinx.android.synthetic.main.toolbar_search.*

class SearchMovieActivity : BaseAppActivity(),
    BaseListener<Movie> {

    private lateinit var mViewModel: SearchMovieViewModel
    private lateinit var adapter: MovieAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        setupViewModel()
        setupViewElement()
    }

    private fun obtainSearchMovieViewModel(): SearchMovieViewModel =
        obtainViewModel(SearchMovieViewModel::class.java)

    private fun setupViewModel() {
        adapter = MovieAdapter()

        mViewModel = obtainSearchMovieViewModel().apply {

            movieListLive.observe(this@SearchMovieActivity, Observer {
                setupRecyclerView(it)
            })

            eventShowProgress.observe(this@SearchMovieActivity, Observer {
                setupEventProgressView(progressBar, it)
            })

        }
    }

    private fun searchMovie(query: String) {
        mViewModel.searchMovies(query)
    }

    private fun setupViewElement() {

        et_search.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {}
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                val query = et_search.text.toString()
                if (!query.equals("")) {
                    searchMovie(query)
                } else {
                    adapter.nukeRecyclerViewData()
                }
            }
        })

        iv_back.setOnClickListener {
            finish()
        }

        iv_close.setOnClickListener {
            et_search.text.clear()
        }
    }

    private fun setupRecyclerView(data: List<Movie>) {
        adapter.setRecyclerViewLayout(this, R.layout.item_list_tv_movie)
        adapter.setRecyclerViewListener(this)
        adapter.setRecyclerViewData(data)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
    }

    override fun onItemClicked(data: Movie) {
        baseStartActivity<DetailMovieActivity, Movie>(
            this,
            DetailMovieActivity.EXTRA_MOVIE, data
        )
    }

    override fun onItemLongClicked(data: Movie) {}

}
