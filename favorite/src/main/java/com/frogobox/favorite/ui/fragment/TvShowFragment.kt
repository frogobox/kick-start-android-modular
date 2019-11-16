package com.frogobox.favorite.ui.fragment


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.frogobox.base.modular.model.FavoriteTvShow
import com.frogobox.base.modular.rvadapter.FavoriteTvShowAdapter
import com.frogobox.base.ui.BaseFragment
import com.frogobox.base.ui.adapter.BaseListener
import com.frogobox.favorite.ui.activity.MainActivity
import com.frogobox.favorite.R
import com.frogobox.favorite.ui.activity.DetailTvShowActivity
import com.frogobox.favorite.viewmodel.TvShowViewModel
import kotlinx.android.synthetic.main.empty_view.*
import kotlinx.android.synthetic.main.fragment_tv_movie_list.*

/**
 * A simple [Fragment] subclass.
 */
class TvShowFragment : BaseFragment(),
    BaseListener<FavoriteTvShow> {

    private lateinit var mViewModel: TvShowViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        setupViewModel()
        return inflater.inflate(R.layout.fragment_tv_movie_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getTvShow()
    }

    private fun getTvShow() {
        mViewModel.getFavoriteTvShow()
    }

    private fun setupViewModel() {
        mViewModel = (activity as MainActivity).obtainTvShowViewModel().apply {

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

    override fun onItemLongClicked(data: FavoriteTvShow) {

    }

}
