package com.frogobox.base.util

import android.annotation.TargetApi
import android.content.Context
import android.content.SharedPreferences
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Build
import android.preference.PreferenceManager
import com.frogobox.base.BaseHelper
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
class Helper : BaseHelper() {

    object Func {

        fun isNetworkAvailable(context: Context): Boolean? {
            var isConnected: Boolean? = false // Initial Value
            val connectivityManager =
                context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val activeNetwork: NetworkInfo? = connectivityManager.activeNetworkInfo
            if (activeNetwork != null && activeNetwork.isConnected) isConnected = true
            return isConnected
        }

        fun removeBackSlash(string: String) : String{
            val newString = string.replace("'\'/","")
            return newString
        }

        fun getCurrentDate(format: String): String {
            val simpleDateFormat = SimpleDateFormat(format, Locale.getDefault())
            return simpleDateFormat.format(Date())
        }

    }

    object SystemLocale {

        private val SELECTED_LANGUAGE = "Locale.Helper.Selected.Language"

        fun onAttach(context: Context): Context {
            val lang = getPersistedData(context, Locale.getDefault().language)
            return setLocale(context, lang)
        }

        fun onAttach(context: Context, defaultLanguage: String): Context {
            val lang = getPersistedData(context, defaultLanguage)
            return setLocale(context, lang)
        }

        fun getLanguage(context: Context): String? {
            return getPersistedData(context, Locale.getDefault().language)
        }

        fun setLocale(context: Context, language: String?): Context {
            persist(context, language)

            return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                updateResources(context, language)
            } else updateResourcesLegacy(context, language)

        }

        private fun getPersistedData(context: Context, defaultLanguage: String): String? {
            val preferences = PreferenceManager.getDefaultSharedPreferences(context)
            return preferences.getString(SELECTED_LANGUAGE, defaultLanguage)
        }

        private fun persist(context: Context, language: String?) {
            val preferences = PreferenceManager.getDefaultSharedPreferences(context)
            val editor = preferences.edit()

            editor.putString(SELECTED_LANGUAGE, language)
            editor.apply()
        }

        @TargetApi(Build.VERSION_CODES.N)
        private fun updateResources(context: Context, language: String?): Context {
            val locale = Locale(language)
            Locale.setDefault(locale)

            val configuration = context.resources.configuration
            configuration.setLocale(locale)
            configuration.setLayoutDirection(locale)

            return context.createConfigurationContext(configuration)
        }

        private fun updateResourcesLegacy(context: Context, language: String?): Context {
            val locale = Locale(language)
            Locale.setDefault(locale)

            val resources = context.resources

            val configuration = resources.configuration
            configuration.locale = locale
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                configuration.setLayoutDirection(locale)
            }

            resources.updateConfiguration(configuration, resources.displayMetrics)

            return context
        }
    }

    object Preference {

        fun getSp(context: Context): SharedPreferences {
            return context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)

        }

        object Save {
            fun savePrefFloat(sharedPreferences: SharedPreferences, constPref: String, data: Float) {
                sharedPreferences.edit().putFloat(constPref, data).apply()
            }

            fun savePrefInt(sharedPreferences: SharedPreferences, constPref: String, data: Int) {
                sharedPreferences.edit().putInt(constPref, data).apply()
            }

            fun savePrefString(sharedPreferences: SharedPreferences, constPref: String, data: String) {
                sharedPreferences.edit().putString(constPref, data).apply()
            }

            fun savePrefBoolean(sharedPreferences: SharedPreferences, constPref: String, data: Boolean) {
                sharedPreferences.edit().putBoolean(constPref, data).apply()
            }

            fun savePrefLong(sharedPreferences: SharedPreferences, constPref: String, data: Long) {
                sharedPreferences.edit().putLong(constPref, data).apply()
            }

        }

        object Delete {

            fun deletePref(sharedPreferences: SharedPreferences, constPref: String) {
                sharedPreferences.edit().remove(constPref).apply()
            }

        }

        object Load {

            fun loadPrefFloat(sharedPreferences: SharedPreferences, constPref: String): Float {
                return sharedPreferences.getFloat(constPref, 0f)
            }

            fun loadPrefString(sharedPreferences: SharedPreferences, constPref: String): String {
                return sharedPreferences.getString(constPref, "")!!
            }

            fun loadPrefInt(sharedPreferences: SharedPreferences, constPref: String): Int {
                return sharedPreferences.getInt(constPref, 0)
            }

            fun loadPrefLong(sharedPreferences: SharedPreferences, constPref: String): Long {
                return sharedPreferences.getLong(constPref, 0)
            }

            fun loadPrefBoolean(sharedPreferences: SharedPreferences, constPref: String): Boolean {
                return sharedPreferences.getBoolean(constPref, false)
            }

        }

    }

}