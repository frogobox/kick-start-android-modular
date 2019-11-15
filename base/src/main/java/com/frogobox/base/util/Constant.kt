package com.frogobox.base.util

import android.annotation.TargetApi
import android.content.ContentResolver
import android.content.Context
import android.content.SharedPreferences
import android.content.UriMatcher
import android.database.Cursor
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.net.Uri
import android.os.Build
import android.preference.PreferenceManager
import android.provider.BaseColumns
import com.frogobox.base.modular.model.FavoriteMovie
import com.frogobox.base.modular.model.FavoriteTvShow
import java.text.SimpleDateFormat
import java.util.*

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
 * com.frogobox.base.util
 *
 */
class Constant {

    object RoomDatabase {
        const val DATABASE_NAME = "movie.db"

        const val CONTENT_AUTHORITY = "com.frogobox.submission" // Nama Domain Aplikasi
        const val SCHEME = "content"

        object Movie {

            const val TABLE_NAME = "favorite_movie"
            const val _ID = BaseColumns._ID
            const val COLUMN_ID = "id"
            const val COLUMN_TITLE = "title"
            const val COLUMN_POSTER_PATH = "poster_path"
            const val COLUMN_BACKDROP_PATH = "backdrop_path"
            const val COLUMN_OVERVIEW = "overview"

            val PROJECTION = arrayOf(
                _ID,
                COLUMN_ID,
                COLUMN_TITLE,
                COLUMN_POSTER_PATH,
                COLUMN_BACKDROP_PATH,
                COLUMN_OVERVIEW
            )

            fun SELECTION(id: Int) : String = "$COLUMN_ID = '$id'"

            const val CODE_DIR = 100 // Projection All
            const val CODE_ITEM = 101 // Projection ID

            val CONTENT_AUTHORITY_MOVIE = "$CONTENT_AUTHORITY.provider.FavoriteMovieProvider"
            val CONTENT_URI = Uri.Builder().scheme(SCHEME).authority(CONTENT_AUTHORITY_MOVIE)
                .appendPath(TABLE_NAME).build()
            val CONTENT_LIST_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY_MOVIE + "." + TABLE_NAME
            val CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY_MOVIE + "." + TABLE_NAME
            val URI_MATCHER = UriMatcher(UriMatcher.NO_MATCH)

            init {
                URI_MATCHER.addURI(CONTENT_AUTHORITY_MOVIE, TABLE_NAME, CODE_DIR)
                URI_MATCHER.addURI(CONTENT_AUTHORITY_MOVIE, TABLE_NAME + "/*", CODE_ITEM)
            }

            fun setupCursor(data: Cursor): List<FavoriteMovie> {
                val listFavMovie = mutableListOf<FavoriteMovie>()
                if (data.moveToFirst()) {
                    while (!data.isAfterLast()) {
                        val favorite = FavoriteMovie(
                            table_id = data.getInt(data.getColumnIndex(_ID)),
                            id = data.getInt(data.getColumnIndex(COLUMN_ID)),
                            title = data.getString(data.getColumnIndex(COLUMN_TITLE)),
                            poster_path = data.getString(data.getColumnIndex(COLUMN_POSTER_PATH)),
                            backdrop_path = data.getString(data.getColumnIndex(COLUMN_BACKDROP_PATH)),
                            overview = data.getString(data.getColumnIndex(COLUMN_OVERVIEW))
                        )
                        listFavMovie.add(favorite)
                        data.moveToNext()
                    }
                }
                return listFavMovie
            }


        }

        object TvShow {
            const val TABLE_NAME = "favorite_tv_show"
            const val _ID = BaseColumns._ID
            const val COLUMN_ID = "id"
            const val COLUMN_NAME = "name"
            const val COLUMN_POSTER_PATH = "poster_path"
            const val COLUMN_BACKDROP_PATH = "backdrop_path"
            const val COLUMN_OVERVIEW = "overview"

            val PROJECTION = arrayOf(
                _ID,
                COLUMN_ID,
                COLUMN_NAME,
                COLUMN_POSTER_PATH,
                COLUMN_BACKDROP_PATH,
                COLUMN_OVERVIEW
            )

            fun SELECTION(id: Int) : String = "$COLUMN_ID = '$id'"

            const val CODE_DIR = 200 // Projection All
            const val CODE_ITEM = 201 // Projection ID

            val CONTENT_AUTHORITY_TV_SHOW = "$CONTENT_AUTHORITY.provider.FavoriteTvShowProvider"
            val CONTENT_URI = Uri.Builder().scheme(SCHEME).authority(CONTENT_AUTHORITY_TV_SHOW)
                .appendPath(TABLE_NAME).build()
            val CONTENT_LIST_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "." + CONTENT_AUTHORITY_TV_SHOW + "/" + TABLE_NAME
            val CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "." + CONTENT_AUTHORITY_TV_SHOW + "/" + TABLE_NAME
            val URI_MATCHER = UriMatcher(UriMatcher.NO_MATCH)

            init {
                URI_MATCHER.addURI(CONTENT_AUTHORITY_TV_SHOW, TABLE_NAME, CODE_DIR)
                URI_MATCHER.addURI(CONTENT_AUTHORITY_TV_SHOW, TABLE_NAME + "/*", CODE_ITEM)
            }

            fun setupCursor(data: Cursor): List<FavoriteTvShow> {
                val listFavTvShow = mutableListOf<FavoriteTvShow>()
                if (data.moveToFirst()) {
                    while (!data.isAfterLast()) {
                        val favorite = FavoriteTvShow(
                            table_id = data.getInt(data.getColumnIndex(_ID)),
                            id = data.getInt(data.getColumnIndex(COLUMN_ID)),
                            name = data.getString(data.getColumnIndex(COLUMN_NAME)),
                            poster_path = data.getString(data.getColumnIndex(COLUMN_POSTER_PATH)),
                            backdrop_path = data.getString(data.getColumnIndex(COLUMN_BACKDROP_PATH)),
                            overview = data.getString(data.getColumnIndex(COLUMN_OVERVIEW))
                        )
                        listFavTvShow.add(favorite)
                        data.moveToNext()
                    }
                }
                return listFavTvShow
            }

        }

    }

    object Constant {
        const val PACKAGE_ROOT = "com.frogobox.submission"
        const val PATH_MAIN_ACTIVITY = "com.frogobox.submission.view.ui.activity.MainActivity"

        // Format Date
        const val DATE_TIME_GLOBAL = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'" //
        const val DATE_TIME_STANDARD = "yyyy-MM-dd HH:mm:ss" // 2018-10-02 12:12:12
        const val DATE_ENGLISH_YYYY_MM_DD = "yyyy-MM-dd" // 2018-10-02
        const val DATE_ENGLISH_YYYY_MM_DD_CLEAR = "yyyy MM dd" // 2018 10 02
        const val DATE_DD_MM_YYYY = "dd-MM-yyyy" // 02-10-2018
        const val DATE_EEEE_DD_MM_YYYY = "EEEE, dd MMMM yyyy" // 02-10-2018
        const val DATE_DD_MM_YYYY_CLEAR = "dd MM yyyy" // 02-10-2018

        const val SEARCH_TV_SHOW = "SEARCH_TV_SHOW"
        const val SEARCH_MOVIE = "SEARCH_MOVIE"

    }

    object Alarm {
        const val EXTRA_TITLE = "extra_content_title"
        const val EXTRA_TEXT = "extra_content_text"
        const val EXTRA_NOTIFICATION_ID = "extra_notification_id"
        const val EXTRA_CHANNEL_ID = "extra_channel_id"
        const val EXTRA_CHANNEL_NAME = "extra_channel_name"

        const val TYPE_EXTRA_DAILY_REMINDER = "_type_daily_reminder"
        const val TYPE_EXTRA_DAILY_RELEASE = "_type_daily_release"
    }

    object Notif {

        const val DAILY_REMINDER_NOTIFICATION_ID = 3
        const val DAILY_REMINDER_CHANNEL_ID = "REMINDER"
        const val DAILY_REMINDER_CHANNEL_NAME = "DAILY_REMINDER"

        const val DAILY_RELEASE_NOTIFICATION_ID = 4
        const val DAILY_RELEASE_CHANNEL_ID = "RELEASE_"
        const val DAILY_RELEASE_CHANNEL_NAME = "DAILY_RELEASE_"

        const val ID_REPEATING_DAILY = 80
        const val ID_REPEATING_RELEASE = 81

        const val TIME_DAILY_REMINDER = "07:00"
        const val TIME_DAILY_RELEASE = "08:00"

    }

    object Pref {
        const val PREF_NAME = "MoviePref"
        const val PREF_RELEASE_REMINDER = "PREF_RELEASE_REMINDER"
        const val PREF_DAILY_REMINDER = "PREF_DAILY_REMINDER"
    }


}