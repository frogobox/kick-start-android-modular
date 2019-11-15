package com.frogobox.base.modular.source.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.frogobox.base.BuildConfig
import com.frogobox.base.modular.model.FavoriteMovie
import com.frogobox.base.modular.model.FavoriteTvShow
import com.frogobox.base.modular.source.local.dao.FavoriteMovieDao
import com.frogobox.base.modular.source.local.dao.FavoriteTvShowDao
import com.frogobox.base.util.Constant.RoomDatabase.DATABASE_NAME

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
 * com.frogobox.base.modular.source.local
 *
 */
@Database(entities = [
    (FavoriteMovie::class),
    (FavoriteTvShow::class)
], version = 1)


abstract class AppDatabase : RoomDatabase() {

    abstract fun favoriteMovieDao(): FavoriteMovieDao
    abstract fun favoriteTvShowDao(): FavoriteTvShowDao

    companion object {

        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE
                    ?: buildDatabase(context).also {
                        INSTANCE = it
                    }
            }

        private fun buildDatabase(context: Context): AppDatabase {
            return if (BuildConfig.DEBUG) {
                Room.databaseBuilder(context.applicationContext,
                    AppDatabase::class.java, DATABASE_NAME)
                    .addMigrations(MIGRATION_2_3)
                    .fallbackToDestructiveMigration() // FOR DEVELOPMENT ONLY !!!!
                    .build()
            } else {
                Room.databaseBuilder(context.applicationContext,
                    AppDatabase::class.java, DATABASE_NAME)
                    .addMigrations(MIGRATION_2_3)
                    .build()
            }
        }

        private val MIGRATION_2_3: Migration = object : Migration(2, 3) {
            override fun migrate(database: SupportSQLiteDatabase) {

            }
        }
    }
}