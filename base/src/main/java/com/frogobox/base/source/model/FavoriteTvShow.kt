package com.frogobox.base.source.model

import android.content.ContentValues
import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.frogobox.base.util.Constant.RoomDatabase.TvShow.COLUMN_BACKDROP_PATH
import com.frogobox.base.util.Constant.RoomDatabase.TvShow.COLUMN_ID
import com.frogobox.base.util.Constant.RoomDatabase.TvShow.COLUMN_NAME
import com.frogobox.base.util.Constant.RoomDatabase.TvShow.COLUMN_OVERVIEW
import com.frogobox.base.util.Constant.RoomDatabase.TvShow.COLUMN_POSTER_PATH
import com.frogobox.base.util.Constant.RoomDatabase.TvShow.TABLE_NAME
import com.frogobox.base.util.Constant.RoomDatabase.TvShow._ID
import kotlinx.parcelize.Parcelize

/**
 * Created by Faisal Amir
 * FrogoBox Inc License
 * =========================================
 * movie
 * Copyright (C) 16/11/2019.
 * All rights reserved
 * -----------------------------------------
 * Name     : Muhammad Faisal Amir
 * E-mail   : faisalamircs@gmail.com
 * Github   : github.com/amirisback
 * LinkedIn : linkedin.com/in/faisalamircs
 * -----------------------------------------
 * FrogoBox Software Industries
 * com.frogobox.base.model
 *
 */
@Entity(tableName = TABLE_NAME)
@Parcelize
data class FavoriteTvShow(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = _ID)
    var table_id: Int = 0,

    @ColumnInfo(name = COLUMN_ID)
    var id: Int? = null,

    @ColumnInfo(name = COLUMN_NAME)
    var name: String? = null,

    @ColumnInfo(name = COLUMN_POSTER_PATH)
    var poster_path: String? = null,

    @ColumnInfo(name = COLUMN_BACKDROP_PATH)
    var backdrop_path: String? = null,

    @ColumnInfo(name = COLUMN_OVERVIEW)
    var overview: String? = null

) : Parcelable {

    fun fromContentValues(values: ContentValues): FavoriteTvShow {
        val favoriteTvShow = FavoriteTvShow()
        if (values.containsKey(COLUMN_ID)) {
            favoriteTvShow.id = values.getAsInteger(COLUMN_ID)!!
        }

        if (values.containsKey(COLUMN_NAME)) {
            favoriteTvShow.name = values.getAsString(COLUMN_NAME)
        }

        if (values.containsKey(COLUMN_POSTER_PATH)) {
            favoriteTvShow.poster_path = values.getAsString(COLUMN_POSTER_PATH)
        }

        if (values.containsKey(COLUMN_BACKDROP_PATH)) {
            favoriteTvShow.backdrop_path = values.getAsString(COLUMN_BACKDROP_PATH)
        }

        if (values.containsKey(COLUMN_OVERVIEW)) {
            favoriteTvShow.overview = values.getAsString(COLUMN_OVERVIEW)
        }

        return favoriteTvShow
    }

}