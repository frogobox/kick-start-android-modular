package com.frogobox.base.service

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.frogobox.base.BaseApiModel
import com.frogobox.base.BuildConfig
import com.frogobox.base.R
import com.frogobox.base.source.model.Movie
import com.frogobox.base.source.remote.ApiService
import com.frogobox.base.util.Constant
import com.frogobox.base.util.Constant.Alarm.EXTRA_CHANNEL_ID
import com.frogobox.base.util.Constant.Alarm.EXTRA_CHANNEL_NAME
import com.frogobox.base.util.Constant.Alarm.EXTRA_NOTIFICATION_ID
import com.frogobox.base.util.Constant.Alarm.EXTRA_TEXT
import com.frogobox.base.util.Constant.Alarm.EXTRA_TITLE
import com.frogobox.base.util.Constant.Alarm.TYPE_EXTRA_DAILY_RELEASE
import com.frogobox.base.util.Constant.Alarm.TYPE_EXTRA_DAILY_REMINDER
import com.frogobox.base.util.Constant.Notif.DAILY_RELEASE_NOTIFICATION_ID
import com.frogobox.base.util.Constant.Notif.DAILY_REMINDER_NOTIFICATION_ID
import com.frogobox.base.util.Helper
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

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
 * com.frogobox.base.service
 *
 */
class AlarmReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        eventStateNotification(context, intent)
    }

    private fun eventStateNotification(context: Context, intent: Intent) {

        val dailyNotifId = intent.getIntExtra("$EXTRA_NOTIFICATION_ID$TYPE_EXTRA_DAILY_REMINDER", 0)
        val releaseNotifId =
            intent.getIntExtra("$EXTRA_NOTIFICATION_ID$TYPE_EXTRA_DAILY_RELEASE", 0)

        if (releaseNotifId == DAILY_RELEASE_NOTIFICATION_ID) {
            setReleaseReminder(context, intent)
        }

        if (dailyNotifId == DAILY_REMINDER_NOTIFICATION_ID) {
            val extraText = intent.getStringExtra("$EXTRA_TEXT$TYPE_EXTRA_DAILY_REMINDER")
            showAlarmNotification(context, intent, TYPE_EXTRA_DAILY_REMINDER, extraText, 0)
        }

    }

    private fun showAlarmNotification(
        context: Context,
        intent: Intent,
        type: String,
        extraText: String?,
        position: Int
    ) {
        val extraTitle = intent.getStringExtra("$EXTRA_TITLE$type")
        val extraNotificationID = intent.getIntExtra("$EXTRA_NOTIFICATION_ID$type", 0)
        val extraChannelID = intent.getStringExtra("$EXTRA_CHANNEL_ID$type")
        val extraChannelName = intent.getStringExtra("$EXTRA_CHANNEL_NAME$type")

        Notification()
            .sendNotification(
                context,
                extraNotificationID + position,
                "$extraChannelID$position",
                "$extraChannelName$position",
                extraTitle,
                extraText
            )

        Log.d("EXTRA CHANNEL NAME LOOP", "$extraChannelID$position")
    }

    fun setRepeatingAlarm(
        context: Context,
        content_title: String,
        content_text: String,
        time: String,
        notification_id: Int,
        channel_id: String,
        channel_name: CharSequence,
        id_repeating: Int,
        type: String
    ) {

        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

        val intent = Intent(context, AlarmReceiver::class.java)
        intent.putExtra("$EXTRA_TITLE$type", content_title)
        intent.putExtra("$EXTRA_TEXT$type", content_text)
        intent.putExtra("$EXTRA_NOTIFICATION_ID$type", notification_id)
        intent.putExtra("$EXTRA_CHANNEL_ID$type", channel_id)
        intent.putExtra("$EXTRA_CHANNEL_NAME$type", channel_name)

        val timeArray = time.split(":".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(timeArray[0]))
        calendar.set(Calendar.MINUTE, Integer.parseInt(timeArray[1]))
        calendar.set(Calendar.SECOND, 0)

        val pendingIntent = PendingIntent.getBroadcast(context, id_repeating, intent, 0)
        alarmManager.setInexactRepeating(
            AlarmManager.RTC_WAKEUP,
            calendar.timeInMillis,
            AlarmManager.INTERVAL_DAY,
            pendingIntent
        )
    }

    fun cancelAlarm(context: Context, id_repeating: Int) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, AlarmReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(context, id_repeating, intent, 0)
        pendingIntent.cancel()

        alarmManager.cancel(pendingIntent)
    }

    private fun setReleaseReminder(context: Context, intent: Intent) {
        val dateToday = Helper.Func.getCurrentDate(Constant.Constant.DATE_ENGLISH_YYYY_MM_DD)

        requestReleaseReminder(context, intent, dateToday, dateToday)
    }

    private fun requestReleaseReminder(
        context: Context,
        intent: Intent,
        dateGte: String,
        dateLte: String
    ) {

        val call = ApiService.getApiService.getReminderReleaseOrdinary(
            BuildConfig.TMDB_API_KEY,
            dateGte,
            dateLte
        )
        call.enqueue(object : Callback<BaseApiModel<List<Movie>>> {
            override fun onResponse(
                call: Call<BaseApiModel<List<Movie>>>,
                response: Response<BaseApiModel<List<Movie>>>
            ) {
                if (response.isSuccessful) {
                    if (response.body()?.results?.size != 0) {
                        getReleaseReminder(context, intent, response.body()?.results!!)
                    }
                }
            }

            override fun onFailure(call: Call<BaseApiModel<List<Movie>>>, t: Throwable) {}

        })
    }

    private fun getReleaseReminder(context: Context, intent: Intent, data: List<Movie>) {
        for (i in data.indices) {
            val textRelease =
                data[i].title + " " + context.getString(R.string.notification_release_today_text)
            showAlarmNotification(
                context,
                intent,
                TYPE_EXTRA_DAILY_RELEASE,
                textRelease,
                i
            )
        }
    }

}