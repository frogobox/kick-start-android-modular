package com.frogobox.mvvm.ui.activity

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.lifecycle.Observer
import com.frogobox.base.BuildConfig
import com.frogobox.base.modular.callback.DeleteViewCallback
import com.frogobox.base.modular.callback.SaveViewCallback
import com.frogobox.base.modular.model.FavoriteTvShow
import com.frogobox.base.modular.model.TvShow
import com.frogobox.base.util.Helper
import com.frogobox.mvvm.R
import com.frogobox.mvvm.util.BaseAppActivity
import com.frogobox.mvvm.viewmodel.DetailTvShowViewModel
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_detail.*

class DetailTvShowActivity : BaseAppActivity(),
    SaveViewCallback,
    DeleteViewCallback {

    companion object {
        const val EXTRA_TV = "EXTRA_TV"
        const val EXTRA_FAV_TV = "EXTRA_FAV_TV"
    }

    private lateinit var mViewModel: DetailTvShowViewModel
    private lateinit var extraFavoriteTvShow: FavoriteTvShow
    private lateinit var extraTvShow: TvShow

    private var isFav = false
    private var menuItem: Menu? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        setupDetailActivity(getString(R.string.title_detail_tv_show))
        setupViewModel()
        setupExtraData()
    }

    private fun obtainDetailTvShowViewModel(): DetailTvShowViewModel =
        obtainViewModel(DetailTvShowViewModel::class.java)

    private fun setupViewModel() {
        mViewModel = obtainDetailTvShowViewModel().apply {

            favoriteTvShow.observe(this@DetailTvShowActivity, Observer {

            })

            eventIsFavorite.observe(this@DetailTvShowActivity, Observer {
                setFavorite(it)
                isFav = it
            })

        }
    }

    private fun stateExtra(listenerTvShow: () -> Unit, listenerFavTvShow: () -> Unit) {
        if (checkExtra(EXTRA_TV)) {
            listenerTvShow()
        } else if (checkExtra(EXTRA_FAV_TV)) {
            listenerFavTvShow()
        }
    }


    private fun setupExtraData() {
        stateExtra({
            extraTvShow = baseGetExtraData(EXTRA_TV)
            val poster = extraTvShow.backdrop_path?.let { Helper.Func.removeBackSlash(it) }
            Glide.with(this).load("${BuildConfig.TMDB_PATH_URL_IMAGE}$poster").into(iv_poster)
            tv_title.text = extraTvShow.name
            tv_overview.text = extraTvShow.overview
            extraTvShow.id?.let { mViewModel.getFavoriteTvShow(it) }
        }) {
            extraFavoriteTvShow = baseGetExtraData(EXTRA_FAV_TV)
            val poster = extraFavoriteTvShow.backdrop_path?.let { Helper.Func.removeBackSlash(it) }
            Glide.with(this).load("${BuildConfig.TMDB_PATH_URL_IMAGE}$poster").into(iv_poster)
            tv_title.text = extraFavoriteTvShow.name
            tv_overview.text = extraFavoriteTvShow.overview
            extraFavoriteTvShow.id?.let { mViewModel.getFavoriteTvShow(it) }
        }
    }

    private fun setFavorite(state: Boolean) {
        if (state)
            menuItem?.getItem(0)?.icon = getDrawable(R.drawable.ic_favorite)
        else
            menuItem?.getItem(0)?.icon = getDrawable(R.drawable.ic_un_favorite)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_favorite, menu)
        menuItem = menu
        setFavorite(isFav)
        return true
    }

    private fun handleFavorite() {
        if (isFav) {
            stateExtra({
                extraTvShow.id?.let { mViewModel.deleteFavoriteTvShow(it, this) }
                extraTvShow.id?.let { mViewModel.getFavoriteTvShow(it) }
            }) {
                extraFavoriteTvShow.id?.let { mViewModel.deleteFavoriteTvShow(it, this) }
                extraFavoriteTvShow.id?.let { mViewModel.getFavoriteTvShow(it) }
            }
        } else {
            stateExtra({
                mViewModel.saveFavoriteTvShow(
                    FavoriteTvShow(
                        id = extraTvShow.id,
                        name = extraTvShow.name,
                        backdrop_path = extraTvShow.backdrop_path,
                        poster_path = extraTvShow.poster_path,
                        overview = extraTvShow.overview
                    ), this
                )
                extraTvShow.id?.let { mViewModel.getFavoriteTvShow(it) }
            }) {
                mViewModel.saveFavoriteTvShow(
                    FavoriteTvShow(
                        id = extraFavoriteTvShow.id,
                        name = extraFavoriteTvShow.name,
                        backdrop_path = extraFavoriteTvShow.backdrop_path,
                        poster_path = extraFavoriteTvShow.poster_path,
                        overview = extraFavoriteTvShow.overview
                    ), this
                )
                extraFavoriteTvShow.id?.let { mViewModel.getFavoriteTvShow(it) }
            }

        }
        setFavorite(isFav)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.toolbar_menu_fav -> {
                handleFavorite()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onShowProgress() {}

    override fun onHideProgress() {}

    override fun onSuccesInsert() {
        showToast(getString(R.string.text_succes_add_favorite))
    }

    override fun onSuccesDelete() {
        showToast(getString(R.string.text_succes_delete_favorite))
    }

    override fun onFailed(message: String) {}

}

