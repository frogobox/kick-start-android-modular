package com.frogobox.favorite.mvvm.tv

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.lifecycle.Observer
import com.frogobox.base.BuildConfig
import com.frogobox.base.callback.DeleteViewCallback
import com.frogobox.base.callback.SaveViewCallback
import com.frogobox.base.source.model.FavoriteTvShow
import com.frogobox.base.source.model.TvShow
import com.frogobox.base.util.Helper
import com.frogobox.favorite.R
import com.frogobox.favorite.databinding.ActivityDetailBinding
import com.frogobox.favorite.util.BaseFavoriteActivity
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_detail.*

class DetailTvShowActivity : BaseFavoriteActivity<ActivityDetailBinding>(), SaveViewCallback, DeleteViewCallback {

    companion object {
        const val EXTRA_FAV_TV = "EXTRA_FAV_TV"
    }

    private lateinit var mViewModel: DetailTvShowViewModel
    private lateinit var extraFavoriteTvShow: FavoriteTvShow
    private lateinit var extraTvShow: TvShow

    private var isFav = false
    private var menuItem: Menu? = null

    override fun setupViewBinding(): ActivityDetailBinding {
        return ActivityDetailBinding.inflate(layoutInflater)
    }

    override fun setupViewModel() {
        mViewModel = obtainDetailTvShowViewModel().apply {

            favoriteTvShow.observe(this@DetailTvShowActivity, {

            })

            eventIsFavorite.observe(this@DetailTvShowActivity, {
                setFavorite(it)
                isFav = it
            })

        }
    }

    override fun setupUI(savedInstanceState: Bundle?) {
        setupDetailActivity(getString(R.string.title_detail_tv_show))
        setupExtraData()
    }

    private fun obtainDetailTvShowViewModel(): DetailTvShowViewModel =
        obtainViewModel(DetailTvShowViewModel::class.java)

    private fun setupExtraData() {
        extraFavoriteTvShow = baseGetExtraData(EXTRA_FAV_TV)
        val poster = extraFavoriteTvShow.backdrop_path?.let { Helper.Func.removeBackSlash(it) }
        Picasso.get().load(BuildConfig.TMDB_PATH_URL_IMAGE + poster).into(iv_poster)
        tv_title.text = extraFavoriteTvShow.name
        tv_overview.text = extraFavoriteTvShow.overview
        extraFavoriteTvShow.id?.let { mViewModel.getFavoriteTvShow(it) }
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
            extraFavoriteTvShow.id?.let { mViewModel.deleteFavoriteTvShow(it, this) }
            extraFavoriteTvShow.id?.let { mViewModel.getFavoriteTvShow(it) }
        } else {
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

