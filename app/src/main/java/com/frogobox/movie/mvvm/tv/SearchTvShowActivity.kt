package com.frogobox.movie.mvvm.tv

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.frogobox.base.source.model.TvShow
import com.frogobox.base.adapter.TvShowAdapter
import com.frogobox.base.BaseListener
import com.frogobox.movie.R
import com.frogobox.movie.util.BaseAppActivity
import kotlinx.android.synthetic.main.fragment_tv_movie_list.*
import kotlinx.android.synthetic.main.toolbar_search.*

class SearchTvShowActivity : BaseAppActivity(),
    BaseListener<TvShow> {

    private lateinit var mViewModel: SearchTvShowViewModel
    private lateinit var adapter: TvShowAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        setupViewModel()
        setupViewElement()
    }

    fun obtainSearchTvShowViewModel(): SearchTvShowViewModel =
        obtainViewModel(SearchTvShowViewModel::class.java)

    private fun setupViewModel() {
        adapter = TvShowAdapter()

        mViewModel = obtainSearchTvShowViewModel().apply {

            tvShowListLive.observe(this@SearchTvShowActivity, Observer {
                setupRecyclerView(it)
            })

            eventShowProgress.observe(this@SearchTvShowActivity, Observer {
                setupEventProgressView(progressBar, it)
            })

        }
    }

    private fun searchTvShow(query: String) {
        mViewModel.searchTvShow(query)
    }

    private fun setupViewElement() {

        et_search.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {}
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                val query = et_search.text.toString()
                if (!query.equals("")) {
                    searchTvShow(query)
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

    private fun setupRecyclerView(data: List<TvShow>) {
        adapter.setRecyclerViewLayout(this, R.layout.item_list_tv_movie)
        adapter.setRecyclerViewListener(this)
        adapter.setRecyclerViewData(data)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
    }

    override fun onItemClicked(data: TvShow) {
        baseStartActivity<DetailTvShowActivity, TvShow>(
            this,
            DetailTvShowActivity.EXTRA_TV, data
        )
    }

    override fun onItemLongClicked(data: TvShow) {}

}
