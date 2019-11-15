package com.frogobox.base.modular.model

import android.content.ContentValues
import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize
import com.frogobox.base.util.Constant.RoomDatabase.Movie.COLUMN_BACKDROP_PATH
import com.frogobox.base.util.Constant.RoomDatabase.Movie.COLUMN_ID
import com.frogobox.base.util.Constant.RoomDatabase.Movie.COLUMN_OVERVIEW
import com.frogobox.base.util.Constant.RoomDatabase.Movie.COLUMN_POSTER_PATH
import com.frogobox.base.util.Constant.RoomDatabase.Movie.COLUMN_TITLE
import com.frogobox.base.util.Constant.RoomDatabase.Movie.TABLE_NAME
import com.frogobox.base.util.Constant.RoomDatabase.Movie._ID

/**
 * Created by Faisal Amir
 * FrogoBox Inc License
 * =========================================
 * mvvm
 * Copyright (C) 16/11/2019.
 * All rights reserved
 * -----------------------------------------
 * Name     : Muhammad Faisal Amir
 * E-mail   : faisalamircs@gmail.com
 * Github   : github.com/amirisback
 * LinkedIn : linkedin.com/in/faisalamircs
 * -----------------------------------------
 * FrogoBox Software Industries
 * com.frogobox.base.modular.model
 *
 */
@Entity(tableName = TABLE_NAME)
@Parcelize
data class FavoriteMovie(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = _ID)
    var table_id: Int = 0,

    @ColumnInfo(name = COLUMN_ID)
    var id: Int? = null,

    @ColumnInfo(name = COLUMN_TITLE)
    var title: String? = null,

    @ColumnInfo(name = COLUMN_POSTER_PATH)
    var poster_path: String? = null,

    @ColumnInfo(name = COLUMN_BACKDROP_PATH)
    var backdrop_path: String? = null,

    @ColumnInfo(name = COLUMN_OVERVIEW)
    var overview: String? = null

) : Parcelable {

    fun fromContentValues(values: ContentValues): FavoriteMovie {
        val favoriteMovie = FavoriteMovie()
        if (values.containsKey(COLUMN_ID)) {
            favoriteMovie.id = values.getAsInteger(COLUMN_ID)!!
        }

        if (values.containsKey(COLUMN_TITLE)) {
            favoriteMovie.title = values.getAsString(COLUMN_TITLE)
        }

        if (values.containsKey(COLUMN_POSTER_PATH)) {
            favoriteMovie.poster_path = values.getAsString(COLUMN_POSTER_PATH)
        }

        if (values.containsKey(COLUMN_BACKDROP_PATH)) {
            favoriteMovie.backdrop_path = values.getAsString(COLUMN_BACKDROP_PATH)
        }

        if (values.containsKey(COLUMN_OVERVIEW)) {
            favoriteMovie.overview = values.getAsString(COLUMN_OVERVIEW)
        }

        return favoriteMovie
    }
}