package com.frogobox.favorite.mvvm.movie

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.lifecycle.Observer
import com.frogobox.base.BuildConfig
import com.frogobox.base.callback.DeleteViewCallback
import com.frogobox.base.callback.SaveViewCallback
import com.frogobox.base.source.model.FavoriteMovie
import com.frogobox.base.source.model.Movie
import com.frogobox.base.util.Helper
import com.frogobox.favorite.R
import com.frogobox.favorite.databinding.ActivityDetailBinding
import com.frogobox.favorite.util.BaseFavoriteActivity
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_detail.*

class DetailMovieActivity : BaseFavoriteActivity<ActivityDetailBinding>(), SaveViewCallback, DeleteViewCallback {

    companion object {
        const val EXTRA_FAV_MOVIE = "EXTRA_FAV_MOVIE"
    }

    private lateinit var mViewModel: DetailMovieViewModel
    private lateinit var extraFavoriteMovie: FavoriteMovie
    private lateinit var extraMovie: Movie

    private var isFav = true
    private var menuItem: Menu? = null

    override fun setupViewBinding(): ActivityDetailBinding {
        return ActivityDetailBinding.inflate(layoutInflater)
    }

    override fun setupViewModel() {
        mViewModel = obtainDetailMovieViewModel().apply {

            favoriteMovie.observe(this@DetailMovieActivity, Observer {

            })

            eventIsFavorite.observe(this@DetailMovieActivity, Observer {
                setFavorite(it)
                isFav = it
            })

        }
    }

    override fun setupUI(savedInstanceState: Bundle?) {
        setupDetailActivity(getString(R.string.title_detail_movie))
        setupExtraData()
    }

    fun obtainDetailMovieViewModel(): DetailMovieViewModel =
        obtainViewModel(DetailMovieViewModel::class.java)


    private fun setupExtraData() {
        extraFavoriteMovie = baseGetExtraData(EXTRA_FAV_MOVIE)
        val poster = extraFavoriteMovie.backdrop_path?.let { Helper.Func.removeBackSlash(it) }
        Picasso.get().load(BuildConfig.TMDB_PATH_URL_IMAGE + poster).into(iv_poster)
        tv_title.text = extraFavoriteMovie.title
        tv_overview.text = extraFavoriteMovie.overview
        extraFavoriteMovie.id?.let { mViewModel.getFavoriteMovie(it) }
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
            extraFavoriteMovie.id?.let { mViewModel.deleteFavoriteMovie(it, this) }
            extraFavoriteMovie.id?.let { mViewModel.getFavoriteMovie(it) }
        } else {
            mViewModel.saveFavoriteMovie(
                FavoriteMovie(
                    id = extraFavoriteMovie.id,
                    title = extraFavoriteMovie.title,
                    backdrop_path = extraFavoriteMovie.backdrop_path,
                    poster_path = extraFavoriteMovie.poster_path,
                    overview = extraFavoriteMovie.overview
                ), this
            )
            extraFavoriteMovie.id?.let { mViewModel.getFavoriteMovie(it) }
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