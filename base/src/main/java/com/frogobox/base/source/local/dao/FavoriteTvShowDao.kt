package com.frogobox.base.source.local.dao

import android.database.Cursor
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.frogobox.base.source.model.FavoriteTvShow
import com.frogobox.base.util.Constant.RoomDatabase.TvShow.COLUMN_ID
import com.frogobox.base.util.Constant.RoomDatabase.TvShow.TABLE_NAME
import com.frogobox.base.util.Constant.RoomDatabase.TvShow._ID
import io.reactivex.rxjava3.core.Single

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
 * com.frogobox.base.source.local.dao
 *
 */
@Dao
interface FavoriteTvShowDao {

    @Query("SELECT * FROM $TABLE_NAME")
    fun getAllData(): Single<List<FavoriteTvShow>>

    @Insert
    fun insertData(data: FavoriteTvShow)

    @Query("DELETE FROM $TABLE_NAME WHERE $COLUMN_ID = :tableId")
    fun deleteDataFromTableId(tableId: Int)

    @Query("DELETE FROM $TABLE_NAME")
    fun nukeData()

    // Content Provider ----------------------------------------------------------------------------

    @Query("SELECT * FROM $TABLE_NAME")
    fun getCursorAllData(): Cursor

    @Query("SELECT * FROM $TABLE_NAME WHERE $_ID = :id")
    fun getCursorById(id: Long): Cursor

    @Insert
    fun insertCursor(data: FavoriteTvShow): Long

    @Query("DELETE FROM $TABLE_NAME WHERE $_ID = :id")
    fun deleteById(id: Long): Int

}