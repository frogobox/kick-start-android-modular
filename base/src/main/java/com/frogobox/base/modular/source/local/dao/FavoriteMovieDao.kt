package com.frogobox.base.modular.source.local.dao

import android.database.Cursor
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.frogobox.base.modular.model.FavoriteMovie
import com.frogobox.base.util.Constant.RoomDatabase.Movie.COLUMN_ID
import com.frogobox.base.util.Constant.RoomDatabase.Movie.TABLE_NAME
import com.frogobox.base.util.Constant.RoomDatabase.Movie._ID
import io.reactivex.Single

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
 * com.frogobox.base.modular.source.local.dao
 *
 */
@Dao
interface FavoriteMovieDao {

    @Query("SELECT * FROM $TABLE_NAME")
    fun getAllData(): Single<List<FavoriteMovie>>

    @Insert
    fun insertData(data: FavoriteMovie)

    @Query("DELETE FROM $TABLE_NAME WHERE $COLUMN_ID = :tableId")
    fun deleteDataFromTableId(tableId: Int)

    @Query("DELETE FROM $TABLE_NAME ")
    fun nukeData()

    // Content Provider ----------------------------------------------------------------------------

    @Query("SELECT * FROM $TABLE_NAME")
    fun getCursorAllData() : Cursor

    @Query("SELECT * FROM $TABLE_NAME WHERE $_ID = :id")
    fun getCursorById(id : Long) : Cursor

    @Insert
    fun insertCursor(data: FavoriteMovie) : Long

    @Query("DELETE FROM $TABLE_NAME WHERE $_ID = :id")
    fun deleteById(id: Long): Int

}